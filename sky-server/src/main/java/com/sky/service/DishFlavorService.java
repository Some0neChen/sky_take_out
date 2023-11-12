package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.DishFlavor;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 36-20-2023/11/11
 */
public interface DishFlavorService extends IService<DishFlavor> {
    public void insertBatch(List<DishFlavor> flavorList);
}
