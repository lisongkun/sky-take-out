package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 向数据表中添加菜品
     *
     * @param dish
     */
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("INSERT INTO dish VALUES (null,#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill
    void add(Dish dish);
}
