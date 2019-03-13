package com.leyou.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private long total; // 总条数
    private long totalPage; // 总页数
    private List<T> items; // 当前页数据

    public PageResult() {//无参构造函数
    }
    public PageResult(Long total, List<T> items) {//商品列表分页查询
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}