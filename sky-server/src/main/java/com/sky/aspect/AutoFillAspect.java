package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.beancontext.BeanContext;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 03-23-2023/11/10
 */
/*自定义切面类，实现公共字段自动填充*/
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /*切入点*/
    @Pointcut("execution(* com.sky.service.impl.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /*前置通知，在通知中进行公共字段赋值*/
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("************开始进行公共字段填充************");

        //获取该方法是update方法还是insert方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = annotation.value();

        //获取拦截到的对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) return;//无对象返回
        Object entity = args[0];

        //准备赋值的数据
        LocalDateTime time = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        try {
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            setUpdateTime.invoke(entity, time);
            setUpdateUser.invoke(entity, currentId);
            //根据不同的方法赋予不同的值
            if (value == OperationType.INSERT) {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreatUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);

                setCreateTime.invoke(entity, time);
                setCreatUser.invoke(entity, currentId);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
