package com.luck.cloud.function.mine.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liuyin on 2019/4/16 15:11
 * Description: 我的粉丝和我的关注
 */
public class PersonListActivity extends BaseActivity {
    @Bind(R.id.rv_common_list)
    XRecyclerView mRvList;
    private PersonAdapter<PersonModel> adapter;

    private StudyTabModel dict;
    //1关注  2粉丝
    private int type;


    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.common_activity_list;
    }

    @Override
    protected void initView(Bundle bundle) {
            type=getIntent().getIntExtra("type",1);
            if (type==1){
                setTitle("我的关注");
            }else{
                setTitle("我的粉丝");
            }
    }

    @Override
    protected void loadData() {
        adapter = new PersonAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(layoutManager);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        // mRvList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRvList.setAdapter(adapter);
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                PersonModel itemsBean = adapter.getList().get(position);

                Intent intent=new Intent(PersonListActivity.this,PersonWitnessActivity.class);
                startActivity(intent);

            }
        });
        adapter.setListener(new PersonAdapter.OnPersonClickListener() {
            @Override
            public void attentionCallback(PersonModel bean,int position) {
                bean.setStatus(2);
                adapter.notifyItemChanged(position+1);
            }
        });
        mRvList.setLoadingListener(new OnRecyclerLoadingListener() {
            @Override
            public void onRefresh() {
                requestData(1,null);
            }

            @Override
            public void onLoadMore(int reqPage) {
                requestData(reqPage,null);
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

                    Intent intent=new Intent(PersonListActivity.this,WebActivity.class);
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
    private void requestData(final int page,String keyWord) {
        List<PersonModel> list=new ArrayList<>();
        for (int i=0;i<6;i++){
            PersonModel model=new PersonModel();
            model.setStatus(1);
            list.add(model);
        }
        adapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
//        showRDialog();
//        OKHttpManager.getJoint(URLConstant.STUDY_LIST, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseBean<StudyScienceModel>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//                adapter.setErrorReqList(message, mRvList);
//            }
//
//            @Override
//            public void onResponse(BaseBean<StudyScienceModel> response) {
//                hideRDialog();
//                if (response.getCode().equals("SUCCESS")){
//                    List<StudyScienceModel.RecordsBean> list=response.getData().getRecords();
//                    adapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
//                }else{
//                    ToastUtil.toastShortCenter(response.getMsg());
//                }
//            }
//        }, this);
    }

    public void searchData(String keyWord){
        requestData(1,keyWord);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
