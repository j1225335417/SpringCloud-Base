package com.xinghuo.user.core.manager;

import com.xinghuo.framework.common.exception.BaseResultCodeEnum;
import com.xinghuo.framework.common.exception.BizException;
import com.xinghuo.framework.common.model.UserLoginInfo;
import com.xinghuo.framework.redis.RedisClient;
import com.xinghuo.framework.web.result.Assert;
import com.xinghuo.user.auth.JwtProperties;
import com.xinghuo.user.core.model.constant.RedisKeysConstants;
import com.xinghuo.user.core.model.entity.Permission;
import com.xinghuo.user.core.model.entity.RolePermission;
import com.xinghuo.user.core.model.vo.RoleVO;
import com.xinghuo.user.core.service.EmployeeRoleService;
import com.xinghuo.user.core.service.PermissionService;
import com.xinghuo.user.core.service.RolePermissionService;
import com.xinghuo.user.core.service.RoleService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class PermissionManager {

    @Resource
    private PermissionService permissionService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private EmployeeRoleService employeeRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private RedisClient redisClient;

    @Resource
    private JwtProperties jwtProperties;

    public void checkEmployeePermission(UserLoginInfo userLoginInfo, Map<String, String> request) {
        String uri = Optional.ofNullable(request.get("uri")).orElse("");
        String method = Optional.ofNullable(request.get("method")).orElse("");
        String service = Optional.ofNullable(request.get("service")).orElse("");

        List<Permission> permissions = redisClient.get(RedisKeysConstants.USER_PERMISSION_LIST.concat(userLoginInfo.getEmployeeId().toString()));

        String[] uris = permissions.stream()
                .filter(permission -> permission.getMethod().equalsIgnoreCase(method) && permission.getService().equalsIgnoreCase(service))
                .map(Permission::getUri).toArray(String[]::new);

        PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition(uris);
        List<String> matchingPatterns = patternsRequestCondition.getMatchingPatterns(uri);

        Assert.notTrue(matchingPatterns.isEmpty(), BaseResultCodeEnum.NO_OPERATE_PERMISSION);

        //如果uri直接等于 就返回
        Optional<String> matchingUri = matchingPatterns.stream().findFirst();
        if (matchingUri.isPresent() && matchingUri.get().equals(uri)) {
            return;
        }

        Permission permission = permissionService.getPermission(uri, method.toLowerCase());
        if (Objects.nonNull(permission)) {
            //uri 不等于并且传进来的uri 是存在的，说明是一个没有url参数的uri，
            throw new BizException(BaseResultCodeEnum.NO_OPERATE_PERMISSION);
        }
    }

    public void setEmployeePermissionToRedis(Long employeeId) {
        List<RoleVO> roleListVO = roleService.listEmployeeRole(employeeId);
        List<Long> roleIds = roleListVO.stream().map(RoleVO::getId).collect(Collectors.toList());

        List<RolePermission> rolePermissions = rolePermissionService.listRolePermission(roleIds);

        List<Long> permissionIdList = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        List<Permission> permissions = permissionService.listPermission(permissionIdList);
        List<Permission> sharePermission = permissionService.listPermission();
        if (permissions.isEmpty()) {
            permissions = sharePermission;
        } else {
            permissions.addAll(sharePermission);
        }
        redisClient.set(RedisKeysConstants.USER_PERMISSION_LIST.concat(employeeId.toString()), permissions, jwtProperties.getExpiration(), TimeUnit.MINUTES);
    }
}
