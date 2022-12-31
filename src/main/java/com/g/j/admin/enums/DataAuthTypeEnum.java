package com.g.j.admin.enums;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public enum DataAuthTypeEnum {
    ALL("all", "全部权限"),
    SELF("self", "本人可见"),
    DEPT("dept", "所在部门可见"),
    DEPT_SUB("dept_sub", "所在部门以及子级"),
    CHOICE_DEPT("choice_dept", "选择部门可见"),
    CUSTOM("custom", "自定义");

    private String authType;
    private String desc;

    DataAuthTypeEnum(String authType, String desc) {
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

    public static DataAuthTypeEnum parse(String authType) {
        for (DataAuthTypeEnum dataAuthTypeEnum : DataAuthTypeEnum.values()) {
            if (dataAuthTypeEnum.getAuthType().equals(authType)) {
                return dataAuthTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.authType;
    }
}
