/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 14:03
 */
package com.lin.student_admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    private String name;
    @JsonIgnore
    private String password;
    @Column(insertable = false,columnDefinition = "String default 'https://image.yangxiansheng.top/img/57ed425a-c71e-4201-9428-68760c0537c4.jpg?imagelist'")
    private String avatar;
    private Integer gender;
    private String job;
    private Integer classNo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
