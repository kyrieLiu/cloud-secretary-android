package com.luck.cloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.luck.cloud.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/6/11 13:34
 * Description:  确定提示弹框
 */
public class TimeSelectDialog extends Dialog {

    @Bind(R.id.date_select)
    TimePicker datePicker;

    public TimeSelectDialog(Context context) {
        super(context, R.style.ThemeCustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_picker);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.close,R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.confirm:
                int hour=datePicker.getHour();
                int minute=datePicker.getMinute();
//                int year=datePicker.getYear();
//                int month=datePicker.getMonth()+1;
//                int day=datePicker.getDayOfMonth();
                if (listener!=null){
                    listener.callback((hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute));
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
