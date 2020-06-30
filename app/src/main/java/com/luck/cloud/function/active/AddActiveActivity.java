package com.luck.cloud.function.active;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.widget.dialog.DateSelectDialog;

import butterknife.Bind;
import butterknife.OnClick;

public class AddActiveActivity extends BaseActivity {
    @Bind(R.id.startTime)
    TextView startTime;
    @Bind(R.id.endTime)
    TextView endTime;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_active;
    }

    @OnClick({R.id.startTime,R.id.endTime,R.id.bt_initiate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startTime:
                DateSelectDialog dialog = new DateSelectDialog(this);
                dialog.setListener(new DateSelectDialog.OnSelectListener() {
                    @Override
                    public void callback(String date) {
                        startTime.setText(date);
                    }
                });
                dialog.show();
                break;
            case R.id.endTime:
                DateSelectDialog endDialog = new DateSelectDialog(this);
                endDialog.setListener(new DateSelectDialog.OnSelectListener() {
                    @Override
                    public void callback(String date) {
                        endTime.setText(date);
                    }
                });
                endDialog.show();
                break;
            case R.id.bt_initiate:
                finish();
                break;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("发起活动");
    }

    @Override
    protected void loadData() {

    }
}
