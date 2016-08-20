package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.px.px_utils.ApkAutoUpdate.Utils.FormatNumber;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ApkFileDownloadTask implements Runnable {

    private final int MSG_START_DOWNLOAD = 101;
    private final int MSG_PAUSE_DOWNLOAD = 102;
    private final int MSG_PROGRESS_CHANGED = 103;
    private final int MSG_COMPLETED_DOWNLOAD = 104;
    private final String apkFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PxDownload/";
    private DownloadSQLiteDao downloadSQLiteDao;
    private ApkFileDownloadInfo apkFileDownloadInfo;
    private static ApkFileDownloadStatusListener apkFileDownloadStatusListener;
    public static boolean isPauseDownload = false;


    public ApkFileDownloadTask(Context context ,ApkFileDownloadInfo apkFileDownloadInfo) {
        this.apkFileDownloadInfo = apkFileDownloadInfo;
        downloadSQLiteDao = DownloadSQLiteDao.getInstance(context);
    }

    public static void setDownloadStatusListener (ApkFileDownloadStatusListener apkFileDownloadStatusListener1){
        apkFileDownloadStatusListener = apkFileDownloadStatusListener1;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_START_DOWNLOAD:
                    if(apkFileDownloadStatusListener != null){
                        apkFileDownloadStatusListener.startDownload(true , (String) msg.obj);
                    }
                    break;
                case MSG_PAUSE_DOWNLOAD:
                    if(apkFileDownloadStatusListener != null){
                        apkFileDownloadStatusListener.pauseDownload(true);
                    }
                    break;
                case MSG_PROGRESS_CHANGED:
                    if(apkFileDownloadStatusListener != null){
                        apkFileDownloadStatusListener.downloadProgressChanged(true ,(int) msg.obj);
                    }
                    break;
                case MSG_COMPLETED_DOWNLOAD:
                    if(apkFileDownloadStatusListener != null){
                        apkFileDownloadStatusListener.completedDownload(true , 100);
                    }
                    break;
            }
        }
    };

    @Override
    public void run() {
        if(downloadSQLiteDao.isExists(apkFileDownloadInfo.getApkFileName() , apkFileDownloadInfo.getApkFileDownloadUrl())) {
            apkFileDownloadInfo = downloadSQLiteDao.queryDataByApkName(apkFileDownloadInfo.getApkFileName() ,
                    apkFileDownloadInfo.getApkFileDownloadUrl()).get(0);
        }
        //Log.d("----px----",apkFileDownloadInfo.getApkFileDownloadUrl());
        HttpURLConnection httpURLConnection = null;
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;
        long startPosition = apkFileDownloadInfo.getDownloadStartPosition() + apkFileDownloadInfo.getDownloadCompletedPosition();
        long completedPosition ;
        int progress;
        try {
            URL url = new URL(apkFileDownloadInfo.getApkFileDownloadUrl());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                apkFileDownloadInfo.setApkFileSize(httpURLConnection.getContentLength());
            }
            if(apkFileDownloadInfo.getApkFileSize() < 0){
                return;
            }else {
                handler.obtainMessage(MSG_START_DOWNLOAD , FormatNumber.long2Mb(httpURLConnection.getContentLength())).sendToTarget();
                downloadSQLiteDao.insertData(apkFileDownloadInfo);
            }
            File dir = new File(apkFilePath);
            if(! dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir , apkFileDownloadInfo.getApkFileName());
            randomAccessFile = new RandomAccessFile(file ,"rwd");
            randomAccessFile.setLength(apkFileDownloadInfo.getApkFileSize());
            randomAccessFile.seek(startPosition);
            inputStream = httpURLConnection.getInputStream();
            byte [] buffer = new byte[1024];
            int length = -1;
            long time = System.currentTimeMillis();
            completedPosition = apkFileDownloadInfo.getDownloadCompletedPosition();
            while ((length = inputStream.read(buffer)) != -1) {
                randomAccessFile.write(buffer , 0 , length);
                completedPosition += length;
                if(System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                    progress = (int)(completedPosition*100L/apkFileDownloadInfo.getApkFileSize());
                    //Log.d("----px----",progress+"");
                    handler.obtainMessage(MSG_PROGRESS_CHANGED ,progress).sendToTarget();
                }
                if(isPauseDownload){
                    downloadSQLiteDao.insertOrUpdateData(apkFileDownloadInfo);
                    handler.obtainMessage(MSG_PAUSE_DOWNLOAD ).sendToTarget();
                    return;
                }
            }
            if(downloadSQLiteDao.isExists(apkFileDownloadInfo.getApkFileName() ,apkFileDownloadInfo.getApkFileDownloadUrl())){
                downloadSQLiteDao.deleteDataByApkName(apkFileDownloadInfo.getApkFileName() ,apkFileDownloadInfo.getApkFileDownloadUrl());
            }
            handler.obtainMessage(MSG_COMPLETED_DOWNLOAD).sendToTarget();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!= null) {
                    inputStream.close();
                }
                if(httpURLConnection!= null) {
                    httpURLConnection.disconnect();
                }
                if(randomAccessFile!= null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
