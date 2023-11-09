package com.sky.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 25-05-2023/11/10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> {
    @Autowired
    CategoryMapper mapper;

    //分页查询
    public PageResult getPage(CategoryPageQueryDTO pageQueryDTO) {
        IPage<Category> iPage = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        mapper.selectPageByName(iPage,pageQueryDTO.getName(),pageQueryDTO.getType());
        System.out.println(iPage.getRecords());
        return new PageResult(iPage.getTotal(),iPage.getRecords());
    }
}
