package com.railway.ydhdemo.ui.presenter;

import android.util.Log;
import android.widget.Toast;

import com.railway.ydhdemo.MainActivity;
import com.railway.ydhdemo.base.BasePresenter;
import com.railway.ydhdemo.common.CommonSubscriber;
import com.railway.ydhdemo.callback.ResponseCallBack;
import com.railway.ydhdemo.ui.bean.User;
import com.railway.ydhdemo.ui.bean.UserInfo;
import com.railway.ydhdemo.ui.bean.VersionInfo;
import com.railway.ydhdemo.ui.model.MainModel;
import com.railway.ydhdemo.utils.ApiException;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainPresenter extends BasePresenter<MainActivity, MainModel> {
    @Inject
    public MainPresenter() {
        Log.e("创建了: ", "MainPresenter");
    }

    public void get(HashMap<String, String> params) {
        mView.showLoadingDialog();
        mModel.get(params, new CommonSubscriber<User>() {
            @Override
            public void getData(User user) {
                mView.cancelLoadingDialog();
                mView.getSuccess(user);
            }

            @Override
            public void error(ApiException e) {
                Toast.makeText(mView, e.getMsg(), Toast.LENGTH_SHORT).show();
                mView.cancelLoadingDialog();
            }
        });
    }

    public void getVersionInfo(HashMap<String, String> params) {
        mView.showLoadingDialog();
        mModel.getVersionInfo(params, new CommonSubscriber<VersionInfo>() {
            @Override
            public void getData(VersionInfo versionInfo) {
                mView.cancelLoadingDialog();
                mView.setVersionInfo(versionInfo);
            }

            @Override
            public void error(ApiException e) {
                Toast.makeText(mView, e.getMsg(), Toast.LENGTH_SHORT).show();
                mView.cancelLoadingDialog();
            }
        });
    }

    public void getUserInfo(HashMap<String, String> params) {
        mView.showLoadingDialog();
        mModel.getUserInfo(params, new CommonSubscriber<UserInfo>() {
            @Override
            public void getData(UserInfo userInfo) {
                mView.cancelLoadingDialog();
                Log.e("结果", userInfo.toString());
                // mView.setVersionInfo(versionInfo);
            }

            @Override
            public void error(ApiException e) {
                Toast.makeText(mView, e.getMsg(), Toast.LENGTH_SHORT).show();
                mView.cancelLoadingDialog();
            }
        });
    }

    public void upload(Map<String, RequestBody> params){
        mView.showLoadingDialog();
        mModel.upload(params, new ResponseCallBack() {
            @Override
            public void callBack(ResponseBody body) {
                mView.cancelLoadingDialog();
                mView.setUpload(body);
            }

            @Override
            public void complete() {
                mView.cancelLoadingDialog();
            }
        });
    }

    public void download(String download_url) {
        mModel.download(download_url, new ResponseCallBack() {
            @Override
            public void callBack(ResponseBody body) {
                mView.setUpdateApk(body);
            }

            @Override
            public void complete() {

            }
        });
    }
}
