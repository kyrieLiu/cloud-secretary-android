package com.luck.cloud.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyin on 2018/7/31 14:23
 * @Describe 数据解析工具
 */
public class JsonUtils {

    private static Gson mGson = new Gson();

    private volatile static JsonUtils jsonUtils;

    public static JsonUtils getInstance(){
        if (jsonUtils==null){
            synchronized (JsonUtils.class){
                jsonUtils =new JsonUtils();
            }
        }
        return jsonUtils;
    }

    /**
     * 将对象准换为json字符串
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToJson(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将List集合转为Json
     */
    public static <T> String listToJson(List<T> list) {
        return mGson.toJson(list);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static  <T> T jsonToObjectWithType(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }

    /**
     * JSON转List集合
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static  <T> ArrayList<T> jsonToList(String json, Class<T> cls) {
        Type type = new TypeToken<List<T>>(){}.getType();
        ArrayList<T> list = new Gson().fromJson(json,type);
        return list;
    }





}
