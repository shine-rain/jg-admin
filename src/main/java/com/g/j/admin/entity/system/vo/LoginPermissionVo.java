package com.g.j.admin.entity.system.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Data
@Accessors(chain = true)
public class LoginPermissionVo implements Serializable {

    private List<String> dashboardGrid;

    private List<MenuVo> menu;

    private List<String> permissions;

}
