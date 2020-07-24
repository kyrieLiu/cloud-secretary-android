package com.luck.cloud.function.office.lowincome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseRecordBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.mine.bean.PersonInfoBean;
import com.luck.cloud.function.office.OfficeActivity;
import com.luck.cloud.function.office.beans.LowIncomePerson;
import com.luck.cloud.function.office.beans.LowIncomePerson;
import com.luck.cloud.function.office.notice.NoticeAdapter;
import com.luck.cloud.function.office.notice.NoticeBean;
import com.luck.cloud.function.office.notice.NoticeListBean;
import com.luck.cloud.manager.RxManager;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.dialog.SelectMenuDialog;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class RemoveLowIncomeActivity extends BaseActivity {

    @Bind(R.id.rv_common_list)
    RecyclerView mRvList;

    private LowIncomeAdapter<LowIncomePerson> mAdapter;

    private boolean isUpdate;

    @Override
    protected void back() {
        if (isUpdate){
            setResult(100);
        }
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_remove_low;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("脱贫");

    }

    @Override
    protected void loadData() {

        List<LowIncomePerson> list=Temporary.list;


        mAdapter = new LowIncomeAdapter(this);
        mAdapter.setList(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(mAdapter);
       mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
       mAdapter.setListener(new LowIncomeAdapter.ItemClickListener() {
           @Override
           public void deleteCallback(LowIncomePerson bean, int position) {
               SelectMenuDialog dialog = new SelectMenuDialog(RemoveLowIncomeActivity.this);
               dialog.setListener(new SelectMenuDialog.OnMenuSelectListener() {
                   @Override
                   public void callback() {
                       dialog.dismiss();
                       deleteArrange(bean,position);
                   }
               });
               dialog.show();
               dialog.setTitle("提醒");
               dialog.setContent("是否脱贫");
           }
       });
    }

    private void deleteArrange(LowIncomePerson bean,int position) {
        showRDialog();
        params.clear();
        params.put("id",bean.getFamilyId());
        OKHttpManager.postJsonRequest(URLConstant.LOW_DELETE, params, new OKHttpManager.ResultCallback<BaseBean<PersonInfoBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(BaseBean<PersonInfoBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {
                    ToastUtil.toastShortCenter("脱贫成功");
                    List<LowIncomePerson> list = mAdapter.getList();
                    list.remove(position);
                    mAdapter.notifyDataSetChanged();
                    isUpdate=true;

                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

//    private void requestData(final int page) {
//        params.clear();
//        params.put("noticeStatus",1);
//        showRDialog();
//        OKHttpManager.getJoint(URLConstant.NOTICE_LIST, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseRecordBean<LowIncomePerson>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//                mAdapter.setErrorReqList(message, mRvList);
//            }
//
//            @Override
//            public void onResponse(BaseRecordBean<LowIncomePerson> response) {
//                hideRDialog();
//                final List<LowIncomePerson> list = response.getData().getRecords();
//                mAdapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
//            }
//        }, this);
//    }
    private void getNoticeDetail(int id) {
        showRDialog();
        OKHttpManager.getJoint(URLConstant.NOTICE_DETAIL, null, new int[]{id}, new OKHttpManager.ResultCallback<BaseBean<NoticeBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<NoticeBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {

                    Temporary.webContent = response.getData().getContent();

                    Intent intent = new Intent(RemoveLowIncomeActivity.this, WebActivity.class);
                    startActivity(intent);


                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
