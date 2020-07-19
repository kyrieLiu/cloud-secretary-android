package com.luck.cloud.function.office.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.mine.work.DateUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/2/27 15:19
 * Description:待办事项
 */
public class NoticeAdapter<T extends NoticeBean> extends BaseRecyclerViewAdapter<T> {

    public NoticeAdapter(List<T> list, Context context) {
        super(list, context);
    }

    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {

        @Bind(R.id.tv_foot_name)
        TextView name;
        @Bind(R.id.tv_foot_time)
        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {

            name.setText(bean.getTitleName());
            time.setText(DateUtil.getUnderlineDay(bean.getCreateTime()));

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
