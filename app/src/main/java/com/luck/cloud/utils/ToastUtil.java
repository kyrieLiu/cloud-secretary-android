package com.luck.cloud.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;


public class ToastUtil {
    private static Toast toast;
    private static View toastView;

    public static void toastShortCenter(String msg) {
        if (TextUtils.isEmpty(msg)) msg = "";
        toast = Toast.makeText(AppApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void toastLongCenter(String msg) {
        if (TextUtils.isEmpty(msg)) msg = "";
        toast = Toast.makeText(AppApplication.getInstance(), msg, Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 自定义吐司布局
     *
     * @param message
     */
    public static void toastView(String message) {
        if (TextUtils.isEmpty(message)) return;
        if (toastView == null) {
            toastView = LayoutInflater.from(AppApplication.getInstance()).inflate(R.layout.toast_center, null);
        }
        TextView textView = toastView.findViewById(R.id.toast_msg);
        textView.setText(message);
        Toast toast = new Toast(AppApplication.getInstance().getApplicationContext());
        toast.setView(toastView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 提示无权限
     */
    public static void showNoPermission() {
        String message = "app在一期不对集团其他部门员工开放";
        toast = Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_LONG);
        toast.setText(message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
