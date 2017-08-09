package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.text.style.TypefaceSpan;
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
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.FileUtils;
import com.zfsoftmh.common.utils.ImageCompressUtils;
import com.zfsoftmh.common.utils.ImageUtil;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.PerformEdit;
import com.zfsoftmh.common.utils.SizeUtils;
import com.zfsoftmh.entity.CloudNoteListInfo;
import com.zfsoftmh.entity.ImageCompressInfo;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.common.home.mine.MineBottomSheetDialogFragment;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangshimei
 * @date: 17/5/26
 * @Description: 云笔记详情
 */

public class NoteDetailFragment extends BaseFragment<NoteDetailPresenter> implements NoteDetailContract.View,
        MineBottomSheetDialogFragment.OnViewClickListener,
        EasyPermissions.PermissionCallbacks {

    private EditText add_cloud_note_title_et; // 笔记标题
    private LinearLayout add_note_label_layout; // 笔记标签类别视图
    private View note_label_view; // 标签颜色视图
    private TextView note_label_name_tv; // 标签类别名
    private EditText add_cloud_note_content_et; // 笔记内容
    private ImageView cloud_note_delete_iv, add_pic_iv, cloud_note_repeal_iv; // 一键删除，添加图片，撤销按钮
    private ScrollView share_note_content_sll; // 笔记内容视图
    private AppSettingsDialog dialog;
    private ProgressDialog shareDialog; // 分享对话框

    private PerformEdit mPerformEdit; // 撤销工具类
    private String mFilePath, noteFileName, userId, app_token; // 拍照存储地址，笔记文件名,当前用户ID
    private int screen_width, screen_height; // 屏幕宽度
    private SimpleDateFormat t; // 日期格式
    private static final String BOTTOM_SHEET_DIALOG_FRAGMENT = "BOTTOM_SHEET_DIALOG_FRAGMENT";
    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2; //拍照时的请求码
    private static final int REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS = 3; //从相册中选取的权限
    private static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 3; //从相册中选取的请求码
    private static final int REQUEST_CODE_TAKE_CAMERA = 4; //拍照
    private static final int REQUEST_CODE_LABEL = 5; //标签请求码
    private static final int REQUEST_CODE_READ_DISK = 6; // 读写本地磁盘权限

    private SHARE_MEDIA share_media1;
    private static final int REQUEST_CODE_PERMISSIONS = 1; //分享授权的请求码
    private static final String TAG = "NoteDetailFragment";

    private Handler mHandler = new Handler();
    private NoteLabelItemInfo labelInfo;// 标签实体类

    private CloudNoteListInfo noteListInfo = null;
    private String memoCatalogId = "", catalogColor = "", memoCatalogName = ""; // 标签ID，标签颜色值，标签名
    private String memoPath = null, memoTitle; // 文件下路径,笔记标题
    private String startContent = null; // 笔记起始内容
    private String picFileName, saveDir;
    private String newNoteName = null; // 下载笔记存储文件名

    public static NoteDetailFragment newInstance(CloudNoteListInfo bean) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("cloudNoteList", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initVariables() {
        app_token = DbHelper.getAppToken(getActivity());
        userId = DbHelper.getUserId(getActivity());
        // 默认标签值
        labelInfo = DbHelper.getValueBySharedPreferences(getActivity(), "NoteLabelItemInfo", "NoteLabelItemInfo", NoteLabelItemInfo.class);
        t = new SimpleDateFormat("yyyyMMddssSSS");
        getImageFromCameraPath();
        getScreenWidth();
        // 获取 SD 卡根目录
        saveDir();
        shareDialog = new ProgressDialog(getActivity());
        shareDialog.setMessage(getResources().getString(R.string.sharing));
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        noteListInfo = bundle.getParcelable("cloudNoteList");
        noteFileName = noteListInfo.memoFileName;
        memoPath = noteListInfo.memoPath;
        memoTitle = noteListInfo.memoTitle;
        memoCatalogId = noteListInfo.memoCatalogId;
        catalogColor = noteListInfo.catalogColor;
        memoCatalogName = noteListInfo.memoCatalogName;
        if (catalogColor == null && labelInfo != null)
            catalogColor = labelInfo.catalogColor;
        if (memoCatalogId == null && labelInfo != null)
            memoCatalogId = labelInfo.memoCatalogId;
        if (memoCatalogName == null && labelInfo != null)
            memoCatalogName = labelInfo.memoCatalogName;

        // 根据时间戳命名存储文件名（防止文件重复）
        String time = CloudNoteUtils.getCreateTime();
        time = time.replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
        newNoteName = time + "_" + noteFileName;
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.fragment_add_cloud_note;
    }

    @Override
    protected void initViews(View view) {
        note_label_view = view.findViewById(R.id.note_label_view);

        add_pic_iv = (ImageView) view.findViewById(R.id.add_pic_iv);
        note_label_name_tv = (TextView) view.findViewById(R.id.note_label_name_tv);
        cloud_note_delete_iv = (ImageView) view.findViewById(R.id.cloud_note_delete_iv);
        cloud_note_repeal_iv = (ImageView) view.findViewById(R.id.cloud_note_repeal_iv);
        share_note_content_sll = (ScrollView) view.findViewById(R.id.share_note_content_sll);
        add_cloud_note_title_et = (EditText) view.findViewById(R.id.add_cloud_note_title_et);
        add_note_label_layout = (LinearLayout) view.findViewById(R.id.add_note_label_layout);
        add_cloud_note_content_et = (EditText) view.findViewById(R.id.add_cloud_note_content_et);
        mPerformEdit = new PerformEdit(add_cloud_note_content_et, getActivity());
        add_cloud_note_content_et.setVisibility(View.GONE);
        setData();
        readDiskPermission();
        repealIconShow();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void initListener() {
        add_pic_iv.setOnClickListener(onceClickListener);
        cloud_note_delete_iv.setOnClickListener(onceClickListener);
        cloud_note_repeal_iv.setOnClickListener(onceClickListener);
        add_note_label_layout.setOnClickListener(onceClickListener);
    }

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
//                    add_cloud_note_content_et.setSelection(add_cloud_note_content_et.getText().length());
                    break;
            }
        }
    };

    @Override
    public void setData() {
        // 设置笔记标题
        if (memoTitle != null) {
            add_cloud_note_title_et.setText(memoTitle);
        } else {
            add_cloud_note_title_et.setText(Constant.default_cloud_note_title);
        }

        // 设置标签颜色
        if (catalogColor != null) {
            CloudNoteUtils.getColorView(note_label_view).setColor(
                    CloudNoteUtils.getColorValue(catalogColor));
        }
        // 设置标签名
        if (memoCatalogName != null) {
            note_label_name_tv.setText(memoCatalogName);
        }

    }

    /**
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
                if (startContent != null) {
                    if (s.length() == startContent.length()) {
                        cloud_note_repeal_iv.setVisibility(View.GONE);
                    } else {
                        cloud_note_repeal_iv.setVisibility(View.VISIBLE);
                    }
                }

                if (s.length() == 0) {
                    cloud_note_repeal_iv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                add_cloud_note_content_et.setSelection(add_cloud_note_content_et.getText().length());
            }
        });

        add_cloud_note_content_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = getNoteContent();
                if (!TextUtils.isEmpty(s)
                        && hasFocus
                        && cloud_note_repeal_iv.getVisibility() == View.GONE
                        && !s.equals(startContent)) {
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
                    cloud_note_repeal_iv.setVisibility(View.GONE);
                } else {
                    add_pic_iv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 读写磁盘动态权限请求
     */
    @Override
    public void readDiskPermission() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_disk),
                REQUEST_CODE_READ_DISK, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 下载笔记并存入本地磁盘
     */
    @Override
    public void downloadFile() {
        createUpLoadPicDialog(Constant.note_loading);
//        presenter.downloadCloudNote(memoPath, Config.CLOUD_NOTE_PATH + newNoteName);
        presenter.downloadNote(memoPath);
    }

    /**
     * 新建目录（用来保存下载的笔记文件）
     */
    @Override
    public void saveDir() {
        saveDir = Environment.getExternalStorageDirectory()
                + "/cloud_note_file";
//        FileUtils.deleteDir(saveDir);
        // 新建目录
        File dir = new File(saveDir);
        if (!dir.exists())
            dir.mkdir();
    }

    /**
     * 获取下载文件
     *
     * @param s 下载笔记文件
     */
    @Override
    public void getDownloadFileContent(String s) {
        s = CloudNoteUtils.replaceBlank(s);
        if (s != null) {
            Observable.just(s)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<String, List<String>>() {
                        @Override
                        public List<String> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                            List<String> mContentList = new ArrayList<String>();
                            mContentList = CloudNoteUtils.getContentList(s);
                            if (mContentList != null) {
                                return mContentList;
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<String>>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull List<String> strings) throws Exception {
                            if (strings != null && strings.size() > 0) {
                                Editable editable = add_cloud_note_content_et.getEditableText();
                                for (String str : strings) {
                                    if (str.contains("<img") && str.contains("src=")) { // 图片
                                        str = str.replace("<img src=", "").replace("/>", "");
                                        SpannableString newLine2 = new SpannableString("\n\n");
                                        // 插入图片前换行
//                                        insertIntoEditText(newLine2);
//                                        editable.append(newLine2);
                                        loadImage(str);
                                        // 插入换行符，使图片单独占一行
                                        // 插入图片前换行
//                                        editable.append(newLine2);
                                        insertIntoEditText(newLine2);
                                    } else {
                                        if (!str.equals("")) {
                                            SpannableString newLine2 = new SpannableString("\n\n");

                                            loadText(str);// 文字

                                            // 插入图片前换行
                                            insertIntoEditText(newLine2);
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
        hideUpLoadPicDialog();
        handler.sendEmptyMessage(1);
    }

    /**
     * 加载文字
     *
     * @param s 文本内容
     */
    @Override
    public void loadText(String s) {
        Editable editable = add_cloud_note_content_et.getEditableText();
        // 插入文字
        SpannableString ss = new SpannableString(s);
        // 将选择的图片追加到EditText中光标所在的位置
//        editable.append(ss);

        insertIntoEditText(ss);
    }

    /**
     * 加载图片
     *
     * @param s 图片下载地址
     */
    @Override
    public void loadImage(String s) {
        String[] split = s.split("/");
        String picName = split[split.length - 1];
        Editable editable = add_cloud_note_content_et.getEditableText();

        String str = "<img src=" + s + "/>";
        final String tempUrl = Constant.mBitmapTag + str + Constant.mBitmapTag;
        Drawable mDrawable = null;// 默认显示图片
        Resources resources = getActivity().getResources();
        mDrawable = resources.getDrawable(R.mipmap.hploading_default);
//        BitmapDrawable bd = (BitmapDrawable) mDrawable;
//        Drawable drawable = CloudNoteUtils.getDrawable(screen_width,screen_height, bd.getBitmap());
        mDrawable.setBounds(0, 0, screen_width - (SizeUtils.dp2px(21, BaseApplication.getContext()) * 2),
                screen_height / 2);
        ImageSpan imageSpan = new ImageSpan(mDrawable);
        SpannableString spannableString = new SpannableString(tempUrl);
        spannableString.setSpan(imageSpan, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        editable.append(spannableString);
//        int startIndex = editable.getSpanStart(imageSpan);
//        int endIndex = editable.getSpanEnd(imageSpan);

        int start = add_cloud_note_content_et.getSelectionStart();
        // 设置ss要添加的位置
        editable.insert(start,spannableString);
        // 把et添加到Edittext中
//        add_cloud_note_content_et.setText(editable);
        // 设置Edittext光标在最后显示
        add_cloud_note_content_et.setSelection(start + spannableString.length());


        presenter.downloadImage(s, start, start + spannableString.length(), Config.CLOUD_NOTE_PATH + picName, tempUrl);
    }


    /**
     * 压缩下载图片
     *
     * @param file
     * @return
     */
    @Override
    public void compressImage(File file, final int startIndex, final int endIndex, final String placeholder) {
        // 图片压缩
        ImageCompressUtils.compressToFile(getActivity(), file, new CallBackListener<ImageCompressInfo>() {
            @Override
            public void onSuccess(ImageCompressInfo data) {
                if (data != null) {
                    if (data.bitmap != null) {
                        if (data.outfile != null && !data.outfile.equals("")) {
                            showDownloadImage(data.bitmap, startIndex, endIndex, placeholder);
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
     * 显示下载图片
     *
     * @param bitmap      显示图片
     * @param startIndex  图片占位符起始位置
     * @param endIndex    图片占位符末尾位置
     * @param placeholder 占位符
     */
    @Override
    public void showDownloadImage(Bitmap bitmap, int startIndex, int endIndex, String placeholder) {
        Editable editable = add_cloud_note_content_et.getEditableText();
        Drawable d = CloudNoteUtils.getDrawable(screen_width, screen_height, bitmap);
        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imSpan = new ImageSpan(d);
        SpannableString spannableString = new SpannableString(
                placeholder);
        spannableString.setSpan(imSpan, 0,
                spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editable.replace(startIndex, endIndex, spannableString);


    }

    public void insertIntoEditText(SpannableString ss) {
        // 先获取EditText原有的内容
        Editable editable = add_cloud_note_content_et.getText();
        int start = add_cloud_note_content_et.getSelectionStart();
        // 设置ss要添加的位置
        editable.insert(start,ss);
        // 把et添加到Edittext中
//        add_cloud_note_content_et.setText(editable);
        // 设置Edittext光标在最后显示
        add_cloud_note_content_et.setSelection(start + ss.length());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    add_cloud_note_content_et.setVisibility(View.VISIBLE);
                    startContent = add_cloud_note_content_et.getText().toString();
                    break;
            }
        }
    };

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
            /**
             * 读写本地磁盘权限
             */
            case REQUEST_CODE_READ_DISK:
                downloadFile();
                break;
        }

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
                    String filePath = ImageUtil.getImagePath(getActivity(), uri);
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
        ImageCompressUtils.compressToFile(getActivity(), file, new CallBackListener<ImageCompressInfo>() {
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

    public RequestBody getRequestBody(String s) {
        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), s);
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
            String time = CloudNoteUtils.getCreateTime();
            time = time.replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
            File file = null;
            // 编写上传文件
            file = CloudNoteUtils.write(getNoteContent(), time + "_" + noteFileName);
            if (file.exists() && file != null) {
                picturePath = CloudNoteUtils.getImagePath(picPathList);
                contentFlag = CloudNoteUtils.getContentFlag(picPathList);
                Map<String, RequestBody> params = new LinkedHashMap<>();
                params.put("createTime", getRequestBody(CodeUtil.getEncodedValueWithToken(createTime, app_token)));
                params.put("memoTitle", getRequestBody(CodeUtil.getEncodedValueWithToken(memoTitle, app_token)));
                params.put("contentFlag", getRequestBody(CodeUtil.getEncodedValueWithToken(contentFlag, app_token)));
                params.put("memoCatalogId ", getRequestBody(CodeUtil.getEncodedValueWithToken(memoCatalogId, app_token)));
                params.put("username", getRequestBody(CodeUtil.getEncodedValueWithToken(userId, app_token)));
                params.put("apptoken", getRequestBody(app_token));
                params.put("picturePath", getRequestBody(CodeUtil.getEncodedValueWithToken(picturePath, app_token)));

//                String content = CloudNoteUtils.readTextFile(file.getPath());

                presenter.uploadCloudNote(getFilePart(noteFileName, file, noteFileName), params);
            }
        } else {
            upLoadFailure(Constant.please_input_note_content);
        }
    }

    @Override
    public void getScreenWidth() {
        // 获取屏幕的物理尺寸
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        screen_width = metric.widthPixels; // 屏幕宽度（像素）
        screen_height = metric.heightPixels;
    }

    @Override
    public void upLoadFailure(String errMsg) {
        showToastMsgShort(errMsg);
    }


    /**
     * 删除拍照后图片
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
        FileUtils.deleteDir(saveDir);
    }


    @Override
    public void skipPage() {
        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
//        getActivity().finish();
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
        config.setCancelButtonTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
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

    /**
     * 分享笔记内容
     *
     * @param share_media
     */
    private void share(SHARE_MEDIA share_media) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/share");
        File myCaptureFile = new File(file, "noteShareImg.png");
        if (myCaptureFile.exists()) {
            UMImage image = new UMImage(getActivity(), myCaptureFile);
            // 缩略图生成
            Bitmap bitmap = BitmapFactory.decodeFile(myCaptureFile
                    .getAbsolutePath());
            ThumbnailUtils.extractThumbnail(bitmap, 30, 30,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            UMImage thumb = new UMImage(getActivity(), bitmap);
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
        UMShareAPI.get(getActivity()).release();
        // 删除本地笔记目录
        FileUtils.deleteDir(saveDir);
        // 删除本地压缩图片
        deleteCompressPictures();
        noteListInfo = null;
        memoPath = null;
        newNoteName = null;
    }

}
