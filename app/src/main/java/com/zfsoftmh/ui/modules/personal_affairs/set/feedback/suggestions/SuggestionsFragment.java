package com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zfsoftmh.BuildConfig;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.AppUtils;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.FileUtils;
import com.zfsoftmh.common.utils.ImageCompressUtils;
import com.zfsoftmh.common.utils.ImageUtil;
import com.zfsoftmh.entity.ImageCompressInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.common.home.mine.MineBottomSheetDialogFragment;
import com.zxy.tiny.core.FileKit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangshimei
 * @date: 17/6/6
 * @Description: 我要吐槽
 */

public class SuggestionsFragment extends BaseFragment<SuggestionsPresenter> implements
        SuggestionsContract.View, ZzImageBox.OnImageClickListener,
        MineBottomSheetDialogFragment.OnViewClickListener,
        EasyPermissions.PermissionCallbacks {

    private EditText comments_et; // 问题与意见填写框
    private EditText tel_et; // 联系电话
    private EditText qq_et; // 联系QQ
    private ZzImageBox zz_image_box; // 添加图片编辑器
    private AppSettingsDialog dialog;

    private static final String BOTTOM_SHEET_DIALOG_FRAGMENT = "BOTTOM_SHEET_DIALOG_FRAGMENT";
    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2; //拍照时的请求码
    private static final int REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS = 3; //从相册中选取的权限
    private static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 3; //从相册中选取的请求码
    private static final int REQUEST_CODE_TAKE_CAMERA = 4; //拍照

    private String mFilePath, app_token; // 拍照图片存储路径
    private List<String> allImages;

    public static SuggestionsFragment newInstance() {
        return new SuggestionsFragment();
    }

    @Override
    protected void initVariables() {
        app_token = DbHelper.getAppToken(getActivity());
        getImageFromCameraPath();// 拍照图片存储路径
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_suggestions;
    }

    @Override
    protected void initViews(View view) {
        comments_et = (EditText) view.findViewById(R.id.comments_et);
        tel_et = (EditText) view.findViewById(R.id.tel_et);
        qq_et = (EditText) view.findViewById(R.id.qq_et);
        zz_image_box = (ZzImageBox) view.findViewById(R.id.zz_image_box);
    }

    @Override
    protected void initListener() {
        zz_image_box.setOnImageClickListener(this);
    }

    /**
     * 图片点击
     *
     * @param position
     * @param filePath
     * @param iv
     */
    @Override
    public void onImageClick(int position, String filePath, ImageView iv) {

    }

    /**
     * 删除图片
     *
     * @param position
     * @param filePath
     */
    @Override
    public void onDeleteClick(int position, String filePath) {
        zz_image_box.removeImage(position);
    }

    /**
     * 添加图片
     */
    @Override
    public void onAddClick() {
        createAddPicDialog();
    }

    /**
     * 6.0版本动态请求权限
     */
    @Override
    public void createAppSettingDialog() {
        if (dialog == null) {
            dialog = new AppSettingsDialog
                    .Builder(this)
                    .setTitle(R.string.request_permissions)
                    .setRationale(R.string.permissions_rationale)
                    .setPositiveButton(R.string.Ok)
                    .setNegativeButton(R.string.cancel)
                    .build();
        }
        dialog.show();
    }

    /**
     * 创建插入图片底部视图
     */
    @Override
    public void createAddPicDialog() {
        MineBottomSheetDialogFragment fragment = MineBottomSheetDialogFragment.newInstance();
        fragment.setOnViewClickListener(this);
        fragment.show(getChildFragmentManager(), BOTTOM_SHEET_DIALOG_FRAGMENT);
    }

    /**
     * 本地相册选取请求
     */
    @Override
    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_FROM_ALBUM);
    }

    /**
     * 拍照请求
     */
    @Override
    public void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 指定拍照
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File file = new File(mFilePath);
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
                uri = Uri.fromFile(file);// 加载路径
            }

            // 指定存储路径，这样就可以保存原图了
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // 拍照返回图片
            startActivityForResult(intent, REQUEST_CODE_TAKE_CAMERA);
        } else {
            showToastMsgShort(getResources().getString(R.string.please_insert_sdcard));
        }
    }

    /**
     * 拍照后图片存储
     */
    @Override
    public void getImageFromCameraPath() {
        mFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + "SuggestionsPhoto.png";
    }

    /**
     * 压缩图片
     *
     * @param file
     */
    @Override
    public void getCompressImage(File file) {
        ImageCompressUtils.compressToFile(context, file, new CallBackListener<ImageCompressInfo>() {
            @Override
            public void onSuccess(ImageCompressInfo data) {
                if (data != null) {
                    if (data.bitmap != null) {
                        if (data.outfile != null && !data.outfile.equals("")) {
                            // 添加压缩之后的图片路径
                            zz_image_box.addImage(data.outfile);
                        }
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                showToastMsgShort(Constant.image_compress_failure);
            }
        });
    }

    /**
     * 上传加载框
     *
     * @param upLoad 上传信息
     */
    @Override
    public void createUpLoadPicDialog(String upLoad) {
        showProgressDialog(upLoad);
    }

    @Override
    public void hideUpLoadPicDialog() {
        hideProgressDialog();
    }

    @Override
    public void upLoadFailure(String errMsg) {
        showToastMsgShort(errMsg);
    }

    /**
     * 删除拍照图片
     */
    @Override
    public void deleteCameraPic() {
        File myCaptureFile = new File(mFilePath);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
    }

    @Override
    public void skipPage() {

    }

    /**
     * 获取问题与意见内容
     *
     * @return
     */
    @Override
    public String getComments() {
        return comments_et.getText().toString();
    }

    /**
     * 获取联系电话
     *
     * @return
     */
    @Override
    public String getTel() {
        return tel_et.getText().toString();
    }

    /**
     * 获取QQ
     *
     * @return
     */
    @Override
    public String getQQ() {
        return qq_et.getText().toString();
    }

    /**
     * 提交反馈内容
     */
    @Override
    public void commitSuggestions() {

        if (!TextUtils.isEmpty(getComments())) {
            String ver = AppUtils.getAppVersionName(context); //app的版本号
            String[] split = ver.split("-");
            String schoolCode = "", versionNumber = "", userName = "", telephone = "", qq = "", textContent = "";
            schoolCode = split[0]; // 学校编码
            versionNumber = split[split.length - 1]; // 版本号
            userName = DbHelper.getUserId(context); // 用户名
            if (getTel() != null) {
                telephone = getTel(); // 手机号
            }
            if (getQQ() != null) {
                qq = getQQ(); // QQ
            }

            textContent = getComments();
            allImages = zz_image_box.getAllImages();
            ArrayList<File> files = new ArrayList<File>();
            if (allImages != null) {
                for (int i = 0; i < allImages.size(); i++) {
                    String path = null;
                    File file = null;
                    path = allImages.get(i).toString();
                    file = new File(path);
                    files.add(file);
                }
            }

            // 图片集合
            List<MultipartBody.Part> images = new ArrayList<>();
            int count = 0;
            for (File file : files) {
                images.add(getFilePart("file" + count, file, "file" + count + ".jpg"));
                count++;
            }
            // 字符串参数集合
            Map<String, RequestBody> params = new LinkedHashMap<>();
            params.put("schoolCode", getRequestBody(CodeUtil.getEncodedValueWithToken(schoolCode, app_token)));
            params.put("versionNumber", getRequestBody(CodeUtil.getEncodedValueWithToken(versionNumber, app_token)));
            params.put("username", getRequestBody(CodeUtil.getEncodedValueWithToken(userName, app_token)));
            params.put("telephone", getRequestBody(CodeUtil.getEncodedValueWithToken(telephone, app_token)));
            params.put("qq", getRequestBody(CodeUtil.getEncodedValueWithToken(qq, app_token)));
            params.put("apptoken", getRequestBody(app_token));
            params.put("textContent", getRequestBody(textContent));

            presenter.uploadSuggestions(params, images);
        } else {
            showToastMsgShort(Constant.suggestions_must_make_out);
        }
    }

    public RequestBody getRequestBody(String s) {
        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), s);
    }

    @Override
    public MultipartBody.Part getFilePart(String partName, File file, String fileName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file);
        return MultipartBody.Part.createFormData(partName, fileName, requestBody);
    }

    /**
     * 插入图片底部视图从相册中选取监听
     */
    @Override
    public void onSelectFromAlbumClick() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_select_from_album),
                REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 插入图片底部视图拍照事件监听
     */
    @Override
    public void onTakePicturesClick() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_camera),
                REQUEST_CODE_CAMERA_PERMISSIONS, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    /**
     * 动态权限请求失败回调方法
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            createAppSettingDialog();
        }
    }

    /**
     * 动态权限请求成功回调方法
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            /*
             * 从相册中选取
             */
            case REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS:
                getImageFromAlbum();
                break;

            /*
             * 拍照
             */
            case REQUEST_CODE_CAMERA_PERMISSIONS:
                getImageFromCamera();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            /*
             * 从相册中选取
             */
            case REQUEST_CODE_IMAGE_FROM_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    String filePath = ImageUtil.getImagePath(context, uri);
                    if (filePath != null) {
                        File file = new File(filePath);
                        getCompressImage(file);
                    }
                }

                break;
            /*
             * 拍照
             */
            case REQUEST_CODE_TAKE_CAMERA:
                File file = new File(mFilePath);
                getCompressImage(file);
                break;

        }
    }

    /**
     * 删除本地压缩图片
     */
    private void deleteCompressPictures() {
        File file2 = FileKit.getDefaultFileCompressDirectory();
        FileUtils.deleteDirWithFile(file2);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 删除压缩图片
        deleteCompressPictures();
    }
}
