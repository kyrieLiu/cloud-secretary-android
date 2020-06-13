package com.luck.cloud.function.science;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:科普列表适配器
 */
public class ScienceAdapter<T extends SuperviseHandleBean.ItemsBean> extends BaseRecyclerViewAdapter<T> {


    public ScienceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_science, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
//        @Bind(R.id.tv_item_supervise_handle_title)
//        TextView mTvTitle;
//        @Bind(R.id.tv_item_supervise_handle_belongProject)
//        TextView mTvBelongProject;
//        @Bind(R.id.tv_item_supervise_handle_operationUnit)
//        TextView mTvOperationUnit;
//        @Bind(R.id.tv_item_supervise_charge_person)
//        TextView mTvChargePerson;
//        @Bind(R.id.tv_item_supervise_handle_status)
//        TextView mTvStatus;
//        private ViewUtil util;
        @Bind(R.id.iv_item_science)
ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @Override
        protected void bind(T bean, int position) {
            String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588528793769&di=ebef5b108b41960c01a2ad44060b7935&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200403%2F5e2749e286b34b7da9e9197d19950728.jpeg";
            GlideUtils.loadRoundedCorners(context,imageView,url,10, RoundedCornersTransformation.CornerType.ALL);
//            mTvTitle.setText(bean.getMissionName());
//            util.setTextMessage(mTvBelongProject,"所属项目",bean.getNameAcPark());
//            util.setTextMessage(mTvOperationUnit,"经营单位",bean.getNameRbacDepartment());
//            util.setTextMessage(mTvChargePerson,"责任人",bean.getChargeInfo());
//            mTvStatus.setText(bean.getSupervisoryStatusTitle());
        }
    }
}
