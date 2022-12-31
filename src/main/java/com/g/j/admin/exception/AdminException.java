package com.g.j.admin.exception;

import com.g.j.admin.enums.SystemCodeEnum;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
public class AdminException extends RuntimeException {

    private final String msg;
    private final int code;

    public AdminException(int code, String msg) {
        super(code + ":" + msg, null, true, true);
        this.code = code;
        this.msg = msg;
    }

    public AdminException(String msg) {
        super(msg, null, true, true);
        this.code = SystemCodeEnum.SYSTEM_ERROR.getCode();
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
