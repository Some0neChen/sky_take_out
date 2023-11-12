package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 34-20-2023/11/11
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    void insertBatch(List<DishFlavor> flavorList);

    void removeDishFlavorByDishId(@Param("ids")List<Long> ids);

    List<DishFlavor> selectDishFlavorByDishId(@Param("dishId")Integer id);
}
