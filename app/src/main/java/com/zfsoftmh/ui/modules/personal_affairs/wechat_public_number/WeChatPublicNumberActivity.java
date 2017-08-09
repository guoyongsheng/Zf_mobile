package com.zfsoftmh.ui.modules.personal_affairs.wechat_public_number;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 微信公众好界面
 */

public class WeChatPublicNumberActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = "WeChatPublicNumberActivity";
    private final static int REQUEST_CODE = 1;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.app_name);
        setDisplayHomeAsUpEnabled(true);

        requestPermission();
    }


    private void requestPermission() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_select_from_album),
                REQUEST_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    private void selectFileFromDisk() {
        final File file = android.os.Environment.getExternalStorageDirectory();
        Observable.just(file)
                .map(new Function<File, List<File>>() {
                    @Override
                    public List<File> apply(@io.reactivex.annotations.NonNull File file) throws Exception {

                        return getListFile(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<File> files) throws Exception {
                        int length = files.size();
                        for(int i = 0; i < length; i++){
                            LoggerHelper.e(TAG, " file name = " + files.get(i).getAbsolutePath());
                        }
                    }
                });
    }

    private String[] fileSuffix = new String[]{"doc", "docx", "dot", "xls", "pdf", "ppt", "pptx", "txt"};

    private boolean checkSuffix(String fileName) {
        for (String suffix : fileSuffix) {
            if (fileName != null) {
                if (fileName.toLowerCase().endsWith(suffix)) {
                    return true;
                }
            }
        }
        return false;
    }


    List<File> list = new ArrayList<>();
    private List<File> getListFile(File file) {

        if (file != null && file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) { //文件夹里面存在文件或者文件夹
                for (File f : files) {
                    if (f.isDirectory()) {
                        getListFile(f);
                    } else if (f.exists() && f.canRead() && checkSuffix(f.getName())) {
                       list.add(f);
                    }
                }
            }
        }
        return list;
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
        case REQUEST_CODE:
            selectFileFromDisk();
            break;


        default:
            break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
