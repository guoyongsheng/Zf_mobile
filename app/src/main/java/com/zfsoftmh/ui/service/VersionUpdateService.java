package com.zfsoftmh.ui.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.zfsoftmh.BuildConfig;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.AppUtils;

import java.io.File;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/22
 * @Description: 服务 --- 用于版本跟新
 */

public class VersionUpdateService extends Service {

    private BroadcastReceiver receiver;
    private static final String APK_NAME = "zf_mobile.apk";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return Service.START_NOT_STICKY;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return Service.START_NOT_STICKY;
        }
        String url = bundle.getString("url");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                unregisterReceiver(receiver);
                intent = new Intent(Intent.ACTION_VIEW);
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), APK_NAME);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                    List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, uri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                } else {
                    uri = Uri.fromFile(file);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                startActivity(intent);
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload(url);
        return Service.START_STICKY;
    }

    //开始下载apk
    private void startDownload(String url) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(AppUtils.getAppName(this));
        request.setDescription(getResources().getString(R.string.down_load_apk));
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, APK_NAME);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.enqueue(request);
    }
}
