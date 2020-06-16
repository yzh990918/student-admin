/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-07 16:24
 */
package com.lin.student_admin.repository;

import com.lin.student_admin.model.StudentClass;
import com.lin.student_admin.model.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentClassRepository extends JpaRepository<StudentClass,Long> {
    // 删除学生成绩信息

    void deleteAllBySno(Long sno);

    // 查询出是否有该学生这门课程成绩记录

    StudentClass findBySnoAndCno(Long sno,Long cno);



    // 计算每门学科平均成绩
    List<StudentClass> findAllByCno(Long cno);
    List<StudentClass> findAllBySno(Long sno);

    // 删除一门学科
    void deleteAllById(Long id);
}
