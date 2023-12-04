package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 20-00-2023/12/1
 */
@Api(tags = "用户端菜品浏览接口")
@RestController(value = "userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishServiceImpl dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> selectDishVOByCategoryId(Integer categoryId) {
        //此处我们使用Redis缓存对这段代码进行改进
        //如果我们要查询的分类id所附属菜品已经存于Redis中，则直接从Redis中取数据
        //否则从数据库中取数据，并把数据缓存在Redis中

        //按约定规则构造Redis中的key
        String key = "Category_" + categoryId;

        //查询Redis中是否已经缓存该数据
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<DishVO> list = (List<DishVO>) valueOperations.get(key);
        if(list!=null && list.size()>0)return Result.success(list);

        //如果Redis中没有缓存，则从数据库中取数据并存入Redis中
        list = dishService.selectDishVOByCategoryId(categoryId);
        valueOperations.set(key,list);


        return Result.success(list);
    }
}
