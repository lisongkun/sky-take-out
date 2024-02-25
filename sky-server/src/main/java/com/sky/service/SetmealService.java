package com.sky.service;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;

import java.util.List;

/**
 * @Classname SetmealService
 * @Description 套餐服务控制器
 * @Date 2024/2/25 14:24
 * @Created by Hygge
 */
public interface SetmealService {
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
