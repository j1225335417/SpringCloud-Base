package com.xinghuo.user.core.dubbo;

import com.xinghuo.user.api.EmployeeDubboService;
import com.xinghuo.user.api.vo.EmployeeDO;
import com.xinghuo.user.core.service.EmployeeService;
import com.xinghuo.user.core.service.convert.EmployeeConvert;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService(timeout = 5000)
public class EmployeeDubboServiceImpl implements EmployeeDubboService {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private EmployeeConvert employeeConvert;

    @Override
    public EmployeeDO getEmployeeByUserId(Long userId) {
        return employeeConvert.toDo(employeeService.getEmployeeByUserId(userId));
    }

    @Override
    public List<EmployeeDO> listEmployeeByUserId(List<Long> userIdList) {
        return employeeService.listEmployeeByUserId(userIdList);
    }

    @Override
    public List<EmployeeDO> listEmployeeByRoleId(Long roleId) {
        return employeeService.listEmployeeByRoleId(roleId);
    }
}
