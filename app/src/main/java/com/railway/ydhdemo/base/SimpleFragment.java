package com.railway.ydhdemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.railway.ydhdemo.dialog.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class SimpleFragment extends RxFragment {
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private LoadingDialog mLoadingDialog;
    private Unbinder mUnbinder;
    protected boolean isBinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mView);
        isBinder = true;
        init();
    }

    public void showLoadingDialog() {
        if (mLoadingDialog == null)
            mLoadingDialog = new LoadingDialog.Builder().createLoadingDialog(getActivity()).build();
        mLoadingDialog.showDialog();
    }

    public void cancelLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.cancelDialog();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        isBinder = false;
        cancelLoadingDialog();
        super.onDestroyView();
    }

    protected abstract int getLayoutId();

    protected void init() {
    }

    ;

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面 带返回
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面 带返回
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
