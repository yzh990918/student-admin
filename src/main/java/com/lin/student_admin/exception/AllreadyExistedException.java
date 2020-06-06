/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 20:06
 */
package com.lin.student_admin.exception;

public class AllreadyExistedException extends HttpException{
    public AllreadyExistedException(int code) {
        this.HttpStatusCode = 200;
        this.code = code;
    }
}
