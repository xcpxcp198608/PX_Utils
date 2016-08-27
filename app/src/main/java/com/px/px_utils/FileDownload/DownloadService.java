package com.px.px_utils.FileDownload;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by PX on 2016/8/27.
 */
public class DownloadService extends IntentService {
    private ThreadPoolExecutor threadPoolExecutor;

    public DownloadService() {
        super("DownloadService");
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Log.d("----px----","service start");
        Future<?> future = threadPoolExecutor.submit(new DownloadTask(getApplicationContext() , (DownloadFileInfo) intent.getParcelableExtra("downloadFileInfo")));
    }
}
