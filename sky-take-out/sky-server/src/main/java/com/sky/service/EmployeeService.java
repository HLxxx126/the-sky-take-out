package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新しい従業員を追加する
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * ページング検索
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * アカウントの有効化/無効化
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * Idに基づいて従業員の情報を検索する
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 従業員の情報を修正する
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
