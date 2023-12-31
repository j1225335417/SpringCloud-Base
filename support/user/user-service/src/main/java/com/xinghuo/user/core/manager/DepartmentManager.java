package com.xinghuo.user.core.manager;

import com.xinghuo.framework.common.model.IDict;
import com.xinghuo.framework.web.result.BizAssert;
import com.xinghuo.user.core.model.dto.EmployeeToDepartmentRoleDTO;
import com.xinghuo.user.core.model.entity.EmployeeDepartment;
import com.xinghuo.user.core.model.entity.EmployeeRole;
import com.xinghuo.user.core.model.enums.EmployeeToDepartmentEnum;
import com.xinghuo.user.core.model.vo.RoleVO;
import com.xinghuo.user.core.service.DepartmentRoleService;
import com.xinghuo.user.core.service.EmployeeDepartmentService;
import com.xinghuo.user.core.service.EmployeeRoleService;
import com.xinghuo.user.core.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liao
 */
@Slf4j
@Component
public class DepartmentManager {


    @Resource
    private DepartmentRoleService departmentRoleService;

    @Resource
    private EmployeeRoleService employeeRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private EmployeeDepartmentService employeeDepartmentService;

    /**
     * 员工分配部门下的权限
     */
    @Transactional(rollbackFor = Exception.class)
    public void employeeDistributionDepartmentRole(Long employeeId, Long departmentId) {
        List<RoleVO> roleList = roleService.listRole(departmentId);
        List<Long> roleIds = roleList.stream().map(RoleVO::getId).collect(Collectors.toList());
        employeeRoleService.setRoleToEmployee(employeeId, roleIds);
    }

    /**
     * 员工分配到部门下
     */
    @Transactional(rollbackFor = Exception.class)
    public void employeeToDepartmentRole(Long employeeId, Long departmentId) {
        EmployeeDepartment employeeDepartment = employeeDepartmentService.getEmployeeDepartment(employeeId);
        if (Objects.nonNull(employeeDepartment)) {
            BizAssert.notTrue(employeeDepartment.getDepartmentId().equals(departmentId), "已经存在该部门中了");
            employeeDepartmentService.delete(Collections.singletonList(employeeDepartment.getId()));
        }
        employeeDepartment = new EmployeeDepartment()
                .setDepartmentId(departmentId)
                .setEmployeeId(employeeId);
        employeeDepartmentService.save(employeeDepartment);
        employeeDistributionDepartmentRole(employeeId, departmentId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void employeeToDepartmentRole(EmployeeToDepartmentRoleDTO employeeToDepartmentRoleDTO) {
        Long employeeId = employeeToDepartmentRoleDTO.getEmployeeId();
        Long departmentId = employeeToDepartmentRoleDTO.getDepartmentId();
        EmployeeToDepartmentEnum employeeToDepartmentEnum = IDict.getByCode(EmployeeToDepartmentEnum.class, employeeToDepartmentRoleDTO.getHandType());
        switch (employeeToDepartmentEnum) {
            case CLEAR_BEFORE_DEPARTMENT_ROLE:
                List<Long> roleId = departmentRoleService.listRoleId(departmentId);
                employeeRoleService.deleteEmployeeRole(employeeId, roleId);
                break;
            case CLEAR_HISTORY_ALL_ROLE:
                employeeRoleService.deleteEmployeeAllRole(employeeId);
                break;
            default:
                break;
        }
        employeeToDepartmentRole(employeeId, departmentId);
    }
}
