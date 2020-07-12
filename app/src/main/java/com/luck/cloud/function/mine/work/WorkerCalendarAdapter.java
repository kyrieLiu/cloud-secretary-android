package com.luck.cloud.function.mine.work;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.luck.cloud.R;


import java.util.Calendar;
import java.util.List;

/**
 * 日历GridView中的每一个item显示的textView
 * @author Hesky
 *
 */
public class WorkerCalendarAdapter extends BaseAdapter {
    private boolean isLeapyear = false;  //是否为闰年
    private int daysOfMonth = 0;      //某月的天数
    private int dayOfWeek = 0;        //具体某一天是星期几
    private Context context;
    private DateUtil sc = null;

    private int currentYear;
    private int currentMonth ;
    private int currentDay; ;


    public WorkerRepairCalendarBean[] beanArr;
    private int allViewNumber;//一共需要展示多少个方格

    public int currentPosition=-1;


    public WorkerCalendarAdapter(Context context, int year_c, int month_c, int day_c){
        this.context= context;
        this.currentYear=year_c;
        this.currentMonth=month_c;
        this.currentDay=day_c;

        sc = new DateUtil();
        getCalendar(year_c, month_c,null);


    }
    //重新加载数据
    public void setData(int year_c, int month_c, List<String> dutyList){
        getCalendar(year_c, month_c,dutyList);

    }

    @Override
    public int getCount() {
        return allViewNumber;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar_onduty, parent,false);
            viewHolder.tvDate=convertView.findViewById(R.id.tv_item_onduty_calendar_date);
            viewHolder.mIvPoint=convertView.findViewById(R.id.view_item_onduty_calendar_point);
            viewHolder.rlContent=convertView.findViewById(R.id.rl_item_onduty_calendar_bg);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        WorkerRepairCalendarBean bean = beanArr[position];

        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            viewHolder.tvDate.setVisibility(View.VISIBLE);
            viewHolder.mIvPoint.setVisibility(View.VISIBLE);
            // 当前信息显示
            viewHolder.tvDate.setText(bean.getDay());

            if (bean.isSign()){
                viewHolder.mIvPoint.setVisibility(View.VISIBLE);
            }else{
                viewHolder.mIvPoint.setVisibility(View.GONE);
            }
            if (bean.isSelect()){//是否为选中状态
                viewHolder.rlContent.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                viewHolder.tvDate.setTextColor(context.getResources().getColor(R.color.white));
            }else{
                viewHolder.rlContent.setBackgroundColor(Color.argb(0,0,0,0));
                viewHolder.tvDate.setTextColor(context.getResources().getColor(R.color.main_text_color));
            }

        }else{
            viewHolder.tvDate.setVisibility(View.GONE);
            viewHolder.mIvPoint.setVisibility(View.GONE);
            viewHolder.rlContent.setBackgroundColor(Color.argb(0,0,0,0));
        }

        return convertView;
    }



    //得到某年的某月的天数切这月的第一天是星期几
    public void getCalendar(int year, int month, List<String> dutyList){
        isLeapyear = sc.isLeapYear(year);              //是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);  //某月的天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month);      //某月的第一天是星期几
        int currentDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        currentPosition=-1;

        if (daysOfMonth+dayOfWeek<=35){//如果上月剩余天数加上本月天数少于35天,那么GridView只需要五行就可以,如果多于35天,GridView需要6行
            allViewNumber=35;
        }else{
            allViewNumber=42;
        }
        beanArr =new WorkerRepairCalendarBean[allViewNumber];
        //得到当前月的所有日程日期
        for (int i = 0; i < allViewNumber; i++) {
            if(i < dayOfWeek){  //前一个月
            }else if(i < daysOfMonth + dayOfWeek){   //本月
                String day = String.valueOf(i-dayOfWeek+1);   //得到的日期
                WorkerRepairCalendarBean bean=new WorkerRepairCalendarBean();
                bean.setDay(day);
                boolean isSign=getIsSign(dutyList,bean);
                bean.setSign(isSign);
                if (currentDay==i-dayOfWeek+1&&currentMonth==month&&currentYear==year){
                    bean.setSelect(true);
                    currentPosition=i;
                }else{
                    bean.setSelect(false);
                }
                beanArr[i]=bean;
            }else{   //下一个月
            }
        }
        notifyDataSetChanged();

    }

    private boolean getIsSign(List<String> dutyList, WorkerRepairCalendarBean newBean){
        if (dutyList==null)return false;
        for (String bean:dutyList){
            String checkInDate=bean;
            if (checkInDate!=null){
                String signDay=bean.substring(8,10);
                int comparisonDay= Integer.parseInt(newBean.getDay());
                String comparString;
                if (comparisonDay<10){
                    comparString="0"+comparisonDay;
                }else{
                    comparString=""+comparisonDay;
                }
                if (signDay.equals(comparString)){
                    newBean.setDate(bean);

                    return true;
                }
            }
        }
        return false;
    }
    private class ViewHolder{
        TextView tvDate;
        RelativeLayout rlContent;
        View mIvPoint;
    }

    /**
     * 在点击gridView时,得到这个月中的第一天的位置
     * @return
     */
    public int getStartPositon(){
        return dayOfWeek;
    }

    /**
     * 点击gridView时,得到这个月中最后一天的位置
     * @return
     */
    public int getEndPosition(){
        return  dayOfWeek+daysOfMonth;
    }


    public int getCurrentPosition(){
        return this.currentPosition;
    }
    public void setCurrentPosition(int position){
        this.currentPosition=position;
    }

}
