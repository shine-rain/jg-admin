package com.g.j.admin.entity.system.vo;

import com.g.j.admin.entity.system.po.JgAdmin;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class AdminVo implements Serializable {

    private String id;

    private String avatar;

    private String dept;

    private String deptName;

    private String sex;

    private Date date;

    private List<String> group;
    private String groupName;

    private String mail;

    private String name;

    private String loginName;

    public AdminVo() {

    }

    public AdminVo(JgAdmin jgAdmin) {
        this.id = jgAdmin.getId();
        this.avatar = jgAdmin.getAvatar();
        this.dept = jgAdmin.getDeptId();
        this.date = jgAdmin.getCreateDate();
        this.mail = jgAdmin.getEmail();
        this.name = jgAdmin.getRealName();
        this.loginName = jgAdmin.getLoginName();
        this.sex = jgAdmin.getSex();
    }
}
