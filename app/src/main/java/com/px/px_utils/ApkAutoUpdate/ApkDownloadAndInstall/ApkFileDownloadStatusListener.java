package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface ApkFileDownloadStatusListener {
    void startDownload (boolean isStart ,String apkFileSize);
    void pauseDownload (boolean isPauseDownload);
    void downloadProgressChanged (boolean isDownloading ,int progress);
    void completedDownload (boolean isCompleted , int progress);
}
