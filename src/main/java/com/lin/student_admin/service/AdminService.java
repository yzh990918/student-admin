/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 18:50
 */
package com.lin.student_admin.service;

import com.lin.student_admin.repository.AdminRepository;
import com.lin.student_admin.repository.CourseRepository;
import com.lin.student_admin.repository.StudentRepository;
import com.lin.student_admin.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    // 获取首屏数据

    public List<Long> getPublic(){
        // 四个值分别代表 系统学生总数 系统学科总数 系统教师总数 系统超级管理员总数
        List<Long> items = new ArrayList<>();
        Long StudentCount = studentRepository.count();
        Long CourseCount = courseRepository.count();
        Long TeacherCount = teacherRepository.count();
        Long AdminCount = adminRepository.count();
        items.add(StudentCount);
        items.add(CourseCount);
        items.add(TeacherCount);
        items.add(AdminCount);
        return items;

    }
}
