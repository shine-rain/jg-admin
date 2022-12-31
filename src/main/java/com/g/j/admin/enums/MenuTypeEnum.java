package com.g.j.admin.enums;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public enum MenuTypeEnum {
    /**
     * 菜单
     */
    MENU("menu", "菜单"),
    /**
     * iframe
     */
    IFRAME("iframe", "iframe"),
    /**
     * 按钮
     */
    BUTTON("button", "按钮"),
    /**
     * 外链
     */
    LINK("link", "外链");

    private String menuType;
    private String desc;

    MenuTypeEnum(String menuType, String desc) {
        this.menuType = menuType;
        this.desc = desc;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setAuthType(String menuType) {
        this.menuType = menuType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static MenuTypeEnum parse(String menuType) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.getMenuType().equals(menuType)) {
                return menuTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.menuType;
    }
}
