package com.railway.ydhdemo.ui.model;

import com.railway.ydhdemo.base.BaseModel;
import com.railway.ydhdemo.callback.ResponseCallBack;
import com.railway.ydhdemo.common.CommonSubscriber;
import com.railway.ydhdemo.common.HttpRxObservable;
import com.railway.ydhdemo.inject.module.BaseModule;
import com.railway.ydhdemo.inject.service.ApiService;
import com.railway.ydhdemo.ui.bean.VersionInfo;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class HomeModel extends BaseModel {
    private final RxFragment mRxFragment;
    @Inject
    ApiService mApiService;
    @Inject
    public HomeModel(RxFragment fragment) {
        mRxFragment = fragment;
    }

    public void getVersionInfo(HashMap<String, String> params, CommonSubscriber<VersionInfo> subscriber) {
        Flowable<VersionInfo> userFlowable = mApiService.getVersionInfo(params);
        //被观察者
        Flowable observable = HttpRxObservable.getObservable(userFlowable, mRxFragment);
        observable.subscribe(subscriber);
    }
    public void download(String url, final ResponseCallBack callBack) {
        BaseModule baseModule = new BaseModule(mRxFragment.getContext());
        ApiService apiService = baseModule.mRetrofitDownload.create(ApiService.class);
        Observable<ResponseBody> download = apiService.download(url);
        download.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        callBack.callBack(responseBody);
                    }
                });
    }
}
