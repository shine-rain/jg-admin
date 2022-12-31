package com.g.j.admin.service.system.bridge;

import com.g.j.admin.entity.system.po.JgAdmin;

import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IJgBridgeService {

    /**
     * 根据部门id查询所有子级主键
     * @param deptId 部门id
     * @return id列表
     */
    List<String> getDeptIdsByPid(String deptId);

    /**
     * 根据部门code查询所有子级编码
     * @param code 部门code
     * @return code列表
     */
    List<String> getDeptCodesByCode(String code);

    /**
     * 根据部门编码查询所有用户id
     * @param deptCodes 部门编码
     * @return 结果
     */
    List<String> getUserIdsByDeptCode(List<String> deptCodes);

    /**
     * 根据部门主键 获取编码
     * @param deptId 主键
     * @return 结果
     */
    String getCodeByDeptId(String deptId);

    /**
     * 根据部门主键 获取部门名称
     * @param deptId 主键
     * @return 结果
     */
    String getDeptNameByDeptId(String deptId);

    /**
     * 根据部门id查询用户信息
     * @param deptIds 部门id
     * @return 查询结果
     */
    List<JgAdmin> getJgAdminListByDeptIds(List<String> deptIds);

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 结果
     */
    JgAdmin getJgAdminByUserId(String userId);

    /**
     * 根据用户id获取数据权限标识
     * @param userId 用户id
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
