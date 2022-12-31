package com.g.j.admin.controller.system.user;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.AdminAddModifyDto;
import com.g.j.admin.entity.system.dto.AdminQueryDto;
import com.g.j.admin.entity.system.dto.PwdUpdateDto;
import com.g.j.admin.entity.system.other.ArrParam;
import com.g.j.admin.entity.system.other.UserInfo;
import com.g.j.admin.entity.system.vo.AdminVo;
import com.g.j.admin.service.system.user.IJgAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/admin")
public class JgAdminController extends BaseController {

    @Autowired
    private IJgAdminService iJgAdminService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public SaResult queryPageAdminList(AdminQueryDto adminQueryDto) {
        BasePage<AdminVo> adminVoBasePage = iJgAdminService.queryPageAdminList(adminQueryDto);
        return SaResult.data(adminVoBasePage);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addModifyAdmin(@RequestBody AdminAddModifyDto adminAddModifyDto) {
        Boolean result = iJgAdminService.addModifyAdmin(adminAddModifyDto);
        return SaResult.data(result);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteAdmin(@PathVariable(value = "id") String id) {
        Boolean result = iJgAdminService.deleteAdmin(id);
        return SaResult.data(result);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = "application/json")
    public SaResult resetPwd(@RequestBody ArrParam arrParam) {
        Boolean result = iJgAdminService.resetPwd(arrParam);
        return SaResult.data(result);
    }

    @RequestMapping(value = "/center/update", method = RequestMethod.POST, produces = "application/json")
    public SaResult updateUserInfo(@RequestBody UserInfo userInfo) {
        Boolean result = iJgAdminService.updateUserInfo(userInfo);
        return SaResult.data(result);
    }

    @RequestMapping(value = "/pwd/update", method = RequestMethod.POST, produces = "application/json")
    public SaResult updateUserPwd(@RequestBody PwdUpdateDto pwdUpdateDto) {
        Boolean result = iJgAdminService.updateUserPwd(pwdUpdateDto);
        return SaResult.data(result);
    }

}
