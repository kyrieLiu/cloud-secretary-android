package com.luck.cloud.base;

import com.luck.cloud.function.mine.person.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class BaseRecordBean<T> {
    /**
     * code : SUCCESS
     * msg : 成功
     * data : {"records":[{"userId":299,"fansId":299,"createTime":"2020-07-19T18:39:44","userName":"真实姓名"},{"userId":300,"fansId":299,"createTime":"2020-07-20T14:54:45","userName":"刘隐"}],"total":2,"size":10,"current":1,"orders":[],"searchCount":true,"pages":1}
     */

    private String code;
    private String msg;
    private String message;
    private DataBean<T> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean<T> getData() {
        return data;
    }

    public void setData(DataBean<T> data) {
        this.data = data;
    }

    public static class DataBean<T> {
        /**
         * records : [{"userId":299,"fansId":299,"createTime":"2020-07-19T18:39:44","userName":"真实姓名"},{"userId":300,"fansId":299,"createTime":"2020-07-20T14:54:45","userName":"刘隐"}]
         * total : 2
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
        private ArrayList<T> records;
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

        public ArrayList<T> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<T> records) {
            this.records = records;
        }

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }
    }
}
