package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

/**
 * @Classname ShoppingCartService
 * @Description 购物车Service
 * @Date 2024/2/26 12:22
 * @Created by Hygge
 */
public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
