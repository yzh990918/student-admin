/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-08 19:20
 */
package com.lin.student_admin.util;

import com.lin.student_admin.model.StudentClass;
import com.lin.student_admin.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArrayScore {
    public static Integer getArrayScore(List<StudentClass> coruses){
        int sum =0;
        for (StudentClass item:coruses){
            sum+=item.getScore();
        }
        return sum/coruses.size();
    }
}
