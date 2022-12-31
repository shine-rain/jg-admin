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
@TableName("jg_admin")
public class JgAdmin implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 登录名称
     */
    private String loginName;
    /**
     * 用户编码
     */
    private String adminCode;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 性别
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 部门id
     */
    private String deptId;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    /**
     * 别称
     */
    private String alias;
    /**
     * 用户类型
     */
    private String adminType;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否删除
     */
    private Boolean bitDel;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 真是姓名
     */
    private String realName;
    /**
     * 部门编码
     */
    private String deptCode;
    /**
     * 个性签名
     */
    private String perSignature;

}