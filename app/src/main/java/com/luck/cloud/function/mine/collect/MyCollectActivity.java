package com.luck.cloud.function.mine.collect;

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
public class MyCollectActivity extends BaseActivity {

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
        setTitle("我的收藏");
    }

    @Override
    protected void loadData() {

        setPageData(1);
    }

//    @OnClick({R.id.science_keyWords,R.id.iv_right})
//    public void onClick(View view) {
//        Intent intent=new Intent();
//        switch (view.getId()) {
//            case R.id.science_keyWords:
//                intent.setClass(this, PropertyServiceStandardSearchActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.iv_right:
//                int currentItem=mViewPager.getCurrentItem();
//                if (currentItem==0){
//                    intent.setClass(this, AddDynamicActivity.class);
//                    startActivity(intent);
//                }else{
//                    intent.setClass(this, AddVideoActivity.class);
//                    startActivity(intent);
//                }
//                break;
//        }
//    }


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
        titleList.add("动态");
        titleList.add("视频");

        fragmentList.add(DynamicFragment.getInstance(2,0));
        fragmentList.add(VideoFragment.getInstance(2,0));

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

}
