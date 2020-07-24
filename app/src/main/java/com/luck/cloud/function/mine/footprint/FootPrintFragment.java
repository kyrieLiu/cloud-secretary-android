package com.luck.cloud.function.mine.footprint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.StudyAdapter;
import com.luck.cloud.function.study.model.StudyDetailModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.function.study.model.StudyTabModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 推荐页面
 */
public class FootPrintFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private StudyAdapter<StudyScienceModel.RecordsBean> adapter;
    private Context context;

    private StudyTabModel dict;
    //1学习  2科普
    private int type;

    private String keyWord;


    public static FootPrintFragment getInstance(int type) {
        FootPrintFragment fragment = new FootPrintFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
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
//        if (getArguments()!=null&&getArguments().getSerializable("dict")!=null){
//            dict = (StudyTabModel) bundle.getSerializable("dict");
//        }
        type=bundle.getInt("type");

    }

    @Override
    protected void loadData() {
        adapter = new StudyAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRvList.setLayoutManager(layoutManager);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        // mRvList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRvList.setAdapter(adapter);
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                StudyScienceModel.RecordsBean itemsBean = adapter.getList().get(position);
                // WebActivity.start(context,itemsBean.getId(),itemsBean.getIsRead());
//                Intent intent = new Intent(context, WebActivity.class);
//                context.startActivity(intent);
                getDetailData(itemsBean);
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

    private void getDetailData(StudyScienceModel.RecordsBean bean){
        showRDialog();
        OKHttpManager.getJoint(URLConstant.STUDY_DETAIL, null,new int[]{bean.getInid()}, new OKHttpManager.ResultCallback<BaseBean<StudyDetailModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<StudyDetailModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){

                    Temporary.webContent=response.getData().getContent();

                    Intent intent=new Intent(getActivity(),WebActivity.class);
                    startActivity(intent);


                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }


    /**
     * 请求列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        showRDialog();
//        params.put("inotype",dict.getAtCode());
//        params.put("keyWords",keyWord);
        params.put("contentType",type);
        OKHttpManager.getJoint(URLConstant.STUDY_LIST, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseBean<StudyScienceModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                adapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseBean<StudyScienceModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    List<StudyScienceModel.RecordsBean> list=response.getData().getRecords();
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
    public void onDestroy() {
        super.onDestroy();
    }
}
