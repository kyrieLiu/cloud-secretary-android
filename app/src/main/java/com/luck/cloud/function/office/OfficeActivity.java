package com.luck.cloud.function.office;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.function.home.ScienceAdapter;
import com.luck.cloud.function.home.SuperviseHandleBean;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.luck.cloud.widget.view.LoadExceptionView;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @OnClick({R.id.ll_office_clock, R.id.ll_office_form, R.id.ll_office_notice, R.id.ll_office_document})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_office_clock:
                break;
            case R.id.ll_office_form:
                break;
            case R.id.ll_office_notice:
                break;
            case R.id.ll_office_document:
                break;
        }
    }
}
