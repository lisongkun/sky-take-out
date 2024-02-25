package com.sky.controller.user;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ShopController
 * @Description 用户端 店铺状态控制器
 * @Date 2024/2/25 10:44
 * @Created by Hygge
 */
@Api(tags = "店铺状态管理")
@RequestMapping("/user/shop")
@RestController("userShopController")
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
}
