package com.g.j.admin.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Component
public class JgMetaObjectHandler implements MetaObjectHandler {

    private static final String FIELD_CREATE_TIME = "createDate";
    private static final String FIELD_UPDATE_TIME = "updateDate";
    private static final String FIELD_CREATE_USER = "createUserId";
    private static final String FIELD_UPDATE_USER = "updateUserId";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.strictInsertFill(metaObject, FIELD_CREATE_TIME, Date.class, date);
        if (StpUtil.getLoginId() != null) {
            this.strictInsertFill(metaObject, FIELD_CREATE_USER, String.class, String.valueOf(StpUtil.getLoginId()));
        }
    }


    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, FIELD_UPDATE_TIME, Date.class, new Date());
        if (StpUtil.getLoginId() != null) {
            this.strictUpdateFill(metaObject, FIELD_UPDATE_USER, String.class, String.valueOf(StpUtil.getLoginId()));
        }
    }
}
