package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 36-05-2023/11/12
 */
@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
    int selectCountOnSetmeal(@Param("ids")List<Long> ids);

    void removeBatchBySetmealId(@Param("ids") List<Long> ids);
}
