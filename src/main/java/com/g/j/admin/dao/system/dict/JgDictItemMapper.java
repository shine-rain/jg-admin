package com.g.j.admin.dao.system.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.DictItemQueryDto;
import com.g.j.admin.entity.system.po.JgDictItem;
import org.apache.ibatis.annotations.Param;

public interface JgDictItemMapper extends BaseMapper<JgDictItem> {

    /**
     * 字典项分页数据
     * @param page 分页条件
     * @param dictItemQueryDto 查询条件
     * @return 结果
     */
    BasePage<JgDictItem> queryDictItemPageList(BasePage<DictItemQueryDto> page, @Param("data") DictItemQueryDto dictItemQueryDto);
}