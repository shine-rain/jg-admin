package com.g.j.admin.controller.system.permission;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.system.vo.LoginPermissionVo;
import com.g.j.admin.service.system.menu.IJgMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/system/permission")
public class JgPermissionController  extends BaseController {

    @Autowired
    private IJgMenuService iJgMenuService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public SaResult queryLoginAllPermission() {
        LoginPermissionVo loginPermissionVo = iJgMenuService.queryLoginAllPermission();
        return SaResult.data(loginPermissionVo);
    }
}
