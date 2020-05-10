package com.luck.cloud.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by liuyin on 2019/2/20 15:26
 * 负责做定时器任务
 */
public class TimerTaskUtil {
    private volatile static TimerTaskUtil viewModel;
    private Subscription mSubscription;

    /**
     * 以单例模式获取实例
     *
     * @return 实例
     */
    public static TimerTaskUtil getInstance() {
        if (viewModel == null) {
            synchronized (TimerTaskUtil.class) {
                if (viewModel == null) {
                    viewModel = new TimerTaskUtil();
                }
            }
        }
        return viewModel;
    }

    private TimerTaskUtil() {
    }

    /**
     * 开始倒计时任务
     * @param time 倒计时时间
     * @param listener 回调接口
     */
    public void startCountDown(final int time, final TimerTaskListener listener) {
        //在发送数据的时候设置为60秒不能点击,操作UI主要在UI线程
        mSubscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(time + 1)//设置总共发送的次数
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return time - aLong;
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        listener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        listener.onNext(aLong);
                    }
                });

    }

    public interface TimerTaskListener {
        void onError(Throwable e);

        void onCompleted();

        void onNext(Long aLong);


    }

}
