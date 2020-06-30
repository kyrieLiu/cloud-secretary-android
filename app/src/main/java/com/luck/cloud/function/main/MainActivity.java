package com.luck.cloud.function.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.function.home.HomeFragment;
import com.luck.cloud.function.mine.MineFragment;
import com.luck.cloud.function.science.WitnessFragment;
import com.luck.cloud.manager.ActivitiesManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/2/15 17:46
 *
 * @Describe 首页布局
 */
public class MainActivity extends BaseActivity {
    //fragment容器
    @Bind(R.id.fl_home_container)
    FrameLayout mFlContainer;
    //控制器容器
    @Bind(R.id.rg_tab)
    RadioGroup mRgTab;
    private long exitTime = 0;

    private List<Fragment> mFragments;//存放需要加载的fragment

    private int mIndex;

    private HomeFragment homeFragment;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public class MyLocationListener extends BDAbstractLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location){
            Log.d("tag","百度地图定位"+location.getCity());
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }

    @Override
    protected void back() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ActivitiesManager.getInstance().popAllActivity();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tab_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

//        mLocationClient = new LocationClient(getApplicationContext());
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
//
//        LocationClientOption option = new LocationClientOption();
//
//        option.setIsNeedAddress(true);
////可选，是否需要地址信息，默认为不需要，即参数为false
////如果开发者需要获得当前点的地址信息，此处必须为true
//
//        option.setNeedNewVersionRgc(true);
////可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
//
//        mLocationClient.setLocOption(option);
//
//        mLocationClient.start();

//
//        if (!SpUtil.getIsLogin()) {
//            LoginActivity.start(this);
//        }

        //SpUtil.setToken("customer95478d66d8b54f2fb1a9f2861a927fa4");

        //将导航栏设置为透明色
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @Override
    protected void loadData() {
        mFragments = new ArrayList<>();
        //首页
        homeFragment = new HomeFragment();
        mFragments.add(homeFragment);

        //小云见证
        WitnessFragment showFragment = new WitnessFragment();
        mFragments.add(showFragment);
        //活动
        MineFragment mineFragment = new MineFragment();
        mFragments.add(mineFragment);

        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.fl_home_container, mFragments.get(0)).commit();
    }

    @OnClick({R.id.rb_tab_home, R.id.rb_tab_video, R.id.rb_tab_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_tab_home:
                setIndexSelected(0);
                break;
            case R.id.rb_tab_video:
                setIndexSelected(1);
                break;
            case R.id.rb_tab_mine:
                setIndexSelected(2);
                break;
        }

    }

    /**
     * 切换Fragment
     *
     * @param index
     */
    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments.get(mIndex));
        //判断是否添加
        if (!mFragments.get(index).isAdded()) {
            ft.add(R.id.fl_home_container, mFragments.get(index)).show(mFragments.get(index));
        } else {
            ft.show(mFragments.get(index));
        }

        ft.commit();
        //再次赋值
        mIndex = index;

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d("tag","定位权限回调");
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("tag","定位权限回调");
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homeFragment=null;
    }
}
