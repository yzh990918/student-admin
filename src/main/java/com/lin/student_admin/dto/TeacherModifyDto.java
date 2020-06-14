/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-08 11:29
 */
package com.lin.student_admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherModifyDto {
    private Long id;
    private Long tno;
    private String name;
    private String password;
    private String avatar;
    private Integer gender;
    private String job;
    private Long cno;
    private String subject;
    private String college;
}
