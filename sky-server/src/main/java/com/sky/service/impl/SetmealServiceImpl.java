package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 30-05-2023/11/12
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    SetmealMapper mapper;
    @Autowired
    SetmealDishServiceImpl setmealDishService;


    /*插入新套餐*/
    @Override
    public Result insert(SetmealDTO dto) {
        log.info("要插入的数据为：{}", dto);

        //先插入setmeal，并获得自主递增的主键
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        mapper.insertNew(setmeal);

        Long setmealId = setmeal.getId();

        //将套餐id赋予setmealDish，并批量添加
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
        setmealDishService.saveBatch(setmealDishes);

        return Result.success();
    }

    /*分页查询*/
    @Override
    public Result<PageResult> getPage(SetmealPageQueryDTO dto) {
        log.info("分页参数为：{}", dto);

        IPage<SetmealVO> page = new Page<>(dto.getPage(), dto.getPageSize());
        mapper.selectPageToVo(page, dto);

        log.info("取到的页内容为：{}", page.getRecords());

        PageResult pageResult = new PageResult(page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /*改变套餐的启售状态*/
    @Override
    public Result changeStatus(Long id, Integer status) {
        Setmeal setmeal = mapper.selectById(id);
        setmeal.setStatus(status);
        UpdateTime(setmeal);
        mapper.updateById(setmeal);
        return Result.success();
    }

    /*更新修改时间*/
    @Override
    @AutoFill(OperationType.UPDATE)
    public void UpdateTime(Object o) {
    }

    /*根据id查询套餐*/
    @Override
    public Result<SetmealVO> selectSetmealVoBtId(Long id) {
        SetmealVO setmealVOS = mapper.selectSetmealVoBtId(id);
        return Result.success(setmealVOS);
    }

    @Override
    public Result deleteSetmeal(List<Long> ids) {
        log.info("要批量删除的套餐id列表有：{}", ids);

        //查询批量要删除的套餐列表中正在启售套餐的数量
        //有启售中的套餐则不可删除
        int count = mapper.selectOnShopCount(ids);
        if (count > 0)
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);

        //先删除Setmeal_Dish中的全部相关数据
        setmealDishService.removeBatchBySetmealId(ids);

        //最后删除Setmeal中的数据
        mapper.deleteBatchIds(ids);

        return Result.success();
    }

    @Override
    public Result updateSetmeal(SetmealDTO dto) {
        //对setmeal进行修改
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        UpdateTime(setmeal);
        mapper.updateById(setmeal);

        //对setmeal_dish进行先删再改
        Long setmealId = setmeal.getId();
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);

        //如果修改里套餐内的菜品不为空，则进行逐个添加
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
            setmealDishService.saveBatch(setmealDishes);
        }

        return Result.success();
    }

    /*
     * 用户端根据分类id查询套餐
     * */
    @Override
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    public List<Setmeal> getSetmealByCategoryId(Integer categoryId) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Setmeal::getCategoryId, categoryId);
        wrapper.eq(Setmeal::getStatus, 1);
        List<Setmeal> list = this.list(wrapper);
        return list;
    }
}
