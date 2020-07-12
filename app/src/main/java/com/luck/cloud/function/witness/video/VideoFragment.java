package com.luck.cloud.function.witness.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.function.witness.ScienceAdapter;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;
import com.luck.picture.lib.PictureSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 推荐页面
 */
public class VideoFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private VideoAdapter<SuperviseHandleBean.ItemsBean> adapter;
    private Context context;

    private int type;



    public static VideoFragment getInstance(int type) {
        VideoFragment fragment = new VideoFragment();
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
        adapter = new VideoAdapter(context);
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mRvList.setLayoutManager(layoutManager);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRvList.getLayoutParams();
        lp.setMargins(15, 0, 15, 0);
        mRvList.setLayoutParams(lp);
        mRvList.setAdapter(adapter);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                String videoUrl="http://124.70.179.180/group1/M00/00/00/wKgAHl8JWpKAR-9DATvtgCPh_mk436.mp4";
                PictureSelector.create(getActivity())
                        .themeStyle(R.style.picture_default_style)
                        .externalPictureVideo(videoUrl);
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
        adapter.setSuccessReqList(list, 10, page, mRvList,"暂无推荐内容");
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