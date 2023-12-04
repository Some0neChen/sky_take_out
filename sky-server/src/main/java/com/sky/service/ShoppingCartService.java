package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 57-03-2023/12/4
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    void addShoppingCart(ShoppingCartDTO dto,long userId);

    List<ShoppingCart> listShoppingCart(long userId);

    void deleteOneFromShoppingCart(ShoppingCartDTO dto, long userId);

    void cleanShoppingCart(Long userId);
}
