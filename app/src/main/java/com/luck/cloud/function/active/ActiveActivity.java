package com.luck.cloud.function.active;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.science.GardenInfoBean;
import com.luck.cloud.function.science.RecommendFragment;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.dialog.DateSelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/4/11 15:54
 * Describe:TableLayout 我的活动
 */
public class ActiveActivity extends BaseActivity {

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
        return R.layout.activity_active;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("小云活动");
    }

    @Override
    protected void loadData() {

        rightVisible(R.mipmap.add);

        setPageData(1);
    }

    @OnClick({R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                Intent intent = new Intent(this, AddActiveActivity.class);
                startActivity(intent);
                break;
        }
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
        titleList.add("全部活动");
        titleList.add("已报名活动");
        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.add(new ActiveFragment());
        }

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

}
