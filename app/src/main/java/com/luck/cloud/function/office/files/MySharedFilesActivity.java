package com.luck.cloud.function.office.files;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.utils.PermissionHelper;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.cloud.widget.xrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的共享文件
 */
public class MySharedFilesActivity extends BaseActivity {

    @Bind(R.id.rv_common_list)
    XRecyclerView mRvList;

    @Bind(R.id.tv_right)
    TextView mTvRight;

    private PermissionHelper mHelper;

    private MySharedFilesAdapter<SharedFilesBean.ItemsBean> mAdapter;

    //筛选条件的code值
    private String filterCode;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.common_activity_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("小云文档");
    }

    @Override
    protected void loadData() {

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
        mAdapter = new MySharedFilesAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(mAdapter);
        mRvList.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        mAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                SharedFilesBean.ItemsBean bean = mAdapter.getList().get(position);
                if (TextUtils.isEmpty(bean.getFileAddr())) {
                    ToastUtil.toastShortCenter("暂无文件");
                } else {
                    requestPermission(bean);
                }

            }
        });

        mRvList.refresh();

    }

    /**
     * 获取权限
     */
    private void requestPermission(final SharedFilesBean.ItemsBean bean) {
        if (mHelper == null) mHelper = new PermissionHelper(this);
        mHelper.requestPermissions(getString(R.string.voice_play_hint),
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
//                        FileBean fileBean = new FileBean();
//                        fileBean.setAddr(bean.getFileAddr());
//                        fileBean.setName(bean.getFileSharingTypeTitle());
//                        FileUtil.getInstance().openFile(fileBean, MySharedFilesActivity.this);
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mHelper.againWarnRequestPermission(getString(R.string.image_permission_hint), MySharedFilesActivity.this);
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 获取共享文件数据
     *
     * @param page
     */
    private void requestData(final int page) {
        List<SharedFilesBean.ItemsBean> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new SharedFilesBean.ItemsBean());
        }
        mAdapter.setSuccessReqList(list, 13, page, mRvList,"暂无推荐内容");
//        final RequestBean request = initRequestParams();
//        request.getPageable().setCurrent(page);
//        RequestBean.CondBean condBean = request.getCond();
//        final HashMap<String, String> map = new HashMap<>();
//        map.put("field", "fileSharingType");
//        map.put("data", filterCode);
//        map.put("op", "eq");
//        List<HashMap> list = new ArrayList<HashMap>() {{
//            add(map);
//        }};
//        condBean.setGroupOp("AND");
//        condBean.setRules(list);
//        showRDialog();
//        OKHttpManager.postJsonRequest(URLConstant.SHARED_FILES_LIST, request, new OKHttpManager.ResultCallback<BaseBean<SharedFilesBean>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//                mAdapter.setErrorReqList(message, mRvList);
//            }
//
//            @Override
//            public void onResponse(BaseBean<SharedFilesBean> response) {
//                hideRDialog();
//                final List<SharedFilesBean.ItemsBean> list = response.getBody().getItems();
//                mAdapter.setSuccessReqList(list, request.getPageable().getSize(), page, mRvList,"暂无共享文件");
//
//            }
//        }, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mHelper != null)
            mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 获取筛选数据
     */
    private void getFilterList() {
        List<SharedFilesBean.ItemsBean> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new SharedFilesBean.ItemsBean());
        }
        mAdapter.setSuccessReqList(list, 13, page, mRvList,"暂无推荐内容");
//        showRDialog();
//        OKHttpManager.postAsyn(URLConstant.SHARED_FILES_FILTER_LIST, params, new OKHttpManager.ResultCallback<BaseListBean<ShareFilterBean>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                hideRDialog();
//                ToastUtil.toastShortCenter(message);
//            }
//
//            @Override
//            public void onResponse(BaseListBean<ShareFilterBean> response) {
//                hideRDialog();
//                filterList = response.getBody();
//                if (filterList != null && filterList.size() > 0) {
//
//                    showMenuFilter(filterList);
//                } else {
//                    ToastUtil.toastShortCenter("暂无筛选条件");
//                }
//
//
//            }
//        }, this);
    }

}
