package com.luck.cloud.function.mine.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.common.activity.PropertyServiceStandardSearchActivity;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.witness.GardenInfoBean;
import com.luck.cloud.function.witness.dynamic.DynamicFragment;
import com.luck.cloud.function.witness.dynamic.add.AddDynamicActivity;
import com.luck.cloud.function.witness.video.VideoFragment;
import com.luck.cloud.function.witness.video.add.AddVideoActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/4/11 15:54
 * Describe:TableLayout 科普
 */
public class MyPublishActivity extends BaseActivity {

    @Bind(R.id.mtl_common)
    TabLayout mMtlManagement;
    @Bind(R.id.vp_common)
    ViewPager mViewPager;

    private List<GardenInfoBean.ItemsBean> gardenList;

    private Context context;

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
        setTitle("我的发布");
    }

    @Override
    protected void loadData() {

        setPageData(1);
    }


    /**
     * 加载Fragment
     *
     * @param id
     */
    private void setPageData(int id) {
        List<String> titleList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        titleList.add("动态");
        titleList.add("视频");

        fragmentList.add(DynamicFragment.getInstance(3, SpUtil.getUerId()));
        fragmentList.add(VideoFragment.getInstance(3,SpUtil.getUerId()));

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

}
