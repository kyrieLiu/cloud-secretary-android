package com.luck.cloud.function.study;

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
import com.luck.cloud.function.active.ActiveFragment;
import com.luck.cloud.function.study.model.StudyTabModel;
import com.luck.cloud.function.witness.GardenInfoBean;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/4/11 15:54
 * Describe:TableLayout 科普
 */
public class StudyActivity extends BaseActivity {

    @Bind(R.id.mtl_science_management)
    TabLayout mMtlManagement;
    @Bind(R.id.vp_science_management)
    ViewPager mViewPager;
    @Bind(R.id.science_keyWords)
    EditText etKeyWord;

    private List<Fragment> fragmentList;

    //1学习  2科普
    private int type;

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

        type=getIntent().getIntExtra("type",1);
    }

    @Override
    protected void loadData() {
        getTabList();
    }


    /**
     * 获取园区列表
     */

    private void getTabList() {
        showRDialog();
        params.put("atType", type);
        OKHttpManager.getJoint(URLConstant.STUDY_SCIENCE_TAB, params, null, new OKHttpManager.ResultCallback<BaseListBean<StudyTabModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseListBean<StudyTabModel> response) {
                hideRDialog();
                List<StudyTabModel> list = response.getData();
                initTabs(list);

            }
        }, this);
    }

    private void initTabs(List<StudyTabModel> list) {
        if (list != null && list.size() > 0) {
            List<String> titleList = new ArrayList<>();
            fragmentList = new ArrayList<>();
            for (StudyTabModel bean : list) {
                titleList.add(bean.getCuName());
                fragmentList.add(StudyFragment.getInstance(bean,type));
            }
            CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList, titleList);
            mViewPager.setAdapter(adapter);
            if (titleList.size() > 3) {
                mMtlManagement.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            mMtlManagement.setupWithViewPager(mViewPager);

        }
    }
    @OnClick(R.id.ll_right)
    public void onViewClicked() {
        String keyWord=etKeyWord.getText().toString();
        if (fragmentList != null) {
            int currentIndex=mViewPager.getCurrentItem();
            StudyFragment fragment= (StudyFragment) fragmentList.get(currentIndex);
            fragment.searchData(keyWord);
        };


    }
}
