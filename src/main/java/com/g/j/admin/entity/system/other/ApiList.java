package com.g.j.admin.entity.system.other;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class ApiList implements Serializable {

    /**
     * 标识 类似 user.add
     */
    private String code;
    /**
     * 权限请求地址
     */
    private String url;
}
