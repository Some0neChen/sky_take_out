package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 00-19-2023/11/11
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
