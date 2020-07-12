package com.luck.cloud.function.mine.work;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.MyListView;
import com.luck.cloud.widget.dialog.DateSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by liuyin on 2018/7/31 9:44
 *
 * @Describe 维修指派日历
 */
public class CalendarDesignateActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_worker_repair_designate_time)
    TextView mTvTime;
    @Bind(R.id.gv_worker_repair_designate_calendar)
    GridView mGvCalendar;
    @Bind(R.id.lv_repair_designate_persons)
    MyListView mLvPersons;
    @Bind(R.id.ll_repair_designate_nowork)
    LinearLayout mLvNowork;

    private MaintenanceDesignateOrderAdapter personsAdapter;//维修任务人员里列表

    private WorkerCalendarAdapter calendarAdapter;//日历适配器

    private int year_c = 0;//当年年份
    private int month_c = 0;//当前月份
    private int day_c = 0;//当前日


    private RepairCalendarTimeSelectPop monthSelect;
    private int projectId;
    private PersonWorkAdapter adapter;
    private List<WorkerOrderListBean.DateWorkOrderListDTOListBean> childList;


    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_worker_repair_designate;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("工作日志");
    }

    @Override
    protected void loadData() {

        projectId = getIntent().getIntExtra("projectId", 8);
        Calendar calendar = Calendar.getInstance();
        year_c = calendar.get(Calendar.YEAR);
        month_c = calendar.get(Calendar.MONTH) + 1;
        day_c = calendar.get(Calendar.DATE);


        initCalendarGridView();//初始化日历列表

    }

    //初始化监听日历GridView
    private void initCalendarGridView() {
        calendarAdapter = new WorkerCalendarAdapter(this, year_c, month_c, day_c);
        mGvCalendar.setAdapter(calendarAdapter);

        mGvCalendar.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                int startPosition = calendarAdapter.getStartPositon();
                int endPosition = calendarAdapter.getEndPosition();
                if (startPosition <= position && position < endPosition) {
                    int lastClickPosition = calendarAdapter.getCurrentPosition();
                    if (lastClickPosition != position) {//将当前点击条目背景色设置为暗红色,其他条目无背景
                        WorkerRepairCalendarBean[] beanArr = calendarAdapter.beanArr;
                        WorkerRepairCalendarBean scheduleDay = beanArr[position]; // 这一天的阳历
                        scheduleDay.setSelect(true);
                        if (lastClickPosition < beanArr.length) {
                            if (lastClickPosition != -1) {
                                beanArr[lastClickPosition].setSelect(false);
                            }
                        }
                        calendarAdapter.notifyDataSetChanged();
                        calendarAdapter.setCurrentPosition(position);
                        String day = scheduleDay.getDate();
                        getOrderData(day);
                    }

                }
            }
        });
        personsAdapter = new MaintenanceDesignateOrderAdapter(this);
        mLvPersons.setAdapter(personsAdapter);
        getData(year_c, month_c,day_c);
       // testAdapter();
    }

    private void testAdapter(){
        childList = new ArrayList<>();
        for (int i=0;i<10;i++){
            WorkerOrderListBean.DateWorkOrderListDTOListBean bean=new WorkerOrderListBean.DateWorkOrderListDTOListBean();
            childList.add(bean);
        }
        adapter = new PersonWorkAdapter(this, childList);
        adapter.setShowCount(childList.size());
        mLvPersons.setAdapter(adapter);
    }


    /**
     * 重新计算加载数据
     */
    private void reloadData(int year, int month,int day, List<String> list) {

        calendarAdapter.setData(year, month, list);
        String time = year + "-" + (month < 10 ? "0" + month : month)+ "-" + (day < 10 ? "0" + day : day);
        mTvTime.setText(time);
        int lastClickPosition = calendarAdapter.getCurrentPosition();
        WorkerRepairCalendarBean[] beanArr = calendarAdapter.beanArr;
        if (lastClickPosition < beanArr.length) {
            if (lastClickPosition != -1) {
                WorkerRepairCalendarBean scheduleDay = beanArr[lastClickPosition]; // 这一天的阳历
                String schedule = scheduleDay.getDate();
                getOrderData(schedule);
            }
        }
    }


    @OnClick({R.id.rl_repair_designate_time_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_repair_designate_time_parent:
//                if (adapter.getShowCount()==1){
//                    adapter.setShowCount(childList.size());
//                    adapter.notifyDataSetChanged();
//                }else{
//                    adapter.setShowCount(1);
//                    adapter.notifyDataSetChanged();
//                }
                DateSelectDialog dialog = new DateSelectDialog(this);
                dialog.setListener(new DateSelectDialog.OnSelectListener() {
                    @Override
                    public void callback(String date) {
                        mTvTime.setText(date);
                    }
                });
                dialog.show();
                break;
        }
    }


    private void getData(final int year, final int month,int day) {
        List<String> beanList=new ArrayList<>();
        reloadData(year, month,day, beanList);
//        params.clear();
//        params.put("year", year);
//        params.put("month", month);
//        params.put("projectId", projectId);
//        showRDialog();
//        OKHttpManager.postAsyn(URLConstant.REPAIR_DESIGN_CALENDAR,
//                params, new OKHttpManager.ResultCallback<BaseBean<List<String>>>() {
//                    @Override
//                    public void onError(Request request, Exception e, String msg) {
//                        ToastUtil.toastShortCenter(msg);
//                        hideRDialog();
//                    }
//
//                    @Override
//                    public void onResponse(BaseBean<List<String>> response) {
//                        hideRDialog();
//                        List<String> beanList = response.getBody();
//                        reloadData(year, month, beanList);
//
//                    }
//                }, this);
    }

    private void getOrderData(String date) {
//        if (TextUtils.isEmpty(date)) {
//            mLvNowork.setVisibility(View.VISIBLE);
//            mLvPersons.setVisibility(View.GONE);
//            return;
//        } else {
//            mLvPersons.setVisibility(View.VISIBLE);
//            mLvNowork.setVisibility(View.GONE);
//        }
//        date = date.substring(0, 10);
//        params.clear();
//        params.put("date", date);
//        params.put("projectId", projectId);
//        showRDialog();
//        OKHttpManager.postAsyn(URLConstant.DESIGN_CALEANDAR_DETAIL,
//                params, new OKHttpManager.ResultCallback<BaseBean<List<WorkerOrderListBean>>>() {
//                    @Override
//                    public void onError(Request request, Exception e, String msg) {
//                        ToastUtil.toastShortCenter(msg);
//                        hideRDialog();
//                    }
//
//                    @Override
//                    public void onResponse(BaseBean<List<WorkerOrderListBean>> response) {
//                        hideRDialog();
//                        List<WorkerOrderListBean> beanList = response.getBody();
//                        if (beanList == null || beanList.size() == 0) {
//                            mLvNowork.setVisibility(View.VISIBLE);
//                            mLvPersons.setVisibility(View.GONE);
//                        } else {
//                            mLvNowork.setVisibility(View.GONE);
//                            mLvPersons.setVisibility(View.VISIBLE);
//                            personsAdapter.setList(beanList);
//                        }
//                    }
//                }, this);
    }


}