package com.luck.cloud.function.mine.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseRecordBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import org.apache.poi.util.ArrayUtil;

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
        adapter = new PersonAdapter(this,type);
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
//                if (bean.getIsAttention()==1){
//                    bean.setIsAttention(0);
//                }else{
//                    bean.setIsAttention(1);
//                }
//                adapter.notifyItemChanged(position+1);
                handleAttention(bean,position);
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

    //关注
    private void handleAttention(PersonModel bean, int position){
        params.clear();
        String url=URLConstant.ATTENTION;
        if (bean.getIsAttention()==1){
            url=URLConstant.ATTENTION_CANCEL;
            params.put("id",type==1?bean.getUserId():bean.getFansId());
        }else{
            params.put("userId",type==1?bean.getUserId():bean.getFansId());
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
                    }else{
                        bean.setIsAttention(1);
                    }
                    adapter.notifyItemChanged(position + 1);
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
//        params.put("contentType",type);
        params.clear();
        String url=URLConstant.MY_ATTENTION;
        if (type==2){
            url=URLConstant.MY_FANS;
        }
        OKHttpManager.getJoint(url, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseRecordBean<PersonModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                adapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseRecordBean<PersonModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    ArrayList<PersonModel> list=response.getData().getRecords();

                    for (int i=0;i<list.size();i++){
                        PersonModel model=list.get(i);
                        if (type==1){
                            model.setIsAttention(1);
                        }
                    }

                   adapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
