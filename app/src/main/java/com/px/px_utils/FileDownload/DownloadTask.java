package com.px.px_utils.FileDownload;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.px.px_utils.C;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by PX on 2016/8/27.
 */
public class DownloadTask implements Runnable {

    private SQLiteDao sqLiteDao;
    private DownloadFileInfo downloadFileInfo;
    private static DownloadStatusListener downloadStatusListener;
    public static boolean isPause = false;
    private final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PxDownload/";

    public DownloadTask(Context context , DownloadFileInfo downloadFileInfo) {
        this.downloadFileInfo = downloadFileInfo;
        sqLiteDao = SQLiteDao.getInstance(context);
    }

    public static void setDownloadStatusListener (DownloadStatusListener downloadStatusListener1){
        downloadStatusListener = downloadStatusListener1;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case C.msg.START_DOWNLOAD_FILE:
                    if(downloadStatusListener != null){
                        downloadStatusListener.startDownload(true , (long) msg.obj);
                    }
                    break;
                case C.msg.PAUSE_DOWNLOAD_FILE:
                    if(downloadStatusListener != null){
                        downloadStatusListener.pauseDownload(true , (int) msg.obj);
                    }
                    break;
                case C.msg.PROGRESS_CHANGE_DOWNLOAD_FILE:
                    if(downloadStatusListener != null){
                        downloadStatusListener.downloadProgressChanged(true ,(int) msg.obj);
                    }
                    break;
                case C.msg.COMPLETED_DOWNLOAD_FILE:
                    if(downloadStatusListener != null){
                        downloadStatusListener.completedDownload(true , 100);
                    }
                    break;
            }
        }
    };

    @Override
    public void run() {
//        Log.d("----px----","run");
        if(sqLiteDao.isExists(downloadFileInfo)) {
            downloadFileInfo = sqLiteDao.queryDataByFileFullName(downloadFileInfo.getFileFullName()).get(0);
        }
        Log.d("----px----",downloadFileInfo.getFileDownloadUrl());
        isPause = false;
        HttpURLConnection httpURLConnection = null;
        HttpURLConnection httpURLConnection1 = null;
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;
        long startPosition = downloadFileInfo.getStart() + downloadFileInfo.getCompleted();
        long completedPosition ;
        int progress;
        try {
            URL url = new URL(downloadFileInfo.getFileDownloadUrl());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Log.d("----px----",httpURLConnection.getContentLength()+"");
                downloadFileInfo.setFileLength(httpURLConnection.getContentLength());
                downloadFileInfo.setEnd(httpURLConnection.getContentLength());
                if(downloadFileInfo.getFileLength() < 0){
                    return;
                }else {
                    handler.obtainMessage(C.msg.START_DOWNLOAD_FILE , downloadFileInfo.getFileLength()).sendToTarget();
                }
            }

            File dir = new File(filePath);
            if(! dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir ,downloadFileInfo.getFileFullName());
            randomAccessFile = new RandomAccessFile(file ,"rwd");
            randomAccessFile.setLength(downloadFileInfo.getFileLength());
            randomAccessFile.seek(startPosition);

            URL url1 = new URL(downloadFileInfo.getFileDownloadUrl());
            httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            httpURLConnection1.setRequestMethod("GET");
            httpURLConnection1.setConnectTimeout(5000);
            httpURLConnection1.setRequestProperty("Range" , "bytes="+startPosition+"-"+downloadFileInfo.getEnd());
            if(httpURLConnection1.getResponseCode() == HttpURLConnection.HTTP_PARTIAL){
                inputStream = httpURLConnection1.getInputStream();
                byte [] buffer = new byte[1024];
                int length = -1;
                long time = System.currentTimeMillis();
                completedPosition = downloadFileInfo.getCompleted();
                while ((length = inputStream.read(buffer)) != -1) {
                    randomAccessFile.write(buffer , 0 , length);
                    completedPosition += length;
                    if(System.currentTimeMillis() - time > 1000) {
                        time = System.currentTimeMillis();
                        progress = (int)(completedPosition*100L/downloadFileInfo.getFileLength());
                        Log.d("----px----",progress+"");
                        handler.obtainMessage(C.msg.PROGRESS_CHANGE_DOWNLOAD_FILE ,progress).sendToTarget();
                    }
                    if(isPause){
                        downloadFileInfo.setCompleted(completedPosition);
                        downloadFileInfo.setIsFinished("false");
                        sqLiteDao.insertOrUpdateData(downloadFileInfo);
                        handler.obtainMessage(C.msg.PAUSE_DOWNLOAD_FILE , (int)(completedPosition*100L/downloadFileInfo.getFileLength())).sendToTarget();
                        return;
                    }
                }
                handler.obtainMessage(C.msg.COMPLETED_DOWNLOAD_FILE).sendToTarget();
                if(sqLiteDao.isExists(downloadFileInfo)){
                    sqLiteDao.deleteDataByFileFullName(downloadFileInfo.getFileFullName());
                }
            }
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
