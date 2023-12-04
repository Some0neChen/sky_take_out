package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 58-03-2023/12/4
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    ShoppingCartMapper mapper;
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealMapper setmealMapper;

    @Override
    @CacheEvict(cacheNames = "cacheShoppingCart_", key = "#userId")
    /*添加购物车*/
    public void addShoppingCart(ShoppingCartDTO dto, long userId) {
        ShoppingCart cart = new ShoppingCart();
        BeanUtils.copyProperties(dto, cart);
        cart.setUserId(userId);
        List<ShoppingCart> cartList = mapper.selectShoppingCart(cart);

        if (!cartList.isEmpty()) {
            cart = cartList.get(0);
            cart.setNumber(cart.getNumber() + 1);
            this.updateById(cartList.get(0));
        } else {
            if (cart.getSetmealId() != null) {
                Setmeal setmeal = setmealMapper.selectById(cart.getSetmealId());
                cart.setName(setmeal.getName());
                cart.setImage(setmeal.getImage());
                cart.setAmount(setmeal.getPrice());
            } else {
                Dish dish = dishMapper.selectById(cart.getDishId());
                cart.setName(dish.getName());
                cart.setAmount(dish.getPrice());
                cart.setImage(dish.getImage());
            }
            cart.setNumber(1);
            cart.setCreateTime(LocalDateTime.now());
        }

        mapper.insert(cart);
    }

    @Override
    @Cacheable(cacheNames = "cacheShoppingCart_", key = "#userId")
    /*展现购物车*/
    public List<ShoppingCart> listShoppingCart(long userId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        return mapper.selectList(wrapper);
    }

    @Override
    /*删除购物车中单个商品*/
    @CacheEvict(cacheNames = "cacheShoppingCart_", key = "#userId")
    public void deleteOneFromShoppingCart(ShoppingCartDTO dto, long userId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        if (dto.getDishId() != null) {
            wrapper.eq(ShoppingCart::getDishId, dto.getDishId());
        } else {
            wrapper.eq(ShoppingCart::getSetmealId, dto.getSetmealId());
        }
        if (dto.getDishFlavor() != null) {
            wrapper.eq(ShoppingCart::getDishFlavor, dto.getDishFlavor());
        }
        mapper.delete(wrapper);
    }

    @Override
    @CacheEvict(cacheNames = "cacheShoppingCart_",key = "#userId")
    public void cleanShoppingCart(Long userId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        mapper.delete(wrapper);
    }
}
