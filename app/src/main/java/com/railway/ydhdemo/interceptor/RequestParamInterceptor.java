package com.railway.ydhdemo.interceptor;

import android.util.Log;

import com.railway.ydhdemo.common.HttpMd5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 插值器 对于请求的参数进行进一步定制
 */
public class RequestParamInterceptor implements Interceptor {
    private static final String TAG = "RequestParamInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = original.newBuilder();
        //请求体定制：统一添加参数
        if (original.body() instanceof FormBody) {
            FormBody oidFormBody = (FormBody) original.body();
            JSONObject json = new JSONObject();
            JSONObject data = new JSONObject();
            TreeMap<String, Object> map = new TreeMap<>();
            //把之前的参数添加进去
            try {
                for (int i = 0; i < oidFormBody.size(); i++) {
                    data.put(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                    map.put(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                }
                json.put("sign", HttpMd5.buildSign(map));
                json.put("data", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType parse = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(parse, json.toString());
            Log.e("请求参数", json.toString());

            requestBuilder.method(original.method(), requestBody);
        } else if (original.body() instanceof MultipartBody) {

        } else if (original.body() != null && original.body().contentType() != null &&
                MediaType.parse("application/json").toString().equals(original.body().contentType().toString())) {

        } else if ("GET".equals(original.method())) {
        } else {
            FormBody.Builder newFormBody = new FormBody.Builder();
            requestBuilder.method(original.method(), newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

