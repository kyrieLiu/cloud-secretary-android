package com.luck.cloud.function.witness.dynamic;

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
import com.luck.cloud.function.witness.ScienceAdapter;
import com.luck.cloud.function.witness.SuperviseHandleBean;
import com.luck.cloud.function.witness.model.CommentModel;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.dialog.CommentDialog;
import com.luck.cloud.widget.dialog.DateSelectDialog;
import com.luck.cloud.widget.dialog.SelectMenuDialog;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

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
    private DynamicAdapter<DynamicModel> adapter;
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
        mRvList.addItemDecoration(new ItemLinearDivider(15, 0, 0, getResources().getColor(R.color.gray_color_shallow)));
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

    private void setListener(){
        adapter.setClickListener(new DynamicAdapter.ItemClickListener() {

            @Override
            public void commentCallback(DynamicModel model, int position) {
                CommentDialog dialog=CommentDialog.newInstance();
                dialog.setConfirmCommentListener(new CommentDialog.ConfirmCommentListener() {
                    @Override
                    public void addComment(String content) {
                        model.getCommentModel().add(new CommentModel("刘隐",content));
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show(getActivity().getFragmentManager(),"comment");

//                DateSelectDialog dialog1=new DateSelectDialog(getContext());
//                dialog1.show();
            }

            @Override
            public void transmitCallback(DynamicModel model, int position) {
                ToastUtil.toastShortCenter("已转发");
            }

            @Override
            public void deleteItem(DynamicModel model, int position) {
                SelectMenuDialog dialog=new SelectMenuDialog(getContext());
                dialog.setListener(new SelectMenuDialog.OnMenuSelectListener() {
                    @Override
                    public void callback() {

                    }
                });
                dialog.show();
                dialog.setTitle("提醒");
                dialog.setContent("是否删除该信息");
            }
        });
    }


    /**
     * 请求督查督办列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        List<DynamicModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<CommentModel> models=new ArrayList<>();
            models.add(new CommentModel("刘隐","很强啊,小伙子"));
            models.add(new CommentModel("魏明","你这个地方很厉害啊"));
            models.add(new CommentModel("王青青","哈哈,有机会去找你"));
            DynamicModel model=new DynamicModel();
            model.setCommentModel(models);
            list.add(model);
        }
        adapter.setSuccessReqList(list, 13, page, mRvList, "暂无推荐内容");
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
//                List<DynamicModel> list = null;
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
