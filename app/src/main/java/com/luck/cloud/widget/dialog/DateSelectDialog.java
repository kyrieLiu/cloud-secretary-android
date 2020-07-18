package com.luck.cloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.luck.cloud.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/6/11 13:34
 * Description:  确定提示弹框
 */
public class DateSelectDialog extends Dialog {

    @Bind(R.id.date_select)
    DatePicker datePicker;

    public DateSelectDialog(Context context) {
        super(context, R.style.ThemeCustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_picker);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.close,R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.confirm:
                int year=datePicker.getYear();
                int month=datePicker.getMonth()+1;
                int day=datePicker.getDayOfMonth();
                if (listener!=null){
                    listener.callback(year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                    dismiss();
                }
                break;
        }
    }


    public interface OnSelectListener {
        void callback(String date);
    }

    private OnSelectListener listener;
    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }
}
