package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 45-22-2023/11/30
 */
@RestController(value = "userCategoryController")
@Slf4j
@Api(tags = "用户获取分类接口")
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl service;

    @GetMapping("/list")
    public Result<List<Category>> getCategories(Integer type){
        return service.listByType(type);
    }
}
