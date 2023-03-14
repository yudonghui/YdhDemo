package com.railway.ydhdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.railway.ydhdemo.App;


public class SpHelper {
    public static String FAIL = "fail_way";
    public static String USER_INFO = "userinfo";
    public static String TOKEN = "token";
    public static String ISLOGIN = "isLogin";
    private SharedPreferences sp;

    public SpHelper() {
        sp = App.getContext().getSharedPreferences(FAIL, Context.MODE_PRIVATE);
    }

    public boolean setStorageStr(String key, String value) {//同步存储数据，直接存到硬件磁盘，返回是否成功
        boolean commit = sp.edit().putString(key, value).commit();
        return commit;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void setStorageStrSync(String key, String value) {//先缓存，然后存到硬件磁盘
        sp.edit().putString(key, value).apply();
    }

    public boolean setStorageBoolean(String key, boolean value) {//同步存储数据，直接存到硬件磁盘，返回是否成功
        boolean commit = sp.edit().putBoolean(key, value).commit();
        return commit;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void setStorageBooleanSync(String key, boolean value) {//先缓存，然后存到硬件磁盘
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean setStorageInt(String key, int value) {//同步存储数据，直接存到硬件磁盘，返回是否成功
        boolean commit = sp.edit().putInt(key, value).commit();
        return commit;
    }

    public void setStorageIntSync(String key, int value) {//先缓存，然后存到硬件磁盘
        sp.edit().putInt(key, value).apply();
    }

    public String getStorageStr(String key) {
        return sp.getString(key, "");
    }

    public boolean getStorageBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public int getStorageInt(String key) {
        return sp.getInt(key, 0);
    }
}
