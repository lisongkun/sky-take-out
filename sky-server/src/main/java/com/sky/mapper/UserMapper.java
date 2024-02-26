package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @Classname UserMapper
 * @Description 用户的mapper
 * @Date 2024/2/25 13:49
 * @Created by Hygge
 */
@Mapper
public interface UserMapper {
    /**
     * 根据OpenId查询用户
     * @param openid
     * @return
     */
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User getByOpenid(String openid);

    @Insert("INSERT INTO user (openid,create_time) VALUES (#{openid},#{createTime})")
    void insert(User user);

    /**
     * 根据动态条件统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
