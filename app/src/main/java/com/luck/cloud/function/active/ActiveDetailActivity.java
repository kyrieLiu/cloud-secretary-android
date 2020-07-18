package com.luck.cloud.function.active;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.active.bean.ActiveItemBean;
import com.luck.cloud.function.study.model.StudyDetailModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class ActiveDetailActivity extends BaseActivity {

    @Bind(R.id.tv_unit)
    TextView unit;
    @Bind(R.id.tv_principle)
    TextView principle;
    @Bind(R.id.tv_phone)
    TextView phone;
    @Bind(R.id.tv_address)
    TextView address;
    @Bind(R.id.tv_content)
    TextView content;


    private ActiveItemBean.RecordsBean detailBean;

    private int id;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail_active;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("活动详情");
    }

    @Override
    protected void loadData() {
        id=getIntent().getIntExtra("id",0);
        getDetailData(id);
    }


    @OnClick({R.id.bt_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_apply:
                handleApply();
                break;
        }
    }
    private void handleApply(){
        showRDialog();
        params.put("activityId",id);
        OKHttpManager.postJsonRequest(URLConstant.ACTIVE_APPLY, params, new OKHttpManager.ResultCallback<BaseBean<ActiveItemBean.RecordsBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<ActiveItemBean.RecordsBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    ToastUtil.toastShortCenter("报名成功");
                   setResult(200);
                   finish();
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    private void getDetailData(int id){
        showRDialog();
        OKHttpManager.getJoint(URLConstant.ACTIVE_DETAIL, null,new int[]{id}, new OKHttpManager.ResultCallback<BaseBean<ActiveItemBean.RecordsBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<ActiveItemBean.RecordsBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    detailBean=response.getData();
                    unit.setText(detailBean.getActivityName());
                    principle.setText(detailBean.getActivityContent());
                    phone.setText(detailBean.getUserPhone());
                    address.setText(detailBean.getActivityAddress());
                    content.setText(detailBean.getActivityAddress());

                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }
}
