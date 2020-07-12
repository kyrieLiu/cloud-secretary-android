package com.luck.cloud.function.study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.cloud.FullyGridLayoutManager;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.study.model.StudyModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.function.witness.dynamic.DynamicPictureAdapter;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:科普列表适配器
 */
public class StudyAdapter<T extends StudyScienceModel.RecordsBean> extends BaseRecyclerViewAdapter<T> {


    public StudyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_study, parent, false);
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
        @Bind(R.id.iv_item_study)
        ImageView imageView;
        @Bind(R.id.rv_study_pictures)
        RecyclerView mRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @Override
        protected void bind(T bean, int position) {
            // List<LocalMedia> list = bean.getPictureList();
            imageView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            if (bean.getTitlePicture() != null) {
                imageView.setVisibility(View.VISIBLE);
                String url = bean.getTitlePicture();
                GlideUtils.loadRoundedCorners(context, imageView, url, 10, RoundedCornersTransformation.CornerType.ALL);
            }
//            else if (bean.getPictureList() != null && bean.getPictureList().size() > 0) {
//                mRecyclerView.setVisibility(View.VISIBLE);
//                FullyGridLayoutManager manager = new FullyGridLayoutManager(context,
//                        3, GridLayoutManager.VERTICAL, false);
//                mRecyclerView.setLayoutManager(manager);
//                DynamicPictureAdapter<LocalMedia> mAdapter = new DynamicPictureAdapter(context);
//                mAdapter.setList(list);
//                mRecyclerView.setAdapter(mAdapter);
//
//                mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                            listener.onItemClick(view, position);
//                        }
//                        return false;
//                    }
//                });
//            }


//            mTvTitle.setText(bean.getMissionName());
//            util.setTextMessage(mTvBelongProject,"所属项目",bean.getNameAcPark());
//            util.setTextMessage(mTvOperationUnit,"经营单位",bean.getNameRbacDepartment());
//            util.setTextMessage(mTvChargePerson,"责任人",bean.getChargeInfo());
//            mTvStatus.setText(bean.getSupervisoryStatusTitle());
        }
    }
}
