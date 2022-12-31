package com.g.j.admin.entity.system.dto;

import com.g.j.admin.entity.common.RequestPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminQueryDto extends RequestPage implements Serializable {

    private String adminName;

    private String deptId;

    private List<String> deptIds;
}
