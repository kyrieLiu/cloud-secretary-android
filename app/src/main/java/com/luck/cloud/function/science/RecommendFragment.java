package com.luck.cloud.function.science;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 推荐页面
 */
public class RecommendFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private ScienceAdapter<SuperviseHandleBean.ItemsBean> adapter;
    private Context context;

    private int type;



    public static RecommendFragment getInstance(int type) {
        RecommendFragment fragment = new RecommendFragment();
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
        //type = getArguments().getInt("type");
    }

    @Override
    protected void loadData() {
        adapter = new ScienceAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(adapter);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                SuperviseHandleBean.ItemsBean itemsBean=adapter.getList().get(position);
               // WebActivity.start(context,itemsBean.getId(),itemsBean.getIsRead());
                String body="<p>史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇女，" +
                        "那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻烦你" +
                        "给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对高" +
                        "房价是的分开管理史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇" +
                        "女，那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻" +
                        "烦你给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对" +
                        "高房价是的分开管理史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇" +
                        "女，那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻烦你" +
                        "给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对高房价是" +
                        "的分开管理<img src=\"http://wgzx.test.jingcaiwang.cn/group1/M00/00/68/rBMBOF0J-8WABHG6AAGBabvhhYo556.png\" title=\" 000.png\" alt=\" 000.png\" style=\"white-space: normal;\"/></p>";
                Temporary.webContent=body;
                Intent intent=new Intent(context, WebActivity.class);
                context.startActivity(intent);
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
     * 请求督查督办列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        List<SuperviseHandleBean.ItemsBean> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new SuperviseHandleBean.ItemsBean());
        }
        adapter.setSuccessReqList(list, 13, page, mRvList,"暂无推荐内容");
//        final RequestBean request = initRequestParams();
//        request.getPageable().setCurrent(page);
//        RequestBean.CondBean condBean = new RequestBean.CondBean();
//        condBean.setRules(new ArrayList());
//        condBean.setGroupOp("AND");
//        request.setCond(condBean);
//        showRDialog();
//        OKHttpManager.postJsonRequest(URLConstant.SUPERVISE_LIST, request, new OKHttpManager.ResultCallback<BaseBean<SuperviseHandleBean >>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//                adapter.setErrorReqList(message, mRvList);
//            }
//
//            @Override
//            public void onResponse(BaseBean<SuperviseHandleBean> response) {
//                hideRDialog();
//                SuperviseHandleBean acceptanceBean = response.getData();
//                List<SuperviseHandleBean.ItemsBean> list = null;
//                if (acceptanceBean != null) list = acceptanceBean.getItems();
//                adapter.setSuccessReqList(list, request.getPageable().getSize(), page, mRvList,"暂无推荐内容");
//            }
//        }, this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
