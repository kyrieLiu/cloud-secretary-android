package com.luck.cloud.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.luck.cloud.R;


/**
 * Created by liuyin on 2019/2/28 21:04
 * @Describe loading工具
 */

public class LoadingDialogUtils {
    private Dialog dialog;
    private static LoadingDialogUtils utils = null;

    public LoadingDialogUtils() {
    }

    public static LoadingDialogUtils getUtils() {
        if (utils == null) {
            synchronized (LoadingDialogUtils.class) {
                utils = new LoadingDialogUtils();
            }
        }

        return utils;
    }


    public void showProgressDialog(Context context) {

        if (context != null) {
            if (this.dialog == null) {
                this.dialog = new Dialog(context, R.style.custom_dialog_style);
                View dialogView = View.inflate(context, R.layout.common_waiting_dialog, (ViewGroup) null);

                this.dialog.setContentView(dialogView);
                //this.dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);//点击空白是否消失
            }
            if (!this.dialog.isShowing()) {
                this.dialog.show();
            }
        }

    }


    public void dismissDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.dialog = null;

    }


}
