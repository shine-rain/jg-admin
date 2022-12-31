package com.g.j.admin.service.system.dept;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.dao.system.dept.JgDeptMapper;
import com.g.j.admin.entity.system.dto.DeptAddModifyDto;
import com.g.j.admin.entity.system.dto.DeptQueryDto;
import com.g.j.admin.entity.system.po.JgDept;
import com.g.j.admin.entity.system.vo.DeptVo;
import com.g.j.admin.entity.system.po.JgAdmin;
import com.g.j.admin.enums.DataAuthTypeEnum;
import com.g.j.admin.exception.AdminException;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.service.system.bridge.IJgBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JgDeptServiceImpl extends BaseServiceImpl<JgDeptMapper, JgDept> implements IJgDeptService {

    @Autowired
    private IJgBridgeService iJgBridgeService;

    @Override
    public List<DeptVo> queryTreeDeptList(DeptQueryDto deptQueryDto) {
        List<DeptVo> deptVoList = new ArrayList<>();
        // 根据条件查询
        List<JgDept> jgDeptList = this.baseMapper.queryTreeDeptList(deptQueryDto);
        // 部门列表数据
        if (!CollectionUtils.isEmpty(jgDeptList)) {
            List<String> ids = new ArrayList<>();
            jgDeptList.forEach(jgDept -> {
                if (StringUtils.hasText(jgDept.getPidLevelCollect())) {
                    String[] pids = jgDept.getPidLevelCollect().split(",");
                    ids.addAll(Arrays.asList(pids));
                    ids.add(jgDept.getId());
                } else {
                    // 根级
                    if (jgDept.getPid().equals("0")) {
                        ids.add(jgDept.getId());
                    }
                }
            });
            // 查询所有部门数据
            List<String> tmpIds = ids.stream().distinct().collect(Collectors.toList());
            List<JgDept> jgDepts = listByIds(tmpIds);
            List<DeptVo> deptVos = new ArrayList<>();
            jgDepts.forEach(jgDept -> {
                deptVos.add(new DeptVo(jgDept));
            });
            // 转树形结构数据
            deptVoList = getChildListTree(deptVos, "parentId",0, "id",
                    "children", DeptVo.class, 10);
        }
        return deptVoList;
    }

    @Override
    public Boolean addModifyDept(DeptAddModifyDto deptAddModifyDto) {
        JgDept jgDept = deptAddModifyDto.toJgDept();
        if (StringUtils.hasText(jgDept.getPid())) {
            // 处理 层级 父级编码 主键以及编码的拼接操作
            // 存在父级 非根级
            if (!jgDept.getPid().equals("0")) {
                JgDept jgDeptPid = getById(jgDept.getPid());
                jgDept.setLevel(jgDeptPid.getLevel() + 1);
                jgDept.setParentCode(jgDeptPid.getDeptCode());
                if (StringUtils.hasText(jgDeptPid.getPidLevelCollect())) {
                    jgDept.setPidLevelCollect(jgDeptPid.getPidLevelCollect()+","+jgDept.getPid());
                    jgDept.setCodeLevelCollect(jgDeptPid.getCodeLevelCollect()+","+jgDept.getParentCode());
                } else {
                    jgDept.setPidLevelCollect(jgDept.getPid());
                    jgDept.setCodeLevelCollect(jgDept.getParentCode());
                }
            } else {
                // 根级
                jgDept.setLevel(0);
            }
        } else {
            // 没有选择父级部门 一级部门
            jgDept.setLevel(0);
            jgDept.setPid(String.valueOf(0));
            jgDept.setParentCode(String.valueOf(0));
        }
        // 名称，编码是否重复
        judgeDeptCodeOrNameExist(jgDept);
        return saveOrUpdate(jgDept);
    }

    private void judgeDeptCodeOrNameExist(JgDept jgDept) {
        LambdaQueryWrapper<JgDept> jgDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDeptLambdaQueryWrapper.eq(JgDept::getDeptName, jgDept.getDeptName()).eq(JgDept::getPid, jgDept.getPid());
        if (StringUtils.hasText(jgDept.getId())) {
            jgDeptLambdaQueryWrapper.ne(JgDept::getId,jgDept.getId());
        }
        Long c = count(jgDeptLambdaQueryWrapper);
        if (c.compareTo(0L) > 0) {
            throw new AdminException("部门名称已存在！");
        }
        LambdaQueryWrapper<JgDept> jgDeptCodeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDeptCodeLambdaQueryWrapper.eq(JgDept::getDeptCode, jgDept.getDeptCode());
        if (StringUtils.hasText(jgDept.getId())) {
            jgDeptCodeLambdaQueryWrapper.ne(JgDept::getId,jgDept.getId());
        }
        Long cn = count(jgDeptCodeLambdaQueryWrapper);
        if (cn.compareTo(0L) > 0) {
            throw new AdminException("部门编码已存在！");
        }
    }

    @Override
    public Boolean deleteDept(String id) {
        LambdaQueryWrapper<JgDept> jgDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDeptLambdaQueryWrapper.like(StringUtils.hasText(id), JgDept::getPidLevelCollect, id).select(JgDept::getId);
        List<String> dIds = listObjs(jgDeptLambdaQueryWrapper, Object::toString);
        dIds.add(id);
        List<JgAdmin> jgAdminList = iJgBridgeService.getJgAdminListByDeptIds(dIds);
        if (!CollectionUtils.isEmpty(jgAdminList)) {
            throw new AdminException("部门已被使用，不可删除！");
        }
        return removeBatchByIds(dIds);
    }

    @Override
    public Map<String, Object> getDeptAuthCodesByUserId(String userId) {
        Map<String, Object> deptCode = new HashMap<>();
        List<String> dataType = iJgBridgeService.getDataAuthTypeByUserId(userId);
        if (!CollectionUtils.isEmpty(dataType)) {
            if (dataType.contains(DataAuthTypeEnum.ALL.getAuthType())) {
                deptCode.put("flag", DataAuthTypeEnum.ALL.getAuthType());
                return deptCode;
            }
            if (dataType.contains(DataAuthTypeEnum.SELF.getAuthType())) {
                deptCode.put("flag", DataAuthTypeEnum.SELF.getAuthType());
                return deptCode;
            }
            List<String> codes = new ArrayList<>();
            dataType.forEach(s -> {
                List<String> cs = getDataAuthByDataType(userId, s);
                if (!CollectionUtils.isEmpty(cs)) {
                    codes.addAll(cs);
                }
            });
            if (!CollectionUtils.isEmpty(codes)) {
                List<String> tmpCode = codes.stream().distinct().collect(Collectors.toList());
                List<String> userIds = iJgBridgeService.getUserIdsByDeptCode(tmpCode);
                deptCode.put("userIds", userIds.stream().distinct().map(u -> "'" + u + "'").collect(Collectors.joining(",")));
            }
        }
        return deptCode;
    }

    private List<String> getDataAuthByDataType(String userId, String dataType) {
        List<String> codes = new ArrayList<>();
        // 获取用户所在部门
        JgAdmin jgAdmin = iJgBridgeService.getJgAdminByUserId(userId);
        String deptCode = jgAdmin.getDeptCode();
        // 本部门
        if (DataAuthTypeEnum.DEPT.getAuthType().equals(dataType)) {
            codes.add(deptCode);
            return codes;
        }
        //  本部门以及子部门
        if (DataAuthTypeEnum.DEPT_SUB.getAuthType().equals(dataType)) {
           return iJgBridgeService.getDeptCodesByCode(deptCode);
        }
        // 选择的部门
        if (DataAuthTypeEnum.CHOICE_DEPT.getAuthType().equals(dataType)) {
            return iJgBridgeService.getChoiceDeptDataAuthByUserId(userId);
        }
        return codes;
    }
}
