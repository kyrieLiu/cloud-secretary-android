package com.luck.cloud.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/5/27 11:10
 * Describe: 修改个人信息页面
 */
public class ModifyActivity extends BaseActivity {

    @Bind(R.id.et_modify_content)
    EditText mEtContent;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("修改姓名");
        rightVisible("保存");
    }

    @Override
    protected void loadData() {
        InputFilter[] addressFilter = {new InputFilter.LengthFilter(20)};
        mEtContent.setFilters(addressFilter);
        mEtContent.setHint("请输入姓名");
    }

    @OnClick({R.id.tv_right, R.id.iv_modify_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                String content = mEtContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.toastShortCenter("请输入姓名");
                    return;
                }
                updateUserInfo(content);
                break;
            case R.id.iv_modify_delete:
                mEtContent.setText("");
                break;
        }
    }

    /**
     * 更新用户信息
     *
     * @param content
     */
    private void updateUserInfo(final String content) {
        params.clear();
        params.put("name", content);
        showRDialog();
        OKHttpManager.postJsonRequest(URLConstant.UPDATE_USER_INFO, params, new OKHttpManager.ResultCallback<BaseBean>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean response) {
                hideRDialog();
                SpUtil.setName(content);
                Intent intent = new Intent();
                intent.putExtra("content", content);
                setResult(10, intent);
                finish();
            }
        }, this);
    }
}
