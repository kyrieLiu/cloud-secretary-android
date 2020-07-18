package com.luck.cloud.function.active;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseListBean;
import com.luck.cloud.common.adapter.CommonFragmentPagerAdapter;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.model.StudyTabModel;
import com.luck.cloud.function.witness.GardenInfoBean;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

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
    @Bind(R.id.active_keyWords)
    EditText keyWords;

    private ActiveFragment allActive;
    private ActiveFragment myActive;
    private List<Fragment> fragmentList = new ArrayList<>();

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

        setPageData();

    }

    @OnClick({R.id.iv_right, R.id.iv_handle_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                Intent intent = new Intent(this, AddActiveActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.iv_handle_search:
                int currentIndex = mViewPager.getCurrentItem();
                String keyword = keyWords.getText().toString();
                ActiveFragment fragment = (ActiveFragment) fragmentList.get(currentIndex);
                fragment.searchData(keyword);
                break;
        }
    }


    /**
     * 加载Fragment
     */
    private void setPageData() {
        List<String> titleList = new ArrayList<>();
        titleList.add("全部活动");
        titleList.add("已报名活动");
        allActive = ActiveFragment.getInstance(1);
        myActive = ActiveFragment.getInstance(2);
        fragmentList.add(allActive);
        fragmentList.add(myActive);

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mMtlManagement.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==100){
            allActive.searchData(null);
        }
    }
}
