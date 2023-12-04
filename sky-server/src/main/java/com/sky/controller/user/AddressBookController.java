package com.sky.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
