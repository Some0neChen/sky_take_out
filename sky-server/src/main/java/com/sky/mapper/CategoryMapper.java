package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 22-05-2023/11/10
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    IPage<Category> selectPageByName(IPage<Category> iPage, @Param("name") String name, @Param("type") Integer type);
}
