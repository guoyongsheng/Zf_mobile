package com.zfsoftmh.common.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.entity.ImageCompressInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;

/**
 * @author wesley
 * @date: 2017/5/5
 * @Description: 图片压缩的工具类
 */

public class ImageCompressUtils {


    private static final java.lang.String TAG = "ImageCompressUtils";

    private ImageCompressUtils() {

    }

    /**
     * 压缩图片
     *
     * @param context 上下文对象
     * @param file    文件
     */
    public static void compressToFile(Context context, File file, final CallBackListener<ImageCompressInfo> listener) {
        if (context == null || file == null || listener == null) {
            LoggerHelper.e(TAG, " compressImage " + " context = " + context + " file = " + file + " listener = " + listener);
            return;
        }

        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        Tiny.getInstance().source(file).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
            @Override
            public void callback(boolean isSuccess, Bitmap bitmap, String outfile) {
                if (isSuccess) {
                    ImageCompressInfo bean = new ImageCompressInfo(outfile,bitmap);
                    listener.onSuccess(bean);

                } else {
                    listener.onError(Constant.image_compress_failure);
                }
            }
        });
    }
}
