package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname ShopController
 * @Description 门店状态控制器
 * @Date 2024/2/25 10:34
 * @Created by Hygge
 */
@Api(tags = "店铺状态管理")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取营业状态
     *
     * @return
     */
    @ApiOperation(value = "获取营业状态")
    @GetMapping("/status")
    public Result getStatus() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer shopStatus = (Integer) valueOperations.get(MessageConstant.SHOP_STATUS);
        return Result.success(shopStatus == null ? 0 : shopStatus);
    }

    /**
     * 设置店铺营业状态
     *
     * @param status
     * @return
     */
    @ApiOperation(value = "设置营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable("status") Integer status) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(MessageConstant.SHOP_STATUS, status);
        return Result.success();
    }
}
