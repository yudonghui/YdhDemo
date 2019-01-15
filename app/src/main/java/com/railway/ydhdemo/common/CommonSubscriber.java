package com.railway.ydhdemo.common;


import com.railway.ydhdemo.utils.ApiException;
import com.railway.ydhdemo.utils.ExceptionEngine;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class CommonSubscriber<T extends HttpResponse> extends ResourceSubscriber<T> {
    @Override
    public void onNext(T t) {
        getData(t);
    }

    @Override
    public void onError(Throwable e) {
        ApiException apiException = ExceptionEngine.handleException(e);
        error(apiException);
    }

    @Override
    public void onComplete() {

    }

    public abstract void getData(T t);

    public abstract void error(ApiException e);
}
