package com.railway.ydhdemo.common;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class HttpRxObservable {
    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider自动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity.../RxFragment...
     *
     * @author ZhongDaFeng
     */
    public static Flowable getObservable(Flowable<? extends HttpResponse> apiObservable, LifecycleProvider lifecycle) {
        //showLog(request);
        Flowable observable;
        //随生命周期自动管理.eg:onCreate(start)->onStop(end)
        observable = apiObservable
                .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
    public static Flowable getObservableResponse(Flowable<? extends ResponseBody> apiObservable, LifecycleProvider lifecycle) {
        //showLog(request);
        Flowable observable;
        //随生命周期自动管理.eg:onCreate(start)->onStop(end)
        observable = apiObservable
                .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

}
