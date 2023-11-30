package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.font.MultipleMaster;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 55-17-2023/11/11
 */
@RestController(value = "adminCommonController")
@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "通用接口，负责图片文件上传")
public class CommonController {
    @Autowired
    AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //获取原始文件名后缀
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            //用随机生成的UUID+原始文件后缀名的形式，来确保上传到阿里云的文件每个文件名都不重复
            String uploadFileName = UUID.randomUUID() + extension;
            String filePathURL = aliOssUtil.upload(file.getBytes(), uploadFileName);

            //将图片上传到阿里云后的URL返回给前端
            return Result.success(filePathURL);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e.toString());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
