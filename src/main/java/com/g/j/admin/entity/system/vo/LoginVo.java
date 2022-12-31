package com.g.j.admin.entity.system.vo;

import com.g.j.admin.entity.system.other.UserInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
@Accessors(chain = true)
public class LoginVo implements Serializable {

    private String token;

    private UserInfo userInfo;

}
