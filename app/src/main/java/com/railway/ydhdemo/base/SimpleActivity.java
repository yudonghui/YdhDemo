package com.railway.ydhdemo.base;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.railway.ydhdemo.AppManager;
import com.railway.ydhdemo.broadcast.NetBroadcastReceiver;
import com.railway.ydhdemo.dialog.LoadingDialog;
import com.railway.ydhdemo.utils.NetWorkUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class SimpleActivity extends RxAppCompatActivity implements NetBroadcastReceiver.NetWorkCallBack {
    protected Activity mContext;
    private Unbinder mUnbinder;
    protected LoadingDialog mLoadingDialog;
    private NetBroadcastReceiver mNetBroadcastReceiver;
    /**
     * 网络类型
     */
    private int netMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("创建了","onCreate");
        AppManager.getAppManager().addActivity(this);//添加堆栈
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        init();
    }

    protected void init() {
        setStatusBar();
        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetBroadcastReceiver = new NetBroadcastReceiver(this);
        registerReceiver(mNetBroadcastReceiver, intentFilter);

    }

    protected abstract int getLayoutId();


    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        } else {
        }
    }

    public void showLoadingDialog() {
        if (mLoadingDialog == null)
            mLoadingDialog = new LoadingDialog.Builder().createLoadingDialog(this).build();
        mLoadingDialog.showDialog();
    }

    public void cancelLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.cancelDialog();
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 带返回通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 带返回含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mNetBroadcastReceiver);
        mUnbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
        cancelLoadingDialog();
        super.onDestroy();
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetWorkUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }
}
