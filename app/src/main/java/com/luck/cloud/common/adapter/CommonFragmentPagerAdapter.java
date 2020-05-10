package com.luck.cloud.common.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by liuyin on 2018/7/25
 * ViewPager加载Fragment适配器
 */

public class CommonFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    /**
     * 需要加载的fragment容器
     */
    private List<Fragment> mFragmentList;
    /**
     * tab条目标题
     */
    private List<String> titleList;

    public CommonFragmentPagerAdapter(Context context,
                                      FragmentManager fm,
                                      List<Fragment> fragmentList,
                                      List<String> titleList) {
        super(fm);
        this.mContext = context;
        this.mFragmentList = fragmentList;
        this.titleList =titleList;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titleList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
