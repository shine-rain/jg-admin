package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.po.JgRole;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class RoleAddModifyDto implements Serializable {

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

    public JgRole toJgRole() {
        JgRole jgRole = new JgRole();
        jgRole.setRoleAlias(this.alias).setRoleName(this.label)
                .setRemarks(this.remark).setStatus(this.status)
                .setSortNum(this.sort).setId(this.id);
        return jgRole;
    }

}
