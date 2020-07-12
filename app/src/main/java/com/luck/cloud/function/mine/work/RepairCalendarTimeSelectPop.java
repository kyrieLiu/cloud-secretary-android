package com.luck.cloud.function.mine.work;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.luck.cloud.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liuyin on 2018/11/1 14:25
 */
public class RepairCalendarTimeSelectPop extends CommentPopUtils implements View.OnClickListener{
    private TextView tv_pop_sure;
    private ImageView tv_data_exit;
    //private LoopView loopView_left, loopView_middle;
    private String year, month;
    private Button mSure;
    private int currentYear, currentMonth;

    private List<String> leftList, middleList;


    public RepairCalendarTimeSelectPop(View v, Context context, int layout) {
        super(v, context, layout);
    }

    public RepairCalendarTimeSelectPop(Context context) {
        super(context);

    }



    @Override
    public void initLayout(View v, Context context) {
//        tv_data_exit = v.findViewById(R.id.tv_data_exit);
//        mSure = v.findViewById(R.id.btn_sure);
//        loopView_left = v.findViewById(R.id.lv_pop_select_left);
//        loopView_middle = v.findViewById(R.id.lv_pop_select_right);
//        mSure.setOnClickListener(this);
//        tv_data_exit.setOnClickListener(this);
//        tv_pop_sure = v.findViewById(R.id.tv_pop_sure);
//        LoopView lv_middle = v.findViewById(R.id.lv_pop_select_middle);
//        lv_middle.setVisibility(View.GONE);
        initData();

    }
    private void initData() {
        Calendar mCalendar = Calendar.getInstance();
        //获取当前小时数
        currentYear = mCalendar.get(Calendar.YEAR);
        currentMonth = mCalendar.get(Calendar.MONTH) + 1;
        leftList = new ArrayList<>();
        middleList = new ArrayList<>();

//        initLeftLoop();
//        initMiddleLoop();

    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }


//
//    private void initLeftLoop() {
//        leftList.clear();
//
//        int lessNum = 12 - currentMonth;
//        leftList.add(currentYear+"年");
//        int afterLess = 3 - lessNum;
//        if (afterLess>0){
//            leftList.add((currentYear+1)+"年");
//        }
//
//        //设置是否循环播放
//        loopView_left.setNotLoop();
//        loopView_left.setTextSize(18);
//        year=leftList.get(0);
//
//        //滚动监听
//        loopView_left.setListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                year = leftList.get(index);
//                int selectYear = Integer.parseInt(year.substring(0,4));
//                year=selectYear+"年";
//                setMiddleData(selectYear);
//            }
//        });
//        loopView_left.setItems(leftList);
//        setMiddleData(currentYear);
//
//    }
//
//    private void setMiddleData(int selectYear) {
//        middleList.clear();
//        int lessNum = 12 - currentMonth;
//        if (selectYear == currentYear) {
//            if (lessNum>3){
//                lessNum=currentMonth+3;
//            }else{
//                lessNum=12;
//            }
//            for (int i = currentMonth; i <= lessNum; i++) {
//                if (i < 10) {
//                    middleList.add("0" + i+ "月");
//                } else {
//                    middleList.add(i+ "月");
//                }
//            }
//        }else{
//            for (int i = 1; i <= (3-lessNum); i++) {
//                if (i < 10) {
//                    middleList.add("0" + i+ "月");
//                } else {
//                    middleList.add(i+ "月");
//                }
//            }
//        }
//        month=middleList.get(0);
//        loopView_middle.setItems(middleList);
//    }
//
//    private void initMiddleLoop() {
//        //设置是否循环播放
//        loopView_middle.setNotLoop();
//
//        //滚动监听
//        loopView_middle.setListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                month = middleList.get(index);
//            }
//        });
//        loopView_middle.setTextSize(17);
//
//        //setMiddleData(currentYear, currentMonth);
//    }

    public void setTitle(String title) {
        tv_pop_sure.setText(title);
    }
}
