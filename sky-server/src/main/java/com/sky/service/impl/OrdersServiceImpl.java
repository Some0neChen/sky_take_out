package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import com.sky.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 24-01-2023/12/5
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements OrdersService {
}
