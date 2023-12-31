package com.xinghuo.user.core.dubbo;

import com.xinghuo.framework.common.model.UserLoginInfo;
import com.xinghuo.user.api.UserDubboService;
import com.xinghuo.user.api.dto.ListUserDTO;
import com.xinghuo.user.api.vo.EmployeeDO;
import com.xinghuo.user.api.vo.UserDO;
import com.xinghuo.user.api.vo.UserOverviewDO;
import com.xinghuo.user.core.manager.PermissionManager;
import com.xinghuo.user.core.manager.TokenManager;
import com.xinghuo.user.core.model.entity.Employee;
import com.xinghuo.user.core.service.EmployeeService;
import com.xinghuo.user.core.service.UserService;
import com.xinghuo.user.core.service.UserSystemRelationService;
import com.xinghuo.user.core.service.convert.EmployeeConvert;
import com.xinghuo.user.core.service.convert.UserConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@DubboService(timeout = 5000)
public class UserDubboServiceImpl implements UserDubboService {

    @Resource
    private UserService userService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private TokenManager tokenManager;

    @Resource
    private UserSystemRelationService userSystemRelationService;

    @Resource
    private EmployeeConvert employeeConvert;

    @Resource
    private PermissionManager permissionManager;

    @Resource
    private UserConvert userConvert;

    @Override
    public UserDO getUserDetailInfoById(Long id) {
        UserDO userDO = userConvert.toDo(userService.getUser(id));
        log.info("获取用户信息：{}", userDO);
        EmployeeDO employeeDO = employeeConvert.toDo(employeeService.getEmployeeByUserId(id));
        userDO.setEmployeeDO(employeeDO);
        return userDO;
    }

    @Override
    public List<UserDO> listUserByIds(List<Long> userIds) {
        List<UserDO> userListDO = userService.listUserByIds(userIds);
        if (userListDO.isEmpty()) {
            return Collections.emptyList();
        }
        List<Employee> employees = employeeService.getEmployeeByUserIds(userIds);
        if (employees.isEmpty()) {
            return userListDO;
        }
        List<EmployeeDO> employeeListDO = employeeConvert.toDo(employees);
        Map<Long, EmployeeDO> employeeMap = employeeListDO.stream().collect(Collectors.toMap(EmployeeDO::getUserId, Function.identity()));
        for (UserDO userDO : userListDO) {
            userDO.setEmployeeDO(employeeMap.get(userDO.getId()));
        }
        return userListDO;
    }

    @Override
    public UserDO getUserByPhone(String phone) {
        return userService.getUserByPhone(phone);
    }

    @Override
    public List<UserDO> listUser(ListUserDTO listUserDTO) {
        return userService.listUser(listUserDTO);
    }

    @Override
    public UserLoginInfo checkUserLoginInfo(String token) {
        UserLoginInfo userLoginInfo = tokenManager.checkUserLoginInfo(token);
        log.info("return user login info {} ", userLoginInfo);
        return userLoginInfo;
    }

    @Override
    public UserLoginInfo checkEmployeePermission(String token, Map<String, String> request) {
        UserLoginInfo userLoginInfo = tokenManager.checkUserLoginInfo(token);
        if (userLoginInfo == null) {
            return null;
        }
        permissionManager.checkEmployeePermission(userLoginInfo, request);
        return userLoginInfo;
    }

    @Override
    public List<Long> listUserId(Long systemId) {
        return userSystemRelationService.getUserIdList(systemId);
    }

    /**
     * 获取用户总览信息
     * @return UserOverviewDO
     */
    @Override
    public UserOverviewDO getUserOverview() {
        return userService.getUserOverview();
    }


    @Override
    public Boolean checkUserSystem( Long userId, Long systemId) {
        return userSystemRelationService.checkUserSystem(userId, systemId);
    }

}
