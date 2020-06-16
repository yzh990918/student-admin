/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 11:10
 */
package com.lin.student_admin.api.v1;

import com.lin.student_admin.dto.StudentClassDto;
import com.lin.student_admin.dto.StudentRegisterDto;
import com.lin.student_admin.dto.StudentUserDto;
import com.lin.student_admin.exception.NotFoundException;
import com.lin.student_admin.model.StudentClass;
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
@CrossOrigin(origins = "*")
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

    // 传入学号查询所有的成绩列表
    @GetMapping("/getScoreList")
    public ResponseVo getScoreList(@RequestParam Long sno){
        return new ResponseVo(studentService.getscoreList(sno));
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

    // 删除一个学生信息

    @PostMapping("/delete")
    public Map<String, Object>delete(@RequestParam Long sno){
        studentService.delete(sno);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","删除成功");
        data.put("code",200);
        return data;
    }

    // 修改学生的信息

    @PostMapping("/modify")
    public Map<String, Object>modify(@RequestBody StudentUser studentUser){
        studentService.modifyStudentInfo(studentUser);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","修改成功");
        data.put("code",200);
        return data;
    }

    // 录入成绩或者修改学生的某科成绩
    @PostMapping("/score/addOrModify")
    public Map<String, Object>addOrModifyScore(@RequestBody StudentClass studentClass){
        studentService.addSudentScore(studentClass.getSno(),studentClass.getCno(),studentClass.getScore());
        Map<String, Object>data = new HashMap<>();
        data.put("msg","操作成功");
        data.put("code",200);
        return data;
    }

    // 搜索关键词 name subject college
    @GetMapping("/search")
    public ResponseVo searchBykeyWord(@RequestParam String keyword,@RequestParam(name = "page",defaultValue = "0")Integer page,
                                      @RequestParam(name = "size",defaultValue = "10")Integer size){
        PagingVo<StudentUser>studentUserPagingVo = new PagingVo<>(studentService.searchByKeyword(page,size,keyword,keyword,keyword));
        return new ResponseVo(studentUserPagingVo);
    }

    // 删除一名成绩 传入sno cno


    @PostMapping("/score/delete")
    public Map<String, Object>deletescore(@RequestParam Long id){
            studentService.deleteScore(id);
            Map<String, Object>data = new HashMap<>();
            data.put("msg","删除成功");
            data.put("code",200);
            return data;
    }


}
