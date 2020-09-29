package com.luck.cloud.function.login;

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
import com.luck.cloud.function.login.register.FirstSecretaryFragment;
import com.luck.cloud.function.witness.GardenInfoBean;
import com.luck.cloud.function.witness.dynamic.DynamicFragment;
import com.luck.cloud.function.witness.video.VideoFragment;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.mtl_common)
    TabLayout mMtlManagement;
    @Bind(R.id.vp_common)
    ViewPager mViewPager;

    private List<GardenInfoBean.ItemsBean> gardenList;

    private Context context;
    private String phone="";

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
        setTitle("注册");
    }

    @Override
    protected void loadData() {

        phone=getIntent().getStringExtra("phone");

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
        titleList.add("第一书记");
        titleList.add("大学生及高校");
        titleList.add("驻地村民");
        titleList.add("高校教师");
        titleList.add("扶贫干部");
        titleList.add("其他人员");


        fragmentList.add(FirstSecretaryFragment.getInstance(1,phone));
        fragmentList.add(FirstSecretaryFragment.getInstance(2,phone));
        fragmentList.add(FirstSecretaryFragment.getInstance(3,phone));
        fragmentList.add(FirstSecretaryFragment.getInstance(5,phone));
        fragmentList.add(FirstSecretaryFragment.getInstance(6,phone));
        fragmentList.add(FirstSecretaryFragment.getInstance(4,phone));

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setTabMode(TabLayout.MODE_SCROLLABLE);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

}
