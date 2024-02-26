package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 根据分类ID和状态查询菜品列表
     *
     * @param dish
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId} and status = #{status}")
    List<Dish> list(Dish dish);

    /**
     * 分页查询结果
     *
     * @param categoryId
     * @param name
     * @param status
     */
    Page<DishVO> page(Integer categoryId, String name, Integer status);

    /**
     * 根据ID获取Dish对象
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish getDishById(Long id);

    /**
     * 根据菜品ID删除菜品
     *
     * @param id
     */
    @Delete("DELETE FROM dish WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * 根据菜品信息
     *
     * @param dish
     */
    void update(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
}
