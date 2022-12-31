package com.g.j.admin.entity.system.other;

import com.g.j.admin.entity.system.po.JgAdmin;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private String userId;

    private String avatar;

    private String userName;

    private String realName;

    private String sex;

    private String perSignature;

    private String dashboard;

    private List<String> role;

    public JgAdmin toJgAdmin() {
        JgAdmin jgAdmin = new JgAdmin();
        jgAdmin.setSex(this.sex).setRealName(this.realName)
                .setPerSignature(this.perSignature);
        return jgAdmin;
    }
}
