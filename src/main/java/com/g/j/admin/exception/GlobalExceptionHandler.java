package com.g.j.admin.exception;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常拦截
     * @param e 异常信息
     * @return 返回错误提示
     */
    @ExceptionHandler(value = Exception.class)
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    public SaResult handlerSaTokenException(SaTokenException e) {
        // 根据不同异常细分状态码返回不同的提示
        if(e.getCode() == 11012 ||  e.getCode() == 11013 || e.getCode() == 11016) {
            return SaResult.get(11013,"登录失效","");
        }
        return SaResult.error("服务器繁忙，请稍后重试...");
    }

    @ExceptionHandler(value = AdminException.class)
    public SaResult handlerException(AdminException e) {
        e.printStackTrace();
        return SaResult.error(e.getMsg());
    }
}
