package com.railway.ydhdemo.callback;


import okhttp3.ResponseBody;

public interface ResponseCallBack {
    void callBack(ResponseBody body);
    void complete();
}
