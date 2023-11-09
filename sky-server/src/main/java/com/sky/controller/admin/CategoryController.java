package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.impl.CategoryServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 27-05-2023/11/10
 */
//分类相关接口
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryServiceImpl service;

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> getPage(CategoryPageQueryDTO pageQueryDTO){
        return Result.success(service.getPage(pageQueryDTO));
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用、禁用分类")
    public Result openAndClose(@PathVariable Integer status,Long id){
        return service.openAndClose(status,id);
    }

    @ApiOperation("新增分类")
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO){
        return service.save(categoryDTO);
    }
}
