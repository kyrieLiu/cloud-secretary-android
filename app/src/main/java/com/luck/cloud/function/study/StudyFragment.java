package com.luck.cloud.function.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.DictBean;
import com.luck.cloud.common.entity.RequestBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.model.StudyModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.function.study.model.StudyTabModel;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 推荐页面
 */
public class StudyFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private StudyAdapter<StudyScienceModel.RecordsBean> adapter;
    private Context context;

    private StudyTabModel dict;


    public static StudyFragment getInstance(StudyTabModel dict) {
        StudyFragment fragment = new StudyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dict", dict);
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
        dict = (StudyTabModel) getArguments().getSerializable("dict");
        Log.d("tag", "dict==" + dict);
    }

    @Override
    protected void loadData() {
        adapter = new StudyAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(adapter);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                StudyScienceModel.RecordsBean itemsBean = adapter.getList().get(position);
                // WebActivity.start(context,itemsBean.getId(),itemsBean.getIsRead());
                Intent intent = new Intent(context, WebActivity.class);
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
//        List<StudyModel> list = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            StudyModel model=new StudyModel();
//            String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588528793769&di=ebef5b108b41960c01a2ad44060b7935&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200403%2F5e2749e286b34b7da9e9197d19950728.jpeg";
//            if (i%2==0){
//                List<LocalMedia> picturelist = new ArrayList<>();
//                for (int j=0;j<3;j++){
//                    LocalMedia media = new LocalMedia();
//                    media.setPath(url);
//                    picturelist.add(media);
//                }
//                model.setPictureList(picturelist);
//
//            }else if (i==1){
//                model.setSingleImage(url);
//            }
//            list.add(model);
//        }
//        for(int i=0;i<10;i++){
//            list.add(new StudyModel());
//        }
//        adapter.setSuccessReqList(list, 13, page, mRvList,"暂无推荐内容");
        showRDialog();
        params.put("inotype",dict.getAtCode());
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
//                SuperviseHandleBean acceptanceBean = response.getData();
//                List<StudyModel> list = null;
//                if (acceptanceBean != null) list = acceptanceBean.getItems();
//                adapter.setSuccessReqList(list, request.getPageable().getSize(), page, mRvList, "暂无推荐内容");
            }
        }, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
