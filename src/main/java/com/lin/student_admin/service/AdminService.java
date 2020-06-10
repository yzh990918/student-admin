/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 18:50
 */
package com.lin.student_admin.service;

import com.lin.student_admin.exception.AllreadyExistedException;
import com.lin.student_admin.exception.NotFoundException;
import com.lin.student_admin.exception.PasswordException;
import com.lin.student_admin.model.AdminUser;
import com.lin.student_admin.model.Course;
import com.lin.student_admin.model.StudentUser;
import com.lin.student_admin.model.TeacherUser;
import com.lin.student_admin.repository.AdminRepository;
import com.lin.student_admin.repository.CourseRepository;
import com.lin.student_admin.repository.StudentRepository;
import com.lin.student_admin.repository.TeacherRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 统一角色登录
    public Map<String, Object> login(String username, String password){
        Long result = adminRepository.countByUsernameAndPassword(username, password);
        Long result1 = studentRepository.countByUsernameAndPassword(username, password);
        Long result2 = teacherRepository.countByNameAndPassword(username, password);
        if(result == 1){
            AdminUser user = adminRepository.findOneByUsername(username);
            Map<String, Object> data = new HashMap<>();
            // 暂时不使用jwt token
            data.put("token","admin-token");
            data.put("userInfo",user);
            return data;
        }
        else if(result1 == 1){
            StudentUser user = studentRepository.findOneByUsername(username);
            Map<String, Object> data = new HashMap<>();
            data.put("token","student-token");
            data.put("userInfo",user);
            return data;
        }else if(result2 == 1){
            TeacherUser teacherUser = teacherRepository.findOneByName(username);
            Map<String, Object> data = new HashMap<>();
            data.put("token","teacher-token");
            data.put("userInfo",teacherUser);
            return data;
        }else{
            throw new PasswordException(10004);
        }
    }

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

    // 获取所有学科信息(分页)

    public Page<Course> getAll(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable);
    }

    // 获取所有的教师信息(分页)

    public Page<TeacherUser> getTeacherList(int Page,int size){
        Pageable pageable = PageRequest.of(Page, size);
        return teacherRepository.findAll(pageable);
    }

    // 通过token获取角色信息 uid-唯一标识 教师tno 学生sno 管理员id 配合前端做登录

    public Map<String, Object> getUserInfo(String token,Long uid ){
        Map<String, Object> data = new HashMap<>();
        if("student-token".equals(token)){
            // 学生
            StudentUser result = studentRepository.findOneBySno(uid);
            if(result == null){
                throw new NotFoundException(10002);
            }else{
                List<String>role = new ArrayList<>();
                role.add("student");
                data.put("roles",role);
                data.put("userInfo",result);
            }

        }else if ("teacher-token".equals(token)){
            // 教师
            TeacherUser result2 = teacherRepository.findOneByTno(uid);
            if(result2 == null){
                throw  new NotFoundException(10003);
            }else{
                List<String>role = new ArrayList<>();
                role.add("teacher");
                data.put("roles",role);
                data.put("userInfo",result2);
            }

        }else if("admin-token".equals(token)){
            AdminUser adminUser = adminRepository.findOneById(uid);
            if(adminUser == null){
                throw new NotFoundException(10008);
            }else{
                List<String>role = new ArrayList<>();
                role.add("admin");
                data.put("roles",role);
                data.put("userInfo",adminUser);
            }
        }
        return data;
    }

    // 传入cno 获取单个学科信息
    public Course getCourseInfo(Long cno){
        if(courseRepository.findOneByCno(cno) == null){
            throw new  NotFoundException(10007);
        }else{
            return courseRepository.findOneByCno(cno);

        }
    }

    // 删除单个学科

    @Transactional
    public void deleteCourse(Long cno){
        if(courseRepository.findOneByCno(cno) == null){
            throw new  NotFoundException(10007);
        }else{
            courseRepository.deleteOneByCno(cno);
        }
    }


    // 修改单个学科信息 这里需要传入包换id的整个对象 不允许更改cno

    public void modifyCourse(Course course){
        if(courseRepository.findOneByCno(course.getCno()) == null){
            throw  new NotFoundException(10007);
        }else{
            // 直接保存
            courseRepository.save(course);
        }

    }

    // 增加一个学科信息 不需要传入id
    public void addCourse(Course course){
        Course result = courseRepository.findOneByCno(course.getCno());
        // 如果已经存在
        if(result !=null){
            throw new AllreadyExistedException(10009);
        }else{
            Course course1 = Course.builder().cno(course.getCno()).credit(course.getCredit())
                    .img(course.getImg()).name(course.getName()).period(course.getPeriod())
                    .term(course.getTerm()).build();
            courseRepository.save(course1);
        }
    }
}
