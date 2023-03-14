package com.railway.ydhdemo.base;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.railway.ydhdemo.App;
import com.railway.ydhdemo.callback.PermissionListener;
import com.railway.ydhdemo.component.DaggerFragmentAppComponent;
import com.railway.ydhdemo.component.FragmentAppComponent;
import com.railway.ydhdemo.inject.module.AppModule;
import com.railway.ydhdemo.inject.module.FragmentModule;
import com.railway.ydhdemo.permission.PermissionSetting;
import com.railway.ydhdemo.permission.PermissionUtils;
import com.railway.ydhdemo.permission.YuAlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by codeest on 2016/8/2.
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

    @Inject
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FragmentAppComponent fragmentAppComponent = DaggerFragmentAppComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appModule(new AppModule(App.getContext()))
                .build();
        inject(fragmentAppComponent);
        if (mPresenter != null)
            mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected abstract void inject(FragmentAppComponent fragmentComponent);

    //申请权限之后需要执行的方法。需要的时候再重写
    public void permissonExcute() {
    }

    private PermissionListener mlistener;
    /**
     * 权限申请
     *
     * @param permissions 待申请的权限集合
     * @param listener    申请结果监听事件
     */
    private   void requestRunTimePermission(String[] permissions, PermissionListener listener) {
        mlistener = listener;

        //用于存放为授权的权限
        List<String> permissionList = new ArrayList<>();
        //遍历传递过来的权限集合
        for (String permission : permissions) {
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                //未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        //判断集合
        if (!permissionList.isEmpty()) {  //如果集合不为空，则需要去授权
            requestPermissions(permissionList.toArray(new String[permissionList.size()]), 1);
        } else {  //为空，则已经全部授权
            listener.onGranted();
        }
    }

    /**
     * 权限申请结果
     *
     * @param requestCode  请求码
     * @param permissions  所有的权限集合
     * @param grantResults 授权结果集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //被用户拒绝的权限集合
                    List<String> deniedPermissions = new ArrayList<>();
                    //用户通过的权限集合
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        //获取授权结果，这是一个int类型的值
                        int grantResult = grantResults[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) { //用户拒绝授权的权限
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        } else {  //用户同意的权限
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {  //全部申请成功
                        mlistener.onGranted();
                    } else {  //部分权限申请成功
                        //回调授权成功的接口
                        mlistener.onDenied(deniedPermissions);
                        //回调授权失败的接口
                        mlistener.onGranted(grantedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void requestPermission(final String[] persssions) {
        final PermissionSetting ps = new PermissionSetting(mActivity);
        requestRunTimePermission(persssions
                , new PermissionListener() {
                    @Override
                    public void onGranted() {//全部申请成功的回调
                        permissonExcute();
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) {//部分成功权限

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {//部分未成功权限
                        String message="我们需要以下权限，请在设置中为我们开启："+ "\n"+ PermissionUtils.getName(Arrays.asList(persssions));
                        YuAlertDialog.newBuilder(mContext)
                                .setCancelable(false)
                                .setTitle("提示")
                                .setMessage(message)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ps.execute();
                                    }
                                })
                                .setNegativeButton("不", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ps.cancel();
                                    }
                                })
                                .show();
                    }
                });
    }


}