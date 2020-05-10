package com.luck.cloud.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * Created by liuyin on 2019/2/25 10:35
 * Description:recyclerView适配器父级ViewHolder
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    //条目布局
    public View parentView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        parentView = itemView;
        ButterKnife.bind(this, itemView);
    }

    /**
     * 获将数据绑定到对应的布局控件
     *
     * @param bean
     * @param position
     */
    protected abstract void bind(T bean, int position) ;

}
