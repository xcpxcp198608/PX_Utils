package com.px.px_utils.FileDownload;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PX on 2016/8/27.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FileDownloadInfo.db";
    public static final String TABLE_NAME = "FileDownloadInfo";
    private final String CREATE_TABLE = "create table if not exists "+TABLE_NAME+"(_id integer primary key autoincrement," +
            "fileFullName text,fileDownloadUrl text,fileLength integer,start integer,end integer,completed integer," +
            "isFinished text)";
    private final String DROP_TABLE = "drop table if exists"+TABLE_NAME;
    private static final int DATABASE_VERSION =1;


    public SQLiteHelper(Context context) {
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
