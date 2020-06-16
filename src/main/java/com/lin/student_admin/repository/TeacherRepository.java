/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 18:50
 */
package com.lin.student_admin.repository;

import com.lin.student_admin.model.TeacherUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherUser,Long> {

    TeacherUser findOneByTno(Long tno);

    // 登录

    Long countByNameAndPassword(String name,String password);
    TeacherUser findOneByName(String name);

    // 删除
    void deleteAllByTno(Long tno);
    void deleteAllById(Long id);
    TeacherUser findOneById(Long id);
}
