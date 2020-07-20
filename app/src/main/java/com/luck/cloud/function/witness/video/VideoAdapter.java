package com.luck.cloud.function.witness.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:科普列表适配器
 */
public class VideoAdapter<T extends DynamicModel.RecordsBean> extends BaseRecyclerViewAdapter<T> {


    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
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
        @Bind(R.id.iv_video)
        ImageView imageView;
        @Bind(R.id.tv_video_content)
        TextView tvContent;
        @Bind(R.id.tv_video_play_number)
        TextView number;
        @Bind(R.id.tv_video_time)
        TextView time;
        @Bind(R.id.head_image)
        ImageView headImage;
        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @Override
        protected void bind(T bean, int position) {
            GlideUtils.loadImage(context,imageView,bean.getSurfacePlot());
            tvContent.setText(bean.getContent());
            time.setText(DateUtil.getUnderlineDay(bean.getCreateTime()));
            GlideUtils.loadCircleImage(context,headImage,bean.getUserPhoto());
//            mTvTitle.setText(bean.getMissionName());
//            util.setTextMessage(mTvBelongProject,"所属项目",bean.getNameAcPark());
//            util.setTextMessage(mTvOperationUnit,"经营单位",bean.getNameRbacDepartment());
//            util.setTextMessage(mTvChargePerson,"责任人",bean.getChargeInfo());
//            mTvStatus.setText(bean.getSupervisoryStatusTitle());
        }
    }
}
