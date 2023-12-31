package com.xinghuo.user.core.service.convert;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinghuo.user.core.model.entity.RolePermission;
import com.xinghuo.user.core.model.vo.RolePermissionVO;
import com.xinghuo.user.core.model.dto.RolePermissionDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 系统角色权限表(RolePermission)表服务接口
 *
 * @author makejava
 * @date 2022-05-09 10:18:12
 */
@Mapper(componentModel = "spring")
public interface RolePermissionConvert {

    RolePermissionVO toVo(RolePermission rolePermission);

    List<RolePermissionVO> toVo(List<RolePermission> rolePermission);

    Page<RolePermissionVO> toVo(Page<RolePermission> rolePermission);

    RolePermission toDo(RolePermissionDTO rolePermissionDTO);
}

