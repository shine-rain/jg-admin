package com.g.j.admin.enums;

public enum AuthTypeEnum {
    /**
     * 菜单
     */
    MENU("menu", "菜单权限"),
    /**
     * 部门权限
     */
    DEPT("dept", "部门权限"),
    /**
     * 仪表盘权限
     */
    DASHBOARD("dashboard", "仪表盘权限");

    private String authType;
    private String desc;

    AuthTypeEnum(String authType, String desc) {
        this.authType = authType;
        this.desc = desc;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static AuthTypeEnum parse(String authType) {
        for (AuthTypeEnum authTypeEnum : AuthTypeEnum.values()) {
            if (authTypeEnum.getAuthType().equals(authType)) {
                return authTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.authType;
    }
}
