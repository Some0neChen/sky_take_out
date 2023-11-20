package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 39-05-2023/11/12
 */
@Controller
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

    @Autowired
    SetmealDishMapper mapper;

    @Override
    public void removeBatchBySetmealId(List<Long> ids) {
        mapper.removeBatchBySetmealId(ids);
    }
}
