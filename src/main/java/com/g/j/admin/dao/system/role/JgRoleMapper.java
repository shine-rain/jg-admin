package com.g.j.admin.dao.system.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.RoleQueryDto;
import com.g.j.admin.entity.system.po.JgRole;
import org.apache.ibatis.annotations.Param;

public interface JgRoleMapper extends BaseMapper<JgRole> {

    /**
     * 查询分页数据
     * @param page 分页条件
     * @param roleQueryDto 业务查询条件
     * @return 结果
     */
    BasePage<JgRole> queryPageRoleList(BasePage<RoleQueryDto> page, @Param("data") RoleQueryDto roleQueryDto);
}