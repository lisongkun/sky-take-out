package com.sky.service;

import com.sky.dto.DishDTO;

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
}
