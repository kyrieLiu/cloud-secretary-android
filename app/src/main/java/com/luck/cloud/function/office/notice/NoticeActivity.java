package com.luck.cloud.function.office.notice;

import android.os.Bundle;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;

public class NoticeActivity extends BaseActivity {
    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("小云公告");
    }

    @Override
    protected void loadData() {

    }
}
