package com.soft1851.music.admin.exception;


import com.soft1851.music.admin.common.ResultCode;

/**
 * @Description 自定义异常
 * @Author 涛涛
 * @Date 2020/4/15 17:31
 * @Version 1.0
 **/
public class JwtException extends RuntimeException {
    protected ResultCode resultCode;

    public JwtException(String msg, ResultCode resultCode) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
