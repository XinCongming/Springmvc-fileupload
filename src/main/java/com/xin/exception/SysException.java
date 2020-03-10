package com.xin.exception;

/**
 * Created by xinBa.
 * User: 辛聪明
 * Date: 2020/3/9
 */

/**
 * 自定义异常
 */
public class SysException extends Exception{

    //提示信息
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SysException(String message) {
        this.message = message;
    }
}
