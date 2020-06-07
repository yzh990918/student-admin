/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 17:39
 */
package com.lin.student_admin.api.v1;

import com.lin.student_admin.dto.StudentUserDto;
import com.lin.student_admin.model.Course;
import com.lin.student_admin.model.TeacherUser;
import com.lin.student_admin.service.AdminService;
import com.lin.student_admin.vo.PagingVo;
import com.lin.student_admin.vo.ResponseTVo;
import com.lin.student_admin.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 管理员登陆

    @PostMapping ("/login")
    public ResponseTVo Login(@RequestBody StudentUserDto userDto){
        return new ResponseTVo("登录成功",200,adminService.adminLogin(userDto.getUsername(),userDto.getPassword()));
    }


    // 获取首屏数据

    @GetMapping("/public")
    public ResponseVo getDashedBordData(){
        return new ResponseVo(adminService.getPublic());
    }

    // 获取角色信息

    @GetMapping("/getUserInfo")
    public ResponseVo getUserInfo(@RequestParam String token, @RequestParam Long uid){
        return new ResponseVo(adminService.getUserInfo(token, uid));
    }

    // 获取系统所有课程

    @GetMapping("/getAllCourse")
    public ResponseVo getAllCourse(@RequestParam(name = "page",defaultValue = "0")Integer page,
                                   @RequestParam(name = "size",defaultValue = "20")Integer size){
       PagingVo<Course> coursePagingVo = new PagingVo<>(adminService.getAll(page,size));
       return new ResponseVo(coursePagingVo);
    }

    // 获取系统所有的教师

    @GetMapping("/getAllTeacher")
    public ResponseVo getAllTeacher(@RequestParam(name = "page",defaultValue = "0")Integer page,
                                    @RequestParam(name = "size",defaultValue = "20")Integer size){
      PagingVo<TeacherUser> teacherUserPagingVo = new PagingVo<>(adminService.getTeacherList(page,size));
      return new ResponseVo(teacherUserPagingVo);
    }

    // 删除一门课程

    @PostMapping("/deleteCourse")
    public Map<String, Object>deleteCourse(@RequestParam Long cno){
        adminService.deleteCourse(cno);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","删除成功");
        data.put("code",200);
        return data;
    }

    // 修改一门课程

    @PostMapping("/modifyCourse")
    public Map<String, Object>modify(@RequestBody Course course){
        adminService.modifyCourse(course);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","修改成功");
        data.put("code",200);
        return data;
    }

    // 增加一门课程

    @PostMapping("/addCourse")
    public Map<String, Object>addCourse(@RequestBody Course course){
        adminService.addCourse(course);
        Map<String, Object>data = new HashMap<>();
        data.put("msg","添加成功");
        data.put("code",200);
        return data;
    }




}
