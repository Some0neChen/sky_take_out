package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 35-02-2023/11/21
 */
@RestController(value = "adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("设置店铺营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("店铺状态设置为：{}",status==1?"营业中":"打烊");

        //将营业状态以字符串键的形式存入到Redis中
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("SHOP_STATUS",status);

        return Result.success();
    }

    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到店铺的状态为：{}",shopStatus==1?"营业中":"打烊");
        return Result.success(shopStatus);
    }
}
