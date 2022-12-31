package com.g.j.admin.entity.system.vo;

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
public class MenuVo implements Serializable {

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
     * 重定向
     */
    private String redirect;
    /**
     * 菜单高亮
     */
    private String active;
    /**
     * 功能标识
     */
    private String funIdentification;
    /**
     * 接口权限列表
     */
    private List<ApiList> apiList;
    /**
     * 元数据
     */
    private MenuMeta meta;
    /**
     * 子菜单列表
     */
    private List<MenuVo> children;

    public MenuVo() {

    }

    /**
     * 菜单对象 转 菜单传输对象
     * @param menu
     */
    public MenuVo(JgMenu menu) {
        this.id = menu.getId();
        this.parentId = menu.getPid();
        this.name = menu.getAlias();
        this.path = menu.getRouteAddr();
        this.component = menu.getView();
        this.redirect = menu.getRedirect();
        this.active = menu.getMenuHighlightAddr();
        this.funIdentification = menu.getFunIdentification();

        this.meta = new MenuMeta();
        this.meta.setTitle(menu.getMenuName());
        this.meta.setIcon(menu.getMenuIcon());
        this.meta.setType(menu.getMenuType());
        this.meta.setColor(menu.getColor());
        this.meta.setHidden(menu.getBitHide());
        this.meta.setFullPage(menu.getBitFullPageRoute());
        this.meta.setTag(menu.getTag());
    }
}
