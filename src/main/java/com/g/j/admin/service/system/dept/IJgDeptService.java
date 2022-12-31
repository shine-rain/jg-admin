package com.g.j.admin.service.system.dept;

import com.g.j.admin.entity.system.dto.DeptAddModifyDto;
import com.g.j.admin.entity.system.dto.DeptQueryDto;
import com.g.j.admin.entity.system.po.JgDept;
import com.g.j.admin.entity.system.vo.DeptVo;
import com.g.j.admin.service.common.base.IBaseService;

import java.util.List;
import java.util.Map;

public interface IJgDeptService extends IBaseService<JgDept> {

    /**
     * 查询部门树形结构
     * @param deptQueryDto 查询条件
     * @return 树形结构数据
     */
    List<DeptVo> queryTreeDeptList(DeptQueryDto deptQueryDto);

    /**
     * 新增/修改部门
     * @param deptAddModifyDto 新增/修改数据
     * @return 结果返回
     */
    Boolean addModifyDept(DeptAddModifyDto deptAddModifyDto);

    /**
     * 删除部门
     * @param id 部门主键
     * @return 结果返回
     */
    Boolean deleteDept(String id);

    /**
     * 获取用户拥有数据权限的部门信息
     * @param userId 用户主键
     * @return 结果
     */
    Map<String, Object> getDeptAuthCodesByUserId(String userId);
}
