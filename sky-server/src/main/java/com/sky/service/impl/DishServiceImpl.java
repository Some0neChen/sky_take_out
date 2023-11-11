package com.sky.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 04-19-2023/11/11
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishMapper mapper;
    @Autowired
    DishFlavorServiceImpl dishFlavorService;

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
            dishFlavorService.insertBatch(flavors);
        }
    }
}
