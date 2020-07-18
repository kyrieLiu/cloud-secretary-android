package com.luck.cloud.function.active;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.base.BaseListBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.active.bean.ActiveItemBean;
import com.luck.cloud.function.study.model.StudyDetailModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 活动列表
 */
public class ActiveFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private ActiveAdapter<ActiveItemBean.RecordsBean> adapter;
    private Context context;

    private int type;

    private String keyWord;



    public static ActiveFragment getInstance(int type) {
        ActiveFragment fragment = new ActiveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    
    @Override
    protected int getContentId() {
        return R.layout.common_fragment_xrecycler_list;
    }

    @Override
    protected void initView(Bundle bundle) {
        type = getArguments().getInt("type");
    }

    @Override
    protected void loadData() {
        adapter = new ActiveAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(adapter);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                ActiveItemBean.RecordsBean itemsBean=adapter.getList().get(position);
                Intent intent=new Intent(context, ActiveDetailActivity.class);
                intent.putExtra("id",itemsBean.getActivityId());
                intent.putExtra("apply",type);
                startActivityForResult(intent,200);
            }
        });
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
        mRvList.refresh();
    }


    /**
     * 请求列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        showRDialog();
        params.put("keyWords",keyWord);
        String url=type==1?URLConstant.ACTIVE_LIST:URLConstant.ACTIVE_LIST_MY;
        OKHttpManager.getJoint(url, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseBean<ActiveItemBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                adapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseBean<ActiveItemBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    List<ActiveItemBean.RecordsBean> list=response.getData().getRecords();
                    adapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    public void searchData(String keyWord){
        this.keyWord=keyWord;
        requestData(1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            requestData(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
