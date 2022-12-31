package com.g.j.admin.entity.system.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jg_dept")
public class JgDept implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 父级主键
     */
    private String pid;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 排序号
     */
    private Integer sortNum;

    /**
     * 是否禁用
     */
    private Boolean bitDisable;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态
     */
    private String status;

    /**
     * 层级标识
     */
    private Integer level;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    /**
     * 父级主键 拼接字符串
     */
    private String pidLevelCollect;

    /**
     * 部门编码
     */
    private String deptCode;
    /**
     * code 拼接
     */
    private String codeLevelCollect;

}