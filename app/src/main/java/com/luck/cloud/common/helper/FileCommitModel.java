package com.luck.cloud.common.helper;

import android.text.TextUtils;
import android.util.Log;


import com.luck.cloud.base.BaseBean;
import com.luck.cloud.common.entity.FileUploadBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyin on 2018/8/5 9:45
 * 处理上传图片
 */
public class FileCommitModel {
    private static volatile FileCommitModel viewModel;

    private int uploadNumber;//记录已经上传文件的数量
    private int allFileCount;//所有上传文件的数量
    private List<String> imageServerList;//记录文件在服务器的路径
    private String voiceServerPath;//记录语音文件在服务器的路径

    /**
     * 以单例模式获取实例
     *
     * @return 实例
     */
    public static FileCommitModel getInstance() {
        if (viewModel == null) {
            synchronized (FileCommitModel.class) {
                if (viewModel == null) {
                    viewModel = new FileCommitModel();
                }
            }
        }
        return viewModel;
    }


    /**
     * 提交文件列表
     */
    public void commitComplaintData(ArrayList<String> comImages, String voicePath) {


        allFileCount = 0;
        uploadNumber = 0;
        imageServerList = new ArrayList<>();
        voiceServerPath = null;
        List<File> imageFiles = new ArrayList<>();
        if (comImages.size() > 0) {
            for (int i = 0; i < comImages.size(); i++) {
                File file = new File(comImages.get(i));
                if (file != null) {
                    imageFiles.add(file);
                    allFileCount++;
                }
            }
        }
        if (!TextUtils.isEmpty(voicePath)) {
            File voiceFile = new File(voicePath);
            if (voiceFile != null) {
                allFileCount++;
                uploadFile(voiceFile, 1);
            }
        }

        for (File file : imageFiles) {
            uploadFile(file, 0);
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @param type 文件类型  0代表图片文件    1代表语音文件
     */
    public void uploadFile(final File file, final int type) {
        if (file == null) return;
        OKHttpManager.commonUploadFile(URLConstant.UPLOAD_FILE,
                "file", file, new OKHttpManager.ResultCallback<BaseBean<FileUploadBean>>() {

                    @Override
                    public void onError(int code, String result, String message) {
                        uploadNumber++;
                        if (allFileCount == uploadNumber) {//文件上传完毕
                            if (listener != null) {
                                listener.fileUploadError();
                            }
                        }
                    }

                    @Override
                    public void onResponse(BaseBean<FileUploadBean> response) {
                        uploadNumber++;
                        if (type == 0) {
                            imageServerList.add(response.getData().getFilePath());
                        } else {
                            voiceServerPath = response.getData().getFilePath();
                        }
                        Log.d("tag","imageList=="+imageServerList);
                        if (allFileCount == uploadNumber) {//文件上传完毕
                            if (listener != null) {
                                listener.fileUploadFinish(imageServerList, voiceServerPath);
                            }
                        }
                    }
                }, this);
    }

    public void cancelHttp() {
        OKHttpManager.cancelTag(this);
    }

    private OnModelCallbackListener listener;

    public void setOnModelCallbackListener(OnModelCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnModelCallbackListener {
        void fileUploadFinish(List<String> imageList, String voicePath);

        void fileUploadError();
    }
}
