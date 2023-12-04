package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailMapper;
import com.sky.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 33-01-2023/12/5
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
