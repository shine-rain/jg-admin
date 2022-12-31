package com.g.j.admin.service.system.role;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.dao.system.role.JgRoleAuthMapper;
import com.g.j.admin.entity.system.dto.RoleAuthAddModifyDto;
import com.g.j.admin.entity.system.po.JgRoleAuth;
import com.g.j.admin.entity.system.vo.RoleAuthVo;
import com.g.j.admin.enums.DataAuthTypeEnum;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.enums.AuthTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JgRoleAuthServiceImpl extends BaseServiceImpl<JgRoleAuthMapper, JgRoleAuth> implements IJgRoleAuthService {

    @Override
    public RoleAuthVo getRoleAuthByRoleId(String roleId, String authType) {
        RoleAuthVo roleAuthVo = new RoleAuthVo();
        roleAuthVo.setDataType(DataAuthTypeEnum.ALL.getAuthType()).setAuthIds(new ArrayList<>());
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.eq(JgRoleAuth::getRoleId, roleId).
                eq(JgRoleAuth::getAuthType, authType);
        // 查询关联权限信息
        JgRoleAuth jgRoleAuth = getOne(jgRoleAuthLambdaQueryWrapper);
        if (null != jgRoleAuth) {
            roleAuthVo.setDataType(jgRoleAuth.getDataType());
            // 菜单 和 数据、仪表盘 权限
            String ids = "";
            if (AuthTypeEnum.MENU.getAuthType().equals(authType)) {
                ids = jgRoleAuth.getMenuAuthCollect();
            }
            if (AuthTypeEnum.DEPT.getAuthType().equals(authType)) {
                ids = jgRoleAuth.getDataAuthCollect();
            }
            if (AuthTypeEnum.DASHBOARD.getAuthType().equals(authType)) {
                ids = jgRoleAuth.getDashboardAuthCollect();
            }
            List<String> authIds = JSONUtil.toList(ids, String.class);
            roleAuthVo.setAuthIds(authIds);
        }
        return roleAuthVo;
    }

    @Transactional
    @Override
    public Boolean addRoleAuth(RoleAuthAddModifyDto roleAuthAddModifyDto) {
        // 拼接数据
        List<JgRoleAuth> jgRoleAuths = roleAuthAddModifyDto.toJgRoleAuthList();
        // 删除数据
        LambdaQueryWrapper<JgRoleAuth> jgRoleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgRoleAuthLambdaQueryWrapper.eq(JgRoleAuth::getRoleId, roleAuthAddModifyDto.getRoleId());
        this.baseMapper.delete(jgRoleAuthLambdaQueryWrapper);
        //插入数据
        saveBatch(jgRoleAuths);
        return Boolean.TRUE;
    }

}
