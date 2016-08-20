package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ApkAutoUpdateService extends IntentService {
    private ThreadPoolExecutor threadPoolExecutor;

    public ApkAutoUpdateService() {
        super("ApkAutoUpdateService");
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Future<?> future = threadPoolExecutor.submit(new ApkFileDownloadTask(getApplicationContext() , (ApkFileDownloadInfo) intent.getSerializableExtra("apkFileDownloadInfo")));
    }
}
