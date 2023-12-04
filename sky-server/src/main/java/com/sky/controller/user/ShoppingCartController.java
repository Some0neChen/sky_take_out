package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.impl.ShoppingCartServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 56-03-2023/12/4
 */
@Api(tags = "购物车功能实现")
@RestController(value = "UserShoppingCartController")
@Slf4j
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartServiceImpl cartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO dto) {
        long userId = BaseContext.getCurrentId();
        cartService.addShoppingCart(dto, userId);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("展示购物车")
    public Result<List<ShoppingCart>> listShoppingCart() {
        long userId = BaseContext.getCurrentId();
        List<ShoppingCart> cartList = cartService.listShoppingCart(userId);
        return Result.success(cartList);
    }

    @PostMapping("/sub")
    @ApiOperation("删除购物车中一个商品")
    public Result deleteOneFromShoppingCart(@RequestBody ShoppingCartDTO dto) {
        long userId = BaseContext.getCurrentId();
        cartService.deleteOneFromShoppingCart(dto,userId);
        return Result.success();
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result cleanShoppingCart(){
        cartService.cleanShoppingCart(BaseContext.getCurrentId());
        return Result.success();
    }
}
