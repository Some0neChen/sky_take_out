package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 22-01-2023/12/5
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
