/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 14:25
 */
package com.lin.student_admin.service;

import com.lin.student_admin.dto.StudentRegisterDto;
import com.lin.student_admin.exception.AllreadyExistedException;
import com.lin.student_admin.exception.AuthorizationException;
import com.lin.student_admin.exception.PasswordException;
import com.lin.student_admin.model.Course;
import com.lin.student_admin.model.StudentClass;
import com.lin.student_admin.model.StudentUser;
import com.lin.student_admin.repository.CourseRepository;
import com.lin.student_admin.repository.StudentRepository;
import com.sun.org.apache.xpath.internal.objects.XNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    // 学生登录
    public Map<String, Object> login(String username, String password){
        Long result = studentRepository.countByUsernameAndPassword(username, password);
        if(result == 1){
            StudentUser user = studentRepository.findOneByUsername(username);
            Map<String, Object> data = new HashMap<>();
            // 暂时不使用jwt token
            data.put("token","student-token");
            data.put("userInfo",user);
            return data;
        }else{
            throw new PasswordException(10004);
        }

    }

    // 学生注册
    public void register(StudentRegisterDto studentRegisterDto){
        if(studentRepository.findOneByUsername(studentRegisterDto.getUsername())==null){
            // 这里需要构建借助lombook构建一个对象
            StudentUser user =  StudentUser.builder().name(studentRegisterDto.getName())
                    .address(studentRegisterDto.getAddress()).sno(Instant.now().getEpochSecond()).avatar("https://image.yangxiansheng.top/img/9e2a5d0a-bd5b-457f-ac8e-86554616c87b.jpg?imagelist")
                    .college(studentRegisterDto.getCollege()).username(studentRegisterDto.getUsername())
                    .password(studentRegisterDto.getPassword()).gender(studentRegisterDto.getGender())
                    .subject(studentRegisterDto.getSubject()).mobile(studentRegisterDto.getMobile()).build();
            studentRepository.save(user);
        }else{
            throw new AllreadyExistedException(10005);
        }

    }


//    // 获取学生信息
//    public Map<String, Object>getInfo(String token,Long sno){
//        if("student-token".equals(token)){
//            StudentUser result = studentRepository.findOneBySno(sno);
//            Map<String, Object> data = new HashMap<>();
//            List<String> role = new ArrayList<>();
//            role.add("student");
//            data.put("roles",role);
//            data.put("userInfo",result);
//            return data;
//        }else{
//            throw new AuthorizationException(10006);
//        }
//
//    }


    // 查询学生详情和成绩
    public StudentUser getdetail(Long sno){
        return studentRepository.findOneBySno(sno);
    }

    // 通过cnos查询课程

    public List<Course> getClassByCnos(String cnos){
        //将string 转为数组
        List<Long> cnosList = Arrays.asList(cnos.split(","))
                .stream()
                .map(s->Long.parseLong(s))
                .collect(Collectors.toList());
        return courseRepository.findAllByCnoIn(cnosList);
    }

    //分页查询出所有的学生信息

public Page<StudentUser> getStudentList(Integer page,Integer size){
        // 创建分页对象
    Pageable pageable = PageRequest.of(page,size);
     return this.studentRepository.findAll(pageable);
    }

    // sno 查询学生成绩分布
    public List<Integer> getScoreState(Long sno){
        StudentUser studentUser = studentRepository.findOneBySno(sno);
        List<Integer> data = new ArrayList<>();
        int a =0;
        int b=0;
        int c= 0;
        int d=0;
        int e =0;
        int sum =0;
       for(StudentClass Item:studentUser.getCourseList()){
           Integer score = Item.getScore();
           sum+= score;
           if(score<=60){
               a = a+1;
           }else if(score>60 && score<=70){
               b = b+1;
           }else if(score>70 && score<=80){
               c = c+1;
           }else if(score>80 && score<=90){
               d = d+1;
           }else if(score>90 && score<100){
               e = e+1;
           }
       }
       data.add(studentUser.getCourseList().size());
       data.add(sum/studentUser.getCourseList().size());
       data.add(a);
       data.add(b);
       data.add(c);
       data.add(d);
       data.add(e);
       return data;
    }
}
