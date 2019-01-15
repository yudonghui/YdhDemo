package com.railway.ydhdemo.base;

import javax.inject.Inject;

public class BasePresenter<T extends BaseView, V extends BaseModel> {
    @Inject
    protected V mModel;

    protected T mView;

    public BasePresenter() {
    }

    public void attachView(T view) {
        this.mView = view;
    }

    public void detachView() {

    }
}
