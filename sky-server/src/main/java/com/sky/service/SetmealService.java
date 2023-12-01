package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 30-05-2023/11/12
 */
public interface SetmealService extends IService<Setmeal> {
    Result insert(SetmealDTO dto);

    Result<PageResult> getPage(SetmealPageQueryDTO dto);

    Result changeStatus(Long id, Integer status);

    @AutoFill(OperationType.UPDATE)
    void UpdateTime(Object o);

    Result<SetmealVO> selectSetmealVoBtId(Long id);

    Result deleteSetmeal(List<Long> ids);

    Result updateSetmeal(SetmealDTO dto);

    //用户端根据分类id查询套餐
    List<Setmeal> getSetmealByCategoryId(Integer categoryId);
}
