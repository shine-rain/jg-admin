package com.g.j.admin.entity.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class LoginDto implements Serializable {

    private String userName;

    private String password;

}
