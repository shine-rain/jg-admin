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
@TableName("jg_role_auth")
public class JgRoleAuth implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String roleId;

    private String authType;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    private String dataAuthCollect;

    private String dashboardAuthCollect;

    private String menuAuthCollect;

    private String dataType;

}