package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname SetmealDishMapper
 * @Description 套餐和菜品的中间表
 * @Date 2024/2/24 20:58
 * @Created by Hygge
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品的Id批量获取对应的套餐ID
     *
     * @param ids
     * @return
     */
    List<Integer> getSetmealIdsByDishIds(List<Long> ids);

}
