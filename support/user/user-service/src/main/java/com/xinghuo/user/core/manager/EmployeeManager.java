package com.xinghuo.user.core.manager;

import com.xinghuo.user.core.model.dto.EmployeeSaveDTO;
import com.xinghuo.user.core.model.dto.EmployeeToDepartmentRoleDTO;
import com.xinghuo.user.core.model.entity.Employee;
import com.xinghuo.user.core.model.enums.EmployeeToDepartmentEnum;
import com.xinghuo.user.core.service.EmployeeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class EmployeeManager {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private DepartmentManager departmentManager;

    @Transactional(rollbackFor = Exception.class)
    public Long saveEmployeeAndSetDepartment(EmployeeSaveDTO employeeDTO) {
        Employee employee = this.employeeService.save(employeeDTO);
        EmployeeToDepartmentRoleDTO employeeToDepartmentRoleDTO = new EmployeeToDepartmentRoleDTO();
        employeeToDepartmentRoleDTO.setEmployeeId(employee.getId())
                .setDepartmentId(employeeDTO.getDepartmentId())
                .setHandType(EmployeeToDepartmentEnum.NO_CLEAR.getCode());
        departmentManager.employeeToDepartmentRole(employeeToDepartmentRoleDTO);
        return employee.getId();
    }
}
