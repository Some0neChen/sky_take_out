package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.SetmealServiceImpl;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 33-01-2023/11/13
 */
@Api(tags = "套餐相关接口")
@RestController(value = "adminSetmealController")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    SetmealServiceImpl service;


    @PostMapping()
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#{dto.categoryId}")
    public Result insert(@RequestBody SetmealDTO dto){
        return service.insert(dto);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> getPage(SetmealPageQueryDTO dto){
        return service.getPage(dto);
    }

    @ApiOperation("套餐起售、停售")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result changeStatus(Long id, @PathVariable Integer status){
        return service.changeStatus(id,status);
    }

    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectSetmealVoBtId(@PathVariable Long id){
        return service.selectSetmealVoBtId(id);
    }

    @ApiOperation("批量删除套餐")
    @DeleteMapping()
    public Result deleteSetmeal(@RequestParam List<Long> ids){
        return service.deleteSetmeal(ids);
    }

    @ApiOperation("修改套餐")
    @PutMapping()
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result updateSetmeal(@RequestBody SetmealDTO dto){
        return service.updateSetmeal(dto);
    }
}
