package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zfsoftmh.BuildConfig;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.AppUtils;
import com.zfsoftmh.common.utils.CodeUtil;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.FileUtils;
import com.zfsoftmh.common.utils.ImageUtil;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.SDCardUtils;
import com.zfsoftmh.entity.DisCoveryTypeInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.common.home.mine.MineBottomSheetDialogFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.ReleaseNewsActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.ReleaseNewsAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 失物招领ui
 */

public class LostAndFoundFragment extends BaseFragment<LostAndFoundPresenter> implements
        LostAndFoundContract.View, View.OnClickListener, ReleaseNewsAdapter.OnItemClickListener,
        MineBottomSheetDialogFragment.OnViewClickListener, EasyPermissions.PermissionCallbacks {

    private static final String TAG = "LostAndFoundFragment";
    private static final int REQUEST_CODE_SELECT_FROM_ALBUM_PERMISSIONS = 3; //从相册中选取的权限
    private static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 3; //从相册中选取的请求码
    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2; //拍照时的请求码
    private static final int REQUEST_CODE_TAKE_CAMERA = 4; //拍照
    private EditText et_title; //标题
    private EditText et_location; //地点
    private EditText et_detail; //详情
    private Button btn_release; //发布

    private LinearLayout ll_recycler; //图片的布局
    private ReleaseNewsAdapter adapter; //适配器
    private File file; //裁剪图片保存的位置
    private String packageName; //包名
    private EditText et_phone_number; //手机号码

    @Override
    protected void initVariables() {
        packageName = AppUtils.getAppName(context);
        adapter = new ReleaseNewsAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_lost_and_found;
    }

    @Override
    protected void initViews(View view) {
        et_title = (EditText) view.findViewById(R.id.fragment_lost_and_found_title);
        et_location = (EditText) view.findViewById(R.id.fragment_lost_and_found_location);
        et_detail = (EditText) view.findViewById(R.id.fragment_lost_and_found_detail);
        btn_release = (Button) view.findViewById(R.id.fragment_lost_and_found_release);
        et_phone_number = (EditText) view.findViewById(R.id.fragment_lost_and_found_phone_number);

        ll_recycler = (LinearLayout) view.findViewById(R.id.fragment_lost_and_found_image);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler);
        //LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        recyclerView.setAdapter(adapter);
        updateRecycler();
    }

    @Override
    protected void initListener() {
        btn_release.setOnClickListener(this);
    }

    public static LostAndFoundFragment newInstance() {
        return new LostAndFoundFragment();
    }

    @Override
    public void submit() {
        String title = getTitle(); //标题
        if (checkValueIsNull(title)) {
            showToastMsgShort(getResources().getString(R.string.lost_and_found_msg));
            return;
        }

        String location = getLocation(); //地点
        if (checkValueIsNull(location)) {
            showToastMsgShort(getResources().getString(R.string.lost_and_found_location));
            return;
        }

        String detail = getDetail(); //详情
        if (checkValueIsNull(detail)) {
            showToastMsgShort(getResources().getString(R.string.lost_and_found_detail));
            return;
        }

        String phoneNumber = "";
        if (checkShouldHasImage()) {
            phoneNumber = getPhoneNumber();
            if (checkValueIsNull(phoneNumber)) {
                showToastMsgShort(getResources().getString(R.string.please_input_phone_number));
                return;
            }
        }

        List<String> listPath = adapter.getAllItems();
        Map<String, RequestBody> map = buildParams(title, location, detail, phoneNumber, 1);
        Map<String, RequestBody> file = buildParams(listPath);
        presenter.submit(map, file);
    }

    @Override
    public String getTitle() {
        return et_title.getText().toString();
    }

    @Override
    public String getLocation() {
        return et_location.getText().toString();
    }

    @Override
    public String getDetail() {
        return et_detail.getText().toString();
    }

    @Override
    public boolean checkValueIsNull(String value) {
        return value == null || value.length() == 0;
    }

    @Override
    public void submitSuccess() {
        finish();
    }

    @Override
    public void submitFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
        finish();
    }

    @Override
    public void showProgress(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 发布
         */
        case R.id.fragment_lost_and_found_release:
            submit();
            break;

        default:
            break;
        }
    }

    @Override
    public void finish() {
        ((ReleaseNewsActivity) context).finish();
    }

    @Override
    public boolean checkShouldHasImage() {
        DisCoveryTypeInfo info = getDisCoveryTypeInfo();
        if (info == null) {
            return false;
        }
        int type = info.getType();
        switch (type) {
        /*
         * 没有
         */
        case 0:
        case 1:
            return false;

        /*
         * 有
         */
        case 2:
            return true;

        default:
            break;
        }
        return false;
    }

    @Override
    public DisCoveryTypeInfo getDisCoveryTypeInfo() {
        return DbHelper.getValueBySharedPreferences(context, Config.DB.DISCOVERY_TYPE_NAME,
                Config.DB.DISCOVERY_TYPE_KEY, DisCoveryTypeInfo.class);
    }

    @Override
    public void onItemRemove(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void onItemAdd() {
        MineBottomSheetDialogFragment fragment = MineBottomSheetDialogFragment.newInstance();
        fragment.setOnViewClickListener(this);
        fragment.show(getChildFragmentManager(), TAG);
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

    @Override
    public void openActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_FROM_ALBUM);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
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
            showAppSettingDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
                if (!checkFileIsCreateSuccess()) {
                    LoggerHelper.e(TAG, " onActivityResult " + "文件创建失败");
                    return;
                }
                Uri uri = data.getData();
                String path = ImageUtil.getImageAbsolutePath(context, uri);
                if (path != null) {
                    adapter.insertItem(path);
                }
            }
            break;

        /*
         * 拍照
         */
        case REQUEST_CODE_TAKE_CAMERA:
            if (file != null) {
                adapter.insertItem(file.getAbsolutePath());
            }
            break;


        default:
            break;
        }
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
    public void updateRecycler() {

        if (checkShouldHasImage()) {
            adapter.removeAllItems();
            ll_recycler.setVisibility(View.VISIBLE);
        } else {
            ll_recycler.setVisibility(View.GONE);
        }
    }

    @Override
    public String getPhoneNumber() {
        return et_phone_number.getText().toString();
    }

    @Override
    public Map<String, RequestBody> buildParams(String title, String place, String content, String telephone, int flag) {
        String apptoken = DbHelper.getAppToken(context);
        Map<String, RequestBody> map = new LinkedHashMap<>();
        map.put("username", getRequestBody(getUserId(), true, apptoken));
        map.put("title", getRequestBody(title, true, apptoken));
        map.put("place", getRequestBody(place, true, apptoken));
        map.put("content", getRequestBody(content, false, content));
        map.put("telephone", getRequestBody(telephone, true, apptoken));
        map.put("flag", getRequestBody(String.valueOf(flag), true, apptoken));
        map.put("apptoken", getRequestBody(apptoken, false, apptoken));
        return map;
    }

    @Override
    public RequestBody getRequestBody(String value, boolean isEncoded, String apptoken) {
        if (isEncoded) {
            return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT),
                    CodeUtil.getEncodedValueWithToken(value, apptoken));
        }

        return RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), apptoken);
    }

    @Override
    public Map<String, RequestBody> buildParams(List<String> listPath) {
        Map<String, RequestBody> map = new HashMap<>();
        if (listPath == null) {
            return map;
        }

        int size = listPath.size();
        for (int i = 0; i < size; i++) {
            String path = listPath.get(i);
            if (path != null) {
                File file = new File(path);
                map.put("file " + i + "\"; + filename=\"" + file.getName(),
                        RequestBody.create(MediaType.parse(Config.UPLOAD.UPLOAD_FILE_FORMAT), file));
            }
        }
        return map;
    }

    public void updateUI() {
        updateRecycler();
    }
}
