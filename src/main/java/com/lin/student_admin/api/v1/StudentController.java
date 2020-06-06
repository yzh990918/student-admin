/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 11:10
 */
package com.lin.student_admin.api.v1;

import com.lin.student_admin.dto.StudentRegisterDto;
import com.lin.student_admin.dto.StudentUserDto;
import com.lin.student_admin.exception.NotFoundException;
import com.lin.student_admin.model.StudentUser;
import com.lin.student_admin.service.StudentService;
import com.lin.student_admin.vo.PagingVo;
import com.lin.student_admin.vo.ResponseTVo;
import com.lin.student_admin.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {
    @Autowired
    private StudentService studentService;
    // sno 获取学生详情  查询成绩

    @GetMapping("/detail/{sno}")
    public ResponseVo getdetail(@PathVariable Long sno){
        StudentUser student = studentService.getdetail(sno);
        if(student == null){
            throw new NotFoundException(10002);
        }
        return new ResponseVo(student);
    }
    // 可传入多个cno 获取数组 查询成绩辅助-课程详情

    @GetMapping("/getCourse")
    public ResponseVo getlist(@RequestParam String cnos){
        return new ResponseVo(studentService.getClassByCnos(cnos));
    }

    // 分页获取所有学生信息

    @GetMapping("/getAll")
    public ResponseVo getAll(@RequestParam(name = "page",defaultValue = "0") Integer page,
                                        @RequestParam(name = "size",defaultValue = "10") Integer size){

        PagingVo<StudentUser> result = new PagingVo<>(studentService.getStudentList(page, size));
        return new ResponseVo(result);
    }

    // 查询学生成绩分布情况

    @GetMapping("/getState/{sno}")
    public ResponseVo getScoreState(@PathVariable Long sno){
        return new ResponseVo(studentService.getScoreState(sno));
    }


    // 学生登录

    @PostMapping ("/login")
    public ResponseTVo Login(@RequestBody StudentUserDto userDto){
        return new ResponseTVo("登录成功",200,studentService.login(userDto.getUsername(),userDto.getPassword()));
    }

    //学生注册

    @PostMapping("/register")
   public Map<String, Object> register(@RequestBody StudentRegisterDto studentRegisterDto){
        studentService.register(studentRegisterDto);
        Map<String, Object> data = new HashMap<>();
        data.put("code",200);
        data.put("msg","注册成功");
        return data;
    }

}
