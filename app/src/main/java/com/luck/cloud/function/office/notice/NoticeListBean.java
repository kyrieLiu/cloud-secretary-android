package com.luck.cloud.function.office.notice;

import java.util.List;

public class NoticeListBean {
    /**
     * records : [{"noticeId":3,"titleName":"测试公告标题","content":"<p>测试公告内容<\/p>","startTime":"2020-07-12T16:00:00","endTime":"2020-07-21T16:00:00","createUser":"sunjie","createTime":"2020-07-19T14:45:54","noticeStatus":1,"pushTime":"2020-07-19T14:45:54"},{"noticeId":2,"titleName":"123213213","content":"<p>12321321<\/p>","startTime":"2020-07-18T16:00:00","endTime":"2020-07-20T16:00:00","createUser":"sunjie","createTime":"2020-07-19T14:42:09","noticeStatus":1,"pushTime":"2020-07-19T14:44:49"},{"noticeId":1,"titleName":"1232132","content":"<p>123123213<\/p>","startTime":"2020-07-19T06:39:01","endTime":"2020-07-29T16:00:00","createUser":"sunjie","createTime":"2020-07-19T14:39:06","noticeStatus":1,"pushTime":"2020-07-19T14:39:06"}]
     * total : 3
     * size : 10
     * current : 1
     * orders : []
     * searchCount : true
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private boolean searchCount;
    private int pages;
    private List<NoticeBean> records;
    private List<?> orders;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<NoticeBean> getRecords() {
        return records;
    }

    public void setRecords(List<NoticeBean> records) {
        this.records = records;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }
}
