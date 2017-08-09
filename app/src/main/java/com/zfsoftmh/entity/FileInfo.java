package com.zfsoftmh.entity;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import java.text.DecimalFormat;

/**
 * Created by sy
 * on 2017/7/5.
 * <p>文件内容</p>
 */

public class FileInfo {

    public static final String TYPE_WORD = "word";
    public static final String TYPE_PPT = "ppt";
    public static final String TYPE_XLS = "excel";
    public static final String TYPE_PDF = "pdf";

    private String fileName;
    private String filePath;
    private long fileSize;
    private String suffix;
    private int duration;
    private String mimeType;

    public String getThumbPath() {
        return thumbPath;
    }

    private String thumbPath;

    public String getDuration() {
        return durationFormat(duration);
    }

    public int getDurationValue(){
        return duration;
    }

    public int getSizeValue(){
        return (int) fileSize;
    }

    @SuppressLint("DefaultLocale")
    private String durationFormat(int duration){
        duration = duration / 1000;
        String s = String.format("%02d",duration % 60);
        String m = String.format("%02d",duration / 60);
        return m + ":" + s;
    }

    /**
     * Office Files
     */
    public FileInfo(String fName,String fPath,long size){
        this.fileName = fName;
        this.filePath = fPath;
        this.fileSize = size;
        int lastDotIndex = fPath.lastIndexOf(".");
        if (lastDotIndex > 0) {
            this.suffix = fPath.substring(lastDotIndex + 1);
        }
    }

    /**
     * Media Files
     */
    public FileInfo(String fileName, String filePath, long size, int duration,
                    @Nullable String thumbPath, @Nullable String mimeType){
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = size;
        this.duration = duration;
        this.thumbPath = thumbPath;
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

    public String getFileName() {
        return fileName.contains(".") ? fileName : fileName + "." + suffix;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileSize() {
        return formatFileSize(fileSize);
    }

    public String getSuffixType() {
        String type;
        switch (suffix){
            case "doc":
                type = TYPE_WORD;
                break;
            case "docx":
                type = TYPE_WORD;
                break;
            case "ppt":
                type = TYPE_PPT;
                break;
            case "pptx":
                type = TYPE_PPT;
                break;
            case "pdf":
                type = TYPE_PDF;
                break;
            case "xls":
                type = TYPE_XLS;
                break;
            default:
                type = null;
        }
        return type;
    }

    private String formatFileSize(long size){
        DecimalFormat format = new DecimalFormat("#.00");
        if (size < 1024){
            return format.format((double) size) + "B";
        }else if(size < 1048576){
            return format.format((double) size / 1024) + "KB";
        }else if(size < 1073741824){
            return format.format((double) size / 1048576) + "MB";
        }else{
            return format.format((double) size / 1073741824) + "GB";
        }
    }


    public String getMimeType() {
        return mimeType;
    }
}
