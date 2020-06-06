/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 17:39
 */
package com.lin.student_admin.api.v1;

import com.lin.student_admin.service.AdminService;
import com.lin.student_admin.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/public")
    public ResponseVo getDashedBordData(){
        return new ResponseVo(adminService.getPublic());
    }
}
