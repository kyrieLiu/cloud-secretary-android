package com.luck.cloud.function.office;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.home.SuperviseHandleBean;
import com.luck.cloud.function.mine.bean.PersonInfoBean;
import com.luck.cloud.function.office.notice.NoticeActivity;
import com.luck.cloud.function.office.clock.ClockInActivity;
import com.luck.cloud.function.office.files.MySharedFilesActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.luck.cloud.widget.view.LoadExceptionView;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OfficeActivity extends BaseActivity {


    @Bind(R.id.iv_office_head)
    ImageView iveHead;
    @Bind(R.id.tv_office_username)
    TextView tvUsername;
    @Bind(R.id.tv_office_performance)
    TextView tvPerformance;
    @Bind(R.id.rl_wait_done)
    MeasureRecyclerView rlWaitDone;
    @Bind(R.id.view_wait_done_warn)
    LoadExceptionView viewWaitDoneWarn;
    @Bind(R.id.ll_home_wait_done_parent)
    LinearLayout llHomeWaitDoneParent;
    @Bind(R.id.tv_office_village)
    TextView tvVillage;
    @Bind(R.id.tv_office_poverty)
    TextView tvPoverty;

    private ArrangeAdapter<SuperviseHandleBean.ItemsBean> arrangeAdapter;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_office;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("小云办公");


    }

    @Override
    protected void loadData() {
        getUserInfo();
        initArrange();
    }

    /**
     * 初始化列表
     */
    private void initArrange() {
        arrangeAdapter = new ArrangeAdapter<>(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlWaitDone.setLayoutManager(layoutManager);
        rlWaitDone.setAdapter(arrangeAdapter);
        rlWaitDone.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        arrangeAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        // requestWaitDone();
        List<SuperviseHandleBean.ItemsBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SuperviseHandleBean.ItemsBean bean = new SuperviseHandleBean.ItemsBean();
            list.add(bean);
        }
        arrangeAdapter.setList(list);

    }

    @OnClick({R.id.ll_office_clock, R.id.ll_office_form, R.id.ll_office_notice, R.id.ll_office_document,R.id.add_arrange})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_office_clock:
                intent.setClass(this, ClockInActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_office_form:
                File file = new File(Environment.getExternalStorageDirectory(),"");
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                File targetFile = new File(file, "新元社区APP隐私政策1.docx");
                Log.d("tag",file.getName());
                openFile(this,file);
                break;
            case R.id.ll_office_notice:
                intent.setClass(this, NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_office_document:
                intent.setClass(this, MySharedFilesActivity.class);
                startActivity(intent);
                break;
            case R.id.add_arrange:
                break;
        }
    }

    private void getUserInfo(){
        int id= SpUtil.getUerId();
        OKHttpManager.getJoint(URLConstant.USER_INFO, null,new int[]{id}, new OKHttpManager.ResultCallback<BaseBean<PersonInfoBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(BaseBean<PersonInfoBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    PersonInfoBean bean=response.getData();
                    tvUsername.setText(bean.getPeopleName());
                    GlideUtils.loadCircleImage(OfficeActivity.this,iveHead,bean.getPhotoLogo());

                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    public static void openFile(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
//        Uri uriForFile;
//        if (Build.VERSION.SDK_INT > 23) {
//            //Android 7.0之后
//            uriForFile = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
//            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);//给目标文件临时授权
//        } else {
//            uriForFile = Uri.fromFile(file);
//        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//系统会检查当前所有已创建的Task中是否有该要启动的Activity的Task;
        // 若有，则在该Task上创建Activity；若没有则新建具有该Activity属性的Task，并在该新建的Task上创建Activity。
        intent.setDataAndType(null, "application/msword");
        context.startActivity(intent);
    }
}
