package com.luck.cloud.function.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.cloud.GlideEngine;
import com.luck.cloud.MainActivity;
import com.luck.cloud.R;
import com.luck.cloud.adapter.GridImageAdapter;
import com.luck.cloud.base.BaseActivity;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseFragment;
import com.luck.cloud.callback.OnItemClickRecyclerListener;
import com.luck.cloud.common.activity.PropertyServiceStandardSearchActivity;
import com.luck.cloud.common.entity.RequestBean;
import com.luck.cloud.common.helper.CodePermissionHelper;
import com.luck.cloud.common.helper.FileCommitModel;
import com.luck.cloud.common.helper.PermissionCode;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.config.RxConstant;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.mine.HomeWaitDoneAdapter;
import com.luck.cloud.function.mine.WaitDoneBean;
import com.luck.cloud.function.office.OfficeActivity;
import com.luck.cloud.manager.RxManager;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.GlideImageLoader;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.utils.view.GlideUtils;
import com.luck.cloud.utils.view.ViewUtil;
import com.luck.cloud.widget.MeasureRecyclerView;
import com.luck.cloud.widget.MyScrollView;
import com.luck.cloud.widget.view.LoadExceptionView;
import com.luck.cloud.widget.xrecycler.ItemLinearDivider;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.pixplicity.sharp.OnSvgElementListener;
import com.pixplicity.sharp.Sharp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by liuyin on 2019/2/21 14:00
 * <p>
 * Describe 首页
 */
public class HomeActivity extends BaseActivity {
    //功能菜单列表
    @Bind(R.id.rv_home_function_menu)
    RecyclerView mRvMenu;
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

    @Bind(R.id.iv_home_menu)
    ImageView mIvHeadPicture;

    private ScienceAdapter<SuperviseHandleBean.ItemsBean> waitDoneAdapter;


    private PictureParameterStyle mPictureParameterStyle;
    private PictureCropParameterStyle mCropParameterStyle;


