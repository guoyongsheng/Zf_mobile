package com.zfsoftmh.ui.modules.chatting.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.text.TextUtils;

import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.channel.YWEnum;
import com.alibaba.mobileim.channel.helper.ImageMsgPacker;
import com.alibaba.mobileim.channel.upload.UploadManager;
import com.alibaba.mobileim.channel.util.FileUtils;
import com.alibaba.mobileim.channel.util.WXUtil;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.utility.IMFileTools;
import com.alibaba.mobileim.utility.IMImageCache;
import com.alibaba.mobileim.utility.IMImageUtils;
import com.alibaba.mobileim.utility.IMThumbnailUtils;
import com.alibaba.wxlib.config.StorageConstant;
import com.alibaba.wxlib.util.WXFileTools;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.FileInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.PersonalAffairsApi;
import com.zfsoftmh.ui.base.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sy
 * on 2017/7/21.
 * <p></p>
 */

public class ImageMsgHelper {

    public YWMessage getMessage(Context mContext, String originPicPath) {
        int mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        int mHeightPixels = (int) (mContext.getResources().getDisplayMetrics().heightPixels
                - 32.0F * mContext.getResources().getDisplayMetrics().density);
        File dir = new File(StorageConstant.getFilePath());
        dir.mkdirs();
        File picOriginFile = new File(originPicPath);
        int fileSize = 0;
        if ((picOriginFile.exists()) && (picOriginFile.isFile()))
            fileSize = (int) picOriginFile.length();
        if (fileSize == 0) return null;
        YWMessage message;
        message = handleGifPic(originPicPath, fileSize);
        if (message != null) {
            return message;
        }
        String savePath = StorageConstant.getFilePath() + File.separator + WXUtil.getMD5FileName(UUID.randomUUID().toString());
        int ori = IMImageUtils.getOrientation(originPicPath, BaseApplication.getContext(), null);
        Bitmap origin = IMThumbnailUtils.compressFileAndRotateToBitmapThumb(originPicPath, mWidthPixels, mHeightPixels, ori, savePath, false, false);
        if (origin == null) return null;
        String compressedPath = savePath + "_comp";
        ImageMsgPacker imageMsgPacker = new ImageMsgPacker(YWChannel.getApplication());
        int mMaxHeight = imageMsgPacker.getMaxNeedServerToGiveThumnailHeight();
        int mMinWidth = imageMsgPacker.getMinWidth();
        int[] resizedDimension = IMThumbnailUtils.getResizedDimensionOfThumbnail(origin.getWidth(), origin.getHeight(), mMinWidth, mMaxHeight);
        Bitmap scalebBitmap = IMThumbnailUtils.getCropAndScaledBitmap(origin, resizedDimension[0], resizedDimension[1], resizedDimension[2], resizedDimension[3], false);
        if (scalebBitmap == null) return null;
        String imageType = "jpg";
        imageType = getImageType(originPicPath, picOriginFile, imageType);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(compressedPath);
            if (out != null)
                if (imageType.equals("jpg"))
                    scalebBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                else if (imageType.equals("png"))
                    scalebBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (FileNotFoundException e) {
            WxLog.w("PicSendThread", "run", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        IMImageCache wxImageCache = IMImageCache.findOrCreateCache(mContext, StorageConstant.getFilePath());
//            scalebBitmap = IMImageUtils.getAndCacheChattingBitmap(compressedPath, scalebBitmap, false, 0.0f, this, wxImageCache, true);
        Rect oriRect = new Rect();
        oriRect.set(0, 0, origin.getWidth(), origin.getHeight());
        Rect compRect = new Rect();
        compRect.set(0, 0, scalebBitmap.getWidth(), scalebBitmap.getHeight());
        savePicToSavePath(originPicPath, savePath, origin);
        message = YWMessageChannel.createImageMessage(originPicPath, compressedPath, oriRect.width(), oriRect.height(), fileSize, imageType, YWEnum.SendImageResolutionType.BIG_IMAGE);
        return message;
    }

    private void savePicToSavePath(String originPicPath, String savePath, Bitmap origin){
        if (origin != null)
            try {
                saveToSavePath(originPicPath, savePath, true, origin);
            } catch (IOException e) {
                e.printStackTrace();
                WxLog.e("PicSendThread" + UploadManager.TAG, e.getMessage());
            }
    }

    private void saveToSavePath(String originPath, String savePath,
                                boolean writeCompressedBitmapBackToFile, Bitmap bm) throws IOException{
        IMFileTools.deleteFile(savePath);
        if (writeCompressedBitmapBackToFile)
            IMFileTools.writeBitmap(savePath, bm, 90);
        else
            WXFileTools.copyFileFast(new File(originPath), new File(savePath));
    }


    private String getImageType(String originPicPath, File picOriginFile, String imageType) {
        if ((picOriginFile.isFile()) && (picOriginFile.exists())){
            if ((picOriginFile.exists()) && (picOriginFile.isFile())) {
                BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
                decodeOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(originPicPath, decodeOptions);
                if ((!TextUtils.isEmpty(decodeOptions.outMimeType))
                        && ((decodeOptions.outMimeType.contains("png"))
                        || (decodeOptions.outMimeType.contains("PNG")))) {
                    imageType = "png";
                }
            }
        }
        return imageType;
    }

    private YWMessage handleGifPic(String originPicPath, int fileSize){
        if (originPicPath.endsWith(".gif")) {
            String originPath = StorageConstant.getFilePath() + File.separator + WXUtil.getMD5FileName(UUID.randomUUID().toString());
            FileUtils.copyFile(new File(originPicPath), new File(originPath));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originPicPath, options);
            return YWMessageChannel.createGifImageMessage(originPath, originPath, options.outWidth, options.outHeight, fileSize);
        }
        return null;
    }

    public void uploadFile(FileInfo info, CallBackListener<ArrayList<String>> listener) {
        HttpManager httpManager = BaseApplication.getAppComponent().getHttpHelper();
        PersonalAffairsApi affairsApi = BaseApplication.getAppComponent().getRetrofitForLogin()
                .create(PersonalAffairsApi.class);
        httpManager.outRequest(affairsApi.postMediaFile(
                getRequestBody(DbHelper.getAppToken(BaseApplication.getContext())),
                getFilePart(new File(info.getFilePath()), info.getFileName())), listener);
    }

    private RequestBody getRequestBody(String s) {
        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), s);
    }

    private MultipartBody.Part getFilePart(File file, String fileName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file);
        return MultipartBody.Part.createFormData("file", fileName, requestBody);
    }

}
