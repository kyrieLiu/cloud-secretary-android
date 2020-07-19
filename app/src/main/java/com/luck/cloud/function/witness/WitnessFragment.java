package com.luck.cloud.function.witness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.common.activity.PropertyServiceStandardSearchActivity;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.witness.dynamic.DynamicFragment;
import com.luck.cloud.function.witness.dynamic.add.AddDynamicActivity;
import com.luck.cloud.function.witness.video.VideoFragment;
import com.luck.cloud.function.witness.video.add.AddVideoActivity;
import com.luck.cloud.network.OKHttpManager;
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
public class WitnessFragment extends BaseFragment {

    @Bind(R.id.mtl_science_management)
    TabLayout mMtlManagement;
    @Bind(R.id.vp_science_management)
    ViewPager mViewPager;

    private List<GardenInfoBean.ItemsBean> gardenList;

    private DynamicFragment dynamicFragment;
    private VideoFragment videoFragment;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_witness;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

        setPageData(1);
    }

    @OnClick({R.id.science_keyWords,R.id.iv_right})
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.science_keyWords:
                intent.setClass(Objects.requireNonNull(getActivity()), PropertyServiceStandardSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_right:
                int currentItem=mViewPager.getCurrentItem();
                if (currentItem==0){
                    intent.setClass(Objects.requireNonNull(getActivity()), AddDynamicActivity.class);
                    startActivityForResult(intent,100);
                }else{
                    intent.setClass(Objects.requireNonNull(getActivity()), AddVideoActivity.class);
                    startActivityForResult(intent,100);
                }
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
        titleList.add("动态");
        titleList.add("视频");

        dynamicFragment=new DynamicFragment();
        videoFragment=new VideoFragment();

        fragmentList.add(dynamicFragment);
        fragmentList.add(videoFragment);

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(getContext(), getChildFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (resultCode==100){
                dynamicFragment.refreshData(null);
            }else if (resultCode==200){
                videoFragment.refreshData();
            }
        }
    }
}
