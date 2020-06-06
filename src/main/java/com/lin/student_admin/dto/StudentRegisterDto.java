/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 20:52
 */
package com.lin.student_admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentRegisterDto {
    private Long sno;
    private String name;
    private String username;
    private String password;
    private Integer gender;
    private String college;
    private String avatar;
    private String subject;
    private String mobile;
    private String address;
}
