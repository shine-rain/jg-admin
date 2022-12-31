package com.g.j.admin.service.system.user;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.AdminAddModifyDto;
import com.g.j.admin.entity.system.other.ArrParam;
import com.g.j.admin.entity.system.other.UserInfo;
import com.g.j.admin.entity.system.po.JgAdminRole;
import com.g.j.admin.entity.system.po.JgRole;
import com.g.j.admin.entity.system.vo.AdminVo;
import com.g.j.admin.constant.Const;
import com.g.j.admin.dao.system.admin.JgAdminMapper;
import com.g.j.admin.entity.system.dto.AdminQueryDto;
import com.g.j.admin.entity.system.dto.PwdUpdateDto;
import com.g.j.admin.entity.system.po.JgAdmin;
import com.g.j.admin.exception.AdminException;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.service.system.bridge.IJgBridgeService;
import com.g.j.admin.service.system.role.IJgRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class JgAdminServiceImpl extends BaseServiceImpl<JgAdminMapper, JgAdmin> implements IJgAdminService {

    @Autowired
    private IJgAdminRoleService iJgAdminRoleService;
    @Autowired
    private IJgRoleService iJgRoleService;
    @Autowired
    private IJgBridgeService iJgBridgeService;

    @Override
    public BasePage<AdminVo> queryPageAdminList(AdminQueryDto adminQueryDto) {
        BasePage<AdminVo> adminVoBasePage = new BasePage<>();
        // 部门
        List<String> deptIds = iJgBridgeService.getDeptIdsByPid(adminQueryDto.getDeptId());
        adminQueryDto.setDeptIds(deptIds);
        // 查询用户
        BasePage<JgAdmin> jgAdminBasePage = this.baseMapper.queryPageAdminList(adminQueryDto.getPageObj(), adminQueryDto);
        if (!CollectionUtils.isEmpty(jgAdminBasePage.getList())) {
            List<AdminVo> adminVoList = new ArrayList<>();
            jgAdminBasePage.getList().forEach(jgAdmin -> {
                AdminVo adminVo = new AdminVo(jgAdmin);
                // 部门名称
                String deptName = iJgBridgeService.getDeptNameByDeptId(adminVo.getDept());
                adminVo.setDeptName(deptName);
                // 角色名称
                LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getUserId, jgAdmin.getId()).select(JgAdminRole::getRoleId);
                List<String> rIds = iJgAdminRoleService.listObjs(jgAdminRoleLambdaQueryWrapper,Object::toString);
                if (!CollectionUtils.isEmpty(rIds)) {
                    adminVo.setGroup(rIds);
                    LambdaQueryWrapper<JgRole> jgRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    jgRoleLambdaQueryWrapper.in(JgRole::getId, rIds).select(JgRole::getRoleName);
                    List<String> jgRoles = iJgRoleService.listObjs(jgRoleLambdaQueryWrapper, Object::toString);
                    if (!CollectionUtils.isEmpty(jgRoles)) {
                        String roleName = String.join(",", jgRoles);
                        adminVo.setGroupName(roleName);
                    }
                }
                adminVoList.add(adminVo);
            });
            adminVoBasePage.setRows(adminVoList);
            adminVoBasePage.setTotal(jgAdminBasePage.getTotal());
            adminVoBasePage.setSize(jgAdminBasePage.getSize());
            adminVoBasePage.setPageNumber(jgAdminBasePage.getPageNumber());
        }
        return adminVoBasePage;
    }

    @Transactional
    @Override
    public Boolean addModifyAdmin(AdminAddModifyDto adminAddModifyDto) {
        JgAdmin jgAdmin = adminAddModifyDto.toJgAdmin();
        // 登录名是否重复
        LambdaQueryWrapper<JgAdmin> jgAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminLambdaQueryWrapper.eq(JgAdmin::getLoginName, jgAdmin.getLoginName()).eq(JgAdmin::getBitDel, false);
        if (StringUtils.hasText(jgAdmin.getId())) {
            // 编辑操作 排除本身
            jgAdminLambdaQueryWrapper.ne(JgAdmin::getId, jgAdmin.getId());
        } else {
            // 新增需要密码加密
            String pwd = DigestUtils.md5DigestAsHex(jgAdmin.getPwd().getBytes(StandardCharsets.UTF_8));
            jgAdmin.setPwd(pwd);
        }
        Long c = count(jgAdminLambdaQueryWrapper);
        if (c.compareTo(0L) > 0) {
            throw new AdminException("登录名已存在！");
        }
        // 获取部门编码
        String deptCode = iJgBridgeService.getCodeByDeptId(jgAdmin.getDeptId());
        jgAdmin.setDeptCode(deptCode);
        // 删除角色关联
        if (StringUtils.hasText(jgAdmin.getId())) {
            LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getUserId, jgAdmin.getId());
            iJgAdminRoleService.remove(jgAdminRoleLambdaQueryWrapper);
        }
        saveOrUpdate(jgAdmin);
        // 添加角色关联
        List<JgAdminRole> jgAdminRoleList = new ArrayList<>();
        adminAddModifyDto.getGroup().forEach(roleId -> {
            JgAdminRole jgAdminRole = new JgAdminRole();
            jgAdminRole.setUserId(jgAdmin.getId());
            jgAdminRole.setRoleId(roleId);

            jgAdminRoleList.add(jgAdminRole);
        });
        iJgAdminRoleService.saveBatch(jgAdminRoleList);
        return Boolean.TRUE;
    }

    @Transactional
    @Override
    public Boolean deleteAdmin(String id) {
        // 逻辑删除
        JgAdmin jgAdmin = new JgAdmin();
        jgAdmin.setId(id).setBitDel(true);
        updateById(jgAdmin);
        // 删除角色关联
        LambdaQueryWrapper<JgAdminRole> jgAdminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminRoleLambdaQueryWrapper.eq(JgAdminRole::getUserId, id);
        iJgAdminRoleService.remove(jgAdminRoleLambdaQueryWrapper);
        return Boolean.TRUE;
    }

    @Override
    public Boolean resetPwd(ArrParam arrParam) {
        LambdaQueryWrapper<JgAdmin> jgAdminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgAdminLambdaQueryWrapper.in(JgAdmin::getId, arrParam.getIds());
        JgAdmin jgAdmin = new JgAdmin();
        String pwd = DigestUtils.md5DigestAsHex(Const.DEFAULT_PWD.getBytes(StandardCharsets.UTF_8));
        jgAdmin.setPwd(pwd);
        update(jgAdmin, jgAdminLambdaQueryWrapper);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateUserInfo(UserInfo userInfo) {
        JgAdmin jgAdmin = userInfo.toJgAdmin();
        jgAdmin.setId(String.valueOf(StpUtil.getLoginId()));
        updateById(jgAdmin);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateUserPwd(PwdUpdateDto pwdUpdateDto) {
        if (StringUtils.hasText(pwdUpdateDto.getUserPassword())) {
            JgAdmin jgAdmin = getById(String.valueOf(StpUtil.getLoginId()));
            String pwd = DigestUtils.md5DigestAsHex(pwdUpdateDto.getUserPassword().getBytes(StandardCharsets.UTF_8));
            if (!jgAdmin.getPwd().equals(pwd)) {
                throw new AdminException("原密码错误！");
            }
            if (StringUtils.hasText(pwdUpdateDto.getNewPassword())) {
                String pwdNew = DigestUtils.md5DigestAsHex(pwdUpdateDto.getNewPassword().getBytes(StandardCharsets.UTF_8));
                JgAdmin jgAdminNew = new JgAdmin();
                jgAdminNew.setId(String.valueOf(StpUtil.getLoginId())).setPwd(pwdNew);
                updateById(jgAdminNew);
            }
        }
        return Boolean.TRUE;
    }

}
