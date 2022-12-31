package com.g.j.admin.entity.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.g.j.admin.entity.system.po.JgRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class RoleVo implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 别名
     */
    private String alias;
    /**
     * 名称
     */
    private String label;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    public RoleVo() {

    }

    public RoleVo(JgRole jgRole) {
        this.alias = jgRole.getRoleAlias();
        this.date = jgRole.getCreateDate();
        this.id = jgRole.getId();
        this.label = jgRole.getRoleName();
        this.remark = jgRole.getRemarks();
        this.sort = jgRole.getSortNum();
        this.status = jgRole.getStatus();
    }
}
