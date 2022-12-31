package com.g.j.admin.controller.system.role;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.system.dto.RoleAuthAddModifyDto;
import com.g.j.admin.entity.system.vo.RoleAuthVo;
import com.g.j.admin.service.system.role.IJgRoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/system/role/auth")
public class JgRoleAuthController extends BaseController {

    @Autowired
    private IJgRoleAuthService iJgRoleAuthService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public SaResult getRoleAuthByRoleId(String roleId, String authType) {
        RoleAuthVo roleAuthVo = iJgRoleAuthService.getRoleAuthByRoleId(roleId, authType);
        return SaResult.data(roleAuthVo);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addRoleAuth(@RequestBody RoleAuthAddModifyDto roleAuthAddModifyDto) {
        Boolean res = iJgRoleAuthService.addRoleAuth(roleAuthAddModifyDto);
        return SaResult.data(res);
    }

}
