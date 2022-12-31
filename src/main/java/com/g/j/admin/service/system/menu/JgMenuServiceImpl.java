package com.g.j.admin.service.system.menu;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.constant.Const;
import com.g.j.admin.dao.system.menu.JgMenuMapper;
import com.g.j.admin.entity.system.dto.MenuAddModifyDto;
import com.g.j.admin.entity.system.po.JgAdminRole;
import com.g.j.admin.entity.system.po.JgMenu;
import com.g.j.admin.entity.system.po.JgRoleAuth;
import com.g.j.admin.entity.system.vo.MenuVo;
import com.g.j.admin.enums.MenuTypeEnum;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.entity.system.vo.LoginPermissionVo;
import com.g.j.admin.enums.AuthTypeEnum;
import com.g.j.admin.exception.AdminException;
import com.g.j.admin.service.system.role.IJgRoleAuthService;
import com.g.j.admin.service.system.user.IJgAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Service
public class JgMenuServiceImpl extends BaseServiceImpl<JgMenuMapper, JgMenu> implements IJgMenuService {

    @Autowired
    private IJgAdminRoleService iJgAdminRoleService;
    @Autowired
    private IJgRoleAuthService iJgRoleAuthService;

    @Override
    public LoginPermissionVo queryLoginAllPermission() {
        LoginPermissionVo loginPermissionVo = new LoginPermissionVo();
        // 菜单权限
        List<MenuVo> menuVos;
        List<String> dashboardGrid;
        List<String> permissions;
        // 面板权限 TODO
        dashboardGrid = new ArrayList<>(Arrays.asList("welcome", "ver",
                "time", "progress", "echarts", "about"));
        // 菜单权限
        menuVos = queryAuthTreeMenu();
        // 按钮权限 TODO
        permissions = queryPermissionList();
        permissions.removeAll(Collections.singleton(null));
        loginPermissionVo.setDashboardGrid(dashboardGrid).setMenu(menuVos)
                .setPermissions(permissions);
        return loginPermissionVo;
    }

    public List<String> queryPermissionList() {
        if(StpUtil.hasRole(Const.SUPER_ADMIN)) {
            return queryAllPermission();
        }
        return queryAuthPermission();
    }

    public List<String> queryAllPermission() {
        List<String> menuVos = new ArrayList<>();
        LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgMenuLambdaQueryWrapper.orderByAsc(JgMenu::getSort);
        List<JgMenu> jgMenuList = list(jgMenuLambdaQueryWrapper);
        // 转树形结构数据
        if (!CollectionUtils.isEmpty(jgMenuList)) {
            jgMenuList.forEach(jgMenu -> {
                menuVos.add(jgMenu.getFunIdentification());
            });

        }
        return menuVos;
    }
    public List<MenuVo> queryAllMenu() {
        List<MenuVo> menuVos = new ArrayList<>();
        LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgMenuLambdaQueryWrapper.orderByAsc(JgMenu::getSort);
        List<JgMenu> jgMenuList = list(jgMenuLambdaQueryWrapper);
        // 转树形结构数据
        if (!CollectionUtils.isEmpty(jgMenuList)) {
            List<MenuVo> menuVoList = new ArrayList<>();
            jgMenuList.forEach(jgMenu -> {
                menuVoList.add(new MenuVo(jgMenu));
            });
            menuVos = getChildListTree(menuVoList, "parentId",0, "id",
                    "children", MenuVo.class, 10);
        }
        return menuVos;
    }

