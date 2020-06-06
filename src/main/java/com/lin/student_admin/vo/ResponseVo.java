/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 17:31
 */
package com.lin.student_admin.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVo {
    private String msg="返回成功";
    private Integer code=200;
    private Object data;
    public ResponseVo(Object data){
        this.data = data;
    }
}
