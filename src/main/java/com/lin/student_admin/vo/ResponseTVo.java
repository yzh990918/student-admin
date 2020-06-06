/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 19:47
 */
package com.lin.student_admin.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseTVo {
    private String msg;
    private Integer code;
    private Object data;
}
