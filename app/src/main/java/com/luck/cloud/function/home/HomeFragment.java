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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

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
        String menus[] = {"小云办公", "小云学习", "小云科普", "小云活动"};
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
//                String body="<p>史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇女，" +
//                        "那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻烦你" +
//                        "给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对高" +
//                        "房价是的分开管理史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇" +
//                        "女，那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻" +
//                        "烦你给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对" +
//                        "高房价是的分开管理史莱克的积分凉快圣诞节福利卡接收到了分开讲道理；反馈给家里的分管局领导； 分开了大家转发来看是剪短发了恐惧的妇" +
//                        "女，那地方，你的，父母，小丑女，没出息女，模拟现场，门店，电脑发的，烦恼歌带你飞，等你再放开你的身份，给你的，父母给你，的麻烦你" +
//                        "给，sdmfnsdjfngjksdnfjndfgjnsdjfs.dj,fng,sdjfn的扣分监考老师电饭锅水电费能够理解对方了第三方机构开始的返回给会计师对高房价是" +
//                        "的分开管理<img src=\"http://wgzx.test.jingcaiwang.cn/group1/M00/00/68/rBMBOF0J-8WABHG6AAGBabvhhYo556.png\" title=\" 000.png\" alt=\" 000.png\" style=\"white-space: normal;\"/></p>";
//                Temporary.webContent=body;
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


//    /**
//     * 请求内存卡读写权限
//     */
//    private void requestPermission() {
//        mHelper = new PermissionHelper(this);
//        mHelper.requestPermissions(getResources().getString(R.string.image_permission_first_hint),
//                new PermissionHelper.PermissionListener() {
//                    @Override
//                    public void doAfterGrand(String... permission) {
//                        //startLocation();
//                    }
//
//                    @Override
//                    public void doAfterDenied(String... permission) {
//                        mHelper.againWarnRequestPermission(getResources().getString(R.string.image_permission_hint), AppApplication.getInstance());
//                    }
//                }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
//    }
//
//    //授权回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (mHelper != null)
//            mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    private void startLocation() {
//        //初始化定位
//        mLocationClient = new AMapLocationClient(AppApplication.getInstance());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                tvAddress.setText(aMapLocation.getCity());
//            }
//        });
//        AMapLocationClientOption option = new AMapLocationClientOption();
//        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
//        if (null != mLocationClient) {
//            mLocationClient.setLocationOption(option);
//            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//            mLocationClient.stopLocation();
//            mLocationClient.startLocation();
//        }
//    }

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
                    ActivityCompat.requestPermissions(getActivity(), LOCATIONGPS, LOCATION_READ_PHONE_STATE);
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

//    public AMapLocationClientOption mLocationOption=null;
//
//    private void startLocation(){
//        mLocationClient = new AMapLocationClient(AppApplication.getInstance());
//        mLocationOption = new AMapLocationClientOption();
////设置返回地址信息，默认为true
//        mLocationOption.setNeedAddress(true);
////设置定位监听
//        mLocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                Log.d("tag","定位成功"+aMapLocation.getCity());
//                tvAddress.setText(aMapLocation.getCity());
//            }
//        });
////设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
////设置定位间隔,单位毫秒,默认为2000ms
//       // mLocationOption.setInterval(2000);
////设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        mLocationClient.startLocation();
//    }

    private void startLocation() {
//        //初始化定位
        mLocationClient = new AMapLocationClient(AppApplication.getInstance());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                tvAddress.setText(aMapLocation.getCity());
            }
        });
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //结束轮播
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient = null;
        }
    }
}
