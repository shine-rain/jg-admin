package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.system.po.JgDictItem;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
public class DictItemAddModifyDto implements Serializable {

    private String id;
    private String dictId;

    private String name;

    private String code;

    private Boolean bitEnable;

    public JgDictItem toJgDictItem() {
        JgDictItem jgDictItem = new JgDictItem();
        jgDictItem.setId(this.id).setDictId(this.dictId).setItemCode(this.code)
                .setItemName(this.name).setBitEnable(this.bitEnable);
        return jgDictItem;
    }

}
