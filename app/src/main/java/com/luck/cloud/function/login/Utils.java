package com.luck.cloud.function.login;

/**
 * Created by jiang_yan on 2017/9/30.
 */

import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Utils {
    private Dialog dialog;
    private static Utils utils = null;
    private int codeNum;
    private int common_waiting_dialog;
    private int tv_common_waiting_dialog_content;
    private int custom_dialog_style;
    private Application application;



    public Utils() {
    }

    public static Utils getUtils() {
        if (utils == null) {
            synchronized (Utils.class){
                utils = new Utils();
            }
        }

        return utils;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void info(int common_waiting_dialog, int tv_common_waiting_dialog_content, int custom_dialog_style) {
        this.common_waiting_dialog = common_waiting_dialog;
        this.tv_common_waiting_dialog_content = tv_common_waiting_dialog_content;
        this.custom_dialog_style = custom_dialog_style;
    }

    public void showProgressDialog(Context context) {

        if (context != null) {
            if (this.dialog == null ) {
                this.dialog = new Dialog(context, this.custom_dialog_style);
                View dialogView = View.inflate(context, this.common_waiting_dialog, (ViewGroup) null);

                this.dialog.setContentView(dialogView);
                //this.dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);//点击空白是否消失
            }
            if ( !this.dialog.isShowing()){
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

    public static int dip2px(Context context, float dpValue) {

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public int dip2px(float dpValue) {

        float scale = this.application.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);

    }

    public int getDimen(Context context, int dimen) {
        return context.getResources().getDimensionPixelOffset(dimen);
    }

    public int getDimen(int dimen) {
        return this.application.getResources().getDimensionPixelOffset(dimen);
    }

    public Bitmap toRoundCorner(Bitmap bitmap) {

        int pSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(pSize, pSize, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = Color.GRAY;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, pSize, pSize);
        RectF rectF = new RectF(rect);
        float roundPx = (float) (pSize / 2);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

    private void dialog(Context context) {
        Builder builder = new Builder(context);
        builder.setCancelable(false);
        builder.setTitle("");
        builder.setMessage("");
        builder.setPositiveButton("1", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNeutralButton("2", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        builder.setNegativeButton("3", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


    public String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception var3) {
            return null;
        }
    }

    public int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception var3) {
            return -1;
        }
    }
}
