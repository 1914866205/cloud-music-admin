package com.soft1851.music.admin.exception;


import com.soft1851.music.admin.common.ResultCode;

/**
 * @Description 用户自定义异常
 * @Author 涛涛
 * @Date 2020/4/21 16:12
 * @Version 1.0
 **/
public class CustomException extends RuntimeException {
    protected ResultCode resultCode;

    public CustomException(String msg, ResultCode resultCode) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
