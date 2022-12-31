package com.g.j.admin.dao.system.dept;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.DeptQueryDto;
import com.g.j.admin.entity.system.po.JgDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JgDeptMapper extends BaseMapper<JgDept> {

    /**
     * 查询部门分页数据
     * @param deptQueryDto 查询条件
     * @return 树形结构数据
     */
    BasePage<JgDept> queryPageDeptList(BasePage<DeptQueryDto> page, @Param("data") DeptQueryDto deptQueryDto);

    /**
     * 查询树形结构数据
     * @param deptQueryDto 查询条件
     * @return 树形数据
     */
    List<JgDept> queryTreeDeptList(DeptQueryDto deptQueryDto);
}