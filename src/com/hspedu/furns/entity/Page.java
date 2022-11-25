package com.hspedu.furns.entity;

import java.util.List;

/** 分页信息，
 * @author: guorui fu
 * @versiion: 1.0
 */
public class Page<T> {//使用泛型，使其可以为多种对象进行分页处理
    //每页显示多少数据，其他地方也会用到
    public static final Integer PAGE_SIZE = 3;

    private Integer pageNum;
    private Integer pageSize = PAGE_SIZE;
    //分页后有多少页面，通过totalRow计算得出
    private Integer pageTotalCount;
    private Integer totalRow;
    //当前页pageNum的数据
    private List<T> items;
    //分页导航的字符串
    private String url;


    public Page() {
    }

    public Page(Integer pageNum, Integer pageSize, Integer pageTotalCount, Integer totalRow, List<T> items, String url) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.totalRow = totalRow;
        this.items = items;
        this.url = url;
    }

    public Page(Integer pageNumber, Integer pageSize, List<T> items , Integer pageTotalCount, Integer totalRow, String url) {
        this.pageNum = pageNumber;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.totalRow = totalRow;
        this.items = items;
        this.url = url;
    }

    public Integer getPageNumber() {
        return pageNum;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNum = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pageTotalCount=" + pageTotalCount +
                ", totalRow=" + totalRow +
                ", items=" + items +
                '}';
    }
}
