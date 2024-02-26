package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname DishController
 * @Description 菜品管理控制器
 * @Date 2024/2/24 18:57
 * @Created by Hygge
 */
@RestController
@Api(tags = "菜品管理")
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 添加菜品
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "菜品添加")
    @CacheEvict(cacheNames = "dishCache", key = "#dto.categoryId")
    @PostMapping
    public Result add(@RequestBody DishDTO dto) {
        dishService.add(dto);
        return Result.success();
    }

    /**
     * 分页查询菜品
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "分页查询菜品")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dto) {
        PageResult pageResult = dishService.page(dto);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除菜品")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        dishService.delete(ids);
        return Result.success();
    }

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);

        return Result.success();
    }

    /**
     * 根据ID获取菜品详细信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID获取菜品详细信息")
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable("id") Long id) {
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }


    @ApiOperation(value = "修改菜品信息")
    @PutMapping
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result update(@RequestBody DishDTO dto) {
        dishService.update(dto);
        return Result.success();
    }
}

