/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 21:27
 */
package com.lin.student_admin.exception;

public class AuthorizationException extends HttpException {
    public AuthorizationException(int code) {
        this.code = code;
        this.HttpStatusCode = 200;
    }
}
