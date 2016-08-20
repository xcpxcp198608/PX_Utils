package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class DownloadSQLiteDao implements IDownloadSQLiteDao {

    private SQLiteDatabase sqLiteDatabase;

    private DownloadSQLiteDao(Context context) {
        sqLiteDatabase = new DownloadSQLiteHelper(context).getWritableDatabase();
    }

    private volatile static DownloadSQLiteDao instance;
    public static  synchronized DownloadSQLiteDao getInstance(Context context){
        if(instance == null){
            synchronized (DownloadSQLiteDao.class){
                if(instance == null){
                    instance = new DownloadSQLiteDao(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void insertData(ApkFileDownloadInfo apkFileDownloadInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("apkName" , apkFileDownloadInfo.getApkName());
        contentValues.put("apkFileName" , apkFileDownloadInfo.getApkFileName());
        contentValues.put("apkFileDownloadUrl" , apkFileDownloadInfo.getApkFileDownloadUrl());
        contentValues.put("apkPackageName" , apkFileDownloadInfo.getApkPackageName());
        contentValues.put("apkVersionName" , apkFileDownloadInfo.getApkVersionName());
        contentValues.put("apkVersionCode" , apkFileDownloadInfo.getApkVersionCode());
        contentValues.put("downloadThreadId" , apkFileDownloadInfo.getDownloadThreadId());
        contentValues.put("apkFileSize" , apkFileDownloadInfo.getApkFileSize());
        contentValues.put("downloadStartPosition" , apkFileDownloadInfo.getDownloadStartPosition());
        contentValues.put("downloadStopPosition" , apkFileDownloadInfo.getDownloadStopPosition());
        contentValues.put("downloadCompletedPosition" , apkFileDownloadInfo.getDownloadCompletedPosition());
        sqLiteDatabase.insert(DownloadSQLiteHelper.TABLE_NAME ,null , contentValues);
        contentValues.clear();
    }

    @Override
    public void insertOrUpdateData(ApkFileDownloadInfo apkFileDownloadInfo) {
        if(isExists(apkFileDownloadInfo.getApkFileName() , apkFileDownloadInfo.getApkFileDownloadUrl())){
            updateData(apkFileDownloadInfo.getApkFileName() , apkFileDownloadInfo.getApkFileDownloadUrl());
        }else {
            insertData(apkFileDownloadInfo);
        }
    }

    @Override
    public void deleteDataByApkName(String apkFileName, String apkFileDownloadUrl) {
        sqLiteDatabase.delete(DownloadSQLiteHelper.TABLE_NAME , "apkFileName=? and apkFileDownloadUrl=?",
                new String [] { apkFileName,apkFileDownloadUrl});
    }

    @Override
    public void deleteAllData() {
        sqLiteDatabase.delete(DownloadSQLiteHelper.TABLE_NAME , "_id>?" , new String [] {"0"});
    }

    @Override
    public List<ApkFileDownloadInfo> queryDataByApkName(String apkFileName, String apkFileDownloadUrl) {
        List<ApkFileDownloadInfo> dataList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DownloadSQLiteHelper.TABLE_NAME,  null , "apkFileName=? and apkFileDownloadUrl=?" ,
                new String [] {apkFileName , apkFileDownloadUrl} , null ,null , null);
        while (cursor.moveToNext()){
            ApkFileDownloadInfo apkFileDownloadInfo = new ApkFileDownloadInfo();
            apkFileDownloadInfo.setApkName(cursor.getString(cursor.getColumnIndex("apkName")));
            apkFileDownloadInfo.setApkFileName(cursor.getString(cursor.getColumnIndex("apkFileName")));
            apkFileDownloadInfo.setApkFileDownloadUrl(cursor.getString(cursor.getColumnIndex("apkFileDownloadUrl")));
            apkFileDownloadInfo.setApkPackageName(cursor.getString(cursor.getColumnIndex("apkPackageName")));
            apkFileDownloadInfo.setApkVersionName(cursor.getString(cursor.getColumnIndex("apkVersionName")));
            apkFileDownloadInfo.setApkVersionCode(cursor.getInt(cursor.getColumnIndex("apkVersionCode")));
            apkFileDownloadInfo.setDownloadThreadId(cursor.getInt(cursor.getColumnIndex("downloadThreadId")));
            apkFileDownloadInfo.setApkFileSize(cursor.getLong(cursor.getColumnIndex("apkFileSize")));
            apkFileDownloadInfo.setDownloadStartPosition(cursor.getLong(cursor.getColumnIndex("downloadStartPosition")));
            apkFileDownloadInfo.setDownloadStopPosition(cursor.getLong(cursor.getColumnIndex("downloadStopPosition")));
            apkFileDownloadInfo.setDownloadCompletedPosition(cursor.getLong(cursor.getColumnIndex("downloadCompletedPosition")));
            dataList.add(apkFileDownloadInfo);
        }
        cursor.close();
        return dataList;
    }

    @Override
    public List<ApkFileDownloadInfo> queryAllData() {
        List<ApkFileDownloadInfo> dataList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DownloadSQLiteHelper.TABLE_NAME,  null , "_id>?" ,
                new String [] {"0"} , null ,null , null);
        while (cursor.moveToNext()){
            ApkFileDownloadInfo apkFileDownloadInfo = new ApkFileDownloadInfo();
            apkFileDownloadInfo.setApkName(cursor.getString(cursor.getColumnIndex("apkName")));
            apkFileDownloadInfo.setApkFileName(cursor.getString(cursor.getColumnIndex("apkFileName")));
            apkFileDownloadInfo.setApkFileDownloadUrl(cursor.getString(cursor.getColumnIndex("apkFileDownloadUrl")));
            apkFileDownloadInfo.setApkPackageName(cursor.getString(cursor.getColumnIndex("apkPackageName")));
            apkFileDownloadInfo.setApkVersionName(cursor.getString(cursor.getColumnIndex("apkVersionName")));
            apkFileDownloadInfo.setApkVersionCode(cursor.getInt(cursor.getColumnIndex("apkVersionCode")));
            apkFileDownloadInfo.setDownloadThreadId(cursor.getInt(cursor.getColumnIndex("downloadThreadId")));
            apkFileDownloadInfo.setApkFileSize(cursor.getLong(cursor.getColumnIndex("apkFileSize")));
            apkFileDownloadInfo.setDownloadStartPosition(cursor.getLong(cursor.getColumnIndex("downloadStartPosition")));
            apkFileDownloadInfo.setDownloadStopPosition(cursor.getLong(cursor.getColumnIndex("downloadStopPosition")));
            apkFileDownloadInfo.setDownloadCompletedPosition(cursor.getLong(cursor.getColumnIndex("downloadCompletedPosition")));
            dataList.add(apkFileDownloadInfo);
        }
        cursor.close();
        return dataList;
    }

    @Override
    public void updateData(String apkFileName, String apkFileDownloadUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("apkFileName" , apkFileName);
        contentValues.put("apkFileDownloadUrl" , apkFileDownloadUrl);
        sqLiteDatabase.update(DownloadSQLiteHelper.TABLE_NAME ,contentValues , "apkFileName=? and apkFileDownloadUrl=?" ,
                new String [] {apkFileName , apkFileDownloadUrl});
        contentValues.clear();
    }

    @Override
    public boolean isExists(String apkFileName, String apkFileDownloadUrl) {
        Cursor cursor = sqLiteDatabase.query(DownloadSQLiteHelper.TABLE_NAME,  null , "apkFileName=? and apkFileDownloadUrl=?" ,
                new String [] {apkFileName , apkFileDownloadUrl} , null ,null , null);
        if(cursor.moveToNext()){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }
}
