package com.android.baseproject.core.http.interceptor;

import android.util.Log;

import com.android.baseproject.core.DataManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AppendUrlIntercepter implements Interceptor {
    private String baseUrl;
    public AppendUrlIntercepter(String baseUrl){
        this.baseUrl=baseUrl;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = DataManager.getInstance().getToken();
        Request oldRequest = chain.request();
        HttpUrl.Builder builder = oldRequest
                .url()
                .newBuilder();
        builder.addQueryParameter("token",token);
        Log.e("AppendUrlIntercepter","builder.build()===" + builder.build());
        Request newRequest = oldRequest
                .newBuilder()
                //.addHeader("Authorization",token)
                .url(builder.build())
                .build();
        return chain.proceed(newRequest);
    }
}