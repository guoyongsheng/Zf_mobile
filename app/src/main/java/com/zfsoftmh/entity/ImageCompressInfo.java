package com.zfsoftmh.entity;

import android.graphics.Bitmap;

/**
 * @author wangshimei
 * @date: 17/5/18
 * @Description:
 */

public class ImageCompressInfo {
    public String outfile; // 压缩文件
    public Bitmap bitmap; // 压缩图片

    public ImageCompressInfo() {
    }

    public ImageCompressInfo(String outfile, Bitmap bitmap) {
        this.outfile = outfile;
        this.bitmap = bitmap;
    }
}
