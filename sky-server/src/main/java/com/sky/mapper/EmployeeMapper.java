package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    /**
     * 添加员工信息到数据库
     *
     * @param employee
     */
    @AutoFill
    @Insert("INSERT INTO employee VALUES (null,#{name}, #{username}, #{password},#{phone},#{sex},#{idNumber}," +
            "#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void add(Employee employee);

    /**
     * 分页查询员工列表
     *
     * @param name
     * @return
     */
    Page<Employee> page(String name);

    /**
     * 更新员工实体信息
     *
     * @param employee
     */
    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据ID查询员工
     *
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
