package com.zfsoftmh.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.ui.base.BaseApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

/**
 * 创建时间： 2017/5/9 0009
 * 编写人： 王世美
 * 功能描述： 工具类
 */

public class CloudNoteUtils {

    /**
     * 获取插入图片Drawable
     *
     * @param screen_width
     * @param bitmap
     * @return
     */
    public static Drawable getDrawable(int screen_width, int screen_height, Bitmap bitmap) {
//        bitmap = fitBitmap(bitmap, screen_width);
//        bitmap = scaleBitmap(bitmap,screen_width, screen_height/2);
        Drawable d = new BitmapDrawable(bitmap);
        int imageHeight = ImageUtil.getImageHeight(bitmap, screen_width);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            d.setBounds(0, 0, screen_width - (SizeUtils.dp2px(21, BaseApplication.getContext()) * 2),
//                    screen_height / 2);
//            return d;
//        }
        if (imageHeight < screen_height) {
            d.setBounds(0, 0, screen_width - (SizeUtils.dp2px(21, BaseApplication.getContext()) * 2),
                    imageHeight);
        } else {
            d.setBounds(0, 0, screen_width - (SizeUtils.dp2px(21, BaseApplication.getContext()) * 2),
                    screen_height);
        }

        return d;

    }

    /**
     * fuction: 设置固定的宽度，高度随之变化，使图片不会变形
     *
     * @param target   需要转化bitmap参数
     * @param newWidth 设置新的宽度
     * @return
     */
    public static Bitmap fitBitmap(Bitmap target, int newWidth) {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        // float scaleHeight = ((float)newHeight) / height;
        int newHeight = (int) (scaleWidth * height);
        matrix.postScale(scaleWidth, scaleWidth);
        // Bitmap result = Bitmap.createBitmap(target,0,0,width,height,
        // matrix,true);
        Bitmap bmp = Bitmap.createBitmap(target, 0, 0, width, height, matrix,
                true);
        if (target != null && !target.equals(bmp) && !target.isRecycled()) {
            target.recycle();
            target = null;
        }
        return bmp;
    }

    /**
     * 根据宽高缩放图片
     *
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
//        int newWidth = 320;
//        int newHeight = 480;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true);
        if (bitmap != null && !bitmap.equals(newBm) && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return newBm;
    }


    /**
     * 用集合形式获取控件里的内容
     *
     * @param str
     * @return
     */
    public static List<String> getContentList(String str) {
        String content = str.replaceAll(Constant.mNewLineTag, "");
        List<String> mContentList = new ArrayList<String>();
        if (content.length() > 0 && content.contains(Constant.mBitmapTag)) {
            String[] split = content.split(Constant.mBitmapTag);
            for (String s : split) {
                mContentList.add(s);
            }
        } else {
            mContentList.add(content);
        }
        return mContentList;
    }

    /**
     * 云笔记图文标志
     *
     * @param mContentList
     * @return
     */
    public static String getContentFlag(List<String> mContentList) {
        String contentFlag = "";
        int text = 0, img = 0;
        if (mContentList.size() > 0) {
            for (String s : mContentList) {
                if (s.contains("<img") && s.contains("src=")) {
                    img = 1;
                } else {
                    if (!s.equals(""))
                        text = 1;
                }
            }
            if (text == 1 && img == 0) {// 1为文
                contentFlag = "1";
            } else if (text == 0 && img == 1) {// 2为图
                contentFlag = "2";
            } else if (text == 1 && img == 1) {// 3为图文
                contentFlag = "3";
            }
        }
        return contentFlag;
    }

    /**
     * 生成file文件并编写文件
     *
     * @param content
     * @param fileName
     * @return
     */
    public static File write(String content, String fileName) {
        // 获取 SD 卡根目录
        String saveDir = Environment.getExternalStorageDirectory()
                + "/cloud_note_file";
        // 新建目录
        File dir = new File(saveDir);

        // 删除目录
        FileUtils.deleteDir(saveDir);

        if (!dir.exists())
            dir.mkdir();
        // 新建文件
        File file = new File(saveDir, fileName);
        OutputStreamWriter write = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter out = new BufferedWriter(write);
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取笔记中图片列表名
     *
     * @param mContentList
     * @return
     */
    public static String getImagePath(List<String> mContentList) {
        ArrayList<String> picPathList = new ArrayList<String>();
        if (mContentList.size() > 0) {
            for (String str : mContentList) {
                if (str.contains("<img") && str.contains("src=")) {
                    str = str.replace("<img src=", "").replace(
                            "/>", "");
                    String[] split = str.split("/");
                    String picName = split[split.length - 1];
                    if (picName != null) {
                        picPathList.add(picName);
                    }
                }
            }
        }
        String picturePath = "";
        if (picPathList != null && picPathList.size() > 0) {
            picturePath = picPathList.toString();
            if (picturePath != "") {
                picturePath = picturePath.replace("[", "")
                        .replace("]", "").replace(" ", "");
            }

        }
        return picturePath;
    }

    /**
     * 获取系统时间,格式2016-01-20 13:05:58
     *
     * @return
     */
    public static String getCreateTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        return t1;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return ImageCompressL(bitmap);
    }


    /**
     * 计算 bitmap大小，如果超过235kb，则进行压缩
     */
    public static Bitmap ImageCompressL(Bitmap bitmap) {
        double targetWidth = Math.sqrt(335.00 * 1000);
        if (bitmap.getWidth() > targetWidth || bitmap.getHeight() > targetWidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetWidth / bitmap.getWidth(), targetWidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * 初始化标签圆形视图
     *
     * @param view
     * @return
     */
    public static GradientDrawable getColorView(View view) {
        GradientDrawable mGrad = (GradientDrawable) view.getBackground();
        return mGrad;
    }

    /**
     * 转换颜色值
     *
     * @param color
     * @return
     */
    public static int getColorValue(String color) {
        int color1;
        if (checkColorValue(color)) {
            color = "#" + color;
            color1 = Color.parseColor(color);
        } else {
            color1 = Color.parseColor("#FF3220"); // 默认颜色值
        }
        return color1;
    }

    /**
     * 六位十六进制字符串正则表达式
     *
     * @param color
     * @return
     */
    public static boolean checkColorValue(String color) {
        boolean flag = false;
        String color1 = "[a-f0-9A-F]{6}";
        if (Pattern.matches(color1, color)) {
            // 匹配成功
            flag = true;
        }
        return flag;
    }

    /**
     * 生成随机颜色值（测试数据）
     *
     * @return
     */
    public static String getColor() {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length() == 1 ? "0" + red : red;
        //判断绿色代码的位数
        green = green.length() == 1 ? "0" + green : green;
        //判断蓝色代码的位数
        blue = blue.length() == 1 ? "0" + blue : blue;
        //生成十六进制颜色值
        String color = red + green + blue;

        return color;
    }

    /**
     * 读取文件内容
     *
     * @param strFilePath
     * @return
     */
    public static String readTextFile(String strFilePath) {
        String path = strFilePath;
        // 文件内容
        String content = "";
        // 打开文件
        File file = new File(path);
        // 如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            LoggerHelper.d("CloudNoteUtils", "The File doesn't not exist.");

        } else {
            try {
                InputStream inputStream = new FileInputStream(file);
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    // 分行读取
                    while ((line = bufferedReader.readLine()) != null) {
                        content += line + "\n";
                    }
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                LoggerHelper.d("CloudNoteUtils", "The File doesn't not exist.");
            } catch (IOException e) {
                LoggerHelper.d("CloudNoteUtils", e.getMessage());
            }
        }
        return content;
    }

    /**
     * 下载文件并存入本地磁盘
     *
     * @param body
     * @param mFilePath
     * @return
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, String mFilePath) {
        try {
            // todo change the file location/name according to your needs
//            mFilePath = Environment.getExternalStorageDirectory()
//                    + "/cloud_note_file" + "/" + mFilePath;
            File futureStudioIconFile = new File(mFilePath);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 下载文本文件，直接返回字符串
     *
     * @return 返回文本文件的字符串
     */
    public static String downTxt(ResponseBody body) {
        //创建StringBuffer 对象存放转换后的字符串
        StringBuffer sBuffer = new StringBuffer();
        //创建临时的String变量，临时存放文本文件的每一行字符串
        String line = null;
        //BufferedReader有个可以一次读取一行内容的方法readLine()。
        //所以创建ufferedReader对象存放下载的文本内容
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            //把Get_inputStream()方法返回的是字节流，转换成InputStreamReader类的字符流。
            //由于字节与字符流不好操作所以转换成BufferedReader
            //然后使用它的eadLine()方法一次读取一行内容。
            inputStream = getInputStream(body);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //循环读取每一行的内容赋值给StringBuffer 对象。
            while ((line = bufferedReader.readLine()) != null) {
                sBuffer.append(line);
            }
        } catch (Exception e) {
            // TODO: handleexception
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                // TODO:handle exception
                e.printStackTrace();
            }
        }
        return sBuffer.toString();
    }

    public static InputStream getInputStream(ResponseBody body) {
        InputStream inputStream = null;
        inputStream = body.byteStream();
        return inputStream;
    }

    /**
     * 字符串中的空格、回车、换行符、制表符
     *
     * @param s
     * @return
     */
    public static String replaceBlank(String s) {
        String dest = "";
        if (s != null) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p = Pattern.compile("\n");
            Matcher m = p.matcher(s);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 积分商城返回轮播图网址集合
     *
     * @param s 以,分割的参数字符串
     * @return
     */
    public static List<String> getParams(String s) {
        String[] split = null;
        split = s.split(",");
        return Arrays.asList(split);
    }

}
