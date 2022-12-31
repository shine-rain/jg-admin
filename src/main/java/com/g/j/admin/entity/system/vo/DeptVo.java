package com.g.j.admin.entity.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.g.j.admin.entity.system.po.JgDept;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 返回前端部门数据对象
 */
@Data
public class DeptVo implements Serializable {

    private String id;

    private String label;

    private String parentId;

    private String deptCode;

    private String parentCode;

    private Integer sort;

    private Integer status;

    private String remark;

    private Boolean bitDisable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    List<DeptVo> children;

    public DeptVo() {

    }
    public DeptVo(JgDept jgDept) {
        this.id = jgDept.getId();
        this.label = jgDept.getDeptName();
        this.bitDisable = jgDept.getBitDisable();
        this.parentId = jgDept.getPid();
        this.sort = jgDept.getSortNum();
        this.status = Integer.valueOf(jgDept.getStatus());
        this.remark = jgDept.getRemarks();
        this.date = jgDept.getCreateDate();
        this.deptCode = jgDept.getDeptCode();
        this.parentCode = jgDept.getParentCode();
    }

}
