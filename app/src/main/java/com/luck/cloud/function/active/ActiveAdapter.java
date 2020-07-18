package com.luck.cloud.function.active;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.active.bean.ActiveItemBean;
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:科普列表适配器
 */
public class ActiveAdapter<T extends ActiveItemBean.RecordsBean> extends BaseRecyclerViewAdapter<T> {


    public ActiveAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_active, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
        @Bind(R.id.iv_item_active)
        ImageView imageView;
        @Bind(R.id.tv_active_status)
        TextView status;
        @Bind(R.id.tv_item_active_title)
        TextView title;
        @Bind(R.id.tv_item_active_content)
        TextView content;
        @Bind(R.id.tv_item_active_people)
        TextView people;
        @Bind(R.id.tv_item_active_date)
        TextView date;
        @Bind(R.id.tv_item_active_code)
        TextView code;

        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void bind(T bean, int position) {
            String url=bean.getActivityPicture();
            GlideUtils.loadRoundedCorners(context,imageView,url,10, RoundedCornersTransformation.CornerType.ALL);

            GradientDrawable drawable=new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);


            if (bean.getStartDate()!=null&&bean.getEndDate()!=null){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                long startDate= 0;
                long endDate= 0;
                try {
                    startDate = format.parse(bean.getStartDate()).getTime();
                    endDate = format.parse(bean.getEndDate()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long now=new Date().getTime();
                if (startDate>now){
                    status.setText("未开始");
                    drawable.setColor(Color.parseColor("#FFFF9800"));
                }else if (endDate<now){
                    status.setText("已结束");
                    drawable.setColor(Color.parseColor("#FFFF9800"));
                }else{
                    status.setText("报名中");
                    drawable.setColor(Color.parseColor("#1FA2DB"));
                }
            }else{
                drawable.setColor(Color.parseColor("#1FA2DB"));
            }

            status.setBackground(drawable);

            title.setText(bean.getActivityName());
            content.setText(bean.getActivityContent());
            people.setText(bean.getUserName());
            date.setText(bean.getStartDate()+" 至 "+bean.getEndDate());


        }
    }
}
