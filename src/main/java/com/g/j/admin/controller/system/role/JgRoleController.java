package com.g.j.admin.controller.system.role;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.RoleAddModifyDto;
import com.g.j.admin.entity.system.dto.RoleQueryDto;
import com.g.j.admin.entity.system.vo.RoleVo;
import com.g.j.admin.service.system.role.IJgRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class JgRoleController extends BaseController {

    @Autowired
    private IJgRoleService iJgRoleService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public SaResult queryPageRoleList(RoleQueryDto roleQueryDto) {
        BasePage<RoleVo> roleVoBasePage = iJgRoleService.queryPageRoleList(roleQueryDto);
        return SaResult.data(roleVoBasePage);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addModifyRole(@RequestBody RoleAddModifyDto roleAddModifyDto) {
        Boolean result = iJgRoleService.addModifyRole(roleAddModifyDto);
        return SaResult.data(result);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteRole(@PathVariable(value = "id") String id) {
        Boolean result = iJgRoleService.deleteRole(id);
        return SaResult.data(result);
    }
}
