package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname DishFlavorMapper
 * @Description 菜品和风味的一对多关系
 * @Date 2024/2/24 20:19
 * @Created by Hygge
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入风味
     *
     * @param entities
     */
    void add(List<DishFlavor> entities);

    /**
     * 根据菜品ID删除所有的口味信息
     * @param id
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id=#{id}")
    void deleteByDishId(Integer id);
}
