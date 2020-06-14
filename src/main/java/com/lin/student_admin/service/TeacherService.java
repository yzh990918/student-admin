/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-07 23:03
 */
package com.lin.student_admin.service;

import com.lin.student_admin.dto.StudentRegisterDto;
import com.lin.student_admin.dto.TeacherModifyDto;
import com.lin.student_admin.dto.TeacherRegisterDto;
import com.lin.student_admin.exception.AllreadyExistedException;
import com.lin.student_admin.exception.NotFoundException;
import com.lin.student_admin.exception.PasswordException;
import com.lin.student_admin.model.StudentUser;
import com.lin.student_admin.model.TeacherUser;
import com.lin.student_admin.repository.TeacherRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    // 教师登录
    public Map<String, Object> login(String username, String password){
        Long result = teacherRepository.countByNameAndPassword(username, password);
        if(result == 1){
            TeacherUser user = teacherRepository.findOneByName(username);
            Map<String, Object> data = new HashMap<>();
            // 暂时不使用jwt token
            data.put("token","teacher-token");
            data.put("userInfo",user);
            return data;
        }else{
            throw new PasswordException(10004);
        }

    }

    // 教师注册
    public void register(TeacherRegisterDto teacherUser){
        if(teacherRepository.findOneByName(teacherUser.getName())==null){
            TeacherUser user =  TeacherUser.builder().name(teacherUser.getName()).avatar("https://image.yangxiansheng.top/img/57ed425a-c71e-4201-9428-68760c0537c4.jpg?imagelist")
                    .cno(teacherUser.getCno()).gender(teacherUser.getGender()).job(teacherUser.getJob())
                    .password(teacherUser.getPassword()).tno(Instant.now().getEpochSecond()).college(teacherUser.getCollege()).subject(teacherUser.getSubject()).build();
            teacherRepository.save(user);
        }else{
            throw new AllreadyExistedException(100010);
        }

    }


    // 传入tno 获取单个老师信息

    public TeacherUser getInfo(Long tno){
        TeacherUser teacherUser = teacherRepository.findOneByTno(tno);
        if(teacherUser == null){
            throw new NotFoundException(10003);
        }else{
            return teacherUser;
        }
    }


    // 删除一名教师信息

    @Transactional
    public void delete(Long tno){
        TeacherUser user = teacherRepository.findOneByTno(tno);
        if(user == null){
            throw new NotFoundException(10003);
        }else{
            teacherRepository.deleteAllByTno(tno);
        }
    }


    // 修改一名教师的基本信息需要传入整个对象 包含id

    public void modifyTeacherInfo( TeacherModifyDto teacherModifyDto){
        TeacherUser teacherUser = TeacherUser.builder().college(teacherModifyDto.getCollege()).subject(teacherModifyDto.getSubject()).avatar(teacherModifyDto.getAvatar())
                .cno(teacherModifyDto.getCno()).gender(teacherModifyDto.getGender()).job(teacherModifyDto.getJob())
                .name(teacherModifyDto.getName()).password(teacherModifyDto.getPassword()).tno(teacherModifyDto.getTno()).id(teacherModifyDto.getId())
                .build();
        teacherRepository.save(teacherUser);
    }



}
