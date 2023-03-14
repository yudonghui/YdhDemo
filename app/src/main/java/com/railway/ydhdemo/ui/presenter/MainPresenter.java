package com.railway.ydhdemo.ui.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.railway.ydhdemo.MainActivity;
import com.railway.ydhdemo.base.BasePresenter;
import com.railway.ydhdemo.callback.ResponseCallBack;
import com.railway.ydhdemo.callback.YuDialogInterface;
import com.railway.ydhdemo.common.CommonSubscriber;
import com.railway.ydhdemo.dialog.YuDialog;
import com.railway.ydhdemo.ui.bean.User;
import com.railway.ydhdemo.ui.bean.UserInfo;
import com.railway.ydhdemo.ui.bean.VersionInfo;
import com.railway.ydhdemo.ui.model.MainModel;
import com.railway.ydhdemo.utils.ApiException;
import com.railway.ydhdemo.utils.AppInfo;
import com.railway.ydhdemo.utils.FileUtils;
import com.railway.ydhdemo.utils.LogUtils;
import com.railway.ydhdemo.utils.StringUtil;

import java.io.File;
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

    private VersionInfo.DataBean data;

    public void getVersionInfo(HashMap<String, String> params) {
        mView.showLoadingDialog();
        mModel.getVersionInfo(params, new CommonSubscriber<VersionInfo>() {
            @Override
            public void getData(VersionInfo versionInfo) {
                mView.cancelLoadingDialog();
                if (versionInfo == null) return;
                int currentCode = AppInfo.getAppVersionCode();
                data = versionInfo.getData();
                String status = data.getStatus();
                String netCode = data.getBuild();
                String version = data.getVersion();
                String remark = data.getRemark();
                final String apkUrl = data.getDownload_url();
                if (currentCode < Integer.parseInt(netCode)) {
                    //强制更新
                    //如果版本本地的版本号和发布的版本号不同，那么就提示是否更新
                    new YuDialog.Builder().title("发现新版本 " + version)
                            .message("【更新说明】\n" + remark)
                            .confirm("立即更新", new YuDialogInterface() {
                                @Override
                                public void callBack(View view) {
                                    mView.checkSDPermission();
                                }
                            })
                            .isCancel(!"1".equals(status))//弹出框是否可以取消
                            .cancel("1".equals(status) ? "" : "以后再说")
                            .build(mView);
                }


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

    public void upload(Map<String, RequestBody> params) {
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

    public void download() {
        if (data == null || StringUtil.isNullOrEmpty(data.getDownload_url())) {
            return;
        }
        mModel.download(data.getDownload_url(), new ResponseCallBack() {
            @Override
            public void callBack(final ResponseBody body) {
                final File file = FileUtils.createFile();
                final ProgressDialog progressDialog = new ProgressDialog(mView);
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
                                mView.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // LogUtils.e("current: " + current + " total：" + total);
                                        int cur = (int) (current * 100 / total);
                                        progressDialog.setProgress(cur);
                                        if (cur == 100) {
                                            Toast.makeText(mView, "下载成功", Toast.LENGTH_SHORT).show();
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
                                                LogUtils.e(mView.getPackageName());
                                                mUri = FileProvider.getUriForFile(mView, mView.getPackageName() + ".FileProvider", file);
                                            } else {
                                                mUri = Uri.fromFile(file);
                                            }
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            //执行的数据类型
                                            intent.setDataAndType(mUri, "application/vnd.android.package-archive");
                                            mView.startActivity(intent);
                                            System.exit(0);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }.start();


            }

            @Override
            public void complete() {

            }
        });
    }
}
