/**
 * @作者 努力中的杨先生
 * @描述 分页vo层
 * @创建时间 2020-06-06 16:16
 */
package com.lin.student_admin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagingVo<T> {
    private Long total;
    private Integer size;
    private Integer page;
    private Integer totalPage;
    private List<T>items;

    // 构造初始化
    public PagingVo(Page<T> pageT){
        this.initPageParams(pageT);
        this.items = pageT.getContent();
    }

    // 初始化返回的分页信息
    void initPageParams(Page<T> pageT){
        this.total = pageT.getTotalElements();
        this.size = pageT.getSize();
        this.page = pageT.getNumber();
        this.totalPage = pageT.getTotalPages();
    }
}
