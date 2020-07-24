package com.luck.cloud.function.mine.footprint;

import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseListBean;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.StudyFragment;
import com.luck.cloud.function.study.model.StudyTabModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/4/11 15:54
 * Describe:TableLayout 科普
 */
public class FootPrintActivity extends BaseActivity {

    @Bind(R.id.mtl_common)
    TabLayout mMtlManagement;
    @Bind(R.id.vp_common)
    ViewPager mViewPager;

    private List<Fragment> fragmentList;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.common_tab_viewpager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("我的足迹");
    }

    @Override
    protected void loadData() {
        initTabs();
    }



    private void initTabs() {
            List<String> titleList = new ArrayList<>();
            titleList.add("小云学习");
            titleList.add("小云科普");
            fragmentList = new ArrayList<>();
            FootPrintFragment studyFragment=FootPrintFragment.getInstance(1);
            FootPrintFragment scienceFragment=FootPrintFragment.getInstance(2);
            fragmentList.add(studyFragment);
            fragmentList.add(scienceFragment);
            CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
            mViewPager.setAdapter(adapter);
            mMtlManagement.setupWithViewPager(mViewPager);

    }
}
