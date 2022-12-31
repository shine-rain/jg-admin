package com.g.j.admin.service.system.dict;

import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.vo.DictItemVo;
import com.g.j.admin.entity.system.dto.DictItemAddModifyDto;
import com.g.j.admin.entity.system.dto.DictItemQueryDto;
import com.g.j.admin.entity.system.other.ArrParam;
import com.g.j.admin.entity.system.po.JgDictItem;
import com.g.j.admin.service.common.base.IBaseService;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IDictItemService extends IBaseService<JgDictItem> {

    /**
     * 查询字典项值
     * @param dictItemQueryDto 查询条件
     * @return 结果
     */
    BasePage<DictItemVo> queryDictItemList(DictItemQueryDto dictItemQueryDto);

    /**
     * 新增修改字典项
     * @param dictItemAddModifyDto 修改信息
     * @return 结果
     */
    Boolean addModifyDictItem(DictItemAddModifyDto dictItemAddModifyDto);

    /**
     * 删除字典项
     * @param id 主键
     * @return 结果
     */
    Boolean deleteDictItem(String id);

    /**
     * 批量删除
     * @param arrParam id集合
     * @return 结果
     */
    Boolean deleteDictItemByIds(ArrParam arrParam);
}
