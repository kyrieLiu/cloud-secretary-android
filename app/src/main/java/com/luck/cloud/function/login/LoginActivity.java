package com.luck.cloud.function.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.luck.cloud.PictureMainActivity;
import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.manager.ActivitiesManager;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/2/28 18:17
 *
 * @Describe 登录页
 */
public class LoginActivity extends BaseActivity {
    //手机号
    @Bind(R.id.et_login_account)
    EditText mEtAccount;
    //密码
    @Bind(R.id.et_login_password)
    EditText mEtPassword;
    //登录
    @Bind(R.id.bt_login_enter)
    Button mBtLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    private String account;
    private String password;
    //是否为手机号的表达式
    private String REGEX_MOBILE = "^1\\d{10}$";

    /**
     * 调用该方法是应用内因token或主动退出登录,需要将栈内页面都销毁
     *
     * @param context
     */
    public static void start(Context context) {
        SpUtil.setIsLogin(false);
        Intent intent = new Intent(AppApplication.getInstance(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        AppApplication.getInstance().startActivity(intent);
        ActivitiesManager.getInstance().popOtherActivity(LoginActivity.class);
    }

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {//将导航栏设置为透明色
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void loadData() {
    }


    /**
     * OA用户登录
     */
    private void oaLogin(final String userName, String token) {
        params.clear();
        params.put("loginName", userName);
        params.put("token", token);
        params.put("os", 2);
        params.put("idEntity", 2);
        mBtLogin.setEnabled(false);
        showRDialog();
        OKHttpManager.postJsonNoToken(URLConstant.OA_LOGIN, params, new OKHttpManager.ResultCallback<BaseBean<LoginBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                mBtLogin.setEnabled(true);
            }

            @Override
            public void onResponse(BaseBean<LoginBean> response) {
                hideRDialog();

                operateLoginData(response.getData(), userName, "");
            }
        }, this);
    }


    @OnClick({R.id.bt_login_enter,R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bt_login_enter:
                startLogin();
                break;
            case R.id.tv_register:
                break;
        }

    }

    private void startLogin(){
        final String account = mEtAccount.getText().toString();
        final String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.toastShortCenter("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toastShortCenter("请输入密码");
            return;
        }
        params.clear();
        params.put("username", account);
        params.put("password", password);
        params.put("imageCode", "HACD");
//        params.put("os", 2);
//        params.put("idEntity", 2);
        Intent intent = new Intent(this, PictureMainActivity.class);
        startActivity(intent);
        finish();
      /*  showRDialog();
        OKHttpManager.getAsyn(URLConstant.LOGIN, new OKHttpManager.ResultCallback<BaseBean<LoginBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<LoginBean> response) {
                hideRDialog();
                LoginBean loginBean =response.getData();
                if (loginBean.getApiResource()==null||loginBean.getApiResource().size()==0){
                    ToastUtil.toastShortCenter("当前账号暂无登录权限");
                    return;
                }
                operateLoginData(response.getData(), account, password);
            }
        }, this);*/
    }


    /**
     * 处理登录返回数据
     */
    private void operateLoginData(LoginBean loginBean, String account, String password) {
        SpUtil.setIsLogin(true);
        SpUtil.setLoginAcount(account);
        SpUtil.setLoginPassword(password);
        SpUtil.setToken(loginBean.getToken());
        SpUtil.setJob(loginBean.getNameDepart());
        //存储权限CODE
        SpUtil.setPermissionCode(loginBean.getApiResource());

        Intent intent = new Intent(this, PictureMainActivity.class);
        startActivity(intent);
        finish();
    }
}
