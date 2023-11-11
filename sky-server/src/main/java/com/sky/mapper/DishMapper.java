package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 00-19-2023/11/11
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    IPage<Dish> selectListPage(IPage<Dish> page, @Param("dto") DishPageQueryDTO dto);

    @AutoFill(value = OperationType.INSERT)
    void saveDish(@Param("dish") Dish dish);
}
