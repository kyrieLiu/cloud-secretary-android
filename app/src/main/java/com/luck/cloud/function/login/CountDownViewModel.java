package com.luck.cloud.function.login;

import android.content.Context;
import android.widget.TextView;


import com.luck.cloud.base.BaseBean;
import com.luck.cloud.config.URLConstant;
import com.luck.cloud.network.OKHttpManager;
import com.luck.cloud.utils.ToastUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by liuyin on 2018/7/18
 * 处理定时器业务
 */
public class CountDownViewModel {
    private volatile static CountDownViewModel viewModel;
    private Subscription mSubscription;

    /**
     * 以单例模式获取实例
     *
     * @return 实例
     */
    public static CountDownViewModel getInstance() {
        if (viewModel == null) {
            synchronized (CountDownViewModel.class) {
                if (viewModel == null) {
                    viewModel = new CountDownViewModel();
                }
            }
        }
        return viewModel;
    }

    private CountDownViewModel() {
    }

    ;


    /**
     * 倒计时等待短信
     *
     * @param context
     * @param textView
     * @param phone       手机号
     */
    public void waitSMS(Context context, final TextView textView, String phone) {
        HashMap hashMap = new HashMap();
        hashMap.put("phone", phone);
        Utils.getUtils().showProgressDialog(context);
        OKHttpManager.postJsonNoToken(URLConstant.SEND_MESSAGE_CODE, hashMap, new OKHttpManager.ResultCallback<BaseBean>() {

            @Override
            public void onError(int code, String result, String message) {
                Utils.getUtils().dismissDialog();
                ToastUtil.toastShortCenter(message);
            }

            @Override
            public void onResponse(BaseBean response) {
                Utils.getUtils().dismissDialog();
                if (response.getCode() == "SUCCESS") {
                    ToastUtil.toastShortCenter("验证码已发送,请注意查收");
                    countDownStart(textView);
                } else {
                    ToastUtil.toastShortCenter(response.getMessage());
                }

            }
        }, this);
    }

    private void countDownStart(final TextView textView) {
        //在发送数据的时候设置为60秒不能点击,操作UI主要在UI线程
        mSubscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(120 + 1)//设置总共发送的次数
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return 120 - aLong;
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //在发送数据的时候设置为不能点击
                        textView.setEnabled(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        textView.setEnabled(true);
                        textView.setText("重新获取");
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setEnabled(true);
                        textView.setText("重新获取");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        textView.setText(aLong + "s");
                    }
                });

    }

    /**
     * 取消订阅
     */
    public void cancel() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


}
