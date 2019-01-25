package com.railway.ydhdemo.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.railway.ydhdemo.R;
import com.railway.ydhdemo.base.BaseFragment;
import com.railway.ydhdemo.callback.YuDialogInterface;
import com.railway.ydhdemo.common.Constant;
import com.railway.ydhdemo.component.FragmentAppComponent;
import com.railway.ydhdemo.dialog.YuDialog;
import com.railway.ydhdemo.ui.bean.UploadBean;
import com.railway.ydhdemo.ui.bean.User;
import com.railway.ydhdemo.ui.presenter.HomePresenter;
import com.railway.ydhdemo.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;

public class HomeFragment extends BaseFragment<HomePresenter> {
    @Override
    protected void inject(FragmentAppComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        //获取线上版本的信息
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "android");
        mPresenter.getVersionInfo(params);
    }


    public void getSuccess(User user) {
        if (user != null)
            Log.e("结果", user.toString());
    }

    public void checkSDPermission() {
        requestPermission(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    public void permissonExcute() {
        checkIsAndroid();
    }

    private void checkIsAndroid() {
        // updateApk();
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = mActivity.getPackageManager().canRequestPackageInstalls();
            if (b) {
                updateApk();
            } else {
                dialog();
            }
        } else {
            updateApk();
        }
    }

    private void dialog() {
        YuDialog.Builder builder = new YuDialog.Builder();
        builder.title(getResources().getString(R.string.hint_title))
                .message(getResources().getString(R.string.hint_message))
                .confirm(getResources().getString(R.string.setting), new YuDialogInterface() {
                    @Override
                    public void callBack(View view) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mContext.getPackageName()));
                        startActivityForResult(intent, Constant.REQUEST_6666);
                    }
                })
                .cancel(getResources().getString(R.string.cancel), new YuDialogInterface() {
                    @Override
                    public void callBack(View view) {
                        showToast("取消了");
                    }
                })
                .build(mContext);
    }

    public void setUpload(ResponseBody body) {
        try {
            String string = body.string();
            Gson gson = new Gson();
            UploadBean uploadBean = gson.fromJson(string, UploadBean.class);
            LogUtils.e(uploadBean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //下载文件
    private void updateApk() {
        mPresenter.download();
    }

    private long firstTime;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constant.REQUEST_6666) {//是否允许安装未知来源apk，返回。
            checkIsAndroid();
        }
    }
}
