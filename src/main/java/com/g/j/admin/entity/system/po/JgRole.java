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
@TableName("jg_role")
public class JgRole implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String roleName;

    private String roleAlias;

    private Integer sortNum;

    private Boolean bitDisable;

    private String remarks;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    private String status;

    private String roleType;

}