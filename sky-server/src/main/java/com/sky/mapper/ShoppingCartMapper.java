package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 56-03-2023/12/4
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    List<ShoppingCart> selectShoppingCart(@Param("cart")ShoppingCart cart);
}
