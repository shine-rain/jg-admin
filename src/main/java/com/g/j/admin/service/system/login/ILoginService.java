package com.g.j.admin.service.system.login;

import com.g.j.admin.entity.system.dto.LoginDto;
import com.g.j.admin.entity.system.vo.LoginVo;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public interface ILoginService {

    /**
     * 登录
     * @param loginDto 验证信息
     * @return 结果
     */
    LoginVo doLogin(LoginDto loginDto);
}
