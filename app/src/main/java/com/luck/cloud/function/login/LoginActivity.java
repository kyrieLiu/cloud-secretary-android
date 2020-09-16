package com.luck.cloud.function.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.luck.cloud.PictureMainActivity;
import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.login.register.PhoneBindActivity;
import com.luck.cloud.function.main.MainActivity;
import com.luck.cloud.manager.ActivitiesManager;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.AccountValidatorUtil;
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
    @Bind(R.id.ll_phone)
    LinearLayout llPhone;
    @Bind(R.id.rl_password)
    RelativeLayout rlPassword;
    @Bind(R.id.tv_change_type)
    TextView tvChange;
    @Bind(R.id.et_message_code)
    EditText etCode;
    @Bind(R.id.tv_send_message)
    TextView tvSendMessage;

    //登录方式  0账号密码  1手机验证码
    private int loginType=0;


    private CountDownViewModel countDownViewModel; //定时器model

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
        mEtAccount.setText(SpUtil.getLoginAcount());
        mEtPassword.setText(SpUtil.getLoginPassword());
    }



    @OnClick({R.id.bt_login_enter,R.id.tv_register,R.id.tv_change_type,R.id.tv_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bt_login_enter:
                if (loginType==1){
                    messageCodeLogin(mEtAccount.getText().toString());
                }else{
                    startLogin();
                }
                break;
            case R.id.tv_register:
                Intent intent=new Intent(this, PhoneBindActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.tv_change_type:
                if (loginType==0){
                    rlPassword.setVisibility(View.GONE);
                    llPhone.setVisibility(View.VISIBLE);
                    loginType=1;
                    mEtAccount.setHint("请输入手机号");
                    mEtAccount.setText("");
                }else{
                    rlPassword.setVisibility(View.VISIBLE);
                    llPhone.setVisibility(View.GONE);
                    loginType=0;
                    mEtAccount.setHint("请输入手机号");
                    mEtAccount.setText("");
                }
                break;
            case R.id.tv_send_message:

                sendSMS();
                break;
        }

    }

    /**
     * 做定时器发送短信
     */
    private void sendSMS() {
        String phone = mEtAccount.getText().toString();
        if (!AccountValidatorUtil.isMobile(phone)) {
            ToastUtil.toastShortCenter("请输入正确的手机号");
            return;
        }
        countDownViewModel = CountDownViewModel.getInstance();
        countDownViewModel.waitSMS(this, tvSendMessage, phone,"1");
    }

    /**
     * 验证码登录
     *
     */
    private void messageCodeLogin(final String phone) {
        if (!AccountValidatorUtil.isMobile(phone)) {
            ToastUtil.toastShortCenter("请输入正确的手机号");
            return;
        }
        String code=etCode.getText().toString();
        if (TextUtils.isEmpty(code)){
            ToastUtil.toastShortCenter("请输入验证码");
            return;
        }
        showRDialog();
        String uuid=SpUtil.getMessageCode();
        OKHttpManager.getJointObj(URLConstant.NOTE_LOGIN, null,new String[]{phone,uuid,code}, new OKHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(LoginBean response) {
                if (response.getCode().equals("SUCCESS")) {

                    operateLoginData(response,"","");

                }else{
                    ToastUtil.toastShortCenter(response.getMessage());
                }
            }
        },this);
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
        params.put("username", account+"_park");
        params.put("password", password);
        showRDialog();
        OKHttpManager.postAsyn(URLConstant.LOGIN,params, new OKHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(LoginBean response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {

                    operateLoginData(response,account,password);

                }else{
                    ToastUtil.toastShortCenter(response.getMessage());
                }
            }
        }, this);
    }


    /**
     * 处理登录返回数据
     */
    private void operateLoginData(LoginBean loginBean, String account, String password) {
        SpUtil.setIsLogin(true);
        SpUtil.setLoginAcount(account);
        SpUtil.setLoginPassword(password);
        SpUtil.setToken(loginBean.getToken());
        SpUtil.setUserId(loginBean.getUser().getPeopleId());
        SpUtil.setName(loginBean.getUser().getPeopleName());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
