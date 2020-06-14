package com.luck.cloud.function.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.function.home.HomeFragment;
import com.luck.cloud.function.mine.MineFragment;
import com.luck.cloud.function.science.ScienceFragment;
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
        HomeFragment homeFragment = new HomeFragment();
        mFragments.add(homeFragment);

        //小云见证
        ScienceFragment showFragment = new ScienceFragment();
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

}
