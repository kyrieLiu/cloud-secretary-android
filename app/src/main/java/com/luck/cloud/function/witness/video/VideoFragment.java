package com.luck.cloud.function.witness.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.PlayVideoActivity;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.witness.ScienceAdapter;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 推荐页面
 */
public class VideoFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private VideoAdapter<DynamicModel.RecordsBean> adapter;
    private Context context;

    //1小云见证  2我的收藏  3我的发布  4根据人员ID  5动态
    private int type;

    private int currentPosition=0;

    private int userId;

    public static VideoFragment getInstance(int type,int userId) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("userId", userId);
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
        userId = getArguments().getInt("userId");
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

                currentPosition=position;

                DynamicModel.RecordsBean bean =adapter.getList().get(position);
                String videoUrl =bean.getDyFile();
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, videoUrl);
                intent.putExtra("type",2);
                intent.putExtra(PictureConfig.EXTRA_PREVIEW_VIDEO, true);
                Temporary.bean=bean;
                startActivityForResult(intent,100);
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


    private void requestData(final int page) {
        showRDialog();
        String url=URLConstant.DYNAMIC_LIST;
        if (type==2){
            //我的收藏
            url=URLConstant.MY_COLLECT;
        }
        params.put("dyType",2);
        if (userId!=0){
            params.put("userId",userId);
        }
        OKHttpManager.getJoint(url, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                adapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    List<DynamicModel.RecordsBean> list=response.getData().getRecords();
                    adapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    public void refreshData(){
        mRvList.setReqPage(1);
        requestData(1);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (resultCode==200){
                List<DynamicModel.RecordsBean> list= adapter.getList();
                DynamicModel.RecordsBean recordsBean=list.get(currentPosition);
                DynamicModel.RecordsBean updateBean= (DynamicModel.RecordsBean) Temporary.bean;
                recordsBean.setIsCollect(updateBean.getIsCollect());
                recordsBean.setIsAttention(updateBean.getIsAttention());
                adapter.notifyItemChanged(currentPosition+1);
            }else if (resultCode==300){
                List<DynamicModel.RecordsBean> list= adapter.getList();
                list.remove(currentPosition);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
