package com.luck.cloud.function.mine.work;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.luck.cloud.R;

import java.util.List;

/**
 * Created by liuyin on 2018/12/19 16:46
 */
public class PersonWorkAdapter extends BaseAdapter {
    private List<WorkerOrderListBean.DateWorkOrderListDTOListBean> list;
    private LayoutInflater inflater;
    private Context context;
    private int showCount;

    public PersonWorkAdapter(Context context, List<WorkerOrderListBean.DateWorkOrderListDTOListBean> list) {
        this.list = list;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
          return  showCount;
        //return list.size();
    }

    public void setShowCount(int showCount){
        this.showCount=showCount;
    }

    public int getShowCount(){
        return showCount;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_designate_persons_work, null);
        WorkerOrderListBean.DateWorkOrderListDTOListBean bean=list.get(i);
        TextView mTvTitle=view.findViewById(R.id.tv_item_designate_title);
        TextView mTvTime=view.findViewById(R.id.tv_item_designate_time);
        TextView mTvPerson=view.findViewById(R.id.tv_item_designate_name);
        TextView mTvAddress=view.findViewById(R.id.tv_item_designate_address);
        setTextView(bean,mTvTitle);
        setText(mTvTime,"维修时间",bean.getAppointmentTime());
        List<WorkerOrderListBean.DateWorkOrderListDTOListBean.UserListBean> userList =bean.getUserList();
        StringBuilder builder=new StringBuilder();
        for (WorkerOrderListBean.DateWorkOrderListDTOListBean.UserListBean userBean:userList){
            builder.append(userBean.getName()+"、");
        }
        if (builder.length()>0){
            builder.deleteCharAt(builder.length()-1);
        }
        setText(mTvPerson,"维修人员",builder.toString());
        setText(mTvAddress,"维修地址",bean.getRepairAddress());

        return view;
    }
    private void setTextView(WorkerOrderListBean.DateWorkOrderListDTOListBean bean, TextView textView){
        String userType="";
        if (bean.getWorkOrderType()==0){
            userType="员工报修";
        }else{
            userType="用户报修";
        }
        //报修类型 1、自助区域报修2、公共区域报修3、入户检查报修4、空置房报修5、室外报修6、设备维修
        String repairType="";
        switch (bean.getRepairType()){
            case 1:
                repairType="自助区域报修";
                break;
            case 2:
                repairType="公共区域报修";
                break;
            case 3:
                repairType="入户检查报修";
                break;
            case 4:
                repairType="空置房报修";
                break;
            case 5:
                repairType="室外报修";
                break;
            case 6:
                repairType="设备维修";
                break;
        }
        textView.setText(userType+"—"+repairType);
    }
    /**
     * 加载textView内容,前面显示灰色
     *
     * @param textView
     * @param title
     * @param content
     */
    private void setText(TextView textView, String title, String content) {
        String allContent;
        if (TextUtils.isEmpty(content)) {
            allContent = title + ":";
        } else {
            allContent = title + ": " + content;
        }
        SpannableStringBuilder mSpannable = new SpannableStringBuilder(allContent);
        mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gray_color)), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(mSpannable);
    }

}