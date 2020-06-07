/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 15:38
 */
package com.lin.student_admin.repository;

import com.lin.student_admin.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {

    // 查询cnos数组 的课程详情

    List<Course> findAllByCnoIn(List<Long>cnos);

    // 查询单个学科信息
    Course findOneByName(String name);
    Course findOneByCno(Long cno);

    // 删除单个学科

    void deleteOneByCno(Long cno);

}
