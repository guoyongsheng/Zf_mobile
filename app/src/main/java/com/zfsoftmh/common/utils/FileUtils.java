package com.zfsoftmh.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.zfsoftmh.entity.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author wesley
 * @date: 2017/3/26
 * @Description: 文件相关的工具类
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    private FileUtils() {

    }


    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true: 存在 false: 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }


    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return true: 存在 false: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }


    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }


    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return true: 存在或创建成功 false: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }


    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return true 存在或创建成功  false: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }


    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) {
            return false;
        }
        // 创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            LoggerHelper.e(TAG, " createFileByDeleteOldFile " + e.getMessage());
            return false;
        }
    }


    /**
     * 获取文件夹的大小
     *
     * @param file 文件
     * @return 文件夹的大小
     */
    public static long getFolderSize(File file) {
        if (file == null) {
            return 0;
        }

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            LoggerHelper.e(TAG, " getFolderSize " + e.getMessage());
        }
        return size;
    }


    private static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }

        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWithFile(dir);
    }

    public static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }


    /**
     * 获取指定类型的文件－－最近优先
     * created by sy
     * @param extension　目标后缀名数组　ex:  str[]{".doc",".ppt"}
     */
    public static ArrayList<FileInfo> getSpecificTypeOfFile(Context context, String[] extension){
        ArrayList<FileInfo> result = new ArrayList<>();
        //从外存中获取
        Uri fileUri = MediaStore.Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection = new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
        };
        String selection="";
        for(int i = 0; i < extension.length ; i++){
            if(i != 0)
                selection=selection + " OR ";
            selection = selection + MediaStore.Files.FileColumns.DATA + " LIKE '%" + extension[i] + "'";
        }
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder= MediaStore.Files.FileColumns.DATE_MODIFIED;
        ContentResolver resolver=context.getContentResolver();
        Cursor cursor=resolver.query(fileUri, projection, selection, null, sortOrder);
        if(cursor==null)  return null;
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件优先）
        if(cursor.moveToLast()) {
            do{
                String data=cursor.getString(0);
                String title = cursor.getString(1);
                long size = cursor.getLong(2);
                if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(title) && size != 0)
                    result.add(new FileInfo(title,data,size));
            }while(cursor.moveToPrevious());
        }
        cursor.close();
        return result;
    }

    /**
     * 获取音频文件
     * created by sy
     */
    public static ArrayList<FileInfo> getMusicFiles(Context context){
        ArrayList<FileInfo> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,
                MediaStore.Audio.Media.SIZE);
        if (cursor == null) return null;
        if (cursor.moveToLast()){
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(path) && size != 0 && duration != 0)
                    list.add(new FileInfo(title,path,size,duration,null,mimeType));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        return list;
    }

    /**
     * 获取视频文件
     * created by sy
     */
    public static ArrayList<FileInfo> getVideoFiles(Context context){
        ArrayList<FileInfo> result = new ArrayList<>();
        String []thumb = {MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID};
        String []video = {MediaStore.Video.Media._ID,MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DURATION,MediaStore.Video.Media.MIME_TYPE};
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                video,null,null,MediaStore.Video.Media.SIZE);
        if (cursor == null) return null;
        if (cursor.moveToLast()){
            Cursor thumbCursor;
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                thumbCursor = context.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumb, MediaStore.Video.Thumbnails.VIDEO_ID  + "=" + id, null, null);
                if (thumbCursor == null) continue;
                if (thumbCursor.moveToFirst()){
                    String thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                    String fPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                    result.add(new FileInfo(title,fPath,size,duration,thumbPath,mimeType));
                }
            }while (cursor.moveToPrevious());
            if (thumbCursor != null)
                thumbCursor.close();
        }
        cursor.close();
        return result;
    }

}
