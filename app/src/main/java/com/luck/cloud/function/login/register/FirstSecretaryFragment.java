package com.luck.cloud.function.login.register;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.login.LoginActivity;
import com.luck.cloud.function.login.LoginBean;
import com.luck.cloud.function.witness.dynamic.DynamicFragment;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.dialog.RepairsTypeDialog;

import butterknife.Bind;
import butterknife.OnClick;

public class FirstSecretaryFragment extends BaseFragment {
    @Bind(R.id.et_register_username)
    EditText etUsername;
    @Bind(R.id.et_register_unit)
    EditText etUnit;
    @Bind(R.id.et_register_school)
    EditText etSchool;
    @Bind(R.id.et_register_village)
    EditText etVillage;
    @Bind(R.id.tv_register_industry)
    TextView tvIndustry;
    @Bind(R.id.et_register_idNum)
    EditText etIdNum;
    @Bind(R.id.et_register_realName)
    EditText etRealName;
    @Bind(R.id.et_register_password)
    EditText etPassword;
    @Bind(R.id.et_register_confirm_pass)
    EditText etConfirmPass;
    @Bind(R.id.et_register_nickname)
    EditText etNickname;
    @Bind(R.id.bt_register)
    Button btRegister;

    private int type;
    private String phone;

    @Override
    protected int getContentId() {
        return R.layout.fragment_register;
    }

    public static FirstSecretaryFragment getInstance(int type, String phone) {
        FirstSecretaryFragment fragment = new FirstSecretaryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("phone", phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(Bundle bundle) {
        //1第一书记  2大学生及高校  3驻地村民  4其他人员  5高校教师  6扶贫干部
        type = getArguments().getInt("type");
        phone = getArguments().getString("phone");
        switch (type) {
            case 1:
            case 6:
                etUnit.setVisibility(View.VISIBLE);
                break;
            case 2:
            case 5:
                etSchool.setVisibility(View.VISIBLE);
                break;
            case 3:
                etVillage.setVisibility(View.VISIBLE);
                break;
            case 4:
                tvIndustry.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    private void register() {

        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPass.getText().toString();
        if (!password.equals(confirmPassword)) {
            ToastUtil.toastShortCenter("密码不一致,请重新输入");
            return;
        }


        params.clear();
        params.put("userType", type);
        params.put("peopleLoginname", etUsername.getText().toString());
        params.put("idCard", etIdNum.getText().toString());
        params.put("peopleName", etRealName.getText().toString());
        params.put("peopleMobile", phone);
        params.put("peoplePassword", etConfirmPass.getText().toString());
        params.put("nickname", etNickname.getText().toString());

        params.put("affiliatedUnit", etUnit.getText().toString());
        params.put("school", etSchool.getText().toString());
        params.put("village", etVillage.getText().toString());
        params.put("industry", tvIndustry.getText().toString());

        showRDialog();
        OKHttpManager.postJsonRequest(URLConstant.REGISTER, params, new OKHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(LoginBean response) {
                hideRDialog();
                if ("SUCCESS".equals(response.getCode())) {

                    ToastUtil.toastShortCenter("注册成功");
                    LoginActivity.start(getActivity());

                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);

    }

    @OnClick({R.id.tv_register_industry, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_industry:
                RepairsTypeDialog dialog = new RepairsTypeDialog(getActivity());
                dialog.setTypeSelector(new RepairsTypeDialog.OnTypeSelector() {
                    @Override
                    public void typeSeletor(String string) {
                        tvIndustry.setText(string);
                    }
                });
                dialog.show();
                break;
            case R.id.bt_register:
                register();
                break;
        }
    }
}