    private List<String> queryAuthPermission() {
        List<String> menuVoList = new ArrayList<>();
        // 查询用户角色
        LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getUserId, StpUtil.getLoginId()).select(JgAdminRole::getRoleId);
        List<String> jgAdminRoleList = iJgAdminRoleService.listObjs(jgAdminRoleLambdaQueryWrapper, Object::toString);
        // 查询角色关联菜单权限
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.in(JgRoleAuth::getRoleId, jgAdminRoleList).eq(JgRoleAuth::getAuthType, AuthTypeEnum.MENU)
                .select(JgRoleAuth::getMenuAuthCollect);
        List<String> jgRoleAuthList = iJgRoleAuthService.listObjs(jgRoleAuthLambdaQueryWrapper, Object::toString);
        // 菜单权限树形结构
        if (!CollectionUtils.isEmpty(jgRoleAuthList)) {
            List<String> menuIds= new ArrayList<>();
            jgRoleAuthList.forEach(s -> {
                List<String> tempIds = JSONUtil.toList(s, String.class);
                menuIds.addAll(tempIds);
            });
            LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgMenuLambdaQueryWrapper.in(JgMenu::getId, menuIds).orderByAsc(JgMenu::getSort);
            // 根据权限查询菜单列表
            List<JgMenu> jgMenuList = list(jgMenuLambdaQueryWrapper);
            if (!CollectionUtils.isEmpty(jgMenuList)) {
                jgMenuList.forEach(jgMenu -> {
                    menuVoList.add(jgMenu.getFunIdentification());
                });
            }
        }
        return menuVoList;
    }

    private List<MenuVo> queryAuthMenu() {
        List<MenuVo> menuVoList = new ArrayList<>();
        // 查询用户角色
        LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getUserId, StpUtil.getLoginId()).select(JgAdminRole::getRoleId);
        List<String> jgAdminRoleList = iJgAdminRoleService.listObjs(jgAdminRoleLambdaQueryWrapper, Object::toString);
        // 查询角色关联菜单权限
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.in(JgRoleAuth::getRoleId, jgAdminRoleList).eq(JgRoleAuth::getAuthType, AuthTypeEnum.MENU)
                .select(JgRoleAuth::getMenuAuthCollect);
        List<String> jgRoleAuthList = iJgRoleAuthService.listObjs(jgRoleAuthLambdaQueryWrapper, Object::toString);
        // 菜单权限树形结构
        if (!CollectionUtils.isEmpty(jgRoleAuthList)) {
            List<String> menuIds= new ArrayList<>();
            jgRoleAuthList.forEach(s -> {
                List<String> tempIds = JSONUtil.toList(s, String.class);
                menuIds.addAll(tempIds);
            });
            LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgMenuLambdaQueryWrapper.in(JgMenu::getId, menuIds).orderByAsc(JgMenu::getSort);
            // 根据权限查询菜单列表
            List<JgMenu> jgMenuList = list(jgMenuLambdaQueryWrapper);
            if (!CollectionUtils.isEmpty(jgMenuList)) {
                List<MenuVo> menuVos = new ArrayList<>();
                jgMenuList.forEach(jgMenu -> {
                    menuVos.add(new MenuVo(jgMenu));
                });
                menuVoList = getChildListTree(menuVos, "parentId",0, "id",
                        "children", MenuVo.class, 10);
            }
        }
        return menuVoList;
    }

    @Override
    public List<MenuVo> queryAuthTreeMenu() {
        if(StpUtil.hasRole(Const.SUPER_ADMIN)) {
            return queryAllMenu();
        }
        return queryAuthMenu();
    }

    @Override
    public String addModifyMenu(MenuAddModifyDto menuAddModifyDto) {
        JgMenu jgMenu = menuAddModifyDto.toJgMenu();
        // 修改操作
        if (StringUtils.hasText(jgMenu.getId())) {
            // 菜单进行名称检测
            if (MenuTypeEnum.MENU.getMenuType().equals(jgMenu.getMenuType())) {
                // 修改操作
                JgMenu jgMenuId = getById(jgMenu.getId());
                LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
                jgMenuLambdaQueryWrapper.eq(JgMenu::getMenuName, jgMenu.getMenuName())
                        .eq(JgMenu::getLevel, jgMenuId.getLevel()).ne(JgMenu::getId, jgMenu.getId());
                Long c = count(jgMenuLambdaQueryWrapper);
                if (c.compareTo(0L) > 0) {
                    throw new AdminException("同级菜单名称已存在！");
                }
            }
        } else {
            // 新增操作
            if (StringUtils.hasText(jgMenu.getPid())) {
                // 子菜单添加
                JgMenu jgMenuId = getById(jgMenu.getPid());
                // 所属层级
                jgMenu.setLevel(jgMenuId.getLevel() + 1);
                // 拼接主键
                if (StringUtils.hasText(jgMenuId.getPidLevelCollect())) {
                    jgMenu.setPidLevelCollect(jgMenuId.getPidLevelCollect() +","+ jgMenu.getPid());
                } else {
                    jgMenu.setPidLevelCollect(jgMenu.getPid());
                }
                if (MenuTypeEnum.MENU.getMenuType().equals(jgMenu.getMenuType())) {
                    // 判断是否名字重复
                    LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    jgMenuLambdaQueryWrapper.eq(JgMenu::getMenuName, jgMenu.getMenuName()).eq(JgMenu::getLevel, jgMenu.getLevel());
                    Long c = count(jgMenuLambdaQueryWrapper);
                    if (c.compareTo(0L) > 0) {
                        throw new AdminException("同级菜单名称已存在！");
                    }
                }
            } else {
                // 根级菜单添加
                jgMenu.setLevel(0);
                jgMenu.setPid(String.valueOf(0));
                if (MenuTypeEnum.MENU.getMenuType().equals(jgMenu.getMenuType())) {
                    LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    jgMenuLambdaQueryWrapper.eq(JgMenu::getMenuName, jgMenu.getMenuName()).eq(JgMenu::getLevel, jgMenu.getLevel());
                    Long c = count(jgMenuLambdaQueryWrapper);
                    if (c.compareTo(0L) > 0) {
                        throw new AdminException("同级菜单名称已存在！");
                    }
                }
            }
        }
        saveOrUpdate(jgMenu);
        return jgMenu.getId();
    }

    @Transactional
    @Override
    public Boolean deleteMenu(String id) {
        LambdaQueryWrapper<JgMenu> jgMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgMenuLambdaQueryWrapper.like(StringUtils.hasText(id), JgMenu::getPidLevelCollect, id).select(JgMenu::getId);
        List<String> mIds = listObjs(jgMenuLambdaQueryWrapper, Object::toString);
        mIds.add(id);
        return removeBatchByIds(mIds);
    }

}
