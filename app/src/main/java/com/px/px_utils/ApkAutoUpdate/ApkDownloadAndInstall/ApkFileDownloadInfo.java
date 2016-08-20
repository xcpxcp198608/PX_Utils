package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ApkFileDownloadInfo implements Serializable{
    private String apkName;
    private String apkFileName;
    private String apkFileDownloadUrl;
    private String apkPackageName;
    private String apkVersionName;
    private int apkVersionCode;
    private int downloadThreadId;
    private long apkFileSize;
    private long downloadStartPosition;
    private long downloadStopPosition;
    private long downloadCompletedPosition;

    public String getApkFileDownloadUrl() {
        return apkFileDownloadUrl;
    }

    public void setApkFileDownloadUrl(String apkFileDownloadUrl) {
        this.apkFileDownloadUrl = apkFileDownloadUrl;
    }

    public String getApkFileName() {
        return apkFileName;
    }

    public void setApkFileName(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public long getApkFileSize() {
        return apkFileSize;
    }

    public void setApkFileSize(long apkFileSize) {
        this.apkFileSize = apkFileSize;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkPackageName() {
        return apkPackageName;
    }

    public void setApkPackageName(String apkPackageName) {
        this.apkPackageName = apkPackageName;
    }

    public int getApkVersionCode() {
        return apkVersionCode;
    }

    public void setApkVersionCode(int apkVersionCode) {
        this.apkVersionCode = apkVersionCode;
    }

    public String getApkVersionName() {
        return apkVersionName;
    }

    public void setApkVersionName(String apkVersionName) {
        this.apkVersionName = apkVersionName;
    }

    public long getDownloadCompletedPosition() {
        return downloadCompletedPosition;
    }

    public void setDownloadCompletedPosition(long downloadCompletedPosition) {
        this.downloadCompletedPosition = downloadCompletedPosition;
    }

    public long getDownloadStartPosition() {
        return downloadStartPosition;
    }

    public void setDownloadStartPosition(long downloadStartPosition) {
        this.downloadStartPosition = downloadStartPosition;
    }

    public long getDownloadStopPosition() {
        return downloadStopPosition;
    }

    public void setDownloadStopPosition(long downloadStopPosition) {
        this.downloadStopPosition = downloadStopPosition;
    }

    public int getDownloadThreadId() {
        return downloadThreadId;
    }

    public void setDownloadThreadId(int downloadThreadId) {
        this.downloadThreadId = downloadThreadId;
    }

    @Override
    public String toString() {
        return "ApkFileDownloadInfo{" +
                "apkFileDownloadUrl='" + apkFileDownloadUrl + '\'' +
                ", apkName='" + apkName + '\'' +
                ", apkFileName='" + apkFileName + '\'' +
                ", apkPackageName='" + apkPackageName + '\'' +
                ", apkVersionName='" + apkVersionName + '\'' +
                ", apkVersionCode=" + apkVersionCode +
                ", downloadThreadId=" + downloadThreadId +
                ", apkFileSize=" + apkFileSize +
                ", downloadStartPosition=" + downloadStartPosition +
                ", downloadStopPosition=" + downloadStopPosition +
                ", downloadCompletedPosition=" + downloadCompletedPosition +
                '}';
    }
}
