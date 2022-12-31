package com.g.j.admin.entity.system.vo;

import com.g.j.admin.entity.system.po.JgDict;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class DictVo implements Serializable {

    private String id;

    private String code;

    private String name;

    private String parentId;

    private List<DictVo> children;

    public DictVo() {

    }

    public DictVo(JgDict jgDict) {
        this.id = jgDict.getId();
        this.parentId = jgDict.getParentId();
        this.name = jgDict.getDictName();
        this.code = jgDict.getDictCode();
    }
}
