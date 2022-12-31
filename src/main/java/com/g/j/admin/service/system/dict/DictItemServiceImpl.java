package com.g.j.admin.service.system.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.g.j.admin.dao.system.dict.JgDictItemMapper;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.vo.DictItemVo;
import com.g.j.admin.service.common.base.BaseServiceImpl;
import com.g.j.admin.entity.system.dto.DictItemAddModifyDto;
import com.g.j.admin.entity.system.dto.DictItemQueryDto;
import com.g.j.admin.entity.system.other.ArrParam;
import com.g.j.admin.entity.system.po.JgDictItem;
import com.g.j.admin.exception.AdminException;
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
public class DictItemServiceImpl extends BaseServiceImpl<JgDictItemMapper, JgDictItem> implements IDictItemService{

    @Override
    public BasePage<DictItemVo> queryDictItemList(DictItemQueryDto dictItemQueryDto) {
        BasePage<DictItemVo> dictItemVoBasePage = new BasePage<>();
        BasePage<JgDictItem> jgDictItemBasePage = this.baseMapper.queryDictItemPageList(dictItemQueryDto.getPageObj(), dictItemQueryDto);
        if (!CollectionUtils.isEmpty(jgDictItemBasePage.getList())) {
            List<DictItemVo> dictItemVos = new ArrayList<>();
            jgDictItemBasePage.getList().forEach(jgDictItem -> {
                dictItemVos.add(new DictItemVo(jgDictItem));
            });
            dictItemVoBasePage.setRows(dictItemVos);
            dictItemVoBasePage.setTotal(jgDictItemBasePage.getTotal());
            dictItemVoBasePage.setSize(jgDictItemBasePage.getSize());
            dictItemVoBasePage.setPageNumber(jgDictItemBasePage.getPageNumber());
        }
        return dictItemVoBasePage;
    }

    @Override
    public Boolean addModifyDictItem(DictItemAddModifyDto dictItemAddModifyDto) {
        JgDictItem jgDictItem = dictItemAddModifyDto.toJgDictItem();
        LambdaQueryWrapper<JgDictItem> jgDictItemCodeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDictItemCodeLambdaQueryWrapper.eq(JgDictItem::getItemCode, jgDictItem.getItemCode());
        if (StringUtils.hasText(jgDictItem.getId())) {
            jgDictItemCodeLambdaQueryWrapper.ne(JgDictItem::getId, jgDictItem.getId());
        }
        Long c = count(jgDictItemCodeLambdaQueryWrapper);
        if (c.compareTo(0L) > 0) {
            throw new AdminException("字典项编码已存在！");
        }
        LambdaQueryWrapper<JgDictItem> jgDictItemNameLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jgDictItemNameLambdaQueryWrapper.eq(JgDictItem::getItemCode, jgDictItem.getItemCode());
        Long cn = count(jgDictItemNameLambdaQueryWrapper);
        if (StringUtils.hasText(jgDictItem.getId())) {
            jgDictItemNameLambdaQueryWrapper.ne(JgDictItem::getId, jgDictItem.getId());
        }
        if (cn.compareTo(0L) > 0) {
            throw new AdminException("字典项名称已存在！");
        }
        return saveOrUpdate(jgDictItem);
    }

    @Override
    public Boolean deleteDictItem(String id) {
        if (StringUtils.hasText(id)) {
            return removeById(id);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteDictItemByIds(ArrParam arrParam) {
        if (!CollectionUtils.isEmpty(arrParam.getIds())) {
            return removeBatchByIds(arrParam.getIds());
        }
        return Boolean.FALSE;
    }
}
