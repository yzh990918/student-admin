/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 14:25
 */
package com.lin.student_admin.service;

import com.lin.student_admin.dto.StudentRegisterDto;
import com.lin.student_admin.exception.AllreadyExistedException;
import com.lin.student_admin.exception.AuthorizationException;
import com.lin.student_admin.exception.NotFoundException;
import com.lin.student_admin.exception.PasswordException;
import com.lin.student_admin.model.Course;
import com.lin.student_admin.model.StudentClass;
import com.lin.student_admin.model.StudentUser;
import com.lin.student_admin.repository.CourseRepository;
import com.lin.student_admin.repository.StudentClassRepository;
import com.lin.student_admin.repository.StudentRepository;
import com.lin.student_admin.util.ArrayScore;
import com.sun.org.apache.xpath.internal.objects.XNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentClassRepository studentClassRepository;


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

    // 删除一名学生的信息和成绩信息

    @Transactional
    public void delete(Long sno){
        StudentUser result = studentRepository.findOneBySno(sno);
        if(result == null){
            throw new NotFoundException(10002);
        }else{
            studentRepository.deleteAllBySno(sno);
            studentClassRepository.deleteAllBySno(sno);
        }
    }

    @Transactional
    public void deleteOne(Long sno,Long cno){
        studentClassRepository.deleteById(studentClassRepository.findBySnoAndCno(sno, cno).getId());
    }


    // 修改一名学生的基本信息需要传入整个对象 包含id

    public void modifyStudentInfo(StudentUser studentUser){
        // 查询出这名学生的密码 因为管理员才能修改学生账户密码 其他角色没有权限
        String password = studentRepository.findOneById(studentUser.getId()).getPassword();

        // 默认没有传入密码时
        if(studentUser.getPassword()==null){
            // 传入默认密码
            studentUser.setPassword(password);
        }else{
            studentUser.setPassword(studentUser.getPassword());
        }
        studentRepository.save(studentUser);
    }


    // 录入或修改学生成绩

    public void addSudentScore(Long sno,Long cno,Integer score){
        // 传入学号 课程名称 成绩
        Course course = courseRepository.findOneByCno(cno);
        if(course == null){
            throw new NotFoundException(10007);
        }else{
            StudentUser user = studentRepository.findOneBySno(sno);
            if(user == null){
                throw new NotFoundException(10002);

            }else{
                // 获取cno 然后操作student-class
                StudentClass studentClass= studentClassRepository.findBySnoAndCno(sno,cno);
                // 如果有这门课程的成绩,则视为修改成绩
                if(studentClass != null){
                    studentClass.setScore(score);
                    studentClassRepository.save(studentClass);
                }else{
                    // 录入成绩 构建成绩对象 保存
                    StudentClass studentClass1 = StudentClass.builder().sno(sno).cno(cno).score(score).build();
                    studentClassRepository.save(studentClass1);
                }
            }

        }
    }



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

    //分页查询出所有的学生信息 暂时无法做成绩排序

public Page<StudentUser> getStudentList(Integer page,Integer size){
        Pageable pageable = PageRequest.of(page,size);
     return this.studentRepository.findAll(pageable);
    }

    // 关键词搜索学生列表

public Page<StudentUser>searchByKeyword(Integer page,Integer size,String keyword1,String keyword2,String keyword3){
Pageable pageable = PageRequest.of(page,size);
return studentRepository.findByNameContainingOrSubjectContainingOrCollegeContaining(keyword1,keyword2,keyword3,pageable);
}



    // sno 查询学生成绩分布

    public Map<String, Object> getScoreState(Long sno) {
        StudentUser studentUser = studentRepository.findOneBySno(sno);
        if (studentUser == null) {
            throw new NotFoundException(10002);
        }
        // 成绩分布数组
        List<Integer> data = new ArrayList<>();
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int sum = 0;
        if (studentUser.getCourseList().size() == 0) {
            throw new NotFoundException(100011);
        }
        for (StudentClass Item : studentUser.getCourseList()) {
            Integer score = Item.getScore();
            sum += score;
            if (score <= 60) {
                a = a + 1;
            } else if (score > 60 && score <= 70) {
                b = b + 1;
            } else if (score > 70 && score <= 80) {
                c = c + 1;
            } else if (score > 80 && score <= 90) {
                d = d + 1;
            } else if (score > 90 && score < 100) {
                e = e + 1;
            }
        }
        data.add(a);
        data.add(b);
        data.add(c);
        data.add(d);
        data.add(e);
        // 成绩数组
        List<Object> score = new ArrayList<>();
        for (StudentClass item : studentUser.getCourseList()) {
            Integer s = item.getScore();
            Map<String, Object> scoredata = new HashMap<>();
            Long cno = item.getCno();
            Course couseDetail = courseRepository.findOneByCno(cno);
            // 传入cno 计算平均分
            List<StudentClass> courses = studentClassRepository.findAllByCno(cno);
            if (courses.size() == 0) {
                scoredata.put("courseAvg", 0);
            } else {
                scoredata.put("courseAvg", ArrayScore.getArrayScore(courses));
            }
            scoredata.put("cno", cno);
            scoredata.put("scoreId", item.getId());
            scoredata.put("courseName", couseDetail.getName());
            scoredata.put("img", couseDetail.getImg());
            scoredata.put("credit", couseDetail.getCredit());
            scoredata.put("term", couseDetail.getTerm());
            scoredata.put("period", couseDetail.getPeriod());
            scoredata.put("score", s);
            score.add(scoredata);
        }
        Map<String, Object> items = new HashMap<>();
        items.put("distribute", data);
        items.put("scoreData", score);
        items.put("sum", sum);
        items.put("average", sum / studentUser.getCourseList().size());
        items.put("CourseCount", studentUser.getCourseList().size());
        return items;
    }

    // 删除一门成绩
@Transactional
    public void deleteScore(Long id){
            studentClassRepository.deleteById(id);
    }

}
