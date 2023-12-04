package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 17-23-2023/12/4
 */
@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Autowired
    AddressBookMapper mapper;

    /*添加新地址*/
    @Override
    @CacheEvict(cacheNames = "cacheAddressBook_", key = "#userId")
    public void addNewAddress(AddressBook addressBook, long userId) {
        addressBook.setUserId(userId);
        mapper.insert(addressBook);
    }

    /*列出该用户的所有地址*/
    @Override
    @Cacheable(cacheNames = "cacheAddressBook_", key = "#userId")
    public List<AddressBook> listAddressBook(long userId) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId);
        return mapper.selectList(wrapper);
    }

    /*查询用户的默认地址*/
    @Override
    @Cacheable(cacheNames = "userDefaultAddress", key = "#userId")
    public AddressBook getDefaultAddress(long userId) {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId);
        wrapper.eq(AddressBook::getIsDefault, 1);
        return mapper.selectOne(wrapper);
    }

    /*更新地址*/
    @Override
    @CacheEvict(cacheNames = "cacheAddressBook_",key = "#userId")
    public void updateAddress(AddressBook addressBook, long userId) {
        addressBook.setUserId(userId);
        mapper.updateById(addressBook);
    }

    /*根据地址id删除地址*/
    @Override
    @CacheEvict(cacheNames = "cacheAddressBook_",key = "#userId")
    public void deleteAddressById(Long id,long userId) {
        mapper.deleteById(id);
    }

    @Override
    public AddressBook selectAddressById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    @CacheEvict(cacheNames = "userDefaultAddress", key = "#userId")
    public void setDefaultAddress(AddressBook address,long userId) {
        log.info("接收到的id为：{}",address.getId());
        //将原来的默认地址取消默认设置
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId,userId)
                .eq(AddressBook::getIsDefault,1)
                .set(AddressBook::getIsDefault,0);
        mapper.update(updateWrapper);

        //将id所指定的的地址设定为默认地址
        updateWrapper.clear();
        address.setIsDefault(1);
        mapper.updateById(address);
    }

}
