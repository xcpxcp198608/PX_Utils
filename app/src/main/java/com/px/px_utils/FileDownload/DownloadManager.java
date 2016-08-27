package com.px.px_utils.FileDownload;

import android.content.Context;
import android.content.Intent;


/**
 * 创建DownloadFileInfo 对象（name and url is not null）
 * 创建DownloadManager对象;
 * 执行DownloadManager的startDownload 方法开始下载 ， 并设置监听；
 * Created by PX on 2016/8/27.
 */
public class DownloadManager {

    private Context context;

    public DownloadManager(Context context) {
        this.context = context;
    }

    public void startDownload (DownloadFileInfo downloadFileInfo) {
        Intent intent = new Intent(context , DownloadService.class);
        intent.putExtra("downloadFileInfo" ,downloadFileInfo);
        context.startService(intent);
    }

    public void pauseDownload () {
        DownloadTask.isPause = true ;
    }

    public void setDownloadStatusListener (DownloadStatusListener downloadStatusListener) {
        DownloadTask.setDownloadStatusListener(downloadStatusListener);
    }
}
