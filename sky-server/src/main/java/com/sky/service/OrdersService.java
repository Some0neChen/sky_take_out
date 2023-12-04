package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderSubmitVO;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 23-01-2023/12/5
 */
public interface OrdersService extends IService<Orders> {
    OrderSubmitVO userSubmit(OrdersSubmitDTO dto);
}
