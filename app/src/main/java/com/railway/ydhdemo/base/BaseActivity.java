package com.railway.ydhdemo.base;

import android.widget.Toast;

import com.railway.ydhdemo.App;
import com.railway.ydhdemo.component.ActivityAppComponent;
import com.railway.ydhdemo.component.DaggerActivityAppComponent;
import com.railway.ydhdemo.inject.module.ActivityModule;
import com.railway.ydhdemo.inject.module.ApiModule;

import javax.inject.Inject;


public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    protected void init() {
        super.init();
        ActivityAppComponent activityAppComponent = DaggerActivityAppComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .apiModule(new ApiModule(App.getContext()))
                .build();
        inject(activityAppComponent);
        if (mPresenter != null)
            mPresenter.attachView(this);
        initData();
    }

    public void initData() {
    }

    /**
     * 注入
     */
    public abstract void inject(ActivityAppComponent activityComponent);

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
