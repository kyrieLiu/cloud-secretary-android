package com.luck.cloud.function.mine;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.home.HomeMenuBean;
import com.luck.cloud.function.login.LoginActivity;
import com.luck.cloud.function.mine.bean.PersonInfoBean;
import com.luck.cloud.function.mine.collect.MyCollectActivity;
import com.luck.cloud.function.mine.dynamic.DynamicActivity;
import com.luck.cloud.function.mine.footprint.FootPrintActivity;
import com.luck.cloud.function.mine.person.PersonListActivity;
import com.luck.cloud.function.mine.publish.MyPublishActivity;
import com.luck.cloud.function.mine.work.CalendarDesignateActivity;
import com.luck.cloud.function.study.StudyActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.pixplicity.sharp.OnSvgElementListener;
import com.pixplicity.sharp.Sharp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @Bind(R.id.iv_head_portrait)
    ImageView ivPortrait;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.tv_mine_identity)
    TextView tvMineIdentity;
    @Bind(R.id.iv_personal_right)
    ImageView ivPersonalRight;
    @Bind(R.id.rl_mine_menu)
    MeasureRecyclerView recyclerView;
    @Bind(R.id.tv_mine_dynamic)
    TextView tvDynamic;
    @Bind(R.id.tv_mine_attention)
    TextView tvAttention;
    @Bind(R.id.tv_mine_fans)
    TextView tvFans;

    @Override
    protected int getContentId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPersonRightIcon();
    }

    @Override
    protected void loadData() {
        getUserInfo();
        initFunctionMenu();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            getUserInfo();
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
                    tvMineName.setText(bean.getPeopleName());
                    GlideUtils.loadCircleImage(getContext(),ivPortrait,bean.getPhotoLogo());

                    tvDynamic.setText(String.valueOf(bean.getDynamicCount()));
                    tvAttention.setText(String.valueOf(bean.getAttentionCount()));
                    tvFans.setText(String.valueOf(bean.getFansCount()));

                    switch (bean.getUserType()){
                        case 1:
                            tvMineIdentity.setText(bean.getAffiliatedUnit()==null?"":bean.getAffiliatedUnit()+"   第一书记");
                            break;

                        case 2:
                            tvMineIdentity.setText(bean.getSchool()+"   大学生及高校");
                            break;
                        case 3:
                            tvMineIdentity.setText(bean.getVillage()+"   驻地村民");
                            break;
                        case 4:
                            tvMineIdentity.setText(bean.getIndustry());
                            break;
                        case 5:
                            tvMineIdentity.setText(bean.getSchool()+"   高校教师");
                            break;
                        case 6:
                            tvMineIdentity.setText(bean.getAffiliatedUnit()==null?"":bean.getAffiliatedUnit()+"   扶贫干部");
                            break;
                    }

                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    /**
     * 初始化功能菜单
     */
    private void initFunctionMenu() {
        String menus[] = {"我的发布", "我的收藏", "我的足迹", "我的名片", "工作日志", "钉钉交流", "退出登录"};
        int icons[] = {R.mipmap.mine_active, R.mipmap.mine_collect, R.mipmap.mine_dynamic,
                R.mipmap.mine_file, R.mipmap.mine_schedule, R.mipmap.dingding, R.mipmap.mine_setting};
        List<HomeMenuBean> list = new ArrayList<>();
        for (int i = 0; i < menus.length; i++) {
            HomeMenuBean model = new HomeMenuBean();
            model.setMenuName(menus[i]);
            model.setIconPath(icons[i]);
            list.add(model);
        }
        MineMenuAdapter<HomeMenuBean> adapter = new MineMenuAdapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeMenuBean bean = list.get(position);
                Intent intent=new Intent();
                switch (bean.getMenuName()) {
                    case "钉钉交流":
                        openDing("com.alibaba.android.rimet");
                        break;
                    case "我的发布":
                        intent.setClass(Objects.requireNonNull(getContext()), MyPublishActivity.class);
                        startActivity(intent);
                        break;
                    case "我的收藏":
                        intent.setClass(Objects.requireNonNull(getContext()), MyCollectActivity.class);
                        startActivity(intent);
                        break;
                    case "我的足迹":
                        intent.setClass(Objects.requireNonNull(getContext()), FootPrintActivity.class);
                        startActivity(intent);
                        break;
                    case "我的名片":
                        intent.setClass(Objects.requireNonNull(getContext()), PersonDataActivity.class);
                        startActivity(intent);
                        break;
                    case "工作日志":
                        intent.setClass(Objects.requireNonNull(getContext()), CalendarDesignateActivity.class);
                        startActivity(intent);
                        break;
                    case "退出登录":
                        LoginActivity.start(getContext());
                        break;
                }
            }
        });
    }

    private void openDing(String packageName) {
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo pi = null;
        try {
            pi = packageManager.getPackageInfo("com.alibaba.android.rimet", 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveInfo = apps.iterator().next();
        if (resolveInfo != null) {
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            this.startActivity(intent);
        }
    }

    private void setPersonRightIcon() {
        Sharp sharp = Sharp.loadResource(getResources(), R.raw.right_icon);
        sharp.setOnElementListener(new OnSvgElementListener() {
            @Override
            public void onSvgStart(@NonNull Canvas canvas, @Nullable RectF rectF) {

            }

            @Override
            public void onSvgEnd(@NonNull Canvas canvas, @Nullable RectF rectF) {

            }

            @Override
            public <T> T onSvgElement(@Nullable String s, @NonNull T t, @Nullable RectF rectF, @NonNull Canvas canvas, @Nullable RectF rectF1, @Nullable Paint paint) {
                Random random = new Random();
                paint.setColor(Color.parseColor("#ffffff"));
                return t;
            }

            @Override
            public <T> void onSvgElementDrawn(@Nullable String s, @NonNull T t, @NonNull Canvas canvas, @Nullable Paint paint) {

            }
        });
        sharp.into(ivPersonalRight);
    }

    @OnClick({R.id.ll_mine_personal,R.id.ll_dynamic,R.id.ll_attention,R.id.ll_fans})
    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_mine_personal:
                intent.setClass(getContext(), PersonDataActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.ll_dynamic:
                intent.setClass(getContext(), DynamicActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_attention:
                intent.setClass(getContext(), PersonListActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,100);
                break;
            case R.id.ll_fans:
                intent.setClass(getContext(), PersonListActivity.class);
                intent.putExtra("type",2);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            getUserInfo();
        }
    }
}
