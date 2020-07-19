package com.luck.cloud.function.home;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.base.BaseListBean;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.common.activity.PropertyServiceStandardSearchActivity;
import com.luck.cloud.common.activity.WebActivity;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.active.ActiveActivity;
import com.luck.cloud.function.office.OfficeActivity;
import com.luck.cloud.function.office.notice.NoticeBean;
import com.luck.cloud.function.study.StudyActivity;
import com.luck.cloud.function.study.StudyAdapter;
import com.luck.cloud.function.study.model.StudyDetailModel;
import com.luck.cloud.function.study.model.StudyScienceModel;
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

    private StudyAdapter<StudyScienceModel.RecordsBean> recommendAdapter;


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


        initFunctionMenu();//初始化功能菜单

        initBanner();//初始化banner图

        initInformations();//初始化列表

        initNotice();//初始化通知公告


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initInformations();
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
                        intent.putExtra("type", 1);
                        startActivity(intent);
                        break;
                    case "小云活动":
                        intent.setClass(getContext(), ActiveActivity.class);
                        startActivity(intent);
                        break;
                    case "小云科普":
                        intent.setClass(getContext(), StudyActivity.class);
                        intent.putExtra("type", 2);
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

        OKHttpManager.getJoint(URLConstant.HOME_NOTICE, params, new int[]{}, new OKHttpManager.ResultCallback<BaseListBean<NoticeBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseListBean<NoticeBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {
                    List<NoticeBean> list = response.getData();
                    if (list!=null&&list.size()>0){
                        List<ArticleListBean> communityList = new ArrayList<>();
                        for (NoticeBean bean:list){
                            ArticleListBean itemBean=new ArticleListBean();
                            itemBean.setTitle(bean.getTitleName());
                            itemBean.setId(bean.getNoticeId());
                            communityList.add(itemBean);
                        }
                        svNotice.setArticleList(communityList);
                        svNotice.setOnMyClickListener(new RelativeSwitcherView.OnMyClickListener() {
                            @Override
                            public void clickWhich(ArticleListBean bean) {
                                if (bean != null) {
                                getNoticeDetail(bean.getId());
                                }
                            }
                        });
                    }
                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    private void getNoticeDetail(int id) {
        showRDialog();
        OKHttpManager.getJoint(URLConstant.NOTICE_DETAIL, null, new int[]{id}, new OKHttpManager.ResultCallback<BaseBean<NoticeBean>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<NoticeBean> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {

                    Temporary.webContent = response.getData().getContent();

                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    startActivity(intent);


                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    /**
     * 初始化banner图
     */
    private void initBanner() {

        params.put("isfirst", 1);
        OKHttpManager.getJoint(URLConstant.STUDY_LIST, params, new int[]{1, 100}, new OKHttpManager.ResultCallback<BaseBean<StudyScienceModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<StudyScienceModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {
                    List<StudyScienceModel.RecordsBean> list = response.getData().getRecords();
                    showBanner(list);
                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    private void showBanner(List<StudyScienceModel.RecordsBean> list) {
        List<String> bannerList = new ArrayList<>();
        for (StudyScienceModel.RecordsBean bean : list) {
            bannerList.add(bean.getTitlePicture());
        }
//        List<Integer> imageURL = new ArrayList<Integer>() {
//            {
//                add(R.mipmap.banner1);
//                add(R.mipmap.banner2);
//                add(R.mipmap.banner3);
//            }
//        };

        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                StudyScienceModel.RecordsBean bean = list.get(position);
                getDetailData(bean);
            }
        });

        mBanner.startAutoPlay();
    }

    private void getDetailData(StudyScienceModel.RecordsBean bean) {
        showRDialog();
        OKHttpManager.getJoint(URLConstant.STUDY_DETAIL, null, new int[]{bean.getInid()}, new OKHttpManager.ResultCallback<BaseBean<StudyDetailModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<StudyDetailModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {

                    Temporary.webContent = response.getData().getContent();

                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    startActivity(intent);


                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    /**
     * 初始化列表
     */
    private void initInformations() {
        recommendAdapter = new StudyAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvWaitDone.setLayoutManager(layoutManager);
        mRvWaitDone.setAdapter(recommendAdapter);
        mRvWaitDone.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        recommendAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                StudyScienceModel.RecordsBean itemsBean = recommendAdapter.getList().get(position);
                getDetailData(itemsBean);
            }
        });

        showRDialog();
        OKHttpManager.getJoint(URLConstant.STUDY_LIST, null, new int[]{1, 10}, new OKHttpManager.ResultCallback<BaseBean<StudyScienceModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
                refreshLayout.finishRefresh();
                //adapter.setErrorReqList(message, mRvList);
            }

            @Override
            public void onResponse(BaseBean<StudyScienceModel> response) {
                refreshLayout.finishRefresh();
                hideRDialog();
                if (response.getCode().equals("SUCCESS")) {
                    List<StudyScienceModel.RecordsBean> list = response.getData().getRecords();
                    recommendAdapter.setList(list);
                } else {
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);

    }

    @OnClick({R.id.main_search, R.id.ll_home_wait_done_more})
    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_home_wait_done_more:
                intent.setClass(getContext(), MoreRecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.main_search:
                intent.setClass(getContext(), PropertyServiceStandardSearchActivity.class);
                startActivity(intent);
                break;
        }
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
                    requestPermissions(LOCATIONGPS, LOCATION_READ_PHONE_STATE);
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
        public void onReceiveLocation(BDLocation location) {
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
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }
}
