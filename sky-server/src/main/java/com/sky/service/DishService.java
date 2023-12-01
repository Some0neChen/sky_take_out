package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 03-19-2023/11/11
 */
public interface DishService extends IService<Dish> {
    void saveDish(DishDTO dto);

    Result useOrStop(Dish dish,Integer status);

    Result delete(List<Long> ids);

    Result<DishVO> selectOneDish(Integer id);

    Result updateDish(DishDTO dishDTO);

    public void updateSetTime(Dish dish);

    Result<List<Dish>> getDishesByCategoryId(Long categoryId);

    //用户端

    //根据分类id查询菜品
    List<DishVO> selectDishVOByCategoryId(Integer categoryId);
}
