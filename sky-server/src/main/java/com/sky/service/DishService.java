package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @Classname DishService
 * @Description 菜品的Service
 * @Date 2024/2/24 19:00
 * @Created by Hygge
 */
public interface DishService {
    /**
     * 添加菜品
     *
     * @param dto
     */
    void add(DishDTO dto);

    /**
     * 分页查询菜品结果
     *
     * @param dto
     * @return
     */
    PageResult page(DishPageQueryDTO dto);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void delete(List<Integer> ids);
}
