package com.luck.cloud.common.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.luck.cloud.R;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.entity.Temporary;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.function.witness.model.DynamicModel;
import com.luck.cloud.manager.ActivitiesManager;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.SpUtil;
import com.luck.cloud.utils.ToastUtil;
import com.luck.cloud.widget.dialog.SelectMenuDialog;
import com.luck.picture.lib.PictureBaseActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.SdkVersionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author：luck
 * @data：2017/8/28 下午11:00
 * @描述: 视频播放类
 */
public class PlayVideoActivity extends PictureBaseActivity implements
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, View.OnClickListener {
    private String videoPath;
    private ImageButton ibLeftBack;
    private MediaController mMediaController;
    private VideoView mVideoView;
    private ImageView iv_play;
    private int mPositionWhenPaused = -1;

    private Dialog loadingDialog;

    private TextView tvAttention,tvCollect,tvDelete;

    //1动态  2视频
    private int type;
    private DynamicModel.RecordsBean bean;
    private boolean isUpdate=false;

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    public boolean isRequestedOrientation() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getResourceId() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void initPictureSelectorStyle() {
        if (config.style != null) {
            if (config.style.pictureLeftBackIcon != 0) {
                ibLeftBack.setImageResource(config.style.pictureLeftBackIcon);
            }
        }
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        initView();

        boolean isExternalPreview = getIntent().getBooleanExtra
                (PictureConfig.EXTRA_PREVIEW_VIDEO, false);
        if (TextUtils.isEmpty(videoPath)) {
            LocalMedia media = getIntent().getParcelableExtra(PictureConfig.EXTRA_MEDIA_KEY);
            if (media == null || TextUtils.isEmpty(media.getPath())) {
                finish();
                return;
            }
            videoPath = media.getPath();
        }
        if (TextUtils.isEmpty(videoPath)) {
            closeActivity();
            return;
        }
        ibLeftBack = findViewById(R.id.pictureLeftBack);
        mVideoView = findViewById(R.id.video_view);
        mVideoView.setBackgroundColor(Color.BLACK);
        iv_play = findViewById(R.id.iv_play);
        mMediaController = new MediaController(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setMediaController(mMediaController);
        ibLeftBack.setOnClickListener(this);
        iv_play.setOnClickListener(this);

//        tvConfirm.setVisibility(config.selectionMode
//                == PictureConfig.SINGLE
//                && config.enPreviewVideo && !isExternalPreview ? View.VISIBLE : View.GONE);
    }

    private void initView(){
        tvAttention=findViewById(R.id.tv_attention);
        tvCollect=findViewById(R.id.tv_collect);
        tvDelete=findViewById(R.id.tv_delete);

        videoPath = getIntent().getStringExtra(PictureConfig.EXTRA_VIDEO_PATH);
        type=getIntent().getIntExtra("type",1);
        if (type==1){
            tvAttention.setVisibility(View.GONE);
            tvCollect.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
        }else{
            bean= (DynamicModel.RecordsBean) Temporary.bean;
            if (bean.getUserId()== SpUtil.getUerId()){
                tvAttention.setVisibility(View.GONE);
                tvCollect.setVisibility(View.GONE);
            }else{
                tvDelete.setVisibility(View.GONE);
            }
            if (bean.getIsAttention()==1){
                tvAttention.setText("取消关注");
            }else{
                tvAttention.setText("关注");
            }
            if (bean.getIsCollect()==1){
                tvCollect.setText("取消收藏");
            }else{
                tvCollect.setText("收藏");
            }

            tvAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleAttention();
                }
            });
            tvCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleCollect();
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectMenuDialog dialog = new SelectMenuDialog(getContext());
                    dialog.setListener(new SelectMenuDialog.OnMenuSelectListener() {
                        @Override
                        public void callback() {
                            dialog.dismiss();
                            handleDelete();
                        }
                    });
                    dialog.show();
                    dialog.setTitle("提醒");
                    dialog.setContent("是否删除该信息");
                }
            });
        }
    }
    //删除
    private void handleDelete(){
        HashMap<String,Object> params=new HashMap<>();
        params.clear();
        params.put("id",bean.getDyId());
        showRDialog();
        OKHttpManager.postJsonRequest(URLConstant.DYNAMIC_REMOVE, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    ToastUtil.toastShortCenter("删除成功");
                    setResult(300);
                    finish();
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    private void handleCollect(){
        HashMap<String,Object> params=new HashMap<>();
        params.clear();
        String url= URLConstant.COLLECT;
        if (bean.getIsCollect()==1){
            url=URLConstant.COLLECT_CANCEL;
            params.put("id",bean.getDyId());
        }else{
            params.put("userId",bean.getUserId());
        }
        params.put("dyId",bean.getDyId());
        showRDialog();
        OKHttpManager.postJsonRequest(url, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    if (bean.getIsCollect()==1){
                        bean.setIsCollect(0);
                        tvCollect.setText("收藏");
                    }else{
                        bean.setIsCollect(1);
                        tvCollect.setText("取消收藏");
                    }
                    isUpdate=true;
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }
    private void handleAttention(){
        HashMap<String,Object> params=new HashMap<>();
        params.clear();
        String url=URLConstant.ATTENTION;
        if (bean.getIsAttention()==1){
            url=URLConstant.ATTENTION_CANCEL;
            params.put("id",bean.getUserId());
        }else{
            params.put("userId",bean.getUserId());
        }
        showRDialog();
        OKHttpManager.postJsonRequest(url, params, new OKHttpManager.ResultCallback<BaseBean<DynamicModel>>() {
            @Override
            public void onError(int code, String result, String message) {
                hideRDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean<DynamicModel> response) {
                hideRDialog();
                if (response.getCode().equals("SUCCESS")){
                    if (bean.getIsAttention()==1){
                        bean.setIsAttention(0);
                        tvAttention.setText("关注");
                    }else{
                        bean.setIsAttention(1);
                        tvAttention.setText("取消关注");
                    }
                    isUpdate=true;
                }else{
                    ToastUtil.toastShortCenter(response.getMsg());
                }
            }
        }, this);
    }

    @Override
    public void onStart() {
        showRDialog();
        // Play Video
        mMediaController.setAnchorView(mVideoView);
//        if (SdkVersionUtils.checkedAndroid_Q() && PictureMimeType.isContent(videoPath)) {
//            //mVideoView.setVideoURI(Uri.parse(videoPath));
//        } else {
//            //mVideoView.setVideoPath(videoPath);
//            mVideoView.setVideoURI(Uri.parse(videoPath));
//        }
        mVideoView.setVideoURI(Uri.parse(videoPath));

        mVideoView.start();
        // mVideoView.requestFocus();
        super.onStart();
    }

    @Override
    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMediaController = null;
        mVideoView = null;
        iv_play = null;

        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }

        OKHttpManager.cancelTag(this);

        super.onDestroy();
    }

    @Override
    public void onResume() {
        // Resume video player
        if (mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }

        super.onResume();
    }

    @Override
    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (null != iv_play) {
            iv_play.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pictureLeftBack) {
            onBackPressed();
        } else if (id == R.id.iv_play) {
            mVideoView.start();
            iv_play.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (config.windowAnimationStyle != null
                && config.windowAnimationStyle.activityPreviewExitAnimation != 0) {
            if (isUpdate){
                Temporary.bean=bean;
                setResult(200);
            }
            finish();
            overridePendingTransition(0, config.windowAnimationStyle != null
                    && config.windowAnimationStyle.activityPreviewExitAnimation != 0 ?
                    config.windowAnimationStyle.activityPreviewExitAnimation : com.luck.picture.lib.R.anim.picture_anim_exit);
        } else {
            closeActivity();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name)) {
                    return getApplicationContext().getSystemService(name);
                }
                return super.getSystemService(name);
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnInfoListener((mp1, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                hideRDialog();
                // video started
                mVideoView.setBackgroundColor(Color.TRANSPARENT);
                return true;
            }
            return false;
        });
    }
    public void showRDialog() {

        if (loadingDialog == null) {
            loadingDialog = new Dialog(this, R.style.custom_dialog_style);
            View dialogView = View.inflate(this, R.layout.common_waiting_dialog, (ViewGroup) null);
            loadingDialog.setContentView(dialogView);
            loadingDialog.setCanceledOnTouchOutside(false);//点击空白是否消失
        }
        if (!isFinishing() && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideRDialog() {
        if (loadingDialog != null) {
            if (!isFinishing() && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
        loadingDialog = null;
    }

}
