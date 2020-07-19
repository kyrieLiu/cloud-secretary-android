package com.luck.cloud.function.mine.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:科普列表适配器
 */
public class PersonAdapter<T extends PersonModel> extends BaseRecyclerViewAdapter<T> {


    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
//        @Bind(R.id.iv_item_study)
//        ImageView imageView;
//        @Bind(R.id.rv_study_pictures)
//        RecyclerView mRecyclerView;
//        @Bind(R.id.tv_study_title)
//        TextView title;
//        @Bind(R.id.tv_study_source)
//        TextView source;
//        @Bind(R.id.tv_study_time)
//        TextView time;
//        @Bind(R.id.tv_study_watch_num)
//        TextView clickNum;

        @Bind(R.id.tv_attention_status)
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {
            // List<LocalMedia> list = bean.getPictureList();
//            imageView.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.GONE);
//            if (bean.getTitlePicture() != null) {
//                imageView.setVisibility(View.VISIBLE);
//                String url = bean.getTitlePicture();
//                GlideUtils.loadRoundedCorners(context, imageView, url, 10, RoundedCornersTransformation.CornerType.ALL);
//            }
//
//            title.setText(bean.getInotitle());
//            source.setText(bean.getLinkurl());
//            time.setText(DateUtil.getMinuteTime(bean.getPublishtime()));
//            clickNum.setText(String.valueOf(bean.getClicks()));

            if (bean.getStatus()==1){
                status.setText("取消关注");
            }else{
                status.setText("关注");
            }
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.attentionCallback(bean,position);
                }
            });

        }
    }
    private OnPersonClickListener listener;

    public void setListener(OnPersonClickListener listener) {
        this.listener = listener;
    }

    public interface OnPersonClickListener{
        void attentionCallback(PersonModel bean,int position);
    }
}
