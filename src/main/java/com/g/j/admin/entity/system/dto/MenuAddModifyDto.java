package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.other.ApiList;
import com.g.j.admin.entity.system.other.MenuMeta;
import com.g.j.admin.entity.system.po.JgMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class MenuAddModifyDto implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 父级主键
     */
    private String parentId;
    /**
     * 别名
     */
    private String name;
    /**
     * 路由 路径
     */
    private String path;
    /**
     * 组件 视图
     */
    private String component;
    /**
     * 功能标识
     */
    private String funIdentification;
    /**
     * 重定向
     */
    private String redirect;
    /**
     * 菜单高亮
     */
    private String active;
    /**
     * 接口权限列表
     */
    private List<ApiList> apiList;
    /**
     * 元数据
     */
    private MenuMeta meta;

    public MenuAddModifyDto() {

    }

    /**
     * 转菜单对象
     */
    public JgMenu toJgMenu() {
        JgMenu jgMenu = new JgMenu();
        jgMenu.setId(this.id).setPid(this.parentId).setAlias(this.name).setRouteAddr(this.path)
                .setView(this.component).setRedirect(this.redirect).setMenuHighlightAddr(this.active)
                .setMenuName(this.meta.getTitle()).setMenuIcon(this.meta.getIcon())
                .setMenuType(this.meta.getType()).setColor(this.meta.getColor())
                .setBitHide(this.meta.getHidden()).setBitFullPageRoute(this.meta.getFullPage())
                .setTag(this.meta.getTag()).setFunIdentification(this.funIdentification);

        return jgMenu;
    }
}
