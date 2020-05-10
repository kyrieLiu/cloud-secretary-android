package com.luck.cloud.base;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;


import com.luck.cloud.R;
import com.luck.cloud.common.entity.RequestBean;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.manager.ActivitiesManager;
import com.luck.cloud.network.OKHttpManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by liuyin on 2019/3/14 14:19
 * Describe: 基类Activity
 */
public abstract class BaseActivity extends FragmentActivity {
    protected HashMap<String, Object> params = new HashMap();//装载网络请求参数
    protected int page = 1;//请求数据当前页
    protected int limit = 10;//请求数据单页条数
    private Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        ActivitiesManager.getInstance().pushActivity(this);
        initView(savedInstanceState);
        loadData();
        ImageView back = (ImageView) findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    back();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 描述：返回
     */
    protected abstract void back();

    /**
     * 加载布局文件
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化Views
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 加载数据
     */
    protected abstract void loadData();


    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }


    /**
     * 描述：设置标题
     *
     * @param text 标题
     */
    protected void setTitle(String text) {
        TextView tv_title = (TextView) findViewById(R.id.title);
        if (tv_title != null) {
            tv_title.setText(text);
        }
    }

    /**
     * 描述:显示右菜单全部
     */
    protected void rightVisible(String title, int drawable) {
        rightVisible(title);
        rightVisible(drawable);
    }

    /**
     * 描述:隐藏返回按钮
     */
    protected void backGone() {
        ImageView back = (ImageView) findViewById(R.id.back);
        if (back != null) {
            back.setVisibility(View.GONE);
        }
    }

    /**
     * 描述:显示右菜单文字
     */
    protected void rightVisible(String title) {
        LinearLayout ll_right = (LinearLayout) findViewById(R.id.ll_right);
        if (ll_right != null) {
            ll_right.setVisibility(View.VISIBLE);
        }
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        if (tv_right != null) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(title);
        }
    }

    /**
     * 描述:显示右菜单图片
     */
    protected void rightVisible(int drawable) {
        LinearLayout ll_right = (LinearLayout) findViewById(R.id.ll_right);
        if (ll_right != null) {
            ll_right.setVisibility(View.VISIBLE);
        }
        ImageView iv_right = (ImageView) findViewById(R.id.iv_right);
        if (iv_right != null) {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(drawable);
        }
    }

    /**
     * 初始化请求参数
     */
    protected RequestBean initRequestParams() {
        RequestBean requestBean = new RequestBean();
        RequestBean.CondBean condBean = new RequestBean.CondBean();
        condBean.setGroups(new ArrayList());
        condBean.setRules(new ArrayList());
        requestBean.setCond(condBean);
        RequestBean.PageableBean pageableBean = new RequestBean.PageableBean();
        requestBean.setPageable(pageableBean);
        return requestBean;
    }

    public void showRDialog() {

        if (loadingDialog == null) {
            loadingDialog = new Dialog(this, R.style.custom_dialog_style);
            View dialogView = View.inflate(this, R.layout.common_waiting_dialog, (ViewGroup) null);
            loadingDialog.setContentView(dialogView);
            loadingDialog.setCanceledOnTouchOutside(false);//点击空白是否消失
        }
        if (!isFinishing() && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideRDialog() {
        if (loadingDialog != null) {
            if (!isFinishing() && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
        loadingDialog = null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }

        ActivitiesManager.getInstance().popCurrentActivity(this);

        OKHttpManager.cancelTag(this);

    }


    /**
     * 清理临时压缩的图片和语音
     */
    public void deleteCacheDir() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), AppConstants.SD_PATH);
            deleteDirWihtFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

}
