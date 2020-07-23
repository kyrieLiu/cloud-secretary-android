package com.luck.cloud.function.mine.dynamic;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.witness.GardenInfoBean;
import com.luck.cloud.function.witness.dynamic.DynamicFragment;
import com.luck.cloud.function.witness.video.VideoFragment;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/11 15:54
 * Describe:TableLayout 科普
 */
public class DynamicActivity extends BaseActivity {

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
        setTitle("动态");
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

        fragmentList.add(DynamicFragment.getInstance(5,0));
        fragmentList.add(VideoFragment.getInstance(5,0));

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

}
