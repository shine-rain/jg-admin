package com.g.j.admin.dao.system.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.j.admin.annotation.DataPermission;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.AdminQueryDto;
import com.g.j.admin.entity.system.po.JgAdmin;
import org.apache.ibatis.annotations.Param;

public interface JgAdminMapper extends BaseMapper<JgAdmin> {

    /**
     * 查询分页数据
     * @param page 分页条件
     * @param adminQueryDto 查询条件
     * @return 结果
     */
    @DataPermission
    BasePage<JgAdmin> queryPageAdminList(BasePage<AdminQueryDto> page, @Param("data") AdminQueryDto adminQueryDto);
}