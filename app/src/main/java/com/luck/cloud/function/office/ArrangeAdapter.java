package com.luck.cloud.function.office;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.cloud.FullyGridLayoutManager;
import com.luck.cloud.R;
import com.luck.cloud.adapter.GridImageAdapter;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.home.SuperviseHandleBean;
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.function.office.beans.ArrangeBean;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.RoundedCornersTransformation;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.tools.ScreenUtils;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 10:15
 * Description:安排列表适配器
 */
public class ArrangeAdapter<T extends ArrangeBean> extends BaseRecyclerViewAdapter<T> {


    public ArrangeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arrange, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {
        @Bind(R.id.tv_level)
        TextView tvLevel;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.tv_arrange_title)
        TextView title;
        @Bind(R.id.tv_arrange_content)
        TextView content;
        @Bind(R.id.tv_item_time)
        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            //util=ViewUtil.getViewUtil();
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void bind(T bean, int position) {
            GradientDrawable drawable=new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(5);

            String color="#1FA2DB";
            if ("一般".equals(bean.getPlanDetails())){
                color="#7D7DFF";
            }else if ("重要".equals(bean.getPlanDetails())){
                color="#43c117";
            }else{
                color="#1FA2DB";
            }
            drawable.setStroke(1,Color.parseColor(color));
            tvLevel.setBackground(drawable);
            tvLevel.setTextColor(Color.parseColor(color));
            tvLevel.setText(bean.getPlanDetails());

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.deleteCallback(bean,position);
                }
            });

            title.setText(bean.getPlanName());
            content.setText(bean.getPlanDay()+"   "+ bean.getPlanTime());
            time.setText(DateUtil.getStandardDate(bean.getCreateTime()));
        }
    }

    private ArrangeClickListener listener;

    public void setListener(ArrangeClickListener listener) {
        this.listener = listener;
    }

    public interface ArrangeClickListener{
        void deleteCallback(ArrangeBean bean,int position);
    }
}
