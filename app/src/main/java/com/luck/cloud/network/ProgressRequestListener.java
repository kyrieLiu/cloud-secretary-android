package com.luck.cloud.network;

/**
 * Created by liuyin on 2018/8/5 17:00
 * @Describe 定义接口,回调文件传输进度数据
 */
public interface ProgressRequestListener {
        /**
         * 文件上传进度查询
         * @param bytesWritten  当前写入的字节数
         * @param contentLength  请求体总字节数
         * @param done  上传是否完成
         */
        void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}