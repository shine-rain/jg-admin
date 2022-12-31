package com.g.j.admin.service.common.sotoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.entity.system.po.JgMenu;
import com.g.j.admin.entity.system.po.JgRole;
import com.g.j.admin.entity.system.po.JgRoleAuth;
import com.g.j.admin.service.system.user.IJgAdminService;
import com.g.j.admin.constant.Const;
import com.g.j.admin.entity.system.po.JgAdmin;
import com.g.j.admin.service.system.menu.IJgMenuService;
import com.g.j.admin.service.system.role.IJgRoleAuthService;
import com.g.j.admin.service.system.role.IJgRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private IJgRoleService iJgRoleService;
    @Autowired
    private IJgRoleAuthService iJgRoleAuthService;
    @Autowired
    private IJgMenuService iJgMenuService;
    @Autowired
    private IJgAdminService iJgAdminService;

    public StpInterfaceImpl() {

    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        JgAdmin jgAdmin = iJgAdminService.getById(String.valueOf(loginId));
        if (Const.SUPER_ADMIN.equals(jgAdmin.getLoginName())) {
            // 超级管理员 默认赋值所有权限
            list = getAllPermission();
        } else {
            list =getAuthByUserId(String.valueOf(loginId));
        }
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        // 超级管理员
        JgAdmin jgAdmin = iJgAdminService.getById(String.valueOf(loginId));
        if (Const.SUPER_ADMIN.equals(jgAdmin.getLoginName())) {
            // 超级管理员
            list.add(Const.SUPER_ADMIN);
        } else {
            // 普通角色
            List<JgRole> jgRoleList = iJgRoleService.getRoleByUserId(String.valueOf(loginId));
            if (!CollectionUtils.isEmpty(jgRoleList)) {
                jgRoleList.forEach(jgRole -> {
                    list.add(jgRole.getRoleName());
                    if (!list.contains(Const.ADMIN) && Const.ADMIN.equals(jgRole.getRoleType())) {
                        list.add(Const.ADMIN);
                    }
                });
            }
        }
        return list;
    }

    private List<String> getAuthByUserId(String userId) {
        List<String> list = new ArrayList<>();
        // 根据userId获取角色
        List<JgRole> jgRoleList = iJgRoleService.getRoleByUserId(userId);
        if (!CollectionUtils.isEmpty(jgRoleList)) {
            List<String> rIds = new ArrayList<>();
            jgRoleList.forEach(jgRole -> {
                rIds.add(jgRole.getId());
            });
            // 根据角色获取权限
            LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgRoleAuthLambdaQueryWrapper.in(JgRoleAuth::getRoleId, rIds).select(JgRoleAuth::getMenuAuthCollect);
            List<String> authIds = iJgRoleAuthService.listObjs(jgRoleAuthLambdaQueryWrapper, Object::toString);
            if (!CollectionUtils.isEmpty(authIds)) {
                List<String> menuIds = new ArrayList<>();
                authIds.forEach(s -> {
                    List<String> mIds = JSONUtil.toList(s, String.class);
                    menuIds.addAll(mIds);
                });
                List<String> delDuplicate = menuIds.stream().distinct().collect(Collectors.toList());
                // 查询菜单权限
                List<JgMenu> jgMenuList = iJgMenuService.listByIds(delDuplicate);
                if (!CollectionUtils.isEmpty(jgMenuList)) {
                    jgMenuList.forEach(jgMenu -> {
                        list.add(jgMenu.getAlias());
                    });
                }
            }
        }
        return list;
    }

    private List<String> getAllPermission() {
        List<String> list = new ArrayList<>();
        // 查询所有菜单权限标识
        List<JgMenu> jgMenuList = iJgMenuService.list();
        if (!CollectionUtils.isEmpty(jgMenuList)) {
            jgMenuList.forEach(jgMenu -> {
                list.add(jgMenu.getAlias());
            });
        }
        return list;
    }
}
