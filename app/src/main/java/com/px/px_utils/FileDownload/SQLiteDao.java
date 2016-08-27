package com.px.px_utils.FileDownload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by PX on 2016/8/27.
 */
public class SQLiteDao {

    private SQLiteDatabase sqLiteDatabase;

    private SQLiteDao(Context context) {
        sqLiteDatabase = new SQLiteHelper(context).getWritableDatabase();
    }

    private volatile static SQLiteDao instance;
    public static  synchronized SQLiteDao getInstance(Context context){
        if(instance == null){
            synchronized (SQLiteDao.class){
                if(instance == null){
                    instance = new SQLiteDao(context);
                }
            }
        }
        return instance;
    }

    public void insertData(DownloadFileInfo downloadFileInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fileFullName" , downloadFileInfo.getFileFullName());
        contentValues.put("fileDownloadUrl" , downloadFileInfo.getFileDownloadUrl());
        contentValues.put("fileLength" , downloadFileInfo.getFileLength());
        contentValues.put("start" , downloadFileInfo.getStart());
        contentValues.put("end" , downloadFileInfo.getEnd());
        contentValues.put("completed" , downloadFileInfo.getCompleted());
        contentValues.put("isFinished" , downloadFileInfo.getIsFinished());
        sqLiteDatabase.insert(SQLiteHelper.TABLE_NAME ,null , contentValues);
        contentValues.clear();
    }

    public void insertOrUpdateData(DownloadFileInfo downloadFileInfo) {
        if(isExists(downloadFileInfo)){
            updateData(downloadFileInfo);
        }else {
            insertData(downloadFileInfo);
        }
    }

    public void deleteDataByFileFullName(String fileFullName) {
        sqLiteDatabase.delete(SQLiteHelper.TABLE_NAME , "fileFullName=?",
                new String [] { fileFullName});
    }

    public void deleteAllData() {
        sqLiteDatabase.delete(SQLiteHelper.TABLE_NAME , "_id>?" , new String [] {"0"});
    }

    public List<DownloadFileInfo> queryDataByFileFullName(String fileFullName) {
        List<DownloadFileInfo> dataList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME,  null , "fileFullName=?" ,
                new String [] {fileFullName} , null ,null , null);
        while (cursor.moveToNext()){
            DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
            downloadFileInfo.setFileFullName(cursor.getString(cursor.getColumnIndex("fileFullName")));
            downloadFileInfo.setFileDownloadUrl(cursor.getString(cursor.getColumnIndex("fileDownloadUrl")));
            downloadFileInfo.setFileLength(cursor.getLong(cursor.getColumnIndex("fileLength")));
            downloadFileInfo.setStart(cursor.getLong(cursor.getColumnIndex("start")));
            downloadFileInfo.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
            downloadFileInfo.setCompleted(cursor.getLong(cursor.getColumnIndex("completed")));
            downloadFileInfo.setIsFinished(cursor.getString(cursor.getColumnIndex("isFinished")));
            dataList.add(downloadFileInfo);
        }
        cursor.close();
        return dataList;
    }

    public List<DownloadFileInfo> queryAllData() {
        List<DownloadFileInfo> dataList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME,  null , "_id>?" ,
                new String [] {"0"} , null ,null , null);
        while (cursor.moveToNext()){
            DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
            downloadFileInfo.setFileFullName(cursor.getString(cursor.getColumnIndex("fileFullName")));
            downloadFileInfo.setFileDownloadUrl(cursor.getString(cursor.getColumnIndex("fileDownloadUrl")));
            downloadFileInfo.setFileLength(cursor.getLong(cursor.getColumnIndex("fileLength")));
            downloadFileInfo.setStart(cursor.getLong(cursor.getColumnIndex("start")));
            downloadFileInfo.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
            downloadFileInfo.setCompleted(cursor.getLong(cursor.getColumnIndex("completed")));
            downloadFileInfo.setIsFinished(cursor.getString(cursor.getColumnIndex("isFinished")));
            dataList.add(downloadFileInfo);
        }
        cursor.close();
        return dataList;
    }

    public void updateData(DownloadFileInfo downloadFileInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fileFullName" , downloadFileInfo.getFileFullName());
        contentValues.put("fileDownloadUrl" , downloadFileInfo.getFileDownloadUrl());
        contentValues.put("fileLength" , downloadFileInfo.getFileLength());
        contentValues.put("start" , downloadFileInfo.getStart());
        contentValues.put("end" , downloadFileInfo.getEnd());
        contentValues.put("completed" , downloadFileInfo.getCompleted());
        contentValues.put("isFinished" , downloadFileInfo.getIsFinished());
        sqLiteDatabase.update(SQLiteHelper.TABLE_NAME ,contentValues , "fileFullName=?" ,
                new String [] {downloadFileInfo.getFileFullName()});
        contentValues.clear();
    }

    public boolean isExists(DownloadFileInfo downloadFileInfo) {
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME,  null , "fileFullName=?" ,
                new String [] {downloadFileInfo.getFileFullName()} , null ,null , null);
        if(cursor.moveToNext()){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }
}
