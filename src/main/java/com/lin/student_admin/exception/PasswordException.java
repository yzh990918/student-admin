/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 20:05
 */
package com.lin.student_admin.exception;

public class PasswordException extends HttpException{
    public PasswordException(int code) {
        this.code = code;
        this.HttpStatusCode = 200;
    }
}
