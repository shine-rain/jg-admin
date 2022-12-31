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
@TableName("jg_menu")
public class JgMenu implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String menuName;

    private String pid;

    private String menuType;

    private String funIdentification;

    private String alias;

    private String menuIcon;

    private String routeAddr;

    private String redirect;

    private String menuHighlightAddr;

    private String view;

    private String color;

    private Boolean bitHide;

    private Boolean bitFullPageRoute;

    private String tag;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    private String pidLevelCollect;

    private Integer level;

    private Integer sort;

}