package com.luck.cloud.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.luck.cloud.app.AppApplication;

import java.util.Set;

/**
 * Created by liuyin on 2018/7/23.
 * SharePreference缓存工具类
 */


public class SpUtil {
    private static final Context mcontext;
    public static final String Share_Preferences_Name_Mode_Private = "sp_private";


    public static final String KEY_IS_LOGIN = "KEY_IS_LOGIN";// 是否已经登录
    public static final String KEY_LOGIN_ACOUNT = "KEY_LOGIN_ACOUNT";// 登录账号
    public static final String KEY_LOGIN_PASSWORD = "KEY_LOGIN_PASSWORD";// 登录密码
    public static final String KEY_TOKEN = "KEY_TOKEN";// 应用token
    public static final String KEY_USER_NAME = "KEY_USER_NAME";// 名称
    public static final String KEY_TELEPHONE = "KEY_TELEPHONE";// 手机号
    public static final String KEY_JOB = "KEY_JOB";// 用户角色
    public static final String KEY_USER_ID = "KEY_USER_ID";// 用户ID
    public static final String USER_ADDRESS = "USER_ADDRESS";// 用户地址
    public static final String USER_HEAD_IMAGE = "USER_HEAD_IMAGE";// 用户头像地址
    public static final String KEY_DEVICE_TOKEN = "KEY_DEVICE_TOKEN";// 友盟推送token
    public static final String KEY_UMENG_ALIAS = "KEY_UMENG_ALIAS";// 友盟推送Alias
    public static final String KEY_PARK_NAME = "KEY_PARK_NAME";// 所属园区
    public static final String KEY_PARK_ID= "KEY_PARK_ID";// 所属园区ID
    public static final String KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL";// 第一次安装
    public static final String KEY_PERMISSION_CODE = "KEY_PERMISSION_CODE";//权限CODE

    static {
        mcontext = AppApplication.getInstance().getApplicationContext();
    }

    /**
     * 该配置文件被自己的应用程序访问
     */
    private static SharedPreferences getPreferences() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(
                    Share_Preferences_Name_Mode_Private,
                    Context.MODE_PRIVATE);
        }
        return null;
    }

    /**
     * 设置是否已经登录
     */
    public static boolean setIsLogin(boolean flag) {
        if (getPreferences() != null) {
            return getPreferences().edit()
                    .putBoolean(KEY_IS_LOGIN, flag).commit();
        }
        return false;
    }

    /**
     * 获取是否已经登录
     */
    public static boolean getIsLogin() {
        if (getPreferences() != null) {
            return getPreferences().getBoolean(KEY_IS_LOGIN, false);
        }
        return false;
    }



    /**
     * 获取登录账号
     */
    public static String getLoginAcount() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_LOGIN_ACOUNT, "");
        }
        return "";
    }

    /**
     * 存储登录账号
     */
    public static boolean setLoginAcount(String acount) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_LOGIN_ACOUNT, acount).commit();
        }
        return false;
    }

    /**
     * 获取登录密码
     */
    public static String getLoginPassword() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_LOGIN_PASSWORD, "");
        }
        return "";
    }

    /**
     * 存储登录密码
     */
    public static boolean setLoginPassword(String password) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_LOGIN_PASSWORD, password).commit();
        }
        return false;
    }
    /**
     * 获取用户角色
     */
    public static String getJob() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_JOB, "");
        }
        return "";
    }

    /**
     * 存储用户角色
     */
    public static boolean setJob(String job) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_JOB, job).commit();
        }
        return false;
    }

    public static void setShared(String key, String value) {
        if (getPreferences() != null) {
            getPreferences().edit().putString(key, value).commit();
        }
    }

    /**
     * 存储头像地址
     */
    public static boolean setHeadImage(String path) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(USER_HEAD_IMAGE, path).commit();
        }
        return false;
    }

    /**
     * 获取头像地址
     * @return
     */
    public static String getHeadImage() {
        if (getPreferences() != null) {
            return getPreferences().getString(USER_HEAD_IMAGE, "");
            //return "customer923157a4df9342ea9e615ca2578bef20";
        }
        return "";
    }

    public static String getShared(String key) {
        if (getPreferences() != null) {
           return getPreferences().getString(key, "");
        }
        return "";
    }

    /**
     * 获取token
     */
    public static String getToken() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_TOKEN, "");
            //return "customer923157a4df9342ea9e615ca2578bef20";
        }
        return "";
    }

    /**
     * 存储token
     */
    public static boolean setToken(String token) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_TOKEN, token).commit();
        }
        return false;
    }

    /**
     * 获取个人ID
     */
    public static int getUerId() {
        if (getPreferences() != null) {
            return getPreferences().getInt(KEY_USER_ID, 0);
        }
        return 0;
    }

    /**
     * 存储个人ID
     */
    public static boolean setUserId(int userId) {
        if (getPreferences() != null) {
            return getPreferences().edit().putInt(KEY_USER_ID, userId).commit();
        }
        return false;
    }

    /**
     * 获取名称
     */
    public static String getName() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_USER_NAME, "");
        }
        return "";
    }

    /**
     * 存储名称
     */
    public static boolean setName(String name) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_USER_NAME, name).commit();
        }
        return false;
    }

    /**
     * 获取手机号
     */
    public static String getPhone() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_TELEPHONE, "");
        }
        return "";
    }

    /**
     * 存储手机号
     */
    public static boolean setPhone(String phone) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_TELEPHONE, phone).commit();
        }
        return false;
    }



    /**
     * 获取所属园区
     */
    public static String getParkName() {
        if (getPreferences() != null) {
            return getPreferences().getString(KEY_PARK_NAME, "");
        }
        return "";
    }

    /**
     * 存储所属园区
     */
    public static boolean setParkName(String parkName) {
        if (getPreferences() != null) {
            return getPreferences().edit().putString(KEY_PARK_NAME, parkName).commit();
        }
        return false;
    }

    /**
     * 获取园区ID
     */
    public static int getParkId() {
        if (getPreferences() != null) {
            return getPreferences().getInt(KEY_PARK_ID, 0);
        }
        return 0;
    }

    /**
     * 存储园区ID
     */
    public static boolean setParkId(int parkID) {
        if (getPreferences() != null) {
            return getPreferences().edit().putInt(KEY_PARK_ID, parkID).commit();
        }
        return false;
    }

    /**
     * 获取是否为第一次安装
     */
    public static boolean getIsFirstInstall() {
        if (getPreferences() != null) {
            return getPreferences().getBoolean(KEY_FIRST_INSTALL, true);
        }
        return true;
    }

    /**
     * 存储是否为第一次安装
     */
    public static boolean setIsFirtInstall(boolean isFirst) {
        if (getPreferences() != null) {
            return getPreferences().edit().putBoolean(KEY_FIRST_INSTALL, isFirst).commit();
        }
        return false;
    }

    /**
     * 存储权限Code
     * @param set
     * @return
     */
    public static boolean setPermissionCode(Set<String> set){
        if (getPreferences() != null) {
            return getPreferences().edit().putStringSet(KEY_PERMISSION_CODE, set).commit();
        }
        return false;
    }

    /**
     * 获取权限Code
     */
    public static Set<String> getPermissionCode() {
        if (getPreferences() != null) {
            return getPreferences().getStringSet(KEY_PERMISSION_CODE, null);
        }
        return null;
    }
}
