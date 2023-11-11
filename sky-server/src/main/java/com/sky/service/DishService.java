package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.Result;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 03-19-2023/11/11
 */
public interface DishService extends IService<Dish> {
    void saveDish(DishDTO dto);
}
