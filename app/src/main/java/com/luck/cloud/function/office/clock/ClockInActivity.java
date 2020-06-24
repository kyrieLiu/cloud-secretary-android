package com.luck.cloud.function.office.clock;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.utils.PermissionHelper;

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

    public AMapLocationClient mLocationClient = null;

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
        requestPermission();
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
                        startLocation();
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

    private void startLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.d("tag", "位置" + aMapLocation.getCity());
            }
        });
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }


    @OnClick(R.id.rl_clock_in)
    public void onViewClicked() {
        rlClockIn.setVisibility(View.GONE);
        llClockRecord.setVisibility(View.VISIBLE);
        clockTime.setText("打卡时间:  2020-06-25");
    }
}
