package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
