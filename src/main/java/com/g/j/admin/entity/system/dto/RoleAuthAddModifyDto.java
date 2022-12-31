package com.g.j.admin.entity.system.dto;

import cn.hutool.json.JSONUtil;
import com.g.j.admin.entity.system.po.JgRoleAuth;
import com.g.j.admin.enums.AuthTypeEnum;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class RoleAuthAddModifyDto implements Serializable {

    // 角色id
    private String roleId;

    // 数据权限类型 自己 本部门等等
    private String dataType;

    // 部门权限
    private List<String> deptIds;

    // 菜单权限
    private List<String> menuIds;

    // 面板权限
    private List<String> dashboardIds;

    public List<JgRoleAuth> toJgRoleAuthList() {
        List<JgRoleAuth> jgRoleAuthList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.menuIds)) {
            JgRoleAuth jgRoleMenuAuth = new JgRoleAuth();
            jgRoleMenuAuth.setRoleId(this.roleId).setAuthType(AuthTypeEnum.MENU.getAuthType())
                    .setMenuAuthCollect(JSONUtil.toJsonStr(this.menuIds));
            jgRoleAuthList.add(jgRoleMenuAuth);
        }

        if (StringUtils.hasText(this.dataType)) {
            JgRoleAuth jgRoleDeptAuth = new JgRoleAuth();
            jgRoleDeptAuth.setRoleId(this.roleId).setAuthType(AuthTypeEnum.DEPT.getAuthType())
                    .setDataType(dataType)
                    .setDataAuthCollect(JSONUtil.toJsonStr(this.deptIds));
            jgRoleAuthList.add(jgRoleDeptAuth);
        }

        if (!CollectionUtils.isEmpty(this.dashboardIds)) {
            JgRoleAuth jgRoleDashboardAuth = new JgRoleAuth();
            jgRoleDashboardAuth.setRoleId(this.roleId).setAuthType(AuthTypeEnum.DASHBOARD.getAuthType())
                    .setDashboardAuthCollect(JSONUtil.toJsonStr(this.dashboardIds));
            jgRoleAuthList.add(jgRoleDashboardAuth);
        }

        return jgRoleAuthList;
    }
}
