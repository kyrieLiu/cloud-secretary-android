package com.luck.cloud.function.office.lowincome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseRecyclerViewAdapter;
import com.luck.cloud.base.BaseViewHolder;
import com.luck.cloud.function.mine.work.DateUtil;
import com.luck.cloud.function.office.ArrangeAdapter;
import com.luck.cloud.function.office.beans.ArrangeBean;
import com.luck.cloud.function.office.beans.LowIncomePerson;
import com.luck.cloud.function.office.notice.NoticeBean;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/2/27 15:19
 * Description:待办事项
 */
public class LowIncomeAdapter<T extends LowIncomePerson> extends BaseRecyclerViewAdapter<T> {

    public LowIncomeAdapter(List<T> list, Context context) {
        super(list, context);
    }

    public LowIncomeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_low_income, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder<T> {

        @Bind(R.id.tv_name)
        TextView name;
        @Bind(R.id.tv_delete)
        TextView delete;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(T bean, int position) {

            name.setText(bean.getFamilyName());

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.deleteCallback(bean,position);
                }
            });
        }
    }
    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener{
        void deleteCallback(LowIncomePerson bean, int position);
    }
}
