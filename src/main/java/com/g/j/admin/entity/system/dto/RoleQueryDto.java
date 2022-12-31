package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.common.RequestPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryDto extends RequestPage implements Serializable {

    /**
     * 查询条件
     */
    private String roleName;
}
