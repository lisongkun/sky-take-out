package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation(value = "批量删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        dishService.delete(ids);
        return Result.success();
    }
}

