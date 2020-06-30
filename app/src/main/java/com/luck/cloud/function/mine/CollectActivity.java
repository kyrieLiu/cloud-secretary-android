package com.luck.cloud.function.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.luck.cloud.PictureMainActivity;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.config.RxConstant;
import com.luck.cloud.manager.RxManager;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

public class CollectActivity extends BaseActivity {

    @Bind(R.id.rv_common_list)
    XRecyclerView mRvList;
    private RxManager rxManager;

    private HomeWaitDoneAdapter<WaitDoneBean.ItemsBean> waitDoneAdapter;

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

        setTitle("我的收藏");


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
        waitDoneAdapter = new HomeWaitDoneAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(waitDoneAdapter);
       mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        waitDoneAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(CollectActivity.this, PictureMainActivity.class);
                startActivity(intent);
            }
        });
        //requestData(1);
       mRvList.refresh();
//
//        //观察督查督办验收和修缮审核
//        initObserver();
    }

    /**
     * 观察督查督办验收和修缮审核
     */
    private void initObserver() {
        rxManager = new RxManager();
        rxManager.on(RxConstant.RX_SUPERVISE_ACCEPT, new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                requestData(1);
            }
        });
        rxManager.on(RxConstant.RX_REPAIR_CHECK, new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                requestData(1);
            }
        });
    }

    /**
     * 请求待办事项列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        List<WaitDoneBean.ItemsBean> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new WaitDoneBean.ItemsBean());
        }
        waitDoneAdapter.setSuccessReqList(list, 10, page, mRvList,"暂无推荐内容");
//        final RequestBean request = initRequestParams();
//        request.getPageable().setCurrent(page);
//        showRDialog();
//        OKHttpManager.postJsonRequest(URLConstant.WAIT_DONE_MORE, request, new OKHttpManager.ResultCallback<BaseBean<WaitDoneBean>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//                waitDoneAdapter.setErrorReqList(message, mRvList);
//            }
//
//            @Override
//            public void onResponse(BaseBean<WaitDoneBean> response) {
//                hideRDialog();
//                final List<WaitDoneBean.ItemsBean> list = response.getData().getItems();
//                waitDoneAdapter.setSuccessReqList(list, request.getPageable().getSize(), page, mRvList, "暂无待办事项");
//            }
//        }, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
