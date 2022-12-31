package com.g.j.admin.service.system.role;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.dao.system.role.JgRoleMapper;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.RoleAddModifyDto;
import com.g.j.admin.entity.system.dto.RoleQueryDto;
import com.g.j.admin.entity.system.po.JgAdminRole;
import com.g.j.admin.entity.system.po.JgRole;
import com.g.j.admin.entity.system.po.JgRoleAuth;
import com.g.j.admin.entity.system.vo.RoleVo;
import com.g.j.admin.enums.AuthTypeEnum;
import com.g.j.admin.enums.DataAuthTypeEnum;
import com.g.j.admin.exception.AdminException;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.service.system.user.IJgAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JgRoleServiceImpl extends BaseServiceImpl<JgRoleMapper, JgRole> implements IJgRoleService{

    @Autowired
    private IJgRoleAuthService iJgRoleAuthService;
    @Autowired
    private IJgAdminRoleService iJgAdminRoleService;

    @Override
    public BasePage<RoleVo> queryPageRoleList(RoleQueryDto roleQueryDto) {
        BasePage<RoleVo> roleVoBasePage = new BasePage<>();
        BasePage<JgRole> jgRoleBasePage = this.baseMapper.queryPageRoleList(roleQueryDto.getPageObj(), roleQueryDto);
        // 转换输出对象
        if (!CollectionUtils.isEmpty(jgRoleBasePage.getList())) {
            List<RoleVo> roleVoList = new ArrayList<>();
            jgRoleBasePage.getList().forEach(jgRole -> {
                roleVoList.add(new RoleVo(jgRole));
            });
            roleVoBasePage.setRows(roleVoList);
            roleVoBasePage.setTotal(jgRoleBasePage.getTotal());
            roleVoBasePage.setSize(jgRoleBasePage.getSize());
            roleVoBasePage.setPageNumber(jgRoleBasePage.getPageNumber());
        }
        return roleVoBasePage;
    }

    @Override
    public Boolean addModifyRole(RoleAddModifyDto roleAddModifyDto) {
        JgRole jgRole = roleAddModifyDto.toJgRole();
        LambdaQueryWrapper<JgRole> jgRoleLambdaQueryWrapper  = new LambdaQueryWrapper<>();
        jgRoleLambdaQueryWrapper.eq(JgRole::getRoleName, jgRole.getRoleName());
        if (StringUtils.hasText(jgRole.getId())) {
            // 修改操作排除本身
            jgRoleLambdaQueryWrapper.ne(JgRole::getId, jgRole.getId());
        }
        Long c = count(jgRoleLambdaQueryWrapper);
        if (c.compareTo(0L) > 0) {
            throw new AdminException("角色名称已存在！");
        }
        return saveOrUpdate(jgRole);
    }

    @Transactional
    @Override
    public Boolean deleteRole(String id) {
        removeById(id);
        // 删除角色权限关联
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.eq(JgRoleAuth::getRoleId, id);
        iJgRoleAuthService.remove(jgRoleAuthLambdaQueryWrapper);
        // 删除角色用户关联
        LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getRoleId, id);
        iJgAdminRoleService.remove(jgAdminRoleLambdaQueryWrapper);
        return Boolean.TRUE;
    }

    @Override
    public List<JgRole> getRoleByUserId(String userId) {
        List<JgRole> jgRoleList = new ArrayList<>();
        LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getUserId, userId).select(JgAdminRole::getRoleId);
        List<String> rIds = iJgAdminRoleService.listObjs(jgAdminRoleLambdaQueryWrapper,Object::toString);
        if (!CollectionUtils.isEmpty(rIds)) {
            LambdaQueryWrapper<JgRole> jgRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgRoleLambdaQueryWrapper.in(JgRole::getId, rIds);
            jgRoleList = list(jgRoleLambdaQueryWrapper);
        }
        return jgRoleList;
    }

    @Override
    public List<String> getDataAuthTypeByUserId(String userId) {
        List<String> dataType = new ArrayList<>();

        // 查询所拥有的角色
        List<String> roleIds = new ArrayList<>();
        List<JgRole> jgRoleList = getRoleByUserId(userId);
        jgRoleList.forEach(jgRole -> {
            roleIds.add(jgRole.getId());
        });

        // 根据角色查询拥有的数据权限
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.in(JgRoleAuth::getRoleId, roleIds).eq(JgRoleAuth::getAuthType, AuthTypeEnum.DEPT.getAuthType())
                .select(JgRoleAuth::getDataType);
        List<String> dataAuthList = iJgRoleAuthService.listObjs(jgRoleAuthLambdaQueryWrapper, Object::toString);
        if (dataAuthList.contains(DataAuthTypeEnum.CHOICE_DEPT.getAuthType())) {
            dataType.add(DataAuthTypeEnum.CHOICE_DEPT.getAuthType());
        }
        if (dataAuthList.contains(DataAuthTypeEnum.ALL.getAuthType())) {
            dataType.add(DataAuthTypeEnum.ALL.getAuthType());
            return dataType;
        }
        if (dataAuthList.contains(DataAuthTypeEnum.DEPT_SUB.getAuthType())) {
            dataType.add(DataAuthTypeEnum.DEPT_SUB.getAuthType());
            return dataType;
        }
        if (dataAuthList.contains(DataAuthTypeEnum.DEPT.getAuthType())) {
            dataType.add(DataAuthTypeEnum.DEPT.getAuthType());
            return dataType;
        }
        if (CollectionUtils.isEmpty(dataType)) {
            // 查看自己的数据
            dataType.add(DataAuthTypeEnum.SELF.getAuthType());
        }

        return dataType;
    }

    @Override
    public List<String> getChoiceDeptDataAuthByUserId(String userId) {
        List<String> deptIds = new ArrayList<>();
        List<String> roleIds = new ArrayList<>();
        List<JgRole> jgRoleList = getRoleByUserId(userId);
        jgRoleList.forEach(jgRole -> {
            roleIds.add(jgRole.getId());
        });
        // 根据角色查询拥有的数据权限
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.in(JgRoleAuth::getRoleId, roleIds).eq(JgRoleAuth::getAuthType, AuthTypeEnum.DEPT.getAuthType())
                .eq(JgRoleAuth::getDataType, DataAuthTypeEnum.CHOICE_DEPT.getAuthType())
                .select(JgRoleAuth::getDataAuthCollect);
        List<String> dataAuthList = iJgRoleAuthService.listObjs(jgRoleAuthLambdaQueryWrapper, Object::toString);
        if (!CollectionUtils.isEmpty(dataAuthList)) {
            dataAuthList.forEach(s -> {
                List<String> authIds = JSONUtil.toList(s, String.class);
                deptIds.addAll(authIds);
            });
        }
        return deptIds.stream().distinct().collect(Collectors.toList());
    }
}
