package com.luck.cloud.base;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuyin on 2018/8/2 14:34
 * 加载List  data实体
 */
public class BaseListBean<T> implements Serializable {
    private String code;
    private int count;
    private String msg;
    private String message;
    private ArrayList<T> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
