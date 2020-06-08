/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 14:03
 */
package com.lin.student_admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
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
    private String avatar;
    private Integer gender;
    private String job;
    private Long cno;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "cno")
    private List<StudentClass>courses;
}
