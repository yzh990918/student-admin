/**
 * @作者 努力中的杨先生
 * @描述 简要描述
 * @创建时间 2020-06-06 22:14
 */
package com.lin.student_admin.util;

import java.util.Random;

public class RandomUtil {
    public static int[] testA(int sz) {
        Random random = new Random();
        int a[] = new int[sz];
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(sz);
            for (int j = 1; j < i; j++) {
                while (a[i] == a[j]) {
                    i--;
                }
            }
        }
        return a;
    }
}
