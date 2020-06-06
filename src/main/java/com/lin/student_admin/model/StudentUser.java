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

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sno;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private Integer gender;
    private String college;
    private String subject;
    private String mobile;
    private String address;
    // 添加默认值

    private String avatar;

    // 连表查询

    @OneToMany
    @JoinColumn(name = "sno")
    private List<StudentClass>courseList;
}
