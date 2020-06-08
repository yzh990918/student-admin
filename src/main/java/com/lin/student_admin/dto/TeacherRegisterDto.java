/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-08 11:20
 */
package com.lin.student_admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class TeacherRegisterDto {
    private Long tno;
    private String name;
    private String password;
    private String avatar;
    private Integer gender;
    private String job;
    private Long cno;
}
