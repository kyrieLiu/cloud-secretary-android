package com.luck.cloud.function.mine.footprint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.mine.WaitDoneBean;

import java.util.List;

/**
 * Created by liuyin on 2019/2/27 15:19
 * Description:待办事项
 */
public class FootPrintAdapter<T extends WaitDoneBean.ItemsBean> extends BaseRecyclerViewAdapter<T> {

    public FootPrintAdapter(List<T> list, Context context) {
        super(list, context);
    }

    public FootPrintAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_foot_print, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
//        @Bind(R.id.tv_item_wait_done_status)
//        TextView mTvStatus;
//        @Bind(R.id.rl_item_wait_done_root)
//        RelativeLayout mRlRoot;
//        @Bind(R.id.tv_item_wait_done_type)
//        TextView mTvType;
//        //事项名称
//        @Bind(R.id.tv_item_wait_done_name)
//        TextView mTvName;
//        //时间
//        @Bind(R.id.tv_item_wait_done_time)
//        TextView mTvTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {

//
//            String status = bean.getTypeStatus();
//            if (!TextUtils.isEmpty(status)) {
//                switch (status) {
//                    case "待预审":
//                        mTvStatus.setTextColor(Color.parseColor("#13AE85"));
//                        break;
//                    case "待反馈":
//                        mTvStatus.setTextColor(Color.parseColor("#0AC87A"));
//                        break;
//                    case "待验收":
//                        mTvStatus.setTextColor(Color.parseColor("#53db4f"));
//                        break;
//                    case "储备中":
//                        mTvStatus.setTextColor(Color.parseColor("#28DEC9"));
//                        break;
//                    case "待报审":
//                        mTvStatus.setTextColor(Color.parseColor("#28DEC9"));
//                        break;
//                    case "待终审":
//                        mTvStatus.setTextColor(Color.parseColor("#51A8FF"));
//                        break;
//
//                }
//            }
//            mTvStatus.setText(status);
//
//            mTvName.setText(bean.getTaskName());
//            mTvType.setText(bean.getTypeName());
//
//            mTvTime.setText(bean.getGmtCreate());
//
//            if (context instanceof MainActivity) {
//                mRlRoot.setBackground(null);
//            }
        }


    }
}
