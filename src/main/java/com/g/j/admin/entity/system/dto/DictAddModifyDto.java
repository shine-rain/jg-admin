package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.po.JgDict;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class DictAddModifyDto implements Serializable {

    private String id;
    private String name;

    private String code;

    private String parentId;

    public JgDict toJgDict() {
        JgDict jgDict = new JgDict();
        jgDict.setId(this.id).setDictCode(this.code).setDictName(this.name)
                .setParentId(this.parentId);
        return jgDict;
    }

}
