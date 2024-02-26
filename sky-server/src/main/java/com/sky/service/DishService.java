package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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
    void delete(List<Long> ids);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据ID获取菜品
     *
     * @param id
     * @return
     */
    DishVO getDishById(Long id);

    /**
     * 根据菜品信息
     *
     * @param dto
     */
    void update(DishDTO dto);

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
