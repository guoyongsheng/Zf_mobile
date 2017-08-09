package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeUtils;
import com.zfsoftmh.BuildConfig;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.config.ServiceCodeConfig;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.FileUtils;
import com.zfsoftmh.common.utils.ImageCompressUtils;
import com.zfsoftmh.common.utils.ImageUtil;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.PerformEdit;
import com.zfsoftmh.entity.ImageCompressInfo;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.common.home.mine.MineBottomSheetDialogFragment;
import com.zfsoftmh.ui.modules.office_affairs.cloud_notes.CloudNoteListActivity;
import com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label.NoteLabelActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;
import com.zxy.tiny.core.FileKit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.alibaba.mobileim.gingko.presenter.contact.cache.ContactsCache.getUserId;

/**
 * 创建时间： 2017/5/2 0002
 * 编写人： 王世美
 * 功能描述： 新建云笔记
 */

public class AddCloudNoteFragment extends BaseFragment<AddCloudNotePresenter> implements
        AddCloudNoteContract.View, MineBottomSheetDialogFragment.OnViewClickListener, EasyPermissions.PermissionCallbacks {

    private EditText add_cloud_note_title_et; // 笔记标题
    private LinearLayout add_note_label_layout; // 笔记标签类别视图
    private View note_label_view; // 标签颜色视图
    private TextView note_label_name_tv; // 标签类别名
    private EditText add_cloud_note_content_et; // 笔记内容
    private ImageView cloud_note_delete_iv, add_pic_iv, cloud_note_repeal_iv; // 一键删除，添加图片，撤销按钮
    private ScrollView share_note_content_sll; // 笔记内容视图
    private AppSettingsDialog dialog;
    private ProgressDialog shareDialog; // 分享对话框

    private PerformEdit mPerformEdit;
    private String mFilePath, noteFileName, userId, app_token; // 拍照存储地址，笔记文件名,当前用户ID
    private int screen_width, screen_height; // 屏幕宽度
    private SimpleDateFormat t;
    private static final String BOTTOM_SHEET_DIALOG_FRAGMENT = "BOTTOM_SHEET_DIALOG_FRAGMENT";
    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2; //拍照时的请求码
    private static final int REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS = 3; //从相册中选取的权限
    private static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 3; //从相册中选取的请求码
    private static final int REQUEST_CODE_TAKE_CAMERA = 4; //拍照
    private static final int REQUEST_CODE_LABEL = 5; //标签请求码

    private SHARE_MEDIA share_media1;
    private static final int REQUEST_CODE_PERMISSIONS = 1; //分享授权的请求码
    private static final String TAG = "AddCloudNoteFragment";
    private String memoCatalogId = "";

    private Handler mHandler = new Handler();
    private NoteLabelItemInfo labelInfo;
    private String picFileName, saveDir;

    public static AddCloudNoteFragment newInstance() {
        return new AddCloudNoteFragment();
    }


    @Override
    protected void initVariables() {
        app_token = DbHelper.getAppToken(getActivity());
        labelInfo = DbHelper.getValueBySharedPreferences(getActivity(), "NoteLabelItemInfo", "NoteLabelItemInfo", NoteLabelItemInfo.class);
        if (labelInfo != null)
            memoCatalogId = labelInfo.memoCatalogId;
        // 笔记保存目录
        saveDir = Environment.getExternalStorageDirectory()
                + "/cloud_note_file";
        getImageFromCameraPath();
        getScreenWidth();
        getNoteFileName();
        shareDialog = new ProgressDialog(getActivity());
        shareDialog.setMessage(getResources().getString(R.string.sharing));
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.fragment_add_cloud_note;
    }

    @Override
    protected void initViews(View view) {
        note_label_view = view.findViewById(R.id.note_label_view);
        CloudNoteUtils.getColorView(note_label_view).setColor(
                CloudNoteUtils.getColorValue("a2a2a2"));
        add_pic_iv = (ImageView) view.findViewById(R.id.add_pic_iv);
        note_label_name_tv = (TextView) view.findViewById(R.id.note_label_name_tv);
        cloud_note_delete_iv = (ImageView) view.findViewById(R.id.cloud_note_delete_iv);
        cloud_note_repeal_iv = (ImageView) view.findViewById(R.id.cloud_note_repeal_iv);
        share_note_content_sll = (ScrollView) view.findViewById(R.id.share_note_content_sll);
        add_cloud_note_title_et = (EditText) view.findViewById(R.id.add_cloud_note_title_et);
        add_note_label_layout = (LinearLayout) view.findViewById(R.id.add_note_label_layout);
        add_cloud_note_content_et = (EditText) view.findViewById(R.id.add_cloud_note_content_et);
        mPerformEdit = new PerformEdit(add_cloud_note_content_et, getActivity());
        repealIconShow();
    }

    @Override
    protected void initListener() {
        add_pic_iv.setOnClickListener(onceClickListener);
        cloud_note_delete_iv.setOnClickListener(onceClickListener);
        cloud_note_repeal_iv.setOnClickListener(onceClickListener);
        add_note_label_layout.setOnClickListener(onceClickListener);
    }

    /**
     * 单次监听事件
     */
    private OnceClickListener onceClickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            if (v == null) {
                return;
            }

            int key = v.getId();
            switch (key) {
                /**
                 * 添加图片
                 */
                case R.id.add_pic_iv:
                    createAddPicDialog();
                    break;

                /**
                 * 标签视图
                 */
                case R.id.add_note_label_layout:
                    Intent labelIntent = new Intent(getActivity(), NoteLabelActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "add");
                    labelIntent.putExtras(bundle);
                    startActivityForResult(labelIntent, REQUEST_CODE_LABEL);
                    break;

                /**
                 * 一键删除
                 */
                case R.id.cloud_note_delete_iv:
                    mPerformEdit.clearHistory();
                    add_cloud_note_content_et.setText("");
                    break;

                /**
                 * 撤销
                 */
                case R.id.cloud_note_repeal_iv:
                    mPerformEdit.undo();
                    break;
            }
        }
    };

    /*
  * 撤销以及添加图片按钮可见于不可见
  */
    @Override
    public void repealIconShow() {
        add_cloud_note_content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    cloud_note_repeal_iv.setVisibility(View.GONE);
                } else {
                    cloud_note_repeal_iv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        add_cloud_note_content_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (TextUtils.isEmpty(getNoteContent()) && hasFocus
                        && cloud_note_repeal_iv.getVisibility() == View.GONE) {
                    cloud_note_repeal_iv.setVisibility(View.VISIBLE);
                } else {
                    cloud_note_repeal_iv.setVisibility(View.GONE);
                }
            }
        });
        add_cloud_note_title_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    add_pic_iv.setVisibility(View.GONE);
                } else {
                    add_pic_iv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /*
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

    /*
     * 获取新建笔记标题
     *
     * @return
     */
    @Override
    public String getNoteTitle() {
        return add_cloud_note_title_et.getText().toString();
    }

    /*
     * 获取新建笔记内容
     *
     * @return
     */
    @Override
    public String getNoteContent() {
        return add_cloud_note_content_et.getText().toString();
    }

    /*
     * 创建插入图片底部视图
     */
    @Override
    public void createAddPicDialog() {
        MineBottomSheetDialogFragment fragment = MineBottomSheetDialogFragment.newInstance();
        fragment.setOnViewClickListener(this);
        fragment.show(getChildFragmentManager(), BOTTOM_SHEET_DIALOG_FRAGMENT);
    }

    /*
     * 本地相册选取请求
     */
    @Override
    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_FROM_ALBUM);
    }

    /*
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

    /*
     * 拍照后图片存储
     */
    @Override
    public void getImageFromCameraPath() {
        mFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + "CloudNotePhoto.png";
    }

    /*
     * 获取压缩后图片
     *
     * @param file
     */
    @Override
    public void getCompressImage(File file) {

        // 图片压缩
        ImageCompressUtils.compressToFile(context, file, new CallBackListener<ImageCompressInfo>() {
            @Override
            public void onSuccess(ImageCompressInfo data) {
                if (data != null) {
                    if (data.bitmap != null) {
                        if (data.outfile != null && !data.outfile.equals("")) {
                            File file1 = new File(data.outfile);
                            uploadPic(file1, data.bitmap);
//                            insertViewPic(data.bitmap, data.outfile);

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

    /*
     * 插入图片底部视图从相册中选取监听
     */
    @Override
    public void onSelectFromAlbumClick() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_select_from_album),
                REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    /*
     * 插入图片底部视图拍照事件监听
     */
    @Override
    public void onTakePicturesClick() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_camera),
                REQUEST_CODE_CAMERA_PERMISSIONS, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
            /*
             * 分享授权
             */
            case REQUEST_CODE_PERMISSIONS:
                share(share_media1);
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

            /**
             * 设置标签
             */
            case REQUEST_CODE_LABEL:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    NoteLabelItemInfo labelInfo = bundle.getParcelable("label");
                    if (labelInfo != null) {
                        memoCatalogId = labelInfo.memoCatalogId;
                        note_label_name_tv.setText(labelInfo.memoCatalogName);
                        CloudNoteUtils.getColorView(note_label_view)
                                .setColor(CloudNoteUtils.getColorValue(labelInfo.catalogColor));
                    }
                }
                break;
        }
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    /*
     * 上传图片加载框
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

    /*
     * 上传图片
     *
     * @param file
     * @param bitmap
     */
    @Override
    public void uploadPic(File file, Bitmap bitmap) {
        picFileName = noteFileName.replace(".rtfd", "") + "_"
                + (t.format(new Date())) + ".jpg";

        presenter.uploadPicture(getFilePart(picFileName, file, picFileName), bitmap, getRequestBody(app_token));
    }

    @Override
    public MultipartBody.Part getFilePart(String partName, File file, String fileName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file);
        return MultipartBody.Part.createFormData(partName, fileName, requestBody);
    }

    /*
     * 上传笔记
     */
    @Override
    public void uploadNote() {
        if (getNoteContent() != null && !getNoteContent().equals("")) {
            List<String> picPathList = new ArrayList<String>();
            picPathList = CloudNoteUtils.getContentList(getNoteContent());
            String memoTitle = "", createTime = "", memoPath = "", picturePath = "", contentFlag = "";
            memoTitle = getNoteTitle();
            if (memoTitle == null || memoTitle.equals("")) {
                memoTitle = Constant.default_cloud_note_title;
            }
            File file = null;
            file = CloudNoteUtils.write(getNoteContent(), noteFileName);
            createTime = CloudNoteUtils.getCreateTime();
            memoPath = file.getPath();
            picturePath = CloudNoteUtils.getImagePath(picPathList);
            contentFlag = CloudNoteUtils.getContentFlag(picPathList);
            Map<String, RequestBody> params = new LinkedHashMap<>();
            params.put("createTime", getRequestBody(CodeUtil.getEncodedValueWithToken(createTime, app_token)));
            params.put("memoTitle", getRequestBody(CodeUtil.getEncodedValueWithToken(memoTitle, app_token)));
            params.put("contentFlag", getRequestBody(CodeUtil.getEncodedValueWithToken(contentFlag, app_token)));
            params.put("memoCatalogId ", getRequestBody(CodeUtil.getEncodedValueWithToken(memoCatalogId, app_token)));
            params.put("username", getRequestBody(CodeUtil.getEncodedValueWithToken(userId, app_token)));
//            params.put("memoFileName ", noteFileName);
            params.put("apptoken", getRequestBody(app_token));

            params.put("picturePath", getRequestBody(CodeUtil.getEncodedValueWithToken(picturePath, app_token)));
            if (file != null) {
                presenter.uploadCloudNote(getFilePart(noteFileName, file, noteFileName), params);
            }
        } else {
            upLoadFailure(Constant.please_input_note_content);
        }
    }

    public RequestBody getRequestBody(String s) {
        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), s);
    }

    /*
     * 获取笔记文件名
     */
    @Override
    public void getNoteFileName() {
        userId = DbHelper.getUserId(context);
        t = new SimpleDateFormat("yyyyMMddssSSS");
        noteFileName = userId + (t.format(new Date())) + ".rtfd";
    }

    @Override
    public void upLoadFailure(String errMsg) {
        showToastMsgShort(errMsg);
    }

    /*
     * 向EditText视图插入图片显示
     */
    @Override
    public void insertViewPic(Bitmap bitmap, String picUrl) {
        // 获取光标所在位置
        int index = add_cloud_note_content_et.getSelectionStart();
        Editable editable = add_cloud_note_content_et.getEditableText();
        // 插入换行符，使图片单独占一行
        SpannableString newLine = new SpannableString("\n");
        // 插入图片前换行
        editable.insert(index, newLine);

        Drawable drawable = CloudNoteUtils.getDrawable(screen_width, screen_height, bitmap);

        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(drawable);
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        String s = "<img src=" + picUrl + "/>";
        String tempUrl = Constant.mBitmapTag + s + Constant.mBitmapTag;
        SpannableString spannableString = new SpannableString(tempUrl);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, tempUrl.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在的位置
        if (index < 0 || index >= editable.length()) {
            editable.append(spannableString);
        } else {
            editable.insert(index, spannableString);
        }
        // 为了不让图片挤在一起，我们在图片后面加上一个换行
        editable.insert(index, newLine);
        System.out.println("插入的图片：" + spannableString.toString());
    }

    /*
     * 删除拍照图片
     */
    @Override
    public void deleteCameraPic() {
        File myCaptureFile = new File(mFilePath);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
    }

    /**
     * 删除本地压缩图片
     */
    private void deleteCompressPictures() {
        File file2 = FileKit.getDefaultFileCompressDirectory();
        FileUtils.deleteDirWithFile(file2);
    }

    /*
     * 删除本地新建笔记
     */
    @Override
    public void deleteCloudNote() {
        File file = new File(noteFileName);
        if (file.exists()) {
            file.delete();
        }
    }


    @Override
    public void skipPage() {
        if (uploadListener != null) {
            uploadListener.success();
        }
    }

    /*
     * 布局生成图片
     */
    @Override
    public void generateImg() {
        if (getNoteContent() != null && !getNoteContent().equals("")) {
            createUpLoadPicDialog(Constant.note_loading);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = CloudNoteUtils.getBitmapByView(share_note_content_sll);
                    savePicture(bitmap, "noteShareImg.png");
                }
            }, 1000);

        } else {
            upLoadFailure(Constant.please_input_share_content);
        }
    }

    /*
     * 保存分享图片
     *
     * @param bm
     * @param fileName
     */
    @Override
    public void savePicture(Bitmap bm, String fileName) {
        if (bm == null) {
            upLoadFailure(Constant.share_image_not_exist);
            return;
        } else {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/share");
            if (!file.exists()) {
                file.mkdirs();
            }
            File myCaptureFile = new File(file, fileName);
            if (myCaptureFile.exists()) {
                myCaptureFile.delete();
            }
            try {
                if (!myCaptureFile.exists()) {
                    try {
                        myCaptureFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(myCaptureFile));
                bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                hideUpLoadPicDialog();
                upLoadFailure(Constant.share_image_not_exist);
                e.printStackTrace();
            }
            hideUpLoadPicDialog();
            onShareClick();
        }
    }

    /*
     * 获取屏幕宽度
     */
    @Override
    public void getScreenWidth() {
        // 获取屏幕的物理尺寸
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        screen_width = metric.widthPixels; // 屏幕宽度（像素）
        screen_height = metric.heightPixels;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            /**
             * 分享按钮
             */
            case R.id.share:
                String content = getNoteContent();
                if (content != null && !content.equals("")) {
                    generateImg();
                } else {
                    upLoadFailure(Constant.please_input_share_content);
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * 打开分享面板
     */
    public void onShareClick() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setCancelButtonTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        new ShareAction(getActivity())
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.ALIPAY)
                .setShareboardclickCallback(shareBoardlistener)
                .open(config);
    }

    /*
     * 分享面板item的点击事件
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            share_media1 = share_media;
            requestPermissions();
        }
    };

    /*
     * 请求权限
     */
    private void requestPermissions() {

        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.GET_ACCOUNTS};

        String rationale = getResources().getString(R.string.share_and_auth_need_permissions);
        EasyPermissions.requestPermissions(this, rationale, REQUEST_CODE_PERMISSIONS, mPermissionList);
    }

    /*
     * 分享笔记内容
     *
     * @param share_media
     */
    private void share(SHARE_MEDIA share_media) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/share");
        File myCaptureFile = new File(file, "noteShareImg.png");
        if (myCaptureFile.exists()) {
            UMImage image = new UMImage(context, myCaptureFile);
            // 缩略图生成
            Bitmap bitmap = BitmapFactory.decodeFile(myCaptureFile
                    .getAbsolutePath());
            ThumbnailUtils.extractThumbnail(bitmap, 30, 30,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            UMImage thumb = new UMImage(context, bitmap);
            image.setThumb(thumb);
            new ShareAction(getActivity())
                    .withMedia(image)
                    .setPlatform(share_media)
                    .setCallback(listener)
                    .share();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap = null;// 把原来的 bitmap.recycle().改成这个
            }
        } else {
            upLoadFailure(Constant.share_image_not_exist);
        }
    }

    //分享回调接口
    private UMShareListener listener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onStart share_media = " + share_media);
            SocializeUtils.safeShowDialog(shareDialog);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onResult share_media = " + share_media);
            SocializeUtils.safeCloseDialog(shareDialog);
            showToastMsgShort(getResources().getString(R.string.share_success));
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            LoggerHelper.e(TAG, " onError share_media = " + share_media + " throwable = " + throwable.getMessage());
            SocializeUtils.safeCloseDialog(shareDialog);
            showToastMsgShort(getResources().getString(R.string.share_failure));
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            LoggerHelper.e(TAG, " onCancel share_media = " + share_media);
            SocializeUtils.safeCloseDialog(shareDialog);
            showToastMsgShort(getResources().getString(R.string.share_cancel));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(context).release();
        // 删除本地笔记目录
        FileUtils.deleteDir(saveDir);
        // 删除本地压缩图片
        deleteCompressPictures();
    }

    interface UploadNoteListener {
        void success();
    }

    private UploadNoteListener uploadListener;

    public void setUploadNoteListener(UploadNoteListener uploadNoteListener) {
        uploadListener = uploadNoteListener;
    }
}
