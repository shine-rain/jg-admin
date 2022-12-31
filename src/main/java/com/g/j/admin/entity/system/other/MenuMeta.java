package com.g.j.admin.entity.system.other;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class MenuMeta implements Serializable {

    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单类型
     */
    private String type;
    /**
     * 颜色
     */
    private String color;
    /**
     * 标签
     */
    private String tag;
    /**
     * 是否整页路由
     */
    private Boolean fullPage;
    /**
     * 是否隐藏
     */
    private Boolean hidden;

}
