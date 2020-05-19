package com.luck.cloud.common.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.common.adapter.PropertyServiceStandardAdapter;
import com.luck.cloud.common.entity.PropertyServiceStandardBean;
import com.luck.cloud.common.entity.RequestBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * Description:物业服务标准管理搜索页面
 */
public class PropertyServiceStandardSearchActivity extends BaseActivity {
    @Bind(R.id.rv_common_list)
    XRecyclerView mRvList;
    @Bind(R.id.et_search)
    EditText mEtSearch;

    private String parkId;

    private PropertyServiceStandardAdapter<PropertyServiceStandardBean.ItemsBean> adapter;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        parkId = getIntent().getStringExtra("parkId");

        //搜索框监听
        searchListener();
    }

    private void searchListener() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchStr = charSequence.toString().trim();
                requestData(1, searchStr);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void loadData() {
        adapter = new PropertyServiceStandardAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(adapter);
        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                PropertyServiceStandardBean.ItemsBean itemsBean = adapter.getList().get(position);
                WebActivity.start(PropertyServiceStandardSearchActivity.this, itemsBean.getDocUrl(), "详情");
            }
        });
        mRvList.setLoadingMoreEnabled(false);
        mRvList.setPullRefreshEnabled(false);
    }

    /**
     * 获取数据
     *
     * @param page
     * @param searchStr
     */
    private void requestData(final int page, String searchStr) {
        final RequestBean request = initRequestParams();
        request.getPageable().setCurrent(page);
        request.getPageable().setSize(100000);
        RequestBean.EntityBean entityBean = new RequestBean.EntityBean();
        entityBean.setTitle(searchStr);
        if (!TextUtils.isEmpty(parkId) && !("0".equals(parkId))) {
            entityBean.setAcParkId(parkId);
        }
        request.setEntity(entityBean);
        OKHttpManager.postJsonRequest(URLConstant.GET_DOC_LIST, request, new OKHttpManager.ResultCallback<BaseBean<PropertyServiceStandardBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<PropertyServiceStandardBean> response) {
                final List<PropertyServiceStandardBean.ItemsBean> list = response.getData().getItems();
                adapter.setList(list);
            }
        }, this);
    }
}
