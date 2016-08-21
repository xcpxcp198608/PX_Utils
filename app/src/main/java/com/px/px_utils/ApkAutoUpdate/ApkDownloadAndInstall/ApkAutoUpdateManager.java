package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ApkAutoUpdateManager {

    private Context context;

    public ApkAutoUpdateManager(Context context) {
        this.context = context;
    }

    public void startDownload (ApkFileDownloadInfo apkFileDownloadInfo) {
        Intent intent = new Intent(context , ApkAutoUpdateService.class);
        intent.putExtra("apkFileDownloadInfo" ,apkFileDownloadInfo);
        context.startService(intent);
    }

    public void pauseDownload () {
        ApkFileDownloadTask.isPauseDownload = true ;
    }

    public void setDownloadStatusListener (ApkFileDownloadStatusListener apkFileDownloadStatusListener) {
        ApkFileDownloadTask.setDownloadStatusListener(apkFileDownloadStatusListener);
    }
}
