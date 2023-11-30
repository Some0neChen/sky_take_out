package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
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

    //根据套餐id查询包含的菜品
    List<DishItemVO> getDishItemVOBySetmealId(@Param("SetmealId") Integer SetmealId);
}
