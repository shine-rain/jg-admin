package com.g.j.admin.service.system.role;

import com.g.j.admin.entity.system.dto.RoleAuthAddModifyDto;
import com.g.j.admin.entity.system.po.JgRoleAuth;
import com.g.j.admin.entity.system.vo.RoleAuthVo;
import com.g.j.admin.service.common.base.IBaseService;

public interface IJgRoleAuthService extends IBaseService<JgRoleAuth> {

    /**
     * 根据角色id获取权限列表
     * @param roleId 用户主键
     * @return 返回权限主键列表
     */
    RoleAuthVo getRoleAuthByRoleId(String roleId, String authType);

    /**
     * 新增角色权限关联关系
     * @param roleAuthAddModifyDto 关联数据
     * @return 结果
     */
    Boolean addRoleAuth(RoleAuthAddModifyDto roleAuthAddModifyDto);

}
