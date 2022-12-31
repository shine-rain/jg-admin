package com.g.j.admin.service.system.role;

import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.RoleAddModifyDto;
import com.g.j.admin.entity.system.dto.RoleQueryDto;
import com.g.j.admin.entity.system.po.JgRole;
import com.g.j.admin.entity.system.vo.RoleVo;
import com.g.j.admin.service.common.base.IBaseService;

import java.util.List;

public interface IJgRoleService extends IBaseService<JgRole> {

    /**
     * 查询角色分页数据
     * @param roleQueryDto 查询条件
     * @return 查询结果
     */
    BasePage<RoleVo> queryPageRoleList(RoleQueryDto roleQueryDto);

    /**
     * 新增修改角色
     * @param roleAddModifyDto 数据信息
     * @return 结果
     */
    Boolean addModifyRole(RoleAddModifyDto roleAddModifyDto);

    /**
     * 删除角色
     * @param id 主键
     * @return 结果
     */
    Boolean deleteRole(String id);

    /**
     * 根据用户主键查询角色信息
     * @param userId 用户主键
     * @return 结果
     */
    List<JgRole> getRoleByUserId(String userId);

    /**
     * 获取可查看数据权限的用户集
     * @return 结果
     */
    List<String> getDataAuthTypeByUserId(String userId);

    /**
     * 根据用户拥有的角色权限 查出手动选择的部门权限
     * @param userId 用户主键
     * @return 结果
     */
    List<String> getChoiceDeptDataAuthByUserId(String userId);
}
