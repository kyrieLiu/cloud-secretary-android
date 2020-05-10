package com.luck.cloud.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


import com.luck.cloud.common.activity.FileBrowserActivity;
import com.luck.cloud.common.activity.FileDisplayActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

    private volatile static FileUtil fileUtil;

    public static FileUtil getInstance() {
        if (fileUtil == null) {
            synchronized (JsonUtils.class) {
                fileUtil = new FileUtil();
            }
        }
        return fileUtil;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    public String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        return fileName;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }

        str = paramString.substring(i + 1);
        return str;
    }


//    /**
//     * 打开文件
//     */
//    public void openFile(FileBean fileBean, Context context) {
//        String url;
//        if (!TextUtils.isEmpty(fileBean.getAddr())) {
//            url = fileBean.getAddr();
//        } else {
//            url = fileBean.getUrl();
//        }
//        if (TextUtils.isEmpty(url)) {
//            ToastUtil.toastShortCenter("文件路径异常");
//            return;
//        }
//        Log.e("tag", "文件路径==" + url);
//        String type = getFileCategory(url);
//        if (type != null) {
//            switch (type) {
//                //图片资源
//                case "image":
//                    List<String> imageList = new ArrayList<String>();
//                    imageList.add(url);
//                    ImagePagerActivity.start(context, imageList, 1, fileBean.getName());
//                    break;
//                //用wps打开csv文件
//                case "csv":
//                    // case "doc":
//                    openFileWithWps(fileBean, context);
//                    break;
//                //用浏览器打开音频和视频
//                case "video":
//                case "audio":
//                case "html":
//                    FileBrowserActivity.start(context, url, fileBean.getName());
//                    break;
//                //基于腾讯服务加载加载文件
//                default:
//                    FileDisplayActivity.start(context, url, fileBean.getName());
//                    break;
//            }
//        }
//    }
//
//    /**
//     * 根据后缀名获取文件类型
//     *
//     * @param url
//     * @return
//     */
//    private String getFileCategory(String url) {
//        //获取后缀名前的分隔符"."在fName中的位置。
//        int dotIndex = url.lastIndexOf(".");
//        /* 获取文件的后缀名 */
//        String end = url.substring(dotIndex, url.length()).toLowerCase();
//        String type = "*/*";
//        for (int i = 0; i < MIME_MapTable.length; i++) {
//            if (end.equals(MIME_MapTable[i][0]))
//                type = MIME_MapTable[i][1];
//        }
//        return type;
//
//    }
//
//    private WpsUtil wpsUtil;
//
//    /**
//     * 用WPS打开文件
//     */
//    private void openFileWithWps(final FileBean fileBean, Context context) {
//        if (TextUtils.isEmpty(fileBean.getAddr())) return;
//        wpsUtil = new WpsUtil(new WpsUtil.WpsInterface() {
//            @Override
//            public void doRequest(String filePath) {
//                fileBean.setLocalPath(filePath);
//            }
//
//            @Override
//            public void doFinish() {
//                wpsUtil.appBack();
//            }
//        }, context);
//        if (!TextUtils.isEmpty(fileBean.getLocalPath())) {
//            File file = new File(fileBean.getLocalPath());
//            wpsUtil.openDocumentWithFile(file, true);
//        } else {
//            String url;
//            if (!TextUtils.isEmpty(fileBean.getAddr())) {
//                url = fileBean.getAddr();
//            } else {
//                url = fileBean.getUrl();
//            }
//            if (TextUtils.isEmpty(url)) {
//                ToastUtil.toastShortCenter("文件路径异常");
//                return;
//            }
//            wpsUtil.openDownFile(url);
//        }
//    }

    /**
     * 弹出文件打开插件,选择打开
     *
     * @param context
     * @param param
     */
    public void getWordFileIntent(Context context, String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/msword");

            @SuppressLint("WrongConstant") List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                    PackageManager.GET_ACTIVITIES);
            if (list.size() > 0) {
                context.startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
