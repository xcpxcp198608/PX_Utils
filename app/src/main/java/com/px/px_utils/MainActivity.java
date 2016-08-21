package com.px.px_utils;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.px.px_utils.ApkDownloadAndInstall.ApkAutoUpdateManager;
import com.px.px_utils.ApkDownloadAndInstall.ApkFileDownloadInfo;
import com.px.px_utils.ApkDownloadAndInstall.ApkFileDownloadStatusListener;
import com.px.px_utils.Utils.ApkInstall;
import com.px.px_utils.Utils.ApkCheck;
import com.px.px_utils.Utils.NetworkStatusReceiver;
import com.px.px_utils.Utils.SystemConfig;

/**
 *  demo for download and install module;
 */
public class MainActivity extends AppCompatActivity {
    private final String apkFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PxDownload/";//路径设置不可更改
    private ApkFileDownloadInfo apkFileDownloadInfo;//必须设置文件名 ， 包名，下载路径 , version code；

    private TextView tv_Status;
    private Button bt_Start , bt_Stop;
    private ProgressBar progressBar;
    private ApkAutoUpdateManager apkAutoUpdateManager;
    private NetworkStatusReceiver networkStatusReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Status = (TextView) findViewById(R.id.tv_status);
        bt_Start = (Button) findViewById(R.id.bt_start);
        bt_Stop = (Button) findViewById(R.id.bt_stop);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        apkFileDownloadInfo = new ApkFileDownloadInfo();
        apkFileDownloadInfo.setApkPackageName("com.smule.singandroid");
        apkFileDownloadInfo.setApkFileName("com.smule.singandroid.apk");
        apkFileDownloadInfo.setApkFileDownloadUrl("http://bmob-cdn-5452.b0.upaiyun.com/2016/08/10/9c33565e40121243804386c2a6dd0277.apk");
        apkFileDownloadInfo.setApkVersionCode(377);
        apkAutoUpdateManager = new ApkAutoUpdateManager(MainActivity.this);

        bt_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断同一apk是否已经安装并且当前版本高于或等于准备安装的版本
                if(ApkCheck.isApkInstalled(MainActivity.this , apkFileDownloadInfo.getApkPackageName()) &&
                        (ApkCheck.getInstalledApkVersionCode(MainActivity.this ,apkFileDownloadInfo.getApkPackageName())
                                >= apkFileDownloadInfo.getApkVersionCode())){
                    //启动apk主activity ....
                    Toast.makeText(MainActivity.this , "apk already installed" ,Toast.LENGTH_LONG).show();
                    return;
                }
                //判断需要下载的文件是否已经存在并且版本大于等于准备安装的版本
                if (ApkCheck.isApkFileExists(apkFilePath ,apkFileDownloadInfo.getApkFileName()) &&
                        ApkCheck.getApkFileVersionCode(MainActivity.this ,apkFilePath ,apkFileDownloadInfo.getApkFileName())
                                >= apkFileDownloadInfo.getApkVersionCode()){
                    //判断系统是否ROOT，是的话执行后台静默安装 ， 否则执行正常安装
                    if(SystemConfig.isRoot()){
                        ApkInstall.silentInstallApk(MainActivity.this ,apkFilePath+apkFileDownloadInfo.getApkFileName());
                        Toast.makeText(MainActivity.this , "apk already exists ,installing in background" ,Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this , "apk already exists ,please install" ,Toast.LENGTH_LONG).show();
                        ApkInstall.installApk(MainActivity.this ,apkFilePath , apkFileDownloadInfo.getApkFileName());
                    }
                    return;
                }
                //下载准备安装的apk文件
                apkAutoUpdateManager.startDownload(apkFileDownloadInfo);
                apkAutoUpdateManager.setDownloadStatusListener(new ApkFileDownloadStatusListener() {
                    @Override
                    public void startDownload(boolean isStart, String apkFileSize) {
                        tv_Status.setText("start");
                    }

                    @Override
                    public void pauseDownload(boolean isPauseDownload) {
                        tv_Status.setText("pause");
                    }

                    @Override
                    public void downloadProgressChanged(boolean isDownloading, int progress) {
                        progressBar.setProgress(progress);
                    }

                    @Override
                    public void completedDownload(boolean isCompleted, int progress) {
                        tv_Status.setText("completed");
                        //下载完成后判断系统是否ROOT，是的话执行后台静默安装 ， 否则执行正常安装
                        if(SystemConfig.isRoot()){
                            ApkInstall.silentInstallApk(MainActivity.this ,apkFilePath+apkFileDownloadInfo.getApkFileName());
                        }else {
                            ApkInstall.installApk(MainActivity.this ,apkFilePath , apkFileDownloadInfo.getApkFileName());
                        }
                    }
                });
            }
        });

        bt_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apkAutoUpdateManager.pauseDownload();
            }
        });

        networkStatusReceiver = new NetworkStatusReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkStatusReceiver ,intentFilter );
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkStatusReceiver);
    }
}
