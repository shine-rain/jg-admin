package com.g.j.admin.service.system.bridge;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.entity.system.po.JgDept;
import com.g.j.admin.service.system.user.IJgAdminService;
import com.g.j.admin.config.ApplicationContextHolder;
import com.g.j.admin.entity.system.po.JgAdmin;
import com.g.j.admin.service.system.dept.IJgDeptService;
import com.g.j.admin.service.system.role.IJgRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Service
public class JgBridgeServiceImpl implements IJgBridgeService {

    @Override
    public List<String> getDeptIdsByPid(String deptId) {
        List<String> deptIds = new ArrayList<>();
        if (StringUtils.hasText(deptId)) {
            IJgDeptService iJgDeptService = ApplicationContextHolder.getBean(IJgDeptService.class);
            deptIds.add(deptId);
            LambdaQueryWrapper<JgDept> jgDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgDeptLambdaQueryWrapper.like(JgDept::getPidLevelCollect, deptId).select(JgDept::getId);

            List<String> ids = iJgDeptService.listObjs(jgDeptLambdaQueryWrapper, Object::toString);
            if (!CollectionUtils.isEmpty(ids)) {
                deptIds.addAll(ids);
            }
        }
        return deptIds;
    }

    @Override
    public List<String> getDeptCodesByCode(String code) {
        List<String> codeList = new ArrayList<>();
        if (StringUtils.hasText(code)) {
            IJgDeptService iJgDeptService = ApplicationContextHolder.getBean(IJgDeptService.class);
            codeList.add(code);
            LambdaQueryWrapper<JgDept> jgDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgDeptLambdaQueryWrapper.like(JgDept::getCodeLevelCollect, code).select(JgDept::getDeptCode);
            List<String> codes = iJgDeptService.listObjs(jgDeptLambdaQueryWrapper, Object::toString);
            if (!CollectionUtils.isEmpty(codes)) {
                codeList.addAll(codes);
            }
        }
        return codeList;
    }

    @Override
    public List<String> getUserIdsByDeptCode(List<String> deptCodes) {
        IJgAdminService iJgAdminService = ApplicationContextHolder.getBean(IJgAdminService.class);
        LambdaQueryWrapper<JgAdmin> jgAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminLambdaQueryWrapper.in(JgAdmin::getDeptCode, deptCodes).select(JgAdmin::getId);
        return iJgAdminService.listObjs(jgAdminLambdaQueryWrapper, Object::toString);
    }

    @Override
    public String getCodeByDeptId(String deptId) {
        IJgDeptService iJgDeptService = ApplicationContextHolder.getBean(IJgDeptService.class);
        JgDept jgDept = iJgDeptService.getById(deptId);
        return jgDept.getDeptCode();
    }

    @Override
    public String getDeptNameByDeptId(String deptId) {
        IJgDeptService iJgDeptService = ApplicationContextHolder.getBean(IJgDeptService.class);
        JgDept jgDept = iJgDeptService.getById(deptId);
        return jgDept.getDeptName();
    }

    @Override
    public List<JgAdmin> getJgAdminListByDeptIds(List<String> deptIds) {
        List<JgAdmin> jgAdminList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(deptIds)) {
            IJgAdminService iJgAdminService = ApplicationContextHolder.getBean(IJgAdminService.class);

            LambdaQueryWrapper<JgAdmin> jgAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgAdminLambdaQueryWrapper.in(JgAdmin::getDeptId, deptIds).eq(JgAdmin::getBitDel, Boolean.FALSE);
            jgAdminList = iJgAdminService.list(jgAdminLambdaQueryWrapper);
        }
        return jgAdminList;
    }

    @Override
    public JgAdmin getJgAdminByUserId(String userId) {
        IJgAdminService iJgAdminService = ApplicationContextHolder.getBean(IJgAdminService.class);
        return iJgAdminService.getById(userId);
    }

    @Override
    public List<String> getDataAuthTypeByUserId(String userId) {
        IJgRoleService iJgRoleService = ApplicationContextHolder.getBean(IJgRoleService.class);
        return iJgRoleService.getDataAuthTypeByUserId(userId);
    }

    @Override
    public List<String> getChoiceDeptDataAuthByUserId(String userId) {
        IJgRoleService iJgRoleService = ApplicationContextHolder.getBean(IJgRoleService.class);
        return iJgRoleService.getChoiceDeptDataAuthByUserId(userId);
    }
}
