package com.luck.cloud.function.mine.work;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.widget.MyListView;
import com.luck.cloud.widget.MyScrollView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2018/12/19 16:46
 *
 * @Describe 维修指派记录
 */
public class MaintenanceDesignateOrderAdapter<T extends WorkerOrderListBean> extends BaseListAdapter<T> {

    private Context context;
    private MyScrollView scrollView;

    public MaintenanceDesignateOrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();

    }


    @Override
    protected BaseViewHolder<T> loadView(Context context, T var2, int var3) {
        return new ViewHolder(context, getViewId(var2, var3));
    }

    @Override
    protected int getViewId(T var1, int var2) {
        return R.layout.item_repair_designate_persons;
    }

    class ViewHolder extends BaseViewHolder<T> {

        @Bind(R.id.lv_item_designate_persons_work)
        MyListView mLvWork;
        @Bind(R.id.iv_item_designate_persons_hide_show)
        ImageView mIvHideShow;
        @Bind(R.id.rl_item_repair_design_person_parent)
        RelativeLayout mRlPersonParent;
        @Bind(R.id.tv_item_calendar_order_name)
        TextView mTvName;

        protected ViewHolder(Context context, int layoutID) {
            super(context, layoutID);
        }

        @Override
        protected void prepareData() {
            final WorkerOrderListBean bean = list.get(position);
            mTvName.setText(bean.getName());
            final List<WorkerOrderListBean.DateWorkOrderListDTOListBean> childList = bean.getDateWorkOrderListDTOList();
            if (childList != null && list.size() > 0) {
                PersonWorkAdapter adapter = new PersonWorkAdapter(context, childList);
                if (bean.isShowDetail()) {
                    adapter.setShowCount(childList.size());
                } else {
                    adapter.setShowCount(0);
                }

                mLvWork.setAdapter(adapter);
                mLvWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        WorkerOrderListBean.DateWorkOrderListDTOListBean childBean = childList.get(i);
//                        Intent intent = new Intent(context, RepairListDetailActivity.class);
//                        intent.putExtra("id", childBean.getId());
//                        intent.putExtra("status", childBean.getStatus());
//                        intent.putExtra("isSeeRecord", 0);
//                        context.startActivity(intent);
                    }
                });
                mRlPersonParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (bean.isShowDetail()) {
                            bean.setShowDetail(false);
                            adapter.setShowCount(0);
                            adapter.notifyDataSetChanged();
                            notifyDataSetChanged();
                            mIvHideShow.setImageResource(R.mipmap.icoshow);
                        } else {
                            bean.setShowDetail(true);
                            adapter.setShowCount(childList.size());
                            adapter.notifyDataSetChanged();
                            notifyDataSetChanged();
                            mIvHideShow.setImageResource(R.mipmap.icohide);
                        }
                    }
                });
            }

        }
    }


}
