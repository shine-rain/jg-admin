package com.g.j.admin.service.system.login;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.entity.system.dto.LoginDto;
import com.g.j.admin.entity.system.other.UserInfo;
import com.g.j.admin.entity.system.po.JgAdmin;
import com.g.j.admin.entity.system.po.JgRole;
import com.g.j.admin.entity.system.vo.LoginVo;
import com.g.j.admin.service.system.role.IJgRoleService;
import com.g.j.admin.service.system.user.IJgAdminService;
import com.g.j.admin.exception.AdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IJgAdminService iJgAdminService;
    @Autowired
    private IJgRoleService iJgRoleService;

    @Override
    public LoginVo doLogin(LoginDto loginDto) {
        LoginVo loginVo = new LoginVo();
        LambdaQueryWrapper<JgAdmin> jgAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminLambdaQueryWrapper.eq(JgAdmin::getLoginName, loginDto.getUserName());
        JgAdmin jgAdmin = iJgAdminService.getOne(jgAdminLambdaQueryWrapper);
        if (null == jgAdmin) {
            throw new AdminException("用户名错误！");
        }
        if(jgAdmin.getLoginName().equals(loginDto.getUserName())
                && jgAdmin.getPwd().equals(loginDto.getPassword())) {
            StpUtil.login(jgAdmin.getId());
            List<JgRole> jgRoleList = iJgRoleService.getRoleByUserId(jgAdmin.getId());
            List<String> roleNames = new ArrayList<>();
            if (!CollectionUtils.isEmpty(jgRoleList)) {
                jgRoleList.forEach(jgRole -> {
                    roleNames.add(jgRole.getRoleName());
                });
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(jgAdmin.getId()).setUserName(loginDto.getUserName())
                            .setDashboard(String.valueOf(0)).setRole(roleNames)
                    .setAvatar(jgAdmin.getAvatar()).setRealName(jgAdmin.getRealName())
                    .setSex(jgAdmin.getSex()).setPerSignature(jgAdmin.getPerSignature());
            loginVo.setToken(StpUtil.getTokenValue()).setUserInfo(userInfo);
        } else {
            throw new AdminException("密码错误！");
        }
        return loginVo;
    }
}
