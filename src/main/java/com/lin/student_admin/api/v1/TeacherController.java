/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 13:18
 */
package com.lin.student_admin.api.v1;

import com.lin.student_admin.dto.StudentRegisterDto;
import com.lin.student_admin.dto.StudentUserDto;
import com.lin.student_admin.dto.TeacherUserDto;
import com.lin.student_admin.model.StudentUser;
import com.lin.student_admin.model.TeacherUser;
import com.lin.student_admin.service.TeacherService;
import com.lin.student_admin.vo.ResponseTVo;
import com.lin.student_admin.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    // 教师登录
    @PostMapping("/login")
    public ResponseTVo Login(@RequestBody TeacherUserDto teacherUserDto){
        return new ResponseTVo("登录成功",200,teacherService.login(teacherUserDto.getName(),teacherUserDto.getPassword()));
    }

    // 教师注册
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody TeacherUser teacherUser){
        teacherService.register(teacherUser);
        Map<String, Object> data = new HashMap<>();
        data.put("code",200);
        data.put("msg","注册成功");
        return data;
    }

    // 删除一个教师信息

    @PostMapping("/delete")
    public Map<String, Object>delete(@RequestParam Long tno){
        teacherService.delete(tno);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","删除成功");
        data.put("code",200);
        return data;
    }

    // 修改教师的信息

    @PostMapping("/modify")
    public Map<String, Object>modify(@RequestBody TeacherUser teacherUser){
        teacherService.modifyTeacherInfo(teacherUser);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","修改成功");
        data.put("code",200);
        return data;
    }

    // 获取一名教师信息

    @GetMapping("/getInfo")
    public ResponseVo getInfo(@RequestParam Long tno){
        return new ResponseVo(teacherService.getInfo(tno));
    }

}