    @Override
    protected void back() {
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle bundle) {
        //将导航栏设置为透明色
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void loadData() {

        initBanner();//初始化banner图

        initFunctionMenu();//初始化功能菜单

        initInformations();//初始化列表

        getDefaultStyle();



        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestWaitDone();
            }
        });
    }

    /**
     * 初始化功能菜单
     */
    private void initFunctionMenu() {
        String menus[] = {"小云办公", "小云见证", "小云科普","小云活动","小云学习","小云交流"};
        int icons[] = {R.raw.study, R.raw.science, R.raw.first_secretary,R.raw.witness,R.raw.practice,R.raw.dingding};
        int colors[] = {Color.rgb(253,67,78),Color.rgb(255,96,49),Color.rgb(255,152,82),
                Color.rgb(0,132,245), Color.rgb(96,138,250),Color.rgb(160,118,250)};
        List<HomeMenuBean> list = new ArrayList<>();
        for (int i = 0; i < menus.length; i++) {
            HomeMenuBean model = new HomeMenuBean();
            model.setMenuName(menus[i]);
            model.setIconPath(icons[i]);
            model.setColor(colors[i]);
            list.add(model);
        }
        HomeMenuAdapter adapter = new HomeMenuAdapter(list, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRvMenu.setLayoutManager(layoutManager);
        mRvMenu.setAdapter(adapter);

        adapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeMenuBean bean=list.get(position);
                switch (bean.getMenuName()){
                    case "小云交流":
                        openDing("com.alibaba.android.rimet");
                        break;
                    default:
                        Intent intent=new Intent(HomeActivity.this, OfficeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void openDing(String packageName) {
        PackageManager packageManager = this.getPackageManager();
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
        if (resolveInfo != null ) {
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
        waitDoneAdapter = new ScienceAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvWaitDone.setLayoutManager(layoutManager);
        mRvWaitDone.setAdapter(waitDoneAdapter);
        mRvWaitDone.addItemDecoration(new ItemLinearDivider(1, ViewUtil.dp2px(10), ViewUtil.dp2px(10), getResources().getColor(R.color.gray_color)));
        waitDoneAdapter.setOnItemClickRecyclerAdapter(new OnItemClickRecyclerListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        // requestWaitDone();
        List<SuperviseHandleBean.ItemsBean> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            SuperviseHandleBean.ItemsBean bean=new SuperviseHandleBean.ItemsBean();
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

    @OnClick({R.id.main_search,R.id.iv_home_menu})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_home_menu:
               selectPicture();
                break;
            case R.id.main_search:
                Intent intent=new Intent(this, PropertyServiceStandardSearchActivity.class);
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


    private void selectPicture(){
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                // .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                //.isAutomaticTitleRecyclerTop(false)// 连续点击标题栏RecyclerView是否自动回到顶部,默认true
                //.loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                //.setOutputCameraPath()// 自定义相机输出目录，只针对Android Q以下，例如 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +  File.separator + "Camera" + File.separator;
                //.setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 设置自定义相机按钮状态
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .maxVideoSelectNum(1) // 视频最大选择数量
                //.minVideoSelectNum(1)// 视频最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(true)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义视频播放回调控制，用户可以使用自己的视频播放界面
                //.bindCustomCameraInterfaceListener(new MyPictureSelectorInterfaceListener())// 提供给用户的一些额外的自定义操作回调
                //.cameraFileName(System.currentTimeMillis() +".jpg")    // 重命名拍照文件名、如果是相册拍照则内部会自动拼上当前时间戳防止重复，注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
                //.renameCompressFile(System.currentTimeMillis() +".jpg")// 重命名压缩文件名、 如果是多张压缩则内部会自动拼上当前时间戳防止重复
                //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 如果是多张裁剪则内部会自动拼上当前时间戳防止重复
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isEnableCrop(true)// 是否裁剪
                //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                //.setCircleDimmedColor(ContextCompat.getColor(getContext(), R.color.app_color_white))// 设置圆形裁剪背景色值
                //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .hideBottomControls(false)
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                //.videoMinSecond(10)// 查询多少秒以内的视频
                //.videoMaxSecond(15)// 查询多少秒以内的视频
                //.recordVideoSecond(10)//录制视频秒数 默认60s
                .isPreviewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(false) // 裁剪是否可旋转图片
                //.scaleEnabled(false)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                 .forResult(new MyResultCallback(mIvHeadPicture,getContext()));
    }
    private void getDefaultStyle() {
        // 相册主题
        mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = false;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up;
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down;
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 选择相册目录背景样式
        mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_new_item_select_bg;
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector;
        // 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(getContext(), R.color.picture_color_grey);
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval;
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_fa632d);
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_fa632d);
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(getContext(), R.color.picture_color_grey);
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_wechat_checkbox;
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(getContext(), R.color.app_color_white);
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e");
//        // 自定义相册右侧文本内容设置
//        mPictureParameterStyle.pictureRightDefaultText = "";
//        // 自定义相册未完成文本内容
//        mPictureParameterStyle.pictureUnCompleteText = "";
//        // 自定义相册完成文本内容
//        mPictureParameterStyle.pictureCompleteText = "";
//        // 自定义相册列表不可预览文字
//        mPictureParameterStyle.pictureUnPreviewText = "";
//        // 自定义相册列表预览文字
//        mPictureParameterStyle.picturePreviewText = "";
//
//        // 自定义相册标题字体大小
//        mPictureParameterStyle.pictureTitleTextSize = 18;
//        // 自定义相册右侧文字大小
//        mPictureParameterStyle.pictureRightTextSize = 14;
//        // 自定义相册预览文字大小
//        mPictureParameterStyle.picturePreviewTextSize = 14;
//        // 自定义相册完成文字大小
//        mPictureParameterStyle.pictureCompleteTextSize = 14;
//        // 自定义原图文字大小
//        mPictureParameterStyle.pictureOriginalTextSize = 14;

        // 裁剪主题
        mCropParameterStyle = new PictureCropParameterStyle(
                ContextCompat.getColor(getContext(), R.color.app_color_grey),
                ContextCompat.getColor(getContext(), R.color.app_color_grey),
                Color.parseColor("#393a3e"),
                ContextCompat.getColor(getContext(), R.color.app_color_white),
                mPictureParameterStyle.isChangeStatusBarFontColor);
    }
    public Context getContext() {
        return this;
    }
    /**
     * 返回结果回调
     */
    private static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<ImageView> mAdapterWeakReference;
        private Context context;

        public MyResultCallback(ImageView imageView,Context context) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(imageView);
            this.context=context;
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            if (mAdapterWeakReference.get() != null) {
//                mAdapterWeakReference.get().setList(result);
//                mAdapterWeakReference.get().notifyDataSetChanged();
            }
            Log.d("tag","图片==="+result);
            GlideUtils.loadCircleImage(context,mAdapterWeakReference.get(),result.get(0).getPath());
//            FileCommitModel commitModel = FileCommitModel.getInstance();
//            String path=result.get(0).getPath();
//            ArrayList<String> list=new ArrayList<>();
//            list.add(path);
//            SpUtil.setToken("r8rk/OxEOsLV5WOVp8VISVWN0La6wiDr82V1tyFJfiq6iaql2su/2dfUrWNo5oLzO8tBC9rlTZhytXmsAOGNjNke3c8ZkXs4cKSjdMtFJao=");
//            commitModel.commitComplaintData(list,"");
//            OKHttpManager.getAsyn("http://47.114.80.143/api/station/info/page/1/10", new OKHttpManager.ResultCallback<BaseBean>() {
//                @Override
//                public void onError(int code, String result, String message) {
//
//                }
//
//                @Override
//                public void onResponse(BaseBean response) {
//                        Log.d("tag","返回");
//                }
//            });
        }

        @Override
        public void onCancel() {
        }
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
