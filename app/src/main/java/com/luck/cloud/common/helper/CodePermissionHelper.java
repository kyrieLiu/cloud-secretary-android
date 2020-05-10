package com.luck.cloud.common.helper;

import android.util.Log;


import com.luck.cloud.utils.SpUtil;

import java.util.List;
import java.util.Set;

/**
 * Created by liuyin on 2019/6/20 14:11
 * Description:查询权限工具类
 */
public class CodePermissionHelper {
    private volatile static CodePermissionHelper permissionHelper;

    public static CodePermissionHelper getInstance() {
        if (permissionHelper == null) {
            synchronized (CodePermissionHelper.class) {
                permissionHelper = new CodePermissionHelper();
            }
        }
        return permissionHelper;
    }

    /**
     * 根据权限Code查询是否有权限
     *
     * @param code
     * @return
     */
    public static boolean havePermission(String code) {

        //先判断是不是所有角色都拥有的权限
        PermissionCode permissionCode = PermissionCode.getById(code);
        if (permissionCode.isAllRolePermission()) return true;

        Set<String> set = SpUtil.getPermissionCode();
        if (set != null) {
            return set.contains(code);
        }
        return false;
    }

    /**
     * 将没有的权限按钮去除
     *
     * @param permissionList
     * @return
     */
    public static List<String> filterPermissionList(List<String> permissionList) {
        for (int i = 0; i < permissionList.size(); i++) {
            if (!havePermission(permissionList.get(i))) {
                permissionList.remove(i);
                i--;
            }
        }
        return permissionList;
    }
}
