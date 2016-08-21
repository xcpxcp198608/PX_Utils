package com.px.px_utils.ApkAutoUpdate.ApkDownloadAndInstall;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface IDownloadSQLiteDao {
    void insertData (ApkFileDownloadInfo apkFileDownloadInfo) ;
    void insertOrUpdateData (ApkFileDownloadInfo apkFileDownloadInfo);
    void deleteDataByApkName (String apkFileName , String apkFileDownloadUrl);
    void deleteAllData();
    List<ApkFileDownloadInfo> queryDataByApkName (String apkFileName , String apkFileDownloadUrl);
    List<ApkFileDownloadInfo> queryAllData ();
    void updateData (ApkFileDownloadInfo apkFileDownloadInfo);
    boolean isExists (String apkFileName , String apkFileDownloadUrl);
}
