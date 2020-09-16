package com.luck.cloud.network;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.internal.$Gson$Types;
import com.luck.cloud.app.AppApplication;
import com.luck.cloud.base.BaseBean;
import com.luck.cloud.base.BaseListBean;
import com.luck.cloud.base.BaseRecordBean;
import com.luck.cloud.config.AppConstants;
import com.luck.cloud.function.login.LoginActivity;
import com.luck.cloud.utils.JsonUtils;
import com.luck.cloud.utils.LogUtil;
import com.luck.cloud.utils.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author jiang_zheng_yan 2018年4月17日08:46:43
 */
public class OKHttpManager {

    private static final String TAG = "OKHttpManager";

    private static OKHttpManager mInstance;
    private static OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

    private HttpsDelegate mHttpsDelegate = new HttpsDelegate();
    private DownloadDelegate mDownloadDelegate = new DownloadDelegate();
    private GetDelegate mGetDelegate = new GetDelegate();
    private PostDelegate mPostDelegate = new PostDelegate();
    private static Gson mGson = new Gson();

    public OKHttpManager() {
//    mOkHttpClient.setCookieHandler(new CookieManager(new PersistentCookieStore(), CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(200, TimeUnit.SECONDS);
        builder.writeTimeout(200, TimeUnit.SECONDS);
        builder.readTimeout(200, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OKHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (OKHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OKHttpManager();
                }
            }
        }
        return mInstance;
    }

    public GetDelegate getGetDelegate() {
        return mGetDelegate;
    }

    public PostDelegate getPostDelegate() {
        return mPostDelegate;
    }

    private HttpsDelegate _getHttpsDelegate() {
        return mHttpsDelegate;
    }

    public DownloadDelegate getDownloadDelegate() {
        return mDownloadDelegate;
    }

    public static void cancelTag(Object tag) {
        if (tag == null || mOkHttpClient == null) return;
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        List<Call> list = dispatcher.queuedCalls();
        if (list != null) {
            for (Call call : list) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
        List<Call> runningCalls = dispatcher.runningCalls();
        if (runningCalls != null) {
            for (Call call : runningCalls) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 取消所有请求请求
     */
    public static void cancelAll() {

        if (mOkHttpClient == null) return;
        mOkHttpClient.dispatcher().cancelAll();

    }

    //GET
    public static void getAsyn(String url, ResultCallback callback) {
        getInstance().getGetDelegate().getAsyn(url, callback, null);
    }

    public static void getAsyn(String url, ResultCallback callback, Object tag) {
        getInstance().getGetDelegate().getAsyn(url, callback, tag);
    }
    public static void getJoint(String url, HashMap<String, Object> map,int[] joint, ResultCallback callback, Object tag) {
        if (joint!=null&&joint.length>0){
            for (int i=0;i<joint.length;i++){
                int value=joint[i];
                url=url.concat("/"+value);
            }
        }

        if (map!=null&&map.size()>0){
            url=url.concat("?");
            for(String key:map.keySet()) {
                if (map.get(key)!=null){
                    String value= Objects.requireNonNull(map.get(key)).toString();
                    url=url.concat(key+"="+value+"&");
                }
            }
        }

        Log.e("tag","请求URL=="+url);

        getInstance().getGetDelegate().getAsyn(url, callback, tag);
    }
    public static void getJointObj(String url, HashMap<String, Object> map,Object[] joint, ResultCallback callback, Object tag) {
        if (joint!=null&&joint.length>0){
            for (int i=0;i<joint.length;i++){
                Object value=joint[i];
                url=url.concat("/"+value);
            }
        }

        if (map!=null&&map.size()>0){
            url=url.concat("?");
            for(String key:map.keySet()) {
                if (map.get(key)!=null){
                    String value= Objects.requireNonNull(map.get(key)).toString();
                    url=url.concat(key+"="+value+"&");
                }
            }
        }

        Log.e("tag","请求URL=="+url);

        getInstance().getGetDelegate().getAsyn(url, callback, tag);
    }


    /**
     * post键值对 请求网络数据
     *
     * @param url
     * @param map
     * @param callback
     * @param tag
     */
    public static void postAsyn(String url, HashMap map, final ResultCallback callback, Object tag) {
        if (url == null) {
            return;
        }
        getInstance().getPostDelegate().postAsyn(url, map, callback, tag);
    }

    /**
     * post  JSON数据提交
     *
     * @param url      地址
     * @param callback 回调
     * @param tag      上下文标识
     */
    public static void postJsonRequest(String url, Object object, ResultCallback callback, Object tag) {
        getInstance().getPostDelegate().buildPostRequest(url, object, callback, tag, true);
    }

    /**
     * post  JSON数据提交
     *
     * @param url      地址
     * @param map      数据
     * @param callback 回调
     * @param tag      上下文标识
     */
    public static void postJsonNoToken(String url, HashMap map, ResultCallback callback, Object tag) {
        getInstance().getPostDelegate().buildPostRequest(url, map, callback, tag, false);
    }


    /**
     * //post上传文件
     *
     * @param url
     * @param map
     * @param callback
     */
    public static void postAsyn(String url, HashMap map, String mFilePartKeyName, List<File> files, final ResultCallback callback) {
        if (url == null) {
            return;
        }
        getInstance().getPostDelegate().postAsyn(url, map, mFilePartKeyName, files, callback, null);
    }

    /**
     * //post上传单个文件
     *
     * @param url
     * @param callback
     */
    public static void commonUploadFile(String url, String mFilePartKeyName, File file, final ResultCallback callback, Object tag) {
        if (url == null) {
            return;
        }
        getInstance().getPostDelegate().postSingleFile(url, mFilePartKeyName, file, callback, tag);
    }


    private void deliveryResult(ResultCallback callback, final Request request) {
        if (callback == null) {
            callback = DEFAULT_RESULT_CALLBACK;
        }

        final ResultCallback resultCallback = callback;
        callback.onBefore(request);

        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //如果不是因取消请求导致的网络连接失败,可以对用户进行提示
                        Log.d("tag","e==="+e);
                        if (!call.isCanceled()) {
                            //  网络请求失败
                            sendFailedStringCallback(500, AppConstants.HTTP_CONNECT_ERROR, AppConstants.HTTP_CONNECT_ERROR, resultCallback);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            final String result = response.body().string();
                            LogUtil.e("onResponse: " + result);
                            if (resultCallback.mType == String.class) {
                                sendSuccessResultCallback(result, resultCallback);
                            } else {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.has("code")) {
                                    String code = jsonObject.getString("code");
                                    //token 过期,重新登录
                                    if (code.equals("401")) {
                                        LoginActivity.start(AppApplication.getInstance());
                                        return;
                                    }
                                }
                                Object object = JsonUtils.jsonToObjectWithType(result, resultCallback.mType);
                                if (object != null) {
                                    String code = "SUCCESS";
                                    String message = "";
                                    if (object instanceof BaseBean) {
                                        BaseBean baseBean = (BaseBean) object;
                                        code = baseBean.getCode();
                                        message = baseBean.getMsg()==null?baseBean.getMessage():baseBean.getMsg();
                                    } else if (object instanceof BaseListBean) {
                                        BaseListBean baseListBean = (BaseListBean) object;
                                        code = baseListBean.getCode();
                                        message = baseListBean.getMsg()==null?baseListBean.getMessage():baseListBean.getMsg();
                                    }else if (object instanceof BaseRecordBean) {
                                        BaseRecordBean baseListBean = (BaseRecordBean) object;
                                        code = baseListBean.getCode();
                                        message = baseListBean.getMsg()==null?baseListBean.getMessage():baseListBean.getMsg();
                                    }
                                    if ("SUCCESS".equals(code)) {
                                        sendSuccessResultCallback(object, resultCallback);
                                    } else if ("500".equals(code)) {
                                        sendSuccessResultCallback(object, resultCallback);
                                    } else {
                                        sendFailedStringCallback(400, result, message, resultCallback);
                                    }
                                } else {
                                    sendFailedStringCallback(600, AppConstants.HTTP_DATA_ERROR, AppConstants.HTTP_DATA_ERROR, resultCallback);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            sendFailedStringCallback(600, AppConstants.HTTP_DATA_ERROR, AppConstants.HTTP_DATA_ERROR, resultCallback);
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                            sendFailedStringCallback(600, AppConstants.HTTP_DATA_ERROR, AppConstants.HTTP_DATA_ERROR, resultCallback);
                        } catch (IOException e) {
                            sendFailedStringCallback(600, AppConstants.HTTP_DATA_ERROR, AppConstants.HTTP_CONNECT_ERROR, resultCallback);
                        }
                    }
                });
    }


    private void sendFailedStringCallback(final int code, final String result, final String message, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code, result, message);
                callback.onAfter();

            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    private String getFileName(String path) {

        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    private Request buildPostFormBodyRequestWithHashMap(String url, HashMap map, Object tag) {
        if (map == null) {
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        Log.e("OKHttp", "url: " + url + "  Authorization==" + SpUtil.getToken());
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object object = entry.getValue();
            String value;
            if (object instanceof Integer) {
                value = object.toString();
            } else {
                value = String.valueOf(object);
            }
            Log.e("params", "params:   " + entry.getKey() + "  :  " + value);
            builder.add(entry.getKey(), value);
        }
        Request.Builder reqBuilder = new Request.Builder();
        reqBuilder.url(url).post(builder.build());
        reqBuilder.addHeader("Content-Type", "application/json;charset=utf-8");
        reqBuilder.addHeader("Authorization", SpUtil.getToken());
        //reqBuilder.addHeader("Authorization", "customerff251d93e2394055a5ee3b7a248a43dd");
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        return reqBuilder.build();
    }

    /**
     * //上传图片
     *
     * @param url
     * @param map
     * @param mFilePartKeyName
     * @param files
     * @param callback
     * @param tag
     * @return
     */
    private Request buildPostFormBodyRequestWithHashMapAndListFiles(String url, HashMap<String, Object> map, String mFilePartKeyName, List<File> files, final ResultCallback callback, Object tag) {
        if (map == null) {
            return null;
        }

        MultipartBody.Builder builder_ = new MultipartBody.Builder();
        builder_.setType(MultipartBody.FORM);
        LogUtil.e("url:" + url);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!(value instanceof File)) {
                String pstring = (String) value;
                //参数为字符传
                if (!TextUtils.isEmpty(pstring))
                    builder_.addFormDataPart(key, pstring);
            } else {
                //为文件
                File file = (File) value;
                builder_.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
            LogUtil.e(key + " : " + value);
        }

        LogUtil.e("mFilePartKeyName : " + mFilePartKeyName);
        for (File file_ : files) {
            builder_.addFormDataPart(mFilePartKeyName, file_.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file_));
            LogUtil.e(":" + file_.getAbsolutePath());
        }
        MultipartBody requestBody = builder_.build();
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);

        reqBuilder.addHeader("Content-Type", "application/json;charset=utf-8");
        reqBuilder.addHeader("Authorization", SpUtil.getToken());

        if (tag != null) {
            reqBuilder.tag(tag);
        }
        return reqBuilder.build();
    }


    private Request buildPostJSONRequest(String url, Param[] params, Object tag) {
        if (params == null) {
            params = new Param[0];
        }
        JSONObject json = null;
        try {
            json = new JSONObject();
            for (Param param : params) {
                json.put(param.key, param.value);
            }
        } catch (JSONException e) {

        }
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
        Request.Builder reqBuilder = new Request.Builder();
        reqBuilder.url(url).post(body);
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        return reqBuilder.build();
    }


    public OkHttpClient client() {
        return mOkHttpClient;
    }

    public static OkHttpClient getClient() {
        return getInstance().client();
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter..");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }


        public void onBefore(Request request) {

        }

        public void onAfter() {

        }

        public abstract void onError(int code, String result, String message);

        public abstract void onResponse(T response);
    }

    private final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {


        @Override
        public void onError(int code, String result, String message) {

        }

        @Override
        public void onResponse(String response) {

        }
    };

    public static class Param {
        String key, value;

        public Param() {

        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


    //PostDelegate
    public class PostDelegate {
        private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");
        private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");
        private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

        public Response post(String url, Param[] params) throws IOException {
            return post(url, params, null);
        }

        // 同步的post请求
        public Response post(String url, Param[] params, Object tag) throws IOException {
            Request request = buildPostJSONRequest(url, params, tag);
            Response response = mOkHttpClient.newCall(request).execute();
            return response;
        }


        //异步post请求
        public void postAsyn(String url, Param[] params, final ResultCallback callback, Object tag) {
            Request request = buildPostJSONRequest(url, params, tag);
            deliveryResult(callback, request);
        }

        /**
         * //上传图片
         *
         * @param url
         * @param map
         * @param mFilePartKeyName 图片集合的键的名字
         * @param files
         * @param callback
         * @param tag
         */
        public void postAsyn(String url, HashMap map, String mFilePartKeyName, List<File> files, final ResultCallback callback, Object tag) {

            Request request = buildPostFormBodyRequestWithHashMapAndListFiles(url, map, mFilePartKeyName, files, callback, tag);
            deliveryResult(callback, request);
        }

        /**
         * 上传单个文件
         *
         * @param url
         * @param mFilePartKeyName 图片集合的键的名字
         * @param callback
         * @param tag
         */
        public void postSingleFile(String url, String mFilePartKeyName, File file, final ResultCallback callback, Object tag) {
            Log.e("OkHttp", "url==" + url + "  Authorization==" + SpUtil.getToken());
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
            multipartBuilder.setType(MultipartBody.FORM);

            multipartBuilder.addFormDataPart(mFilePartKeyName, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            RequestBody requestBody = multipartBuilder.build();
            //可监听上传进度
//            ProgressRequestBody body = new ProgressRequestBody(requestBody, new ProgressRequestListener() {
//
//                @Override
//                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
//                }
//            });

            Request.Builder reqBuilder = new Request.Builder().url(url).post(requestBody);
            if (tag != null) {
                reqBuilder.tag(tag);
            }

            reqBuilder.addHeader("Content-Type", "application/json;charset=utf-8");
            reqBuilder.addHeader("Authorization", SpUtil.getToken());


            Request request = reqBuilder.build();
            deliveryResult(callback, request);
        }

        /**
         * 普通请求网络
         *
         * @param url
         * @param map
         * @param callback
         * @param tag
         */
        public void postAsyn(String url, HashMap map, final ResultCallback callback, Object tag) {
            try {

                Request request = buildPostFormBodyRequestWithHashMap(url, map, tag);
                deliveryResult(callback, request);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        /**
         * post请求
         *
         * @param url
         * @param object
         * @param callback
         * @param tag
         * @param haveToken 是否携带token
         */
        public void buildPostRequest(String url, Object object, final ResultCallback callback, Object tag, boolean haveToken) {

            String json = mGson.toJson(object);
            Log.e("OkHttp", "   URL: " + url + "   params: " + json + "   Authorization:  " + SpUtil.getToken());
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
            Request.Builder builder = new Request.Builder();
            builder.url(url).post(requestBody);
            builder.addHeader("Content-Type", "application/json;charset=utf-8");
            if (haveToken) {
                builder.addHeader("Authorization", SpUtil.getToken());
            }

            if (tag != null) builder.tag(tag);
            Request request = builder.build();
            deliveryResult(callback, request);
        }

        //同步的post请求,直接将bodyStr写入请求体
        public Response post(String url, String bodyStr) throws IOException {
            return post(url, bodyStr, null);
        }

        public Response post(String url, String bodyStr, Object tag) throws IOException {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, bodyStr);
            Request request = buildPostRequest(url, body, tag);
            Response response = mOkHttpClient.newCall(request).execute();
            return response;
        }

        //同步的post请求，直接将bodyFile写入请求体
        public Response post(String url, File bodyFile) throws IOException {
            return post(url, bodyFile, null);
        }

        public Response post(String url, File bodyFile, Object tag) throws IOException {
            RequestBody body = RequestBody.create(MEDIA_TYPE_STREAM, bodyFile);
            Request request = buildPostRequest(url, body, tag);
            Response response = mOkHttpClient.newCall(request).execute();
            return response;
        }

        //同步的post请求
        public Response post(String url, byte[] bodyBytes) throws IOException {
            return post(url, bodyBytes, null);
        }

        public Response post(String url, byte[] bodyBytes, Object tag) throws IOException {
            RequestBody body = RequestBody.create(MEDIA_TYPE_STREAM, bodyBytes);
            Request request = buildPostRequest(url, body, tag);
            Response response = mOkHttpClient.newCall(request).execute();
            return response;
        }

        //直接将bodyStr写入请求体
        public void postAsyn(String url, String bodyStr, final ResultCallback callback) {
            postAsyn(url, bodyStr, callback, null);
        }

        public void postAsyn(String url, String bodyStr, final ResultCallback callback, Object tag) {
            postAsynWithMediaType(url, bodyStr, MediaType.parse("text/plain;charset=utf-8"), callback, tag);
        }

        //直接将bodyBytes写入请求体
        public void postAsyn(String url, byte[] bodyBytes, final ResultCallback callback) {
            postAsyn(url, bodyBytes, callback, null);
        }

        public void postAsyn(String url, byte[] bodyBytes, final ResultCallback callback, Object tag) {
            postAsynWithMediaType(url, bodyBytes, MediaType.parse("application/octet-stream;charset=utf-8"), callback, tag);
        }

        //直接将bodyFile写入请求体
        public void postAsyn(String url, File bodyFile, final ResultCallback callback) {
            postAsyn(url, bodyFile, callback, null);
        }

        public void postAsyn(String url, File bodyFile, final ResultCallback callback, Object tag) {
            postAsynWithMediaType(url, bodyFile, MediaType.parse("application/octet-stream;charset=utf-8"), callback, tag);
        }

        //直接将bodyStr写入请求体
        public void postAsynWithMediaType(String url, String bodyStr, MediaType type, final ResultCallback callback, Object tag) {
            RequestBody body = RequestBody.create(type, bodyStr);
            Request request = buildPostRequest(url, body, tag);
            deliveryResult(callback, request);
        }

        //直接将bodyBytes写入请求体
        public void postAsynWithMediaType(String url, byte[] bodyBytes, MediaType type, final ResultCallback callback, Object tag) {
            RequestBody body = RequestBody.create(type, bodyBytes);
            Request request = buildPostRequest(url, body, tag);
            deliveryResult(callback, request);
        }

        //直接将bodyFile写入请求体
        public void postAsynWithMediaType(String url, File bodyFile, MediaType type, final ResultCallback callback, Object tag) {
            RequestBody body = RequestBody.create(type, bodyFile);
            Request request = buildPostRequest(url, body, tag);
            deliveryResult(callback, request);
        }

        //post构造Request的方法
        private Request buildPostRequest(String url, RequestBody body, Object tag) {
            Request.Builder builder = new Request.Builder().
                    addHeader("Content-Type", "application/json")
                    .url(url).post(body);
            if (tag != null) {
                builder.tag(tag);
            }
            Request request = builder.build();
            return request;
        }

    }

    //GetDelegate
    public class GetDelegate {

        private Request buildGetRequest(String url, Object tag) {
            Request.Builder builder = new Request.Builder().url(url);
            if (tag != null) {
                builder.tag(tag);
            }
            builder.addHeader("Content-Type", "application/json;charset=utf-8");
            builder.addHeader("Authorization", SpUtil.getToken());
            return builder.build();
        }

        //通用方法
        public Response get(Request request) throws IOException {
            Call call = mOkHttpClient.newCall(request);
            Response excute = call.execute();
            return excute;
        }

        //同步的get请求
        public Response get(String url) throws IOException {
            return get(url, null);
        }

        public Response get(String url, Object tag) throws IOException {
            final Request request = buildGetRequest(url, tag);
            return get(request);
        }

        public String getAsString(String url) throws IOException {
            return getAsString(url, null);
        }

        public String getAsString(String url, Object tag) throws IOException {
            Response excute = get(url, tag);
            return excute.body().string();
        }

        //通用方法
        public void getAsyn(Request request, ResultCallback callback) {
            deliveryResult(callback, request);
        }


        //异步的get请求
        public void getAsyn(String url, final ResultCallback callback) {
            getAsyn(url, callback, null);
        }

        public void getAsyn(String url, final ResultCallback callback, Object tag) {
            final Request request = buildGetRequest(url, tag);
            getAsyn(request, callback);
        }
    }


    //DownloadDelegate
    //下载相关的模块
    public class DownloadDelegate {


        //异步下载文件
        public void downloadAsyn(final String url, final String destFileDir, final ResultCallback callback, Object tag) {
            if (url == null || TextUtils.isEmpty(url)) {
                return;
            }
            final Request request = new Request.Builder().url(url).tag(tag).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailedStringCallback(500, AppConstants.HTTP_DOWNLOAD_ERROR, AppConstants.HTTP_DOWNLOAD_ERROR, callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        File file = new File(destFileDir);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
                        if (file.exists() && file.length() == huc.getContentLength()) {
                            sendSuccessResultCallback(file.getAbsolutePath(), callback);
                            huc.disconnect();
                            return;
                        }
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        fos.flush();
                        //如果下载文件成功，第一个参数为文件的绝对路径
                        sendSuccessResultCallback(file.getAbsolutePath(), callback);
                    } catch (Exception e) {
//                        sendFailedStringCallback(response.request(), e, callback);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {

                        }
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {

                        }
                    }
                }
            });
        }


        //异步下载文件
        public void downloadAsynWithProgress(final String url, final File file, final ProgressCallback callback, Object tag) {
            if (url == null || TextUtils.isEmpty(url)) {
                return;
            }
            Request request = null;
            try {
                request = new Request.Builder().url(url).tag(tag).build();
            } catch (final Exception e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onDownloadError(e.toString());
                        }
                    }
                });
            }
            if (request == null) {
                return;
            }

            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onDownloadError(e.toString());
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    FileOutputStream fos = null;
                    try {
                        HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
                        long contentLength = huc.getContentLength();
                        long progress = 0;
                        float percent = 0;
                        int len = 0;
                        if (file.exists() && file.length() == contentLength) {
                            if (callback != null)
                                mDelivery.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onDownloadFinish(file.getAbsolutePath());
                                    }
                                });
                            huc.disconnect();
                            return;
                        }
                        is = response.body().byteStream();
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            progress += len;
                            percent = (float) progress / contentLength;
                            if (callback != null) {
                                final float finalPercent = percent;
                                final long finalProgress = progress;
                                mDelivery.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onProgressChanged(finalPercent, finalProgress);
                                    }
                                });
                            }

                        }
                        fos.flush();
                        //如果下载文件成功，第一个参数为文件的绝对路径
                        if (callback != null)
                            mDelivery.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onDownloadFinish(file.getAbsolutePath());
                                }
                            });
                    } catch (Exception e) {
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onDownloadError("errorException");
                                }
                            }
                        });
