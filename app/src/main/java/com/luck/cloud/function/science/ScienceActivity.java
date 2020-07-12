package com.luck.cloud.function.science;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.StudyFragment;
import com.luck.cloud.function.witness.GardenInfoBean;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/11 15:54
 * Describe:TableLayout 科普
 */
public class ScienceActivity extends BaseActivity {

    @Bind(R.id.mtl_science_management)
    TabLayout mMtlManagement;
    @Bind(R.id.vp_science_management)
    ViewPager mViewPager;

    private List<GardenInfoBean.ItemsBean> gardenList;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_study;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void loadData() {

        setPageData(1);
    }



    /**
     * 获取园区列表
     */
    private void getParkData() {
        params.clear();
        showRDialog();
        OKHttpManager.postJsonRequest(URLConstant.PARK_LIST, params, new OKHttpManager.ResultCallback<BaseBean<GardenInfoBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<GardenInfoBean> response) {
                hideRDialog();

                gardenList = response.getData().getItems();
                GardenInfoBean.ItemsBean itemsBean = gardenList.get(0);
                itemsBean.setIsChecked(1);


                setPageData(itemsBean.getId());

            }
        }, this);
    }

    /**
     * 加载Fragment
     *
     * @param id
     */
    private void setPageData(int id) {
        List<String> titleList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        titleList.add("植物");
        titleList.add("动物");
        titleList.add("蒙医药");
        titleList.add("种植技术");
        titleList.add("农业前沿");
        titleList.add("农药");
        for (int i=0;i<titleList.size();i++){
            fragmentList.add(new StudyFragment());
        }

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setTabMode(TabLayout.MODE_SCROLLABLE);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

}