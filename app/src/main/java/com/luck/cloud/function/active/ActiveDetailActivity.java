package com.luck.cloud.function.active;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.active.bean.ActiveItemBean;
import com.luck.cloud.function.study.model.StudyDetailModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.function.witness.dynamic.CommentsAdapter;
import com.luck.cloud.function.witness.model.CommentModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ActiveDetailActivity extends BaseActivity {

    @Bind(R.id.tv_name)
    TextView name;
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
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.bt_apply)
    Button btApply;
    @Bind(R.id.rv_leave_message)
    RecyclerView recyclerView;

    private CommentsAdapter<CommentModel> commentsAdapter;

    private List<CommentModel> commentList;


    private ActiveItemBean.RecordsBean detailBean;

    private int isApply;//是否已报名

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
        isApply=getIntent().getIntExtra("apply",0);
        if (isApply==2){
            btApply.setVisibility(View.GONE);
        }
        getDetailData(id);
    }


    @OnClick({R.id.bt_apply,R.id.bt_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_apply:
                handleApply();
                break;
            case R.id.bt_commit:
                handleLeaveMessage();
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

    private void handleLeaveMessage(){
        showRDialog();
        params.put("activityId",id);
        params.put("messageContent",etMessage.getText().toString());
        OKHttpManager.postJsonRequest(URLConstant.ACTIVE_MESSAGE_SAVE, params, new OKHttpManager.ResultCallback<BaseBean<ActiveItemBean.RecordsBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<ActiveItemBean.RecordsBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    ToastUtil.toastShortCenter("留言成功");
                   etMessage.setText("");
                   getDetailData(id);
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
                    name.setText(detailBean.getActivityName());
                    unit.setText(detailBean.getActivityUnit());
                    principle.setText(detailBean.getActivityContent());
                    phone.setText(detailBean.getUserPhone());
                    address.setText(detailBean.getActivityAddress());
                    content.setText(detailBean.getActivityAddress());
                    commentList=detailBean.getMessages()==null?new ArrayList<>():detailBean.getMessages();
                        initLeaveMessage();
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }
    private void initLeaveMessage(){
        commentsAdapter = new CommentsAdapter(this);
        commentsAdapter.setList(commentList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentsAdapter);
    }
}
