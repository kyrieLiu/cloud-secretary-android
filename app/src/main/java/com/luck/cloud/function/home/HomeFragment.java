package com.luck.cloud.function.home;

import android.Manifest;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luck.cloud.R;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.common.activity.PropertyServiceStandardSearchActivity;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.RequestBean;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.active.ActiveActivity;
import com.luck.cloud.function.main.MainActivity;
import com.luck.cloud.function.mine.WaitDoneBean;
import com.luck.cloud.function.office.OfficeActivity;
import com.luck.cloud.function.office.clock.ClockInActivity;
import com.luck.cloud.function.study.StudyActivity;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.GlideImageLoader;
import com.luck.cloud.utils.PermissionHelper;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.luck.cloud.widget.RelativeSwitcherView;
import com.luck.cloud.widget.view.LoadExceptionView;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by liuyin on 2019/2/21 14:00
 * <p>
 * Describe 首页
 */
public class HomeFragment extends BaseFragment {
    //功能菜单列表
    @Bind(R.id.rv_home_function_menu)
    MeasureRecyclerView mRvMenu;
    //刷新控件
    @Bind(R.id.srl_home_refresh)
    SmartRefreshLayout refreshLayout;
    //待办事项父布局
    @Bind(R.id.ll_home_wait_done_parent)
    LinearLayout mLlWaitDoneParent;
    //待办事项列表
    @Bind(R.id.rl_wait_done)
    MeasureRecyclerView mRvWaitDone;
    @Bind(R.id.banner)
    Banner mBanner;//banner轮播图
    //待办事项请求返回数据提示
    @Bind(R.id.view_wait_done_warn)
    LoadExceptionView mViewWaitWarn;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.sv_notice)
    RelativeSwitcherView svNotice;

    private ScienceAdapter<SuperviseHandleBean.ItemsBean> waitDoneAdapter;


    private static final int LOCATION_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private LocationManager lm;

    private PermissionHelper mHelper;


    @Override
    protected int getContentId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle bundle) {

    }


    @Override
    protected void loadData() {

        initBanner();//初始化banner图

        initFunctionMenu();//初始化功能菜单

        initInformations();//初始化列表

        initNotice();//初始化通知公告


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestWaitDone();
            }
        });

        //requestPermission();
        showGPSContacts();

    }


    /**
     * 初始化功能菜单
     */
    private void initFunctionMenu() {
        String menus[] = {"小云办公", "小云学习", "小云活动", "小云科普"};
        int icons[] = {R.raw.study, R.raw.science, R.raw.first_secretary, R.raw.witness, R.raw.practice, R.raw.dingding};
        int colors[] = {Color.rgb(253, 67, 78), Color.rgb(255, 96, 49), Color.rgb(255, 152, 82),
                Color.rgb(0, 132, 245), Color.rgb(96, 138, 250), Color.rgb(160, 118, 250)};
        List<HomeMenuBean> list = new ArrayList<>();
        for (int i = 0; i < menus.length; i++) {
            HomeMenuBean model = new HomeMenuBean();
            model.setMenuName(menus[i]);
            model.setIconPath(icons[i]);
            model.setColor(colors[i]);
            list.add(model);
        }
        HomeMenuAdapter adapter = new HomeMenuAdapter(list, getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRvMenu.setLayoutManager(layoutManager);
        mRvMenu.setAdapter(adapter);

        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeMenuBean bean = list.get(position);
                Intent intent = new Intent();
                switch (bean.getMenuName()) {
                    case "小云办公":
                        intent.setClass(getContext(), OfficeActivity.class);
                        startActivity(intent);
                        break;
                    case "小云学习":
                        intent.setClass(getContext(), StudyActivity.class);
                        startActivity(intent);
                        break;
                    case "小云活动":
                        intent.setClass(getContext(), ActiveActivity.class);
                        startActivity(intent);
                        break;
//                    default:
//                        intent.setClass(getContext(), OfficeActivity.class);
//                        startActivity(intent);
//                        break;
                }
            }
        });
    }

    private void initNotice() {
        List<ArticleListBean> communityList = new ArrayList<>();
        communityList.add(new ArticleListBean("内蒙古通辽市寻找一名病虫防止专家"));
        communityList.add(new ArticleListBean("内蒙古通辽市开鲁县招募10名道路清扫志愿者"));
        svNotice.setArticleList(communityList);
        svNotice.setOnMyClickListener(new RelativeSwitcherView.OnMyClickListener() {
            @Override
            public void clickWhich(ArticleListBean bean) {
                if (bean != null) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });
    }

    /**
     * 初始化banner图
     */
    private void initBanner() {
        List<Integer> imageURL = new ArrayList<Integer>() {
            {
                add(R.mipmap.banner1);
                add(R.mipmap.banner2);
                add(R.mipmap.banner3);
            }
        };

        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(imageURL);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //ToastUtil.toastShortCenter("position==" + position);
                //H5Activity.start(this, "详情", "http://wwww.baidu.com");
            }
        });

        mBanner.startAutoPlay();

    }


    /**
     * 初始化列表
     */
    private void initInformations() {
        waitDoneAdapter = new ScienceAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvWaitDone.setLayoutManager(layoutManager);
        mRvWaitDone.setAdapter(waitDoneAdapter);
        mRvWaitDone.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        waitDoneAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                getContext().startActivity(intent);
            }
        });

        // requestWaitDone();
        List<SuperviseHandleBean.ItemsBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SuperviseHandleBean.ItemsBean bean = new SuperviseHandleBean.ItemsBean();
            list.add(bean);
        }
        waitDoneAdapter.setList(list);

    }

    /**
     * 请求待办事项数据
     */
    private void requestWaitDone() {
        RequestBean requestBean = initRequestParams();

//        OKHttpManager.postJsonRequest(URLConstant.WAIT_DONE_HOME, requestBean, new OKHttpManager.ResultCallback<BaseBean<WaitDoneBean>>() {
//            @Override
//            public void onError(int code, String result, String message) {
//                if (refreshLayout.getState() == RefreshState.Refreshing) {
//                    refreshLayout.finishRefresh();
//                }
//                mViewWaitWarn.setVisibility(View.VISIBLE);
//                mViewWaitWarn.setExceptionMessage(AppConstants.HTTP_CONNECT_ERROR);
//            }
//
//            @Override
//            public void onResponse(BaseBean<WaitDoneBean> response) {
//                if (refreshLayout.getState() == RefreshState.Refreshing) {
//                    refreshLayout.finishRefresh();
//                }
//                final List<WaitDoneBean.ItemsBean> list = response.getBody().getItems();
//                if (list == null || list.size() == 0) {
//                    mViewWaitWarn.setVisibility(View.VISIBLE);
//                    mViewWaitWarn.setExceptionMessage("暂无待办事项");
//                    mRvWaitDone.setVisibility(View.GONE);
//                } else {
//                    mViewWaitWarn.setVisibility(View.GONE);
//                    mRvWaitDone.setVisibility(View.VISIBLE);
//                }
//                waitDoneAdapter.setList(list);
//            }
//        }, this);
    }

    @OnClick({R.id.main_search})
    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