//                        sendFailedStringCallback(response.request(), e, callback);
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {

                        }
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {

                        }
                    }
                }
            });
        }


        public abstract class DownLoadImageCallBack {
            abstract void getBitMap(Bitmap bitmap);
        }

        public void downloadAsyn(final String url, final String destFileDir, final ResultCallback callback) {
            downloadAsyn(url, destFileDir, callback, null);
        }
    }

    //Https相关模块
    public class HttpsDelegate {
        public void setCertificates(InputStream... certificates) {
            setCertificates(certificates, null, null);
        }

        public TrustManager[] prepareTrustManager(InputStream... certificates) {
            if (certificates == null || certificates.length <= 0) {
                return null;
            }

            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null);
                int index = 0;
                for (InputStream certificate : certificates) {
                    String certificateAlias = Integer.toString(index++);
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                    try {
                        if (certificate != null) {
                            certificate.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                TrustManagerFactory trustManagerFactory = null;
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                return trustManagers;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
            try {
                if (bksFile == null || password == null) {
                    return null;
                }
                KeyStore clientKeyStore = KeyStore.getInstance("BKS");
                clientKeyStore.load(bksFile, password.toCharArray());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(clientKeyStore, password.toCharArray());
                return keyManagerFactory.getKeyManagers();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
            try {
                TrustManager[] trustManagers = prepareTrustManager(certificates);
                KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagers, new TrustManager[]{new MyTrustManager(chooseTrustManager(trustManagers))}, new SecureRandom());
//                mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }

        private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
            for (TrustManager trustManager : trustManagers) {
                if (trustManager instanceof X509TrustManager) {
                    return (X509TrustManager) trustManager;
                }
            }
            return null;
        }

        public class MyTrustManager implements X509TrustManager {
            private X509TrustManager defaultTrustManager;
            private X509TrustManager localTrustManager;

            public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
                TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                var4.init((KeyStore) null);
                defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
                this.localTrustManager = localTrustManager;
            }


            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    defaultTrustManager.checkServerTrusted(chain, authType);
                } catch (CertificateException e) {
                    localTrustManager.checkServerTrusted(chain, authType);
                }
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }

    }

    public static abstract class ProgressCallback {

        protected abstract void onProgressChanged(float percent, long length);

        public abstract void onDownloadFinish(String fileAbsolutePath);

        public abstract void onDownloadError(String error);
    }
}
