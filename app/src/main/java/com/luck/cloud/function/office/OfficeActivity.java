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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.common.activity.ModifyActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.home.SuperviseHandleBean;
import com.luck.cloud.function.mine.bean.PersonInfoBean;
import com.luck.cloud.function.office.beans.LowIncomePerson;
import com.luck.cloud.function.office.lowincome.RemoveLowIncomeActivity;
import com.luck.cloud.function.office.notice.NoticeActivity;
import com.luck.cloud.function.office.clock.ClockInActivity;
import com.luck.cloud.function.office.files.MySharedFilesActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.luck.cloud.widget.dialog.SelectMenuDialog;
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

    private List<LowIncomePerson> personList = new ArrayList<>();

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
        arrangeAdapter.setListener(new ArrangeAdapter.ArrangeClickListener() {
            @Override
            public void deleteCallback(SuperviseHandleBean.ItemsBean bean, int position) {
                SelectMenuDialog dialog = new SelectMenuDialog(OfficeActivity.this);
                dialog.setListener(new SelectMenuDialog.OnMenuSelectListener() {
                    @Override
                    public void callback() {
                        dialog.dismiss();
                        List<SuperviseHandleBean.ItemsBean> list = arrangeAdapter.getList();
                        list.remove(position);
                        arrangeAdapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
                dialog.setTitle("提醒");
                dialog.setContent("是否删除该数据");
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

    @OnClick({R.id.ll_office_clock, R.id.ll_office_form, R.id.ll_office_notice, R.id.ll_office_document, R.id.add_arrange,
            R.id.edit_village, R.id.add_low_income, R.id.add_low_remove})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_office_clock:
                intent.setClass(this, ClockInActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_office_form:
                File file = new File(Environment.getExternalStorageDirectory(), "");
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                File targetFile = new File(file, "新元社区APP隐私政策1.docx");
                openFile(this, file);
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
                intent.setClass(this, AddArrangeActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.edit_village:
                intent.setClass(this, ModifyActivity.class);
                intent.putExtra("title", "编辑驻村情况");
                intent.putExtra("content", "请输入驻村情况");
                intent.putExtra("originalContent", tvVillage.getText().toString());
                startActivityForResult(intent, 200);
                break;
            case R.id.add_low_income:
                intent.setClass(this, ModifyActivity.class);
                intent.putExtra("title", "添加贫困户");
                intent.putExtra("content", "请输入贫苦户");
                startActivityForResult(intent, 300);
                break;
            case R.id.add_low_remove:
                intent.setClass(this, RemoveLowIncomeActivity.class);
                Temporary.list = personList;
                startActivityForResult(intent, 400);
                break;
        }
    }

    private void getUserInfo() {
        int id = SpUtil.getUerId();
        OKHttpManager.getJoint(URLConstant.USER_INFO, null, new int[]{id}, new OKHttpManager.ResultCallback<BaseBean<PersonInfoBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(BaseBean<PersonInfoBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {
                    PersonInfoBean bean = response.getData();
                    tvUsername.setText(bean.getPeopleName());
                    GlideUtils.loadCircleImage(OfficeActivity.this, iveHead, bean.getPhotoLogo());

                } else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            if (requestCode == 200) {
                //编辑驻村情况
                String content = data.getStringExtra("content");
                tvVillage.setText(content);
            } else if (requestCode == 300) {
                //添加贫困户
                String content = data.getStringExtra("content");
                LowIncomePerson person = new LowIncomePerson();
                person.setUsername(content);
                personList.add(person);
               refreshLowIncome(personList);
            } else if (requestCode == 400) {
                refreshLowIncome(personList);
            }
        }
    }
    private void refreshLowIncome(List<LowIncomePerson> list){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < personList.size(); i++) {
            LowIncomePerson item = personList.get(i);
            if (i < personList.size() - 1) {
                buffer.append(item.getUsername() + ",");
            } else {
                buffer.append(item.getUsername());
            }
        }
        tvPoverty.setText(buffer.toString());
    }
}