//            case R.id.iv_home_mine:
//              intent.setClass(getContext(), MineActivity.class);
//              startActivity(intent);
//                break;
            case R.id.main_search:
                intent.setClass(getContext(), PropertyServiceStandardSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 请求待办事项列表数据
     *
     * @param page
     */
    private void requestData(final int page) {
        final RequestBean request = initRequestParams();
        request.getPageable().setCurrent(page);
        showRDialog();
        OKHttpManager.getAsyn(URLConstant.WAIT_DONE_MORE, new OKHttpManager.ResultCallback<BaseBean<WaitDoneBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<WaitDoneBean> response) {
                hideRDialog();
                final List<WaitDoneBean.ItemsBean> list = response.getData().getItems();
            }
        }, this);
    }


    /**
     * 检测GPS、位置权限是否开启
     */

    public void showGPSContacts() {

        //得到系统的位置服务，判断GPS是否激活
        lm = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    //ActivityCompat.requestPermissions(getActivity(), LOCATIONGPS, LOCATION_READ_PHONE_STATE);
                    requestPermissions( LOCATIONGPS, LOCATION_READ_PHONE_STATE);
                } else {
                    startLocation();
                }
            } else {
                startLocation();
            }
        } else {
            Toast.makeText(getContext(), "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }

    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
//             requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case LOCATION_READ_PHONE_STATE:
                //如果用户取消，permissions可能为null.
                if (grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0) { //有权限
                    startLocation();
                } else {
                    Toast.makeText(getContext(), "你未开启定位权限!", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
    }

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            tvAddress.setText(location.getCity());
        }
    }
    private void startLocation() {

        mLocationClient = new LocationClient(getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        option.setNeedNewVersionRgc(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true

        mLocationClient.setLocOption(option);

        mLocationClient.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //结束轮播
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }
}
