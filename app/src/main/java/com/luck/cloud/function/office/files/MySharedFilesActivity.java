package com.luck.cloud.function.office.files;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseRecordBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.callback.OnRecyclerLoadingListener;
import com.luck.cloud.common.activity.FileBrowserActivity;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.study.model.StudyScienceModel;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.FileUtil;
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

    private MySharedFilesAdapter<SharedFilesBean> mAdapter;

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
                SharedFilesBean bean = mAdapter.getList().get(position);
                if (TextUtils.isEmpty(bean.getFilePath())) {
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
    private void requestPermission(final SharedFilesBean bean) {
        if (mHelper == null) mHelper = new PermissionHelper(this);
        mHelper.requestPermissions(getString(R.string.voice_play_hint),
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
//                        FileBean fileBean = new FileBean();
//                        fileBean.setAddr(bean.getFileAddr());
//                        fileBean.setName(bean.getFileSharingTypeTitle());
//                        FileUtil.getInstance().openFile(fileBean, MySharedFilesActivity.this);
                        Intent intent=new Intent(MySharedFilesActivity.this, FileBrowserActivity.class);
                        intent.putExtra("title",bean.getFileName());
                        intent.putExtra("url",bean.getFilePath());
                        startActivity(intent);
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mHelper.againWarnRequestPermission(getString(R.string.image_permission_hint), MySharedFilesActivity.this);
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 请求列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        showRDialog();
        OKHttpManager.getJoint(URLConstant.FILE_LIST, params,new int[]{page,10}, new OKHttpManager.ResultCallback<BaseRecordBean<SharedFilesBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                mAdapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseRecordBean<SharedFilesBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    ArrayList<SharedFilesBean> list=response.getData().getRecords();
                    mAdapter.setSuccessReqList(list, 10, page, mRvList, "暂无内容");
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mHelper != null)
            mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
