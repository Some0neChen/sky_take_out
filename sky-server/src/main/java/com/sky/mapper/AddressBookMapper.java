package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 14-23-2023/12/4
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
