package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.SetmealDish;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 38-05-2023/11/12
 */
public interface SetmealDishService extends IService<SetmealDish> {
    void removeBatchBySetmealId(List<Long> ids);
}
