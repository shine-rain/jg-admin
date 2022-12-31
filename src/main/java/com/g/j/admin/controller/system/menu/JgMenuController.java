package com.g.j.admin.controller.system.menu;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.system.dto.MenuAddModifyDto;
import com.g.j.admin.entity.system.vo.MenuVo;
import com.g.j.admin.service.system.menu.IJgMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class JgMenuController extends BaseController {

    @Autowired
    private IJgMenuService iJgMenuService;

    @RequestMapping(value = "auth", method = RequestMethod.GET, produces = "application/json")
    public SaResult queryAuthTreeMenu() {
        List<MenuVo> menuVoList = iJgMenuService.queryAuthTreeMenu();
        return SaResult.data(menuVoList);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addModifyMenu(@RequestBody MenuAddModifyDto menuAddModifyDto) {
        String result = iJgMenuService.addModifyMenu(menuAddModifyDto);
        return SaResult.data(result);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteMenu(@PathVariable(value = "id") String id) {
        Boolean result = iJgMenuService.deleteMenu(id);
        return SaResult.data(result);
    }
}
