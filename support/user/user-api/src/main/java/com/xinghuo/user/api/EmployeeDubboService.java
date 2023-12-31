package com.xinghuo.user.api;

import com.xinghuo.user.api.vo.EmployeeDO;

import java.util.List;

public interface EmployeeDubboService {

    EmployeeDO getEmployeeByUserId(Long userId);

    List<EmployeeDO> listEmployeeByUserId(List<Long> userId);

    List<EmployeeDO> listEmployeeByRoleId(Long roleId);
}
