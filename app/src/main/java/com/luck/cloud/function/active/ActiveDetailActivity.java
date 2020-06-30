package com.luck.cloud.function.active;

import android.os.Bundle;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;

public class ActiveDetailActivity extends BaseActivity {
    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail_active;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("活动详情");
    }

    @Override
    protected void loadData() {

    }
}
