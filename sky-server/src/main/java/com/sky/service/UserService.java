package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.result.Result;
import com.sky.vo.UserLoginVO;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 38-17-2023/11/30
 */
public interface UserService extends IService<User> {
    User login(UserLoginDTO dto);
}
