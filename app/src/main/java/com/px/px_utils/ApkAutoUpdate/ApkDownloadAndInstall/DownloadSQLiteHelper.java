package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/19.
 */
public class DownloadSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ApkFileDownloadInfo.db";
    public static final String TABLE_NAME = "ApkFileDownloadInfo";
    private final String CREATE_TABLE = "create table if not exists "+TABLE_NAME+"(_id integer primary key autoincrement," +
            "apkName text,apkFileName text,apkFileDownloadUrl text,apkPackageName text,apkVersionName text,apkVersionCode integer," +
            "downloadThreadId integer,apkFileSize integer,downloadStartPosition integer,downloadStopPosition integer," +
            "downloadCompletedPosition integer)";
    private final String DROP_TABLE = "drop table if exists"+TABLE_NAME;
    private static final int DATABASE_VERSION =1;

    public DownloadSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        this.onCreate(db);
    }
}
