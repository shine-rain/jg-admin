package com.g.j.admin.service.system.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.dao.system.dict.JgDictMapper;
import com.g.j.admin.entity.system.po.JgDict;
import com.g.j.admin.entity.system.vo.DictVo;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.entity.system.dto.DictAddModifyDto;
import com.g.j.admin.entity.system.po.JgDictItem;
import com.g.j.admin.exception.AdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class DictServiceImpl extends BaseServiceImpl<JgDictMapper, JgDict> implements IDictService {

    @Autowired
    private IDictItemService iDictItemService;
    @Override
    public List<DictVo> queryDictTree() {
        List<DictVo> dictVoList = new ArrayList<>();
        List<JgDict> jgDictList = list();
        if (!CollectionUtils.isEmpty(jgDictList)) {
            List<DictVo> dictVos = new ArrayList<>();
            jgDictList.forEach(jgDict -> {
                dictVos.add(new DictVo(jgDict));
            });
            // 转树形结构数据
            dictVoList = getChildListTree(dictVos, "parentId",0, "id",
                    "children", DictVo.class, 10);
        }
        return dictVoList;
    }

    @Override
    public Boolean addModifyDict(DictAddModifyDto dictAddModifyDto) {
        JgDict jgDict = dictAddModifyDto.toJgDict();
        if (!"0".equals(jgDict.getParentId())) {
            JgDict jgDictParent = getById(jgDict.getParentId());
            String pidStr = jgDict.getParentId();
            if (StringUtils.hasText(jgDictParent.getPidLevelCollect())) {
                pidStr = jgDictParent.getPidLevelCollect() + "," + pidStr;
            }
            jgDict.setPidLevelCollect(pidStr);
        }
        LambdaQueryWrapper<JgDict> jgDictNameLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDictNameLambdaQueryWrapper.eq(JgDict::getParentId, jgDict.getParentId())
                .eq(JgDict::getDictName, jgDict.getDictName());
        if (StringUtils.hasText(jgDict.getId())) {
            jgDictNameLambdaQueryWrapper.ne(JgDict::getId, jgDict.getId());
        }
        Long c = count(jgDictNameLambdaQueryWrapper);
        if (c.compareTo(0L) > 0) {
            throw new AdminException("字典名称已存在！");
        }

        LambdaQueryWrapper<JgDict> jgDictCodeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDictCodeLambdaQueryWrapper.eq(JgDict::getDictCode, jgDict.getDictCode());
        if (StringUtils.hasText(jgDict.getId())) {
            jgDictCodeLambdaQueryWrapper.ne(JgDict::getId, jgDict.getId());
        }
        Long cn = count(jgDictCodeLambdaQueryWrapper);
        if (cn.compareTo(0L) > 0) {
            throw new AdminException("字典编码已存在！");
        }

        return saveOrUpdate(jgDict);
    }

    @Transactional
    @Override
    public Boolean deleteDict(String id) {
        LambdaQueryWrapper<JgDict> jgDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDictLambdaQueryWrapper.like(JgDict::getPidLevelCollect, id).select(JgDict::getId);
        List<String> ids = listObjs(jgDictLambdaQueryWrapper, Object::toString);
        ids.add(id);
        removeBatchByIds(ids);

        LambdaQueryWrapper<JgDictItem> jgDictItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDictItemLambdaQueryWrapper.in(JgDictItem::getDictId,ids);
        iDictItemService.remove(jgDictItemLambdaQueryWrapper);

        return Boolean.TRUE;
    }
}
