package com.px.px_utils.FileDownload;

/**
 * Created by PX on 2016/8/27.
 */
public interface DownloadStatusListener {
    void startDownload (boolean isStart ,long  fileLength);
    void pauseDownload (boolean isPauseDownload , int progress);
    void downloadProgressChanged (boolean isDownloading ,int progress);
    void completedDownload (boolean isCompleted , int progress);
}
