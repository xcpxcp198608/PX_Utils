package com.px.px_utils;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall.ApkAutoUpdateManager;
import com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall.ApkFileDownloadInfo;
import com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall.ApkFileDownloadStatusListener;
import com.px.px_utils.ApkAutoUpdate.Utils.ApkInstall;
import com.px.px_utils.ApkAutoUpdate.Utils.ApkCheck;
import com.px.px_utils.ApkAutoUpdate.Utils.SystemConfig;

public class MainActivity extends AppCompatActivity {
    private final String apkFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PxDownload/";//路径设置不可更改
    private ApkFileDownloadInfo apkFileDownloadInfo;//必须设置文件名 ， 包名，下载路径；
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apkFileDownloadInfo = new ApkFileDownloadInfo();

        //判断同一apk是否已经安装并且当前版本高于或等于准备安装的版本
        if(ApkCheck.isApkInstalled(MainActivity.this , apkFileDownloadInfo.getApkPackageName()) &&
                (ApkCheck.getInstalledApkVersionCode(MainActivity.this ,apkFileDownloadInfo.getApkPackageName())
                        >= apkFileDownloadInfo.getApkVersionCode())){
            return;
        }
        //判断需要下载的文件是否已经存在
        if (ApkCheck.isApkFileExists(apkFilePath ,apkFileDownloadInfo.getApkFileName()) &&
                ApkCheck.getApkFileVersionCode(MainActivity.this ,apkFilePath ,apkFileDownloadInfo.getApkFileName())
                        >= apkFileDownloadInfo.getApkVersionCode()){
            //判断系统是否ROOT，是的话执行后台静默安装 ， 否则执行正常安装
            if(SystemConfig.isRoot()){
                ApkInstall.silentInstallApk(MainActivity.this ,apkFilePath+apkFileDownloadInfo.getApkFileName());
            }else {
                ApkInstall.installApk(MainActivity.this ,apkFilePath , apkFileDownloadInfo.getApkFileName());
            }
            return;
        }
        //下载准备安装的apk文件
        ApkAutoUpdateManager apkAutoUpdateManager = new ApkAutoUpdateManager(apkFileDownloadInfo , MainActivity.this);
        apkAutoUpdateManager.startDownload();
        apkAutoUpdateManager.setDownloadStatusListener(new ApkFileDownloadStatusListener() {
            @Override
            public void startDownload(boolean isStart, String apkFileSize) {

            }

            @Override
            public void pauseDownload(boolean isPauseDownload) {

            }

            @Override
            public void downloadProgressChanged(boolean isDownloading, int progress) {

            }

            @Override
            public void completedDownload(boolean isCompleted, int progress) {
                //下载完成后判断系统是否ROOT，是的话执行后台静默安装 ， 否则执行正常安装
                if(SystemConfig.isRoot()){
                    ApkInstall.silentInstallApk(MainActivity.this ,apkFilePath+apkFileDownloadInfo.getApkFileName());
                }else {
                    ApkInstall.installApk(MainActivity.this ,apkFilePath , apkFileDownloadInfo.getApkFileName());
                }
            }
        });
    }
}
