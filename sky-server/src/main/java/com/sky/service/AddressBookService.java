package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.AddressBook;
import com.sky.result.Result;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 16-23-2023/12/4
 */
public interface AddressBookService extends IService<AddressBook> {
    void addNewAddress(AddressBook addressBook, long userId);

    List<AddressBook> listAddressBook(long userId);

    AddressBook getDefaultAddress(long userId);

    void updateAddress(AddressBook addressBook, long userId);

    void deleteAddressById(Long id,long userId);

   AddressBook selectAddressById(Long id);

    void setDefaultAddress(AddressBook address,long userId);
}
