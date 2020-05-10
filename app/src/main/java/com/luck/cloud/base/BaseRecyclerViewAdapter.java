package com.luck.cloud.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.widget.view.LoadExceptionView;
import com.luck.cloud.widget.xrecycler.LoadingMoreFooter;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyin on 2019/2/25 10:29
 * Description:recyclerView 父类适配器
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    //list列表数据
    public List<T> list;
    public Context context;
    //点击事件接口回调
    protected OnItemClickRecyclerListener listener;

    public static final int VIEW_TYPE_EMPTY = 0;//网络返回数据异常类型
    public static final int VIEW_TYPE_ITEM = 1;//有数据的类型

    private String message;//列表加载异常或无数据的信息

    public BaseRecyclerViewAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * 绑定布局
     *
     * @param parent
     * @param viewType
     * @return 顶级ViewHolder
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //如果数据有异常,加载条目为异常页面
        if (viewType == VIEW_TYPE_EMPTY && !TextUtils.isEmpty(message)) {
            LoadExceptionView exceptionView = new LoadExceptionView(context);
            exceptionView.setExceptionMessage(message);
            return new BaseViewHolder(exceptionView) {
                @Override
                protected void bind(Object bean, int position) {
                }
            };
        }
        return onCreateBaseViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        //如果List没有数据并且有异常信息,需要加载异常信息页面
        if ((list == null || list.size() == 0) && !TextUtils.isEmpty(message)) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    /**
     * 在实现类中创建BaseViewHolder
     *
     * @return
     */
    public abstract BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定ViewHolder数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        if (list != null && list.size() > position) {
            holder.bind(list.get(position), position);
            listenParentView(holder, position);
        }
    }

    /**
     * 对条目设置点击事件回调
     *
     * @param holder
     * @param position
     */
    private void listenParentView(final BaseViewHolder holder, final int position) {
        if (listener != null) {
            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.parentView, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        // 如果List没有数据,并有有异常信息,可以展示这个异常信息
        if ((list == null || list.size() == 0) && !TextUtils.isEmpty(message)) {
            return 1;
        }
        //如果不为0，按正常的流程跑
        return list == null ? 0 : list.size();
    }


    /**
     * 刷新数据
     *
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * XRecyclerView请求成功更新列表信息
     *
     * @param respList     服务器返回的List长度
     * @param limitSize    设置的每次请求条数  如果没有分页直接传-1
     * @param reqPage      请求当前页
     * @param recyclerView 列表控件
     * @param emptyMessage 空数据提示
     */
    public void setSuccessReqList(List<T> respList, int limitSize, int reqPage, XRecyclerView recyclerView, String emptyMessage) {
        if (respList == null || respList.size() == 0) {
            this.message = emptyMessage;
            recyclerView.setRetainBottomInterval(false);
        } else {
            message = null;
            recyclerView.setRetainBottomInterval(true);
        }
        if (reqPage == 1) {
            list = respList;
        } else {
            if (list == null) list = new ArrayList<>();
            list.addAll(respList);
        }
        setXRecyclerViewProperty(respList, limitSize, recyclerView);
        notifyDataSetChanged();
        //notifyItemRangeChanged(this.list.size() > 1 ? this.list.size() - 2 : 0, list.size());
    }


    /**
     * XRecyclerView请求失败更新列表信息
     *
     * @param message      异常信息
     * @param recyclerView 列表控件
     */
    public void setErrorReqList(String message, XRecyclerView recyclerView) {
        this.message = message;
        list = null;
        setXRecyclerViewProperty(null, -1, recyclerView);
        recyclerView.setRetainBottomInterval(false);
        notifyDataSetChanged();
    }


    /**
     * RecyclerView请求成功更新列表信息
     *
     * @param respList 服务器返回的List长度
     */
    public void setSuccessReqList(List<T> respList) {
        if (respList == null || respList.size() == 0) {
            this.message = AppConstants.HTTP_NO_DATA;
        } else {
            message = null;
        }
        list = respList;
        notifyDataSetChanged();
    }

    /**
     * XRecyclerView请求失败更新列表信息
     *
     * @param message 异常信息
     */
    public void setErrorReqList(String message) {
        this.list = null;
        this.message = message;
        notifyDataSetChanged();
    }

    /**
     * 通过数据设置RecyclerView的属性
     *
     * @param respList     服务器返回的List长度
     * @param limitSize    设置的每次请求条数
     * @param recyclerView 列表控件
     */
    private void setXRecyclerViewProperty(List<T> respList, int limitSize, XRecyclerView recyclerView) {
        //如果正在下拉刷新,停止刷新
        if (recyclerView.getRefreshState() == 2) {
            recyclerView.refreshComplete();
        }
        //如果正在上拉加载,停止加载
        if (recyclerView.getLoadMoreState() == LoadingMoreFooter.STATE_LOADING) {
            recyclerView.loadMoreComplete();
        }
        //如果服务器返回数据小于设置的请求数,说明此次请求已取出最后所有数据,不必再上拉加载
        //limitSize为-1的时候表示没有分页
        if (limitSize != -1 && respList != null && respList.size() >= limitSize) {
            recyclerView.setLoadingMoreEnabled(true);
        } else {
            recyclerView.setLoadingMoreEnabled(false);
        }
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addList(List<T> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        notifyItemRangeChanged(this.list.size() > 1 ? this.list.size() - 2 : 0, list.size());
        //notifyDataSetChanged();
    }

    public List<T> getList() {
        return list;
    }

    /**
     * 添加点击事件的接口回调
     *
     * @param listener
     */
    public void setOnItemClickRecyclerAdapter(OnItemClickRecyclerListener listener) {
        this.listener = listener;
    }

//    public static class Builder{
//        private List<?> list;
//        private int size;
//        private int
//    }
}
