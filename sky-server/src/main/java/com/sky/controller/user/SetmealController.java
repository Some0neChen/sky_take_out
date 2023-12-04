package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.impl.SetmealDishServiceImpl;
import com.sky.service.impl.SetmealServiceImpl;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 26-23-2023/11/30
 */
@Api(tags = "用户套餐浏览接口")
@RestController(value = "userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealServiceImpl setmealService;
    @Autowired
    SetmealDishServiceImpl setmealDishService;

    @ApiOperation("根据分类id查询套餐")
    @GetMapping("/list")
    public Result<List<Setmeal>> getSetmealByCategoryId(Integer categoryId){
        return Result.success(setmealService.getSetmealByCategoryId(categoryId));
    }

    @ApiOperation("根据套餐id查询包含的菜品")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getDishItemVOByCategoryId(@PathVariable(value = "id") Integer id){
        return setmealDishService.getDishItemVOByCategoryId(id);
    }
}
