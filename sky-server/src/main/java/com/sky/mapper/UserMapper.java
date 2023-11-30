package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 35-17-2023/11/30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int insertNewUser(User user);
}
