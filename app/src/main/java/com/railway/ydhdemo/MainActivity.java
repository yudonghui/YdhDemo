package com.railway.ydhdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.railway.ydhdemo.base.BaseActivity;
import com.railway.ydhdemo.callback.DialogHintInterface;
import com.railway.ydhdemo.component.ActivityAppComponent;
import com.railway.ydhdemo.dialog.HintDialog;
import com.railway.ydhdemo.ui.bean.UploadBean;
import com.railway.ydhdemo.ui.bean.User;
import com.railway.ydhdemo.ui.bean.VersionInfo;
import com.railway.ydhdemo.ui.presenter.MainPresenter;
import com.railway.ydhdemo.utils.AppInfo;
import com.railway.ydhdemo.utils.FileUtils;
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

    public void setVersionInfo(VersionInfo versionInfo) {

        if (versionInfo == null) return;
        int currentCode = AppInfo.getAppVersionCode();
        VersionInfo.DataBean data = versionInfo.getData();
        String status = data.getStatus();
        String netCode = data.getBuild();
        String version = data.getVersion();
        String remark = data.getRemark();
        final String apkUrl = data.getDownload_url();
        if (currentCode < Integer.parseInt(netCode)) {
            //强制更新
            //如果版本本地的版本号和发布的版本号不同，那么就提示是否更新
            HintDialog mHintDialog = new HintDialog(mContext);
            mHintDialog.setTitleVisiable(true);
            mHintDialog.setTitle("发现新版本 " + version);
            //verName,updateInfo
            mHintDialog.setVersionMessage("【更新说明】\n" + remark);
            mHintDialog.setMessage("发现新版本 " + version + "\n\n" + "【更新说明】\n" + remark);
            //HintDialogUtils.setTvCancel("以后再说");
            mHintDialog.setConfirm("立即更新", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    checkSDPermission(apkUrl);
                }
            });
            if ("1".equals(status)) {
                mHintDialog.setCancelable(false);
                mHintDialog.setVisibilityCancel();
            } else {
                mHintDialog.setTvCancel("以后再说");
            }
        }
    }

    private void checkSDPermission(String apkUrl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 11111);
            } else {
                updateApk(apkUrl);
            }
        } else {
            updateApk(apkUrl);
        }
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

    public void setUpdateApk(final ResponseBody body) {
        final File file = FileUtils.createFile();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("努力下载中......");
        progressDialog.show();
        progressDialog.setMax(100);
        new Thread() {
            @Override
            public void run() {
                super.run();
                FileUtils.writeFile2Disk(body, file, new FileUtils.FileLoadInterface() {
                    @Override
                    public void onLoading(final long current, final long total) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // LogUtils.e("current: " + current + " total：" + total);
                                int cur = (int) (current * 100 / total);
                                progressDialog.setProgress(cur);
                                if (cur == 100) {
                                    Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    //安装apk
                                    Intent intent = new Intent();
                                    //执行动作
                                    intent.setAction(Intent.ACTION_VIEW);
                                    Uri mUri;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        //通过FileProvider创建一个content类型的Uri
                                        mUri = FileProvider.getUriForFile(mContext, "com.ruihuo.ixungen.FileProvider", file);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                                    } else {
                                        mUri = Uri.fromFile(file);
                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                                    }
                                    //执行的数据类型
                                    intent.setDataAndType(mUri, "application/vnd.android.package-archive");
                                    mContext.startActivity(intent);
                                    System.exit(0);
                                }
                            }
                        });
                    }
                });
            }
        }.start();

    }

    //下载文件
    private void updateApk(String download_url) {
        mPresenter.download(download_url);
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

}
