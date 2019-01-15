package com.railway.ydhdemo.inject.service;


import com.railway.ydhdemo.ui.bean.User;
import com.railway.ydhdemo.ui.bean.UserInfo;
import com.railway.ydhdemo.ui.bean.VersionInfo;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    @GET("v1/cms/getlist")
    Flowable<User> getUser(@QueryMap Map<String, String> params);

    @GET("v1/user/getInfo")
    Flowable<UserInfo> getUserInfo(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("appversion/getVersion")
    Flowable<VersionInfo> getVersionInfo(@FieldMap Map<String, String> params);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);

    @Multipart
    @POST("upload")
    Observable<ResponseBody> upImg(@PartMap Map<String, RequestBody> params);

}
