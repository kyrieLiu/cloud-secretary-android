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

    private int type;

    public PersonAdapter(Context context,int type) {
        super(context);
        this.type=type;
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {

        @Bind(R.id.tv_attention_status)
        TextView status;
        @Bind(R.id.tv_person_name)
        TextView tvName;
        @Bind(R.id.tv_person_num)
        TextView tvTime;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {

            if (bean.getIsAttention()==1){
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

            tvName.setText(type==1?bean.getUserName():bean.getFansName());
            tvTime.setText(DateUtil.getUnderlineDay(bean.getCreateTime()));

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
