package com.luck.cloud.function.mine;

import android.content.Intent;
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
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.function.home.HomeMenuBean;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.pixplicity.sharp.OnSvgElementListener;
import com.pixplicity.sharp.Sharp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @Bind(R.id.iv_home_menu)
    ImageView ivHomeMenu;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.tv_mine_identity)
    TextView tvMineIdentity;
    @Bind(R.id.iv_personal_right)
    ImageView ivPersonalRight;
    @Bind(R.id.rl_mine_menu)
    MeasureRecyclerView recyclerView;

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
        initFunctionMenu();
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
        MineMenuAdapter adapter = new MineMenuAdapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeMenuBean bean = list.get(position);
                switch (bean.getMenuName()) {
                    case "":
                        break;
                    default:
                        Intent intent = new Intent(getContext(), CollectActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
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

    @OnClick({R.id.ll_mine_personal})
    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_mine_personal:
                intent.setClass(getContext(), PersonDataActivity.class);
                startActivity(intent);
                break;
        }
    }

}
