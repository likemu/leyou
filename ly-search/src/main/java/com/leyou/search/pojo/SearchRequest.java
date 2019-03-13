package com.leyou.search.pojo;

import java.util.Map;

/**
 * @Author：lidatu
 * @Date： 2019/01/26 星期六 22:23
 * @Description：
 */

public class SearchRequest {

    private String key;// 搜索条件

    private Integer page;// 当前页

    private Map<String, String> filter;

    private static final int DEFAULT_SIZE = 20;// 每页大小，不从页面接收，而是固定大小
    private static final int DEFAULT_PAGE = 1;// 默认页/每页大小 定义成常量 固定 不能改 不能让用户乱传

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page); //取最大值
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getSize() {
        return DEFAULT_SIZE;
    }

    public Map<String, String> getFilter() {
        return filter;
    }
    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
