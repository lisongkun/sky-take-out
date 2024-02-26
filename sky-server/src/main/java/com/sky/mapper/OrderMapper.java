package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname OrderMapper
 * @Description 订单Mapper
 * @Date 2024/2/26 15:35
 * @Created by Hygge
 */
@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order
     */
    void insert(Orders order);
}
