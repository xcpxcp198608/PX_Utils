package com.px.px_utils.FileDownload;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PX on 2016/8/27.
 */
public class DownloadFileInfo implements Parcelable {
    private String fileFullName;
    private String fileDownloadUrl;
    private long fileLength;
    private long start;
    private long end;
    private long completed;
    private String isFinished;


    public String getFileFullName() {
        return fileFullName;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "DownloadFileInfo{" +
                "fileFullName='" + fileFullName + '\'' +
                ", fileDownloadUrl='" + fileDownloadUrl + '\'' +
                ", fileLength=" + fileLength +
                ", start=" + start +
                ", end=" + end +
                ", completed=" + completed +
                ", isFinished='" + isFinished + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileFullName);
        dest.writeString(this.fileDownloadUrl);
        dest.writeLong(this.fileLength);
        dest.writeLong(this.start);
        dest.writeLong(this.end);
        dest.writeLong(this.completed);
        dest.writeString(this.isFinished);
    }

    public DownloadFileInfo() {
    }

    protected DownloadFileInfo(Parcel in) {
        this.fileFullName = in.readString();
        this.fileDownloadUrl = in.readString();
        this.fileLength = in.readLong();
        this.start = in.readLong();
        this.end = in.readLong();
        this.completed = in.readLong();
        this.isFinished = in.readString();
    }

    public static final Parcelable.Creator<DownloadFileInfo> CREATOR = new Parcelable.Creator<DownloadFileInfo>() {
        @Override
        public DownloadFileInfo createFromParcel(Parcel source) {
            return new DownloadFileInfo(source);
        }

        @Override
        public DownloadFileInfo[] newArray(int size) {
            return new DownloadFileInfo[size];
        }
    };
}
