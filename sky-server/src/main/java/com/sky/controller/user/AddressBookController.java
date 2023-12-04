package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.impl.AddressBookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 19-23-2023/12/4
 */
@RestController(value = "userAddressBookController")
@Slf4j
@Api(tags = "地址簿接口")
@RequestMapping("/user/addressBook")
public class AddressBookController {
    @Autowired
    AddressBookServiceImpl bookService;

    @PostMapping()
    @ApiOperation("新增地址")
    public Result addNewAddress(@RequestBody AddressBook addressBook){
        bookService.addNewAddress(addressBook,getUserId());
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> listAddressBook(){
        List<AddressBook> list = bookService.listAddressBook(getUserId());
        return Result.success(list);
    }

    @ApiOperation("查询默认地址")
    @GetMapping("/default")
    public Result<AddressBook> getDefaultAddress(){
        return Result.success(bookService.getDefaultAddress(getUserId()));
    }

    @ApiOperation("根据id修改地址")
    @PutMapping()
    public Result updateAddress(@RequestBody AddressBook addressBook){
        bookService.updateAddress(addressBook,getUserId());
        return Result.success();
    }

    @DeleteMapping()
    @ApiOperation("根据id删除地址")
    public Result deleteAddressById(Long id){
        bookService.deleteAddressById(id,getUserId());
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> selectAddressById(@PathVariable Long id){
        return Result.success(bookService.selectAddressById(id));
    }

    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public void setDefaultAddress(@RequestBody AddressBook address){
        log.info("接收到的地址为：{}",address);
        bookService.setDefaultAddress(address,getUserId());
    }

    public long getUserId(){
        return BaseContext.getCurrentId();
    }
}
