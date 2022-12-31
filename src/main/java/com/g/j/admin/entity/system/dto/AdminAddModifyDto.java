package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.po.JgAdmin;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class AdminAddModifyDto implements Serializable {

    private String id;

    private String avatar;

    private String name;

    private String sex;

    private String loginName;

    private String password;

    private String deptId;

    private List<String> group;

    public JgAdmin toJgAdmin() {
        JgAdmin jgAdmin = new JgAdmin();
        jgAdmin.setId(this.id).setAvatar(this.avatar).setRealName(this.name)
                .setLoginName(this.loginName).setPwd(this.password)
                .setDeptId(this.deptId).setSex(this.sex);
        return jgAdmin;
    }
}
