package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.vo.UserLoginVO;

/**
 * @Classname UserService
 * @Description 用户服务
 * @Date 2024/2/25 13:40
 * @Created by Hygge
 */
public interface UserService {
    /**
     * 用户登录
     * @param dto
     * @return
     */
    UserLoginVO login(UserLoginDTO dto);
}
