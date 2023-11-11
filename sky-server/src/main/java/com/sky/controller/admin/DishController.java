package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 52-18-2023/11/11
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品接口实现")
public class DishController {
    @Autowired
    DishServiceImpl dishService;

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> getPage(DishPageQueryDTO dto){
        return Result.success(dishService.getPage(dto));
    }

    @PostMapping()
    @ApiOperation("新增菜品")
    public Result saveDish(@RequestBody DishDTO dto){
        dishService.saveDish(dto);
        return Result.success();
    }
}
