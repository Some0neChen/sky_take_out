package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 55-22-2023/11/10
 */
@Target(ElementType.METHOD)//在方法中使用
@Retention(RetentionPolicy.RUNTIME)//注解保存到class文件中，jvm加载class文件之后，仍然存在；
public @interface AutoFill {
    //数据库操作类型UPDATE、INSERT
    OperationType value();
}
