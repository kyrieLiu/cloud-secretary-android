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
    private ArrayList<T> body;

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

    public ArrayList<T> getBody() {
        return body;
    }

    public void setBody(ArrayList<T> body) {
        this.body = body;
    }
}
