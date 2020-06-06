/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 11:36
 */
package com.lin.student_admin.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer HttpStatusCode;
}
