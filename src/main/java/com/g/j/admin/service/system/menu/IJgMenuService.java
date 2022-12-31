package com.g.j.admin.service.system.menu;

import com.g.j.admin.entity.system.dto.MenuAddModifyDto;
import com.g.j.admin.entity.system.po.JgMenu;
import com.g.j.admin.entity.system.vo.MenuVo;
import com.g.j.admin.entity.system.vo.LoginPermissionVo;
import com.g.j.admin.service.common.base.IBaseService;

import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IJgMenuService extends IBaseService<JgMenu> {

    /**
     * 获取登录人员拥有的权限：面板、菜单、操作
     * @return 返回菜单数据
     */
    LoginPermissionVo queryLoginAllPermission();

    /**
     * 获取登录用户拥有菜单权限树形信息
     * @return 权限过滤后的树形结构数据
     */
    List<MenuVo> queryAuthTreeMenu();

    /**
     * 新增修改菜单
     * @param menuAddModifyDto 新增修改数据
     * @return 操作结果
     */
    String addModifyMenu(MenuAddModifyDto menuAddModifyDto);

    /**
     * 删除菜单
     * @param id 主键
     * @return 操作结果
     */
    Boolean deleteMenu(String id);
}
