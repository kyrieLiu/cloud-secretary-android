package com.luck.cloud.function.witness.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.GlideEngine;
import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.PlayVideoActivity;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.function.witness.ScienceAdapter;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.function.witness.model.CommentModel;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.function.witness.model.DynamicModel.RecordsBean;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.dialog.CommentDialog;
import com.luck.cloud.widget.dialog.DateSelectDialog;
import com.luck.cloud.widget.dialog.SelectMenuDialog;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 动态列表
 */
public class DynamicFragment extends BaseFragment {
    @Bind(R.id.xrv_common_list)
    XRecyclerView mRvList;
    private DynamicAdapter<DynamicModel.RecordsBean> adapter;
    private Context context;
    private int type;


    public static DynamicFragment getInstance(int type) {
        DynamicFragment fragment = new DynamicFragment();
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
        adapter = new DynamicAdapter(context);
        setListener();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(adapter);
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

    private void setListener() {
        adapter.setClickListener(new DynamicAdapter.ItemClickListener() {

            @Override
            public void commentCallback(DynamicModel.RecordsBean model, int position) {
                CommentDialog dialog = CommentDialog.newInstance();
                dialog.setConfirmCommentListener(new CommentDialog.ConfirmCommentListener() {
                    @Override
                    public void addComment(String content) {
                       handleComment(model,position,content);
                    }
                });
                dialog.show(getActivity().getFragmentManager(), "comment");

//                DateSelectDialog dialog1=new DateSelectDialog(getContext());
//                dialog1.show();
            }

            @Override
            public void transmitCallback(DynamicModel.RecordsBean model, int position) {
                ToastUtil.toastShortCenter("已转发");
            }

            @Override
            public void likeCallback(DynamicModel.RecordsBean bean, int position) {
                handleLike(bean,position);
            }

            @Override
            public void deleteCallback(DynamicModel.RecordsBean model, int position) {
                SelectMenuDialog dialog = new SelectMenuDialog(getContext());
                dialog.setListener(new SelectMenuDialog.OnMenuSelectListener() {
                    @Override
                    public void callback() {
                        dialog.dismiss();
                        handleDelete(model,position);
                    }
                });
                dialog.show();
                dialog.setTitle("提醒");
                dialog.setContent("是否删除该信息");
            }

            @Override
            public void collectCallback(DynamicModel.RecordsBean bean, int position) {
                handleCollect(bean,position);
            }

            @Override
            public void playVideoCallback(DynamicModel.RecordsBean bean, int position) {
                String videoUrl =bean.getDyFile();
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, videoUrl);
                intent.putExtra(PictureConfig.EXTRA_PREVIEW_VIDEO, true);
                startActivity(intent);
            }

            @Override
            public void attentionCallback(RecordsBean model, int position) {
                handleAttention(model,position);
            }
        });
    }
    //关注
    private void handleAttention(DynamicModel.RecordsBean bean,int position){
        params.clear();
        String url=URLConstant.ATTENTION;
        if (bean.getIsAttention()==1){
            url=URLConstant.ATTENTION_CANCEL;
            params.put("id",bean.getUserId());
        }else{
            params.put("userId",bean.getUserId());
        }
        showRDialog();
        OKHttpManager.postJsonRequest(url, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    if (bean.getIsAttention()==1){
                        bean.setIsAttention(0);
                        ToastUtil.toastShortCenter("取消关注");
                    }else{
                        ToastUtil.toastShortCenter("已关注");
                        bean.setIsAttention(1);
                    }
                    adapter.notifyItemChanged(position + 1);
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }
    //点赞
    private void handleLike(DynamicModel.RecordsBean bean,int position){
        params.clear();
        String url=URLConstant.LIKE;
        if (bean.getIsLike()==1){
            url=URLConstant.LIKE_CANCEL;
            params.put("id",bean.getDyId());
        }else{
            params.put("userId",bean.getUserId());
        }
        params.put("dyId",bean.getDyId());
        showRDialog();
        OKHttpManager.postJsonRequest(url, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    if (bean.getIsLike()==1){
                        bean.setLikeCount(bean.getLikeCount()-1);
                        bean.setIsLike(0);
                    }else{
                        bean.setLikeCount(bean.getLikeCount()+1);
                        bean.setIsLike(1);
                    }
                    adapter.notifyItemChanged(position + 1);
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }
    //收藏
    private void handleCollect(DynamicModel.RecordsBean bean,int position){
        params.clear();
        String url=URLConstant.COLLECT;
        if (bean.getIsCollect()==1){
            url=URLConstant.COLLECT_CANCEL;
            params.put("id",bean.getDyId());
        }else{
            params.put("userId",bean.getUserId());
        }
        params.put("dyId",bean.getDyId());
        showRDialog();
        OKHttpManager.postJsonRequest(url, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    if (bean.getIsCollect()==1){
                        bean.setIsCollect(0);
                    }else{
                        bean.setIsCollect(1);
                    }
                    adapter.notifyItemChanged(position + 1);
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    //评论
    private void handleComment(DynamicModel.RecordsBean bean,int position,String message){
        params.clear();
        params.put("dyId",bean.getDyId());
        params.put("userId",bean.getUserId());
        params.put("messageContent",message);
        showRDialog();
        OKHttpManager.postJsonRequest(URLConstant.COMMENT, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    if (bean.getMessages()==null){
                        bean.setMessages(new ArrayList<CommentModel>());
                    }
                    CommentModel model=new CommentModel();
                    model.setUserName(SpUtil.getName());
                    model.setMessageContent(message);
                    bean.getMessages().add(model);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }
    //删除
    private void handleDelete(DynamicModel.RecordsBean bean,int position){
        params.clear();
        params.put("id",bean.getDyId());
        showRDialog();
        OKHttpManager.postJsonRequest(URLConstant.DYNAMIC_REMOVE, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    ToastUtil.toastShortCenter("删除成功");
                    List<DynamicModel.RecordsBean> list=adapter.getList();
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }


    private void requestData(final int page) {
        showRDialog();
        params.clear();
        params.put("dyType",1);
        OKHttpManager.getJoint(URLConstant.DYNAMIC_LIST, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
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

    public void refreshData(String key){
        mRvList.setReqPage(1);
        requestData(1);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
