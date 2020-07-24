package com.luck.cloud.function.office.clock;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseRecordBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.PermissionHelper;
import com.luck.cloud.utils.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClockInActivity extends BaseActivity {

    @Bind(R.id.clock_time)
    TextView clockTime;
    @Bind(R.id.ll_clock_record)
    LinearLayout llClockRecord;
    @Bind(R.id.rl_clock_in)
    RelativeLayout rlClockIn;
    private PermissionHelper mHelper;


    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_clock;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("小云打卡");
    }

    @Override
    protected void loadData() {
        clockRecord();
    }


    /**
     * 请求内存卡读写权限
     */
    private void requestPermission() {
        mHelper = new PermissionHelper(this);
        mHelper.requestPermissions(getResources().getString(R.string.image_permission_first_hint),
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mHelper.againWarnRequestPermission(getResources().getString(R.string.image_permission_hint), ClockInActivity.this);
                    }
                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    //授权回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mHelper != null)
            mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void clockRecord(){
        showRDialog();
        params.clear();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date=format.format(new Date());
        params.put("clockDate",date);
        OKHttpManager.getJoint(URLConstant.CLOCK_RECORD, params,new int[]{1,100}, new OKHttpManager.ResultCallback<BaseRecordBean<ClockBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseRecordBean<ClockBean> response) {
                hideRDialog();
                List<ClockBean> list=response.getData().getRecords();
                if (list!=null&&list.size()>0){
                    ClockBean bean=list.get(0);
                    rlClockIn.setVisibility(View.GONE);
                    llClockRecord.setVisibility(View.VISIBLE);
                    clockTime.setText("打卡时间:  "+DateUtil.getStandardDate(bean.getClockTime()));
                }

            }
        },this);
    }


    @OnClick(R.id.rl_clock_in)
    public void onViewClicked() {
        handleClock();
    }
    private void handleClock(){
        showRDialog();
        params.clear();
//        Date date=new Date();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
//        String day=format.format(date);
//        params.put("clockDate",day);
//        params.put("clockMonth",monthFormat.format(date));
        // params.put("clockTime",day);
        OKHttpManager.postJsonRequest(URLConstant.SAVE_CLOCK, params, new OKHttpManager.ResultCallback<BaseBean>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean response) {
                hideRDialog();
                rlClockIn.setVisibility(View.GONE);
                llClockRecord.setVisibility(View.VISIBLE);
                DateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                clockTime.setText("打卡时间:  "+formatTime.format(new Date()));
            }
        },this);
    }
}
