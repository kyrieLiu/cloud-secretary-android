package com.luck.cloud.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.common.entity.PropertyServiceStandardBean;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/17 9:45
 * Description:物业服务标准列表
 */
public class PropertyServiceStandardAdapter<T extends PropertyServiceStandardBean.ItemsBean> extends BaseRecyclerViewAdapter<T> {

    public PropertyServiceStandardAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property_service_standard, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
        @Bind(R.id.tv_item_service_standard_title)
        TextView mTvTitle;
        @Bind(R.id.tv_item_service_standard_belongProject)
        TextView mTvBelongProject;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {
            mTvTitle.setText(bean.getTitle());
            mTvBelongProject.setText("所属项目:" + bean.getAcParkName());
        }
    }
}
