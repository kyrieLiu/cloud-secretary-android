package com.luck.cloud.callback;

/**
 * Created by liuyin on 2019/5/16 15:59
 * Description:  XRecyclerView下拉刷新,上拉加载监听回调
 */
public interface OnRecyclerLoadingListener {
    /**
     * XRecyclerView下拉刷新回调
     */
    void onRefresh();

    /**
     * XRecyclerView上拉加载回调
     * @param reqPage  当前页
     */
    void onLoadMore(int reqPage);
}