package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 04-19-2023/11/11
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishMapper mapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;

    /*分页查询*/
    public PageResult getPage(DishPageQueryDTO dto) {
        IPage<DishVO> page = new Page<>(dto.getPage(), dto.getPageSize());
        mapper.selectListPage(page, dto);
        List<DishVO> records = page.getRecords();
        records.removeIf(e -> e.getId() == null);
        System.out.println("records = " + records);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /*添加新菜品*/
    public void saveDish(DishDTO dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        mapper.saveDish(dish);

        List<DishFlavor> flavors = dto.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /*启用或停售菜品*/
    @Override
    @AutoFill(value = OperationType.UPDATE)
    public Result useOrStop(Dish dish,Integer status) {
        dish.setStatus(status);
        this.updateById(dish);
        return Result.success();
    }

    /*删除菜品*/
    @Override
    @Transactional
    public Result delete(List<Long> ids) {
        log.info("删除菜品{}",ids);
        /*for (Long id : ids) {
            Dish dish = this.getById(id);

            //启售菜品不可删
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE))
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);

            //套餐内菜品不可删
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getDishId, dish.getId());
            if (setmealDishMapper.selectCount(setmealDishLambdaQueryWrapper) > 0)
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

            //将味道下的相关列表一并删除
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
            dishFlavorMapper.remove(dishFlavorLambdaQueryWrapper);

            //最后删除菜品
            mapper.deleteById(dish);
        }*/

        //启售菜品不可删
        if(mapper.selectOnShopCount(ids)>0)
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);

        //套餐内菜品不可删
        if(setmealDishMapper.selectCountOnSetmeal(ids)>0)
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);;

        //将味道下的相关列表一并删除
        dishFlavorMapper.removeDishFlavorByDishId(ids);

        //最后删除菜品
        mapper.deleteBatchIds(ids);

        return Result.success();
    }

    /*修改菜品页面的数据回显*/
    @Override
    public Result<DishVO> selectOneDish(Integer id) {
        DishVO dish = mapper.selectOneDish(id);
        log.info("要取的菜品id为：{}，菜品为：{}",id,dish);
        return Result.success(dish);
    }

    /*修改菜品*/
    @Override
    public Result updateDish(DishDTO dishDTO) {
        //修改菜品信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        updateSetTime(dish);//设置更新时间
        log.info("修改的数据为：{}",dish);
        mapper.updateById(dish);

        //若Flavor不为空，对DishFlavor表进行操作，先删再添
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && !flavors.isEmpty()){
            //先删
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishDTO.getId());
            dishFlavorMapper.delete(dishFlavorLambdaQueryWrapper);

            //再添
            Long dishId = dish.getId();
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(dishDTO.getFlavors());
        }

        return Result.success();
    }

    /*插入数据时自动设置创建及修改时间*/
    @AutoFill(value = OperationType.INSERT)
    public void insertSetTime(Object object){
    }

    /*修改数据时自动设置修改时间*/
    @AutoFill(OperationType.UPDATE)
    public void updateSetTime(Dish dish){
    }

    @Override
    public Result<List<Dish>> getDishesByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId,categoryId);
        List<Dish> dishes = mapper.selectList(wrapper);
        return Result.success(dishes);
    }

    //用户端

    //根据分类id查询菜品
    @Override
    public Result<List<DishVO>> selectDishVOByCategoryId(Integer categoryId) {
        List<DishVO> dishVOS = mapper.selectDishVOByCategoryId(categoryId);
        return Result.success(dishVOS);
    }
}
