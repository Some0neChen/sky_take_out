package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Some0neChen
 * @version 1.0
 * creats 28-05-2023/11/12
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    /*插入新套餐*/
    @AutoFill(value = OperationType.INSERT)
    void insertNew(@Param("setmeal") Setmeal setmeal);

    IPage<SetmealVO> selectPageToVo(IPage<SetmealVO> page, @Param("dto") SetmealPageQueryDTO dto);

    SetmealVO selectSetmealVoBtId(@Param("id") Long id);

    int selectOnShopCount(List<Long> ids);
}
