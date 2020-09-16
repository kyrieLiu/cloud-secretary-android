package com.luck.cloud.function.login.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.login.CountDownViewModel;
import com.luck.cloud.function.login.RegisterActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.AccountValidatorUtil;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/5/27 11:10
 * Describe: 修改个人信息页面
 */
public class PhoneBindActivity extends BaseActivity {

    @Bind(R.id.et_modify_content)
    EditText mEtContent;
    @Bind(R.id.tv_send_message)
    TextView tvSend;
    @Bind(R.id.bt_phone_confirm)
    Button btConfirm;
    @Bind(R.id.et_message_code)
    EditText etCode;
    private String title;
    private String content;
    private CountDownViewModel countDownViewModel; //定时器model

    //1注册绑定手机号  2修改手机号
    private int type=1;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("绑定手机号");
    }

    @Override
    protected void loadData() {
        type=getIntent().getIntExtra("type",1);
        if (type==1){
            btConfirm.setText("下一步");
        }else{
            btConfirm.setText("保存");
        }
    }

    @OnClick({R.id.iv_modify_delete,R.id.bt_phone_confirm,R.id.tv_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_modify_delete:
                mEtContent.setText("");
                break;
            case R.id.tv_send_message:
                sendSMS();
                break;
            case R.id.bt_phone_confirm:
               if (type==1){
                   validateCode(mEtContent.getText().toString(),etCode.getText().toString());
               }else{
                   Intent intent=new Intent();
                   intent.putExtra("content",mEtContent.getText().toString());
                   setResult(100,intent);
                   finish();
               }
                break;
        }
    }

    /**
     * 做定时器发送短信
     */
    private void sendSMS() {
        String phone = mEtContent.getText().toString();
        if (!AccountValidatorUtil.isMobile(phone)) {
            ToastUtil.toastShortCenter("请输入正确的手机号");
            return;
        }
        countDownViewModel = CountDownViewModel.getInstance();
        countDownViewModel.waitSMS(this, tvSend, phone);
    }

    /**
     * 校验验证码
     *
     * @param code
     */
    private void validateCode(final String phone,String code) {
        Intent intent=new Intent(PhoneBindActivity.this, RegisterActivity.class);
        intent.putExtra("phone",phone);
        startActivity(intent);
//        params.clear();
//        params.put("phone", phone);
//        params.put("code", code);
//        showRDialog();
//        OKHttpManager.postJsonRequest(URLConstant.UPDATE_USER_INFO, params, new OKHttpManager.ResultCallback<BaseBean>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//            }
//
//            @Override
//            public void onResponse(BaseBean response) {
//                Intent intent=new Intent(PhoneBindActivity.this, RegisterActivity.class);
//                intent.putExtra("phone",phone);
//                startActivity(intent);
//            }
//        }, this);
    }
}
