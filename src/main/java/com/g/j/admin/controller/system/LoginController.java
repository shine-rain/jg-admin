package com.g.j.admin.controller.system;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.system.dto.LoginDto;
import com.g.j.admin.entity.system.vo.LoginVo;
import com.g.j.admin.service.system.login.ILoginService;
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
@RequestMapping("/system/")
public class LoginController extends BaseController {

    @Autowired
    private ILoginService iLoginService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public SaResult doLogin(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = iLoginService.doLogin(loginDto);
        return SaResult.data(loginVo);
    }

    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.data(StpUtil.isLogin());
    }

    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }
}
