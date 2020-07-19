package com.luck.cloud.function.office.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.luck.cloud.PictureMainActivity;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseListBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.RxConstant;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.manager.RxManager;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

public class NoticeActivity extends BaseActivity {

    @Bind(R.id.rv_common_list)
    XRecyclerView mRvList;
    private RxManager rxManager;

    private NoticeAdapter<NoticeBean> waitDoneAdapter;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.common_activity_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("小云公告");


    }

    @Override
    protected void loadData() {

        mRvList.setLoadingListener(new OnRecyclerLoadingListener() {

            @Override
            public void onRefresh() {
                requestData(1);
            }

            @Override
            public void onLoadMore(int reqPage) {
                requestData(reqPage);
            }
        });
        waitDoneAdapter = new NoticeAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(waitDoneAdapter);
       mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        waitDoneAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                NoticeBean bean=waitDoneAdapter.getList().get(position);
                getNoticeDetail(bean.getNoticeId());
            }
        });
       mRvList.refresh();
//
    }

    /**
     * 获取公告列表
     *
     * @param page
     */
    private void requestData(final int page) {
        params.clear();
        params.put("noticeStatus",1);
        showRDialog();
        OKHttpManager.getJoint(URLConstant.NOTICE_LIST, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseBean<NoticeListBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                waitDoneAdapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseBean<NoticeListBean> response) {
                hideRDialog();
                final List<NoticeBean> list = response.getData().getRecords();
                waitDoneAdapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
            }
        }, this);
    }
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

                    Intent intent = new Intent(NoticeActivity.this, WebActivity.class);
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
