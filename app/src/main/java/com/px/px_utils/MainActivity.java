package com.px.px_utils;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.px.px_utils.FileDownload.DownloadFileInfo;
import com.px.px_utils.FileDownload.DownloadManager;
import com.px.px_utils.FileDownload.DownloadStatusListener;

/**
 *  demo of file download;
 */
public class MainActivity extends AppCompatActivity {
    private final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PxDownload/";//路径设置不可更改

    private TextView tv_Status;
    private Button bt_Start , bt_Stop;
    private ProgressBar progressBar;

//    private String url = "ftp://wiatecftp:Wil654321@158.69.229.104/AppMarket/video/btvi3.mp4";
    private String url = "http://bmob-cdn-5452.b0.upaiyun.com/2016/08/25/3d91e1e94063532080ed4960365aa146.apk";
    private DownloadFileInfo downloadFileInfo;
    private DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Status = (TextView) findViewById(R.id.tv_status);
        bt_Start = (Button) findViewById(R.id.bt_start);
        bt_Stop = (Button) findViewById(R.id.bt_stop);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        downloadFileInfo = new DownloadFileInfo();
        downloadFileInfo.setFileFullName("tv2.0.apk");
        downloadFileInfo.setFileDownloadUrl(url);
        downloadManager = new DownloadManager(MainActivity.this);
        downloadManager.setDownloadStatusListener(new DownloadStatusListener() {
            @Override
            public void startDownload(boolean isStart, long fileLength) {
                tv_Status.setText("start:" + fileLength);
            }

            @Override
            public void pauseDownload(boolean isPauseDownload, int progress) {
                tv_Status.setText("pause:" + progress);
            }

            @Override
            public void downloadProgressChanged(boolean isDownloading, int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void completedDownload(boolean isCompleted, int progress) {
                progressBar.setProgress(progress);
                tv_Status.setText("completed:" + progress);
            }
        });

        bt_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.startDownload(downloadFileInfo);
            }
        });

        bt_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.pauseDownload();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadManager.pauseDownload();
    }
}
