package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private SetmealMapper setmealMapper;

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
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    @Cacheable(cacheNames = "dishCache", key = "#dish.categoryId")
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = mapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getFlavorByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
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
    public void delete(List<Long> ids) {
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
        for (Long id :
                ids) {
            mapper.deleteById(id);
            // 删除菜品关联的口味信息
            dishFlavorMapper.deleteByDishId(id);
        }


    }

    /**
     * 根据ID获取菜品
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getDishById(Long id) {
        Dish dishById = mapper.getDishById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dishById, dishVO);
        // 根据dishId查询口味信息
        dishVO.setFlavors(dishFlavorMapper.getFlavorByDishId(id));
        return dishVO;
    }

    /**
     * 根据菜品信息
     *
     * @param dto
     */
    @Override
    public void update(DishDTO dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);

        //修改菜品表基本信息
        mapper.update(dish);

        //删除原有的口味数据
        dishFlavorMapper.deleteByDishId(dto.getId());

        //重新插入口味数据
        List<DishFlavor> flavors = dto.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dto.getId());
            });
            //向口味表插入n条数据
            dishFlavorMapper.add(flavors);
        }
    }

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     */
    @Transactional
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        mapper.update(dish);

        if (status == StatusConstant.DISABLE) {
            // 如果是停售操作，还需要将包含当前菜品的套餐也停售
            List<Long> dishIds = new ArrayList<>();
            dishIds.add(id);
            // select setmeal_id from setmeal_dish where dish_id in (?,?,?)
            List<Integer> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
            if (setmealIds != null && setmealIds.size() > 0) {
                for (Integer setmealId : setmealIds) {
                    Setmeal setmeal = Setmeal.builder()
                            .id(Long.valueOf(setmealId))
                            .status(StatusConstant.DISABLE)
                            .build();
                    setmealMapper.update(setmeal);
                }
            }
        }
    }
}
