package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrdersMapper;
import com.sky.service.OrderDetailService;
import com.sky.service.OrdersService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 24-01-2023/12/5
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @Autowired
    ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    AddressBookServiceImpl addressBookService;


    @Override
    @Transactional
    public OrderSubmitVO userSubmit(OrdersSubmitDTO dto) {
        //1.处理购物车为空和地址簿为空异常
        AddressBook addressBook = addressBookService.selectAddressById(dto.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> cartList = shoppingCartService.listShoppingCart(userId);
        if (cartList.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2.封装Orders数据并SQL插入
        Orders orders = new Orders();
        BeanUtils.copyProperties(dto, orders);
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(System.currentTimeMillis() + "|" + userId);
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());

        ordersMapper.insert(orders);

        //3.封装n条OrderDetail数据并SQL插入
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart:cartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailService.saveBatch(orderDetailList);

        //删除购物车中所有数据
        shoppingCartService.cleanShoppingCart(userId);

        //返回结果
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();
    }
}
