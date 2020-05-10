package com.luck.cloud.function.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/2/21 14:00
 * 首页功能菜单
 */
public class HomeMenuAdapter<T extends HomeMenuBean> extends BaseRecyclerViewAdapter<T> {

    public HomeMenuAdapter(List<T> list, Context context) {
        super(list, context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_grid, null);
        return new MyViewHolder(view);
    }


    class MyViewHolder extends BaseViewHolder<T> {

        @Bind(R.id.iv_item_menu_icon)
        ImageView mIvIcon;
        @Bind(R.id.tv_item_menu_name)
        TextView mTvName;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {
            mIvIcon.setImageResource(bean.getIconPath());
            mTvName.setText(bean.getMenuName());
        }
    }

}


