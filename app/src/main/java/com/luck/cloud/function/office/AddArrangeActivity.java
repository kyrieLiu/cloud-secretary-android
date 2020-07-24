package com.luck.cloud.function.office;

import android.Manifest;
import android.content.Context;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.dialog.DateSelectDialog;
import com.luck.cloud.widget.dialog.RepairsTypeDialog;
import com.luck.cloud.widget.dialog.TimeSelectDialog;
import com.luck.cloud.widget.dialog.TypeSelectDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

public class AddArrangeActivity extends BaseActivity {
    @Bind(R.id.arrange_content)
    EditText content;
    @Bind(R.id.startTime)
    TextView tvTime;
    @Bind(R.id.arrange_date)
    TextView tvDate;
    @Bind(R.id.arrange_level)
    TextView arrangeLevel;
    private String day;


    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_arrange;
    }

    @OnClick({R.id.startTime,R.id.arrange_date, R.id.bt_initiate,R.id.arrange_level})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startTime:
                TimeSelectDialog dialog = new TimeSelectDialog(this);
                dialog.setListener(new TimeSelectDialog.OnSelectListener() {
                    @Override
                    public void callback(String date) {
                        tvTime.setText(date);
                    }
                });
                dialog.show();
                break;
            case R.id.arrange_date:
                DateSelectDialog dateDialog = new DateSelectDialog(this);
                dateDialog.setListener(new DateSelectDialog.OnSelectListener() {
                    @Override
                    public void callback(String date) {
                        AddArrangeActivity.this.day=date;
                        String week=Week(date);
                        tvDate.setText(week);
                    }
                });
                dateDialog.show();
                break;
            case R.id.arrange_level:
                TypeSelectDialog typeDialog=new TypeSelectDialog(this);
                typeDialog.setTypeSelector(new TypeSelectDialog.OnTypeSelector() {
                    @Override
                    public void typeSeletor(String string) {
                        arrangeLevel.setText(string);
                    }
                });
                ArrayList<String> list=new ArrayList<>();
                list.add("重要");
                list.add("一般");
                list.add("不重要");
                typeDialog.setData(list);
                typeDialog.show();
                break;
            case R.id.bt_initiate:
                addArrange();
                break;
        }
    }

    private int getDayofWeek(String dateTime) {

        Calendar cal = Calendar.getInstance();
        if (dateTime.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException | java.text.ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    private String Week(String dateTime) {
        String week = "";
        switch (getDayofWeek(dateTime)) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }


    private void addArrange(){

        params.clear();
        params.put("planName",content.getText().toString());
        params.put("planDate",day);
        params.put("planDay",tvDate.getText().toString());
        params.put("planTime",tvTime.getText().toString());
        params.put("planDetails",arrangeLevel.getText().toString());

        OKHttpManager.postJsonRequest(URLConstant.SAVE_ARRANGE, params, new OKHttpManager.ResultCallback<BaseBean>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean response) {
                hideRDialog();
                if ("SUCCESS".equals(response.getCode())){
                    ToastUtil.toastShortCenter("添加成功");
                    setResult(100);
                    finish();
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }

            }
        },this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("添加日程");
    }

    public Context getContext() {
        return this;
    }

    @Override
    protected void loadData() {

    }
}
