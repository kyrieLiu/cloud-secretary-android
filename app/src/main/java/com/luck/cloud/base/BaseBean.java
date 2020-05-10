package com.luck.cloud.base;

import java.io.Serializable;

/**
 * Created by liuyin on 2018/7/31 14:19
 * @Describe 数据解析基类
 */
public class BaseBean<T>  implements Serializable {
    private static final long serialVersionUID = 1876345352L;
    private String code;
    private String msg;
    private T data;
    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {

        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
