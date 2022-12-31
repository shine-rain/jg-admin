package com.g.j.admin.service.system.user;

import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.AdminAddModifyDto;
import com.g.j.admin.entity.system.dto.AdminQueryDto;
import com.g.j.admin.entity.system.dto.PwdUpdateDto;
import com.g.j.admin.entity.system.other.ArrParam;
import com.g.j.admin.entity.system.other.UserInfo;
import com.g.j.admin.entity.system.po.JgAdmin;
import com.g.j.admin.entity.system.vo.AdminVo;
import com.g.j.admin.service.common.base.IBaseService;

public interface IJgAdminService extends IBaseService<JgAdmin> {

    /**
     * 查询分页用户信息
     * @param adminQueryDto 查询条件
     * @return 结果
     */
    BasePage<AdminVo> queryPageAdminList(AdminQueryDto adminQueryDto);

    /**
     * 新增修改用户
     * @param adminAddModifyDto 数据信息
     * @return 操作结果
     */
    Boolean addModifyAdmin(AdminAddModifyDto adminAddModifyDto);

    /**
     * 删除用户
     * @param id 主键
     * @return 操作结果
     */
    Boolean deleteAdmin(String id);

    /**
     * 重置密码
     * @param arrParam 主键列表
     * @return 结果
     */
    Boolean resetPwd(ArrParam arrParam);

    /**
     * 更新个人信息
     * @param userInfo 用户信息
     * @return 结果
     */
    Boolean updateUserInfo(UserInfo userInfo);

    /**
     * 更新用户密码
     * @param pwdUpdateDto 密码
     * @return 结果
     */
    Boolean updateUserPwd(PwdUpdateDto pwdUpdateDto);

}
