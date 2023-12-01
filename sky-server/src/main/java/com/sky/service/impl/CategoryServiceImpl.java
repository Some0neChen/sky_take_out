package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 25-05-2023/11/10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper mapper;

    //分页查询
    public PageResult getPage(CategoryPageQueryDTO pageQueryDTO) {
        IPage<Category> iPage = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        mapper.selectPageByName(iPage, pageQueryDTO.getName(), pageQueryDTO.getType());
        System.out.println(iPage.getRecords());
        return new PageResult(iPage.getTotal(), iPage.getRecords());
    }

    //启用、禁用分类
    public Result openAndClose(Integer status, Long id) {
        Category category = mapper.selectById(id);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        mapper.updateById(category);
        return Result.success();
    }


    public Result save(CategoryDTO categoryDTO) {
        System.out.println(categoryDTO);
        //对象属性拷贝
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(0);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        this.save(category);
        return Result.success();
    }


    /*修改分类*/
    public Result update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        mapper.updateById(category);
        return Result.success();
    }

    /*根据类型查询分类*/
    public Result<List<Category>> listByType(Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, 1);
        List<Category> categories = mapper.selectList(wrapper);
        return Result.success(categories);
    }

    /*根据id删除分类*/
    public Result deleteCategory(Long id) {
        mapper.deleteById(id);
        return Result.success();
    }
}
