package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 47-02-2023/11/21
 */
@RestController(value = "userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "用户端查询店铺营业状态")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到店铺的状态为：{}",shopStatus==1?"营业中":"打烊");
        return Result.success(shopStatus);
    }
}
