package com.g.j.admin.service.system.dict;

import com.g.j.admin.entity.system.po.JgDict;
import com.g.j.admin.entity.system.dto.DictAddModifyDto;
import com.g.j.admin.entity.system.vo.DictVo;
import com.g.j.admin.service.common.base.IBaseService;

import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface IDictService extends IBaseService<JgDict> {

    /**
     * 字典树形结构
     * @return 结果
     */
    List<DictVo> queryDictTree();

    /**
     * 新增修改字典
     * @param dictAddModifyDto 数据
     * @return 操作结果
     */
    Boolean addModifyDict(DictAddModifyDto dictAddModifyDto);

    /**
     * 删除字典
     * @param id 主键
     * @return 结果
     */
    Boolean deleteDict(String id);
}
