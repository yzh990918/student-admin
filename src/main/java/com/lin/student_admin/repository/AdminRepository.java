/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 18:53
 */
package com.lin.student_admin.repository;

import com.lin.student_admin.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminUser,Long> {
    AdminUser findOneById(Long id);

    Long countByUsernameAndPassword(String userName,String password);
    AdminUser findOneByUsername(String userName);

}
