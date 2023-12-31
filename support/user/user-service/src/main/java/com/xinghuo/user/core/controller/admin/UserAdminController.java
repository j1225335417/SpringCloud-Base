package com.xinghuo.user.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinghuo.user.auth.AuthAdmin;
import com.xinghuo.user.core.model.dto.UserAdminQueryDTO;
import com.xinghuo.user.core.model.entity.User;
import com.xinghuo.user.core.model.vo.UserVO;
import com.xinghuo.user.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "（B端后管访问）用户管理相关接口")
@AuthAdmin
@RestController
@RequestMapping("admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    @ApiOperation("分页查询C端用户信息")
    @GetMapping("page")
    public IPage<UserVO> page(UserAdminQueryDTO userAdminQueryDTO) {
        return userService.pageUser(userAdminQueryDTO);
    }

    @ApiOperation("开启 禁用用户")
    @PutMapping("enable")
    public Boolean enable(Long userId, boolean isEnable) {
        userService.updateById(new User().setId(userId).setIsEnable(isEnable));
        return Boolean.TRUE;
    }
}


