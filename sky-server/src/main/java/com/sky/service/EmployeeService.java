package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     * @param dto
     */
    void add(EmployeeDTO dto);

    /**
     * 分页查询员工列表
     * @param dto
     * @return
     */
    Page<Employee> page(EmployeePageQueryDTO dto);

    /**
     * 修改员工状态
     * @param status
     * @param id
     */
    void status(Integer status, Long id);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 更新员工信息
     * @param dto
     */
    void update(EmployeeDTO dto);
}
