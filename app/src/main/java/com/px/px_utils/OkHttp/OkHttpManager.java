package com.px.px_utils.OkHttp;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/26.
 */
public class OkHttpManager {

    private OkHttpClient okHttpClient;
    private OkHttpManager(){
        okHttpClient = new OkHttpClient();
    }
    private volatile static OkHttpManager instance;
    public static synchronized OkHttpManager getInstance(){
        if(instance == null){
            synchronized (OkHttpManager.class){
                if(instance == null){
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void getData (String url){
        Log.d("----px----","start");
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("----px----",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.d("----px----",response.body().string());
                }else{
                    Log.d("----px----","error");
                }
            }
        });
    }

}
