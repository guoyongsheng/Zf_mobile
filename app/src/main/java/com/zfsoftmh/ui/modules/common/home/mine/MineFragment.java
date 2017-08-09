package com.zfsoftmh.ui.modules.common.home.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.BuildConfig;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.AppUtils;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.FileUtils;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ImageUtil;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.SDCardUtils;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.MyPortalInfo;
import com.zfsoftmh.entity.MyPortalItemInfo;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.favourites.FavouritesListActivity;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.IntegralMallHomeActivity;
import com.zfsoftmh.ui.modules.personal_affairs.my_message.MyMessageActivity;
import com.zfsoftmh.ui.modules.personal_affairs.qr_code.ScanQrCodeActivity;
import com.zfsoftmh.ui.modules.personal_affairs.set.SettingActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * @author wesley
 * @date: 2017/3/15
 * @Description: 我的Fragment
 */

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View, View.OnClickListener,
        EasyPermissions.PermissionCallbacks, MineBottomSheetDialogFragment.OnViewClickListener, MineAdapter.OnItemClickListener {

    private static final String TAG = "MineFragment";
    private static final int REQUEST_CODE_SCAN_QR_CODE_PERMISSIONS = 1; //扫描二维码的请求码
    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2; //拍照时的请求码
    private static final int REQUEST_CODE_LOGIN = 2; //跳转到登录界面的请求码
    private static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 3; //从相册中选取的请求码
    private static final String BOTTOM_SHEET_DIALOG_FRAGMENT = "BOTTOM_SHEET_DIALOG_FRAGMENT";
    private static final int REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS = 3; //从相册中选取的权限
    private static final int REQUEST_CODE_TAKE_CAMERA = 4; //拍照
    private static final int REQUEST_CODE_CROP_IMAGE = 5; //裁剪图片的请求码
    private MineAdapter adapter; //适配器
    private String packageName; //包名
    private File file; //裁剪图片保存的位置
    private OnItemClickListener listener;
    private TextView integral_tv; // 签到按钮
    private String source, isTodaySign; // 积分数，签到状态（1.已签到,0.未签到）
    private TextView mine_integral_number; // 积分
    private CircleImageView circleImageView; //头像
    private ImageView mine_message, mine_skin, mine_setting; // 我的消息，换肤，设置按钮


    @Override
    protected void initVariables() {
        packageName = AppUtils.getAppName(context);
        adapter = new MineAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mine_view;
    }

    @Override
    protected void initViews(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //divider
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
//        recyclerView.addItemDecoration(itemDecoration);

        //adapter
        recyclerView.setAdapter(adapter);

        mine_message = (ImageView) view.findViewById(R.id.mine_message);
        mine_skin = (ImageView) view.findViewById(R.id.mine_skin);
        mine_setting = (ImageView) view.findViewById(R.id.mine_setting);

        initHeaderView();
        initItem();
        loadData();
    }

    @Override
    protected void initListener() {
        mine_message.setOnClickListener(this);
        mine_skin.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
    }

    /**
     * 实例化Fragment
     *
     * @return MineFragment对象
     */
    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onClick(View view) {

        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
            /*
             * 点击头像
             */
        case R.id.fragment_mine_header_iv:
            createUpLoadIconDialog();
            break;

        /**
         * 点击积分商城
         */
        case R.id.mine_integral_number:
            startActivity(IntegralMallHomeActivity.class);
            break;
        /**
         * 点击签到按钮
         */
        case R.id.integral_tv:
            if (isTodaySign != null) {
                if (isTodaySign.equals("0")) { // 未签到
                    presenter.signIn("2", "1");
                } else if (isTodaySign.equals("1")) { // 已签到
                    showToastMsgLong("已签到");
                }
            }
            break;

        /**
         * 我的消息
         */
        case R.id.mine_message:
            startActivity(MyMessageActivity.class);
            break;

        default:
            break;
        }
    }

    /**
     * 签到成功
     *
     * @param integralNumber 返回的积分数
     */
    @Override
    public void singInSuccess(String integralNumber) {
        showToastMsgLong("签到成功");
        SharedPreferenceUtils.write(context, "integral", "integral", integralNumber);
        integral_tv.setText("已签到");
        mine_integral_number.setText("积分：" + integralNumber);
        isTodaySign = "1"; // 已签到状态
    }

    /**
     * 保存积分签到值
     */
    @Override
    public void integralSave() {
        String integralTotal = SharedPreferenceUtils.readString(context, "integral", "integral");
        if (integralTotal != null) {
            mine_integral_number.setText("积分：" + integralTotal);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        integralSave();
    }

    @Override
    public void scanRQCode() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_scan_qr_code),
                REQUEST_CODE_SCAN_QR_CODE_PERMISSIONS, Manifest.permission.CAMERA);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         * 扫描二维码
         */
        case REQUEST_CODE_SCAN_QR_CODE_PERMISSIONS:
            startActivity(ScanQrCodeActivity.class);
            break;

        /*
         * 拍照
         */
        case REQUEST_CODE_CAMERA_PERMISSIONS:
            if (!checkFileIsCreateSuccess()) {
                LoggerHelper.e(TAG, " onPermissionsGranted " + "文件创建失败");
                return;
            }
            Intent intentCamera = new Intent();
            intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intentCamera, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            } else {
                uri = Uri.fromFile(file);
            }
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intentCamera, REQUEST_CODE_TAKE_CAMERA);
            break;

        /*
         * 从相册中选取
         */
        case REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS:
            openActivity();
            break;

        default:
            break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            createAppSettingDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void createAppSettingDialog() {
        showAppSettingDialog();
    }

    @Override
    public void loadData() {
        if (!checkUserIsLogin()) {
            return;
        }
        presenter.loadData();
    }

    @Override
    public boolean checkUserIsLogin() {
        return DbHelper.checkUserIsLogin(context);
    }

    @Override
    public void loadSuccess(MyPortalInfo data) {
        if (data == null) {
            LoggerHelper.e(TAG, " loadSuccess data = " + null);
            return;
        }
        source = data.getSource();
        SharedPreferenceUtils.write(context, "integral", "integral", source);
        integralSave();
        isTodaySign = data.getIsTodaySign();
        setSign(); // 签到状态
        adapter.initVariableItem(data.getList());


    }


    /**
     * 设置签到状态
     */
    private void setSign() {
        if (isTodaySign != null) {
            if (isTodaySign.equals("0")) { // 未签到
                integral_tv.setText("未签到");
            } else if (isTodaySign.equals("1")) { // 已签到
                integral_tv.setText("已签到");
            }
        }
    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void createUpLoadIconDialog() {
        MineBottomSheetDialogFragment fragment = MineBottomSheetDialogFragment.newInstance();
        fragment.setOnViewClickListener(this);
        fragment.show(getChildFragmentManager(), BOTTOM_SHEET_DIALOG_FRAGMENT);
    }

    @Override
    public void cropImage(File cropFile) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", cropFile);
        } else {
            uri = Uri.fromFile(cropFile);
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", getResources().getDimensionPixelSize(R.dimen.circle_image_view_size));
        intent.putExtra("outputY", getResources().getDimensionPixelSize(R.dimen.circle_image_view_size));
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    @Override
    public void upLoadFile(File file) {
        String userId = getUserId();
        presenter.upLoadFile(getRequestBody(userId, true), getRequestBody("", false), getFilePart(userId, file), file.getAbsolutePath());
    }

    @Override
    public MultipartBody.Part getFilePart(String partName, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    @Override
    public RequestBody getRequestBody(String userId, boolean isEncoded) {
        String apptoken = DbHelper.getAppToken(context);
        if (isEncoded) {
            return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT),
                    CodeUtil.getEncodedValueWithToken(userId, apptoken));
        }

        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), apptoken);
    }

    @Override
    public void upLoadFailure(String errMsg) {
        ImageLoaderHelper.loadImage(context, circleImageView, getHeaderPath());
        showToastMsgShort(errMsg);
    }

    @Override
    public void upLoadSuccess(String msg) {
        showToastMsgShort(msg);
    }

    @Override
    public void updateIcon(String path) {
        ImageLoaderHelper.loadImage(context, circleImageView, path);
        presenter.upLoadProfile(path);
    }

    @Override
    public String getUserId() {
        return DbHelper.getUserId(context);
    }

    @Override
    public User getUserInfo() {
        return DbHelper.getUserInfo(context);
    }

    @Override
    public File createNewFile() {
        if (!SDCardUtils.isSDCardEnable()) {
            showToastMsgShort(getResources().getString(R.string.please_insert_sdcard));
            return null;
        }
        String take_picture_dir = SDCardUtils.getSDCardPath() + packageName + "/camera/";
        if (!FileUtils.createOrExistsDir(take_picture_dir)) {
            showToastMsgShort(getResources().getString(R.string.dir_create_failure));
            return null;
        }
        String take_picture_image_path = take_picture_dir + System.currentTimeMillis() + ".png";
        File file = new File(take_picture_image_path);
        if (!FileUtils.createFileByDeleteOldFile(file)) {
            showToastMsgShort(getResources().getString(R.string.dir_create_failure));
            return null;
        }
        return file;
    }

    @Override
    public boolean checkFileIsCreateSuccess() {
        file = createNewFile();
        return file != null;
    }

    @Override
    public void showUpLoadDialog(String upLoad) {
        showProgressDialog(upLoad);
    }

    @Override
    public void hideUpLoadDialog() {
        hideProgressDialog();
    }

    @Override
    public void openActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_FROM_ALBUM);
    }

    @Override
    public void initHeaderView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_mine_header_view, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        circleImageView = (CircleImageView) view.findViewById(R.id.fragment_mine_header_iv);
        TextView tv_name = (TextView) view.findViewById(R.id.fragment_mine_header_name);
        ImageLoaderHelper.loadImage(context, circleImageView, getHeaderPath());
        tv_name.setText(getUserName());
        // 积分
        mine_integral_number = (TextView) view.findViewById(R.id.mine_integral_number);
        // 签到
        integral_tv = (TextView) view.findViewById(R.id.integral_tv);
        integral_tv.getBackground().setAlpha(100);//0~255透明度值
        integral_tv.setOnClickListener(this);
        adapter.setHeaderView(view);
        circleImageView.setOnClickListener(this);
        mine_integral_number.setOnClickListener(this);

    }

    @Override
    public String getUserName() {
        User user = getUserInfo();
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    @Override
    public String getHeaderPath() {
        User user = getUserInfo();
        if (user != null) {
            return user.getHeadPicturePath();
        }
        return null;
    }

    @Override
    public void initItem() {
        List<MyPortalItemInfo> list = new ArrayList<>();

        //设置
        MyPortalItemInfo item_set = new MyPortalItemInfo();
        item_set.setName(getResources().getString(R.string.mine_fragment_text_set));
        item_set.setResId(R.mipmap.settings);
        list.add(item_set);

        //扫一扫
        MyPortalItemInfo item_scan = new MyPortalItemInfo();
        item_scan.setName(getResources().getString(R.string.scan));
        item_scan.setResId(R.mipmap.scan_qr_code);
        list.add(item_scan);

        //时光机
        MyPortalItemInfo item_one_card = new MyPortalItemInfo();
        item_one_card.setName(getResources().getString(R.string.time_machine));
        item_one_card.setResId(R.mipmap.time_machine);
        list.add(item_one_card);

        //我的收藏
        MyPortalItemInfo item_collection = new MyPortalItemInfo();
        item_collection.setName(getResources().getString(R.string.my_collection));
        item_collection.setResId(R.mipmap.favorites);
        list.add(item_collection);

        //积分商城
        MyPortalItemInfo item_attention = new MyPortalItemInfo();
        item_attention.setName(getResources().getString(R.string.integral_mall_title));
        item_attention.setResId(R.mipmap.mypoints);
        list.add(item_attention);

        // 我的消息
        MyPortalItemInfo item_mine_message = new MyPortalItemInfo();
        item_mine_message.setName(getResources().getString(R.string.mine_message));
        item_mine_message.setResId(R.mipmap.mine_message);
        list.add(item_mine_message);

        adapter.initItem(list);
    }

    @Override
    public void saveUserInfo(String path) {
        User user = getUserInfo();
        user.setHeadPicturePath(path);
        DbHelper.saveValueBySharedPreferences(context, Config.DB.USER_NAME, Config.DB.USER_KEY, user);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         *  登录界面返回---重新加载数据
         */
        case REQUEST_CODE_LOGIN:
            loadData();
            break;

        /*
         * 从相册中选取
         */
        case REQUEST_CODE_IMAGE_FROM_ALBUM:
            if (data != null) {
                if (!checkFileIsCreateSuccess()) {
                    LoggerHelper.e(TAG, " onActivityResult " + "文件创建失败");
                    return;
                }
                Uri uri = data.getData();
                String path = ImageUtil.getImageAbsolutePath(context, uri);
                if (path == null) {
                    return;
                }
                File file = new File(path);
                cropImage(file);
            }
            break;

        /*
         * 拍照
         */
        case REQUEST_CODE_TAKE_CAMERA:
            cropImage(file);
            break;

        /*
         * 裁剪图片
         */
        case REQUEST_CODE_CROP_IMAGE:
            if (file == null) {
                showToastMsgShort(getResources().getString(R.string.icon_upload_failure));
                return;
            }
            updateIcon(file.getAbsolutePath());
            upLoadFile(file);
            break;

        default:
            break;
        }
    }

    @Override
    public void onSelectFromAlbumClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.request_permission_by_select_from_album),
                    REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS, Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            openActivity();
        }
    }

    @Override
    public void onTakePicturesClick() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.request_permission_by_camera),
                REQUEST_CODE_CAMERA_PERMISSIONS, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
        /**
         * 设置
         */
        case 0:
            startActivity(SettingActivity.class);
            break;

        /**
         * 扫一扫
         */
        case 1:
            scanRQCode();
            break;

        /**
         * 时光机
         */
        case 2:
            break;

        /**
         * 我的收藏
         */
        case 3:
            startActivity(FavouritesListActivity.class);
            break;

        /**
         * 积分商城
         */
        case 4:
            startActivity(IntegralMallHomeActivity.class);
            break;
        /**
         * 我的消息
         */
        case 5:
            break;

        default:
            break;
        }
    }

    @Override
    public void onItemClick(MyPortalItemInfo itemInfo) {
        if (listener != null) {
            listener.onItemClick(itemInfo);
        }
    }


    /**
     * 自定义回调接口
     */
    public interface OnItemClickListener {

        void onItemClick(MyPortalItemInfo itemInfo);
    }

    @Override
    protected boolean immersionEnabled() {
        return true;
    }

    @Override
    protected void immersionInit() {
        super.immersionInit();
        if (immersionBar == null) {
            return;
        }
        immersionBar.statusBarColor(R.color.colorPrimary)
                .fitsSystemWindows(true)
                .init();
    }
}
