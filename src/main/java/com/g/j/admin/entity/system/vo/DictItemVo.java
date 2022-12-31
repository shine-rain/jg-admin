package com.g.j.admin.entity.system.vo;

import com.g.j.admin.entity.system.po.JgDictItem;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class DictItemVo implements Serializable {

    private String id;

    private String name;

    private String dictId;

    private String code;

    private Boolean bitEnable;

    public DictItemVo(JgDictItem dictItem) {
        this.id = dictItem.getId();
        this.dictId = dictItem.getDictId();
        this.name = dictItem.getItemName();
        this.code = dictItem.getItemCode();
        this.bitEnable = dictItem.getBitEnable();
    }
}
