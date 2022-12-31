package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.common.RequestPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 部门查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeptQueryDto extends RequestPage implements Serializable {

    /**
     * 部门名称查询条件
     */
    private String deptName;

}
