package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname DishServiceImpl
 * @Description 菜品Service的实现类
 * @Date 2024/2/24 19:00
 * @Created by Hygge
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper mapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加菜品
     *
     * @param dto
     */
    @Override
    public void add(DishDTO dto) {
        Dish dish = new Dish();

        BeanUtils.copyProperties(dto, dish);

        mapper.add(dish);

        List<DishFlavor> flavors = dto.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors = flavors.stream().peek(i -> i.setDishId(dish.getId())).collect(Collectors.toList());

            dishFlavorMapper.add(flavors);
        }
    }

    /**
     * 分页查询菜品结果
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<DishVO> page = mapper.page(dto.getCategoryId(), dto.getName(), dto.getStatus());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(i -> {
            Dish dish = mapper.getDishById(i);
            if (dish.getStatus() == StatusConstant.ENABLE)
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        });
        List<Integer> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 删除菜品和对应的口味数据
        for (Integer id :
                ids) {
            mapper.deleteById(id);
            // 删除菜品关联的口味信息
            dishFlavorMapper.deleteByDishId(id);
        }


    }
}
