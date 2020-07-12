package com.luck.cloud.function.login.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.function.witness.dynamic.DynamicFragment;
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
    @Bind(R.id.et_register_phone)
    EditText etPhone;
    @Bind(R.id.et_register_password)
    EditText etPassword;
    @Bind(R.id.et_register_confirm_pass)
    EditText etConfirmPass;
    @Bind(R.id.et_register_nickname)
    EditText etNickname;
    @Bind(R.id.bt_register)
    Button btRegister;

    private int type;

    @Override
    protected int getContentId() {
        return R.layout.fragment_register;
    }

    public static FirstSecretaryFragment getInstance(int type) {
        FirstSecretaryFragment fragment = new FirstSecretaryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(Bundle bundle) {
        //0第一书记  1大学生及高校  2驻地村民  3其他人员
        type = getArguments().getInt("type");
        switch (type){
            case 0:
                etUnit.setVisibility(View.VISIBLE);
                break;
            case 1:
                etSchool.setVisibility(View.VISIBLE);
                break;
            case 2:
                etVillage.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvIndustry.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.tv_register_industry, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_industry:
                RepairsTypeDialog dialog=new RepairsTypeDialog(getActivity());
                dialog.setTypeSelector(new RepairsTypeDialog.OnTypeSelector() {
                    @Override
                    public void typeSeletor(String string) {
                        tvIndustry.setText(string);
                    }
                });
                dialog.show();
                break;
            case R.id.bt_register:
                break;
        }
    }
}
