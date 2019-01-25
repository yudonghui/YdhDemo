package com.railway.ydhdemo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.railway.ydhdemo.base.BaseActivity;
import com.railway.ydhdemo.callback.YuDialogInterface;
import com.railway.ydhdemo.common.Constant;
import com.railway.ydhdemo.component.ActivityAppComponent;
import com.railway.ydhdemo.dialog.YuDialog;
import com.railway.ydhdemo.ui.bean.UploadBean;
import com.railway.ydhdemo.ui.bean.User;
import com.railway.ydhdemo.ui.presenter.MainPresenter;
import com.railway.ydhdemo.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity<MainPresenter> {


    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.download)
    TextView mUpload;
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyaWQiOiIxMDAwNzk2IiwicGhvbmUiOiIxNzYyMTIxMTc5OSIsImV4cCI6MTU0ODgxNjUxMH0.ZF-JIzw2N_Lm-1tQSwKidCY7M1MVc30-9wmKdern9Z4";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void inject(ActivityAppComponent activityComponent) {
        activityComponent.inject(this);
    }

    /**
     * POST 请求
     */
    @Override
    public void initData() {
        //获取线上版本的信息
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "android");
        mPresenter.getVersionInfo(params);
     /*   HomeFragment homeFragment = new HomeFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl, homeFragment).show(homeFragment);
        fragmentTransaction.commit();*/
    }

    /**
     * GET请求
     */
    @OnClick(R.id.textView)
    public void onViewClicked(View view) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("sort", "0");
        params.put("type", "glean");
        params.put("limit", "5");
        mPresenter.get(params);
    }

    /**
     * 上传文件
     */
    @OnClick(R.id.download)
    public void onUpload(View view) {
        Map<String, RequestBody> params = new HashMap<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/0001_1_1_0_00_11.png";
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), file);

        RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), token);

        params.put("token", requestBody1);
        params.put("image", requestBody);
        mPresenter.upload(params);
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
            boolean b = getPackageManager().canRequestPackageInstalls();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1500) {// 如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                AppManager.getAppManager().AppExit(this, false);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_6666) {//是否允许安装未知来源apk，返回。
            checkIsAndroid();
        } else if (requestCode == Constant.REQUEST_6667) {//自己去设置里面开启权限后 返回
            checkSDPermission();
        }
    }
}
