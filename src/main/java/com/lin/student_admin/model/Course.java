/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 14:03
 */
package com.lin.student_admin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Course {
    private Long cno;
    private String name;
    private Integer credit;
    private String term;
    private Integer period;
    private String img;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
