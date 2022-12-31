package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.po.JgDept;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门新增/编辑对象
 */
@Data
public class DeptAddModifyDto implements Serializable {

    private String id;

    private String label;

    private String parentId;

    private String parentCode;

    private String deptCode;

    private Integer sort;

    private Integer status;

    private String remark;

    private Integer level;

    public JgDept toJgDept() {
        JgDept jgDept = new JgDept();
        jgDept.setId(this.id).setDeptName(this.label)
                .setPid(this.parentId).setSortNum(this.sort)
                .setStatus(String.valueOf(this.status)).setDeptCode(this.deptCode)
                .setRemarks(this.remark).setParentCode(this.parentCode);
        return jgDept;
    }

}
