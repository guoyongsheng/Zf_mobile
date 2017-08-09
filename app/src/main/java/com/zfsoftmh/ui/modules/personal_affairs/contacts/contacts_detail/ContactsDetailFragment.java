package com.zfsoftmh.ui.modules.personal_affairs.contacts.contacts_detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.PhoneUtils;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.contacts.qr_code_card.QrCodeCardActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: ui
 */

public class ContactsDetailFragment extends BaseFragment<ContactsDetailPresenter> implements
        ContactsDetailContract.View, ContactsDetailAdapter.OnItemClickListener, EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_PERMISSIONS_CODE = 1;
    private static final int REQUEST_PERMISSIONS_ADD_CONTRACTS_CODE = 2;
    private ContactsItemInfo info;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        info = bundle.getParcelable("info");
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adapter
        ContactsDetailAdapter adapter = new ContactsDetailAdapter(context, info);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    public static ContactsDetailFragment newInstance(ContactsItemInfo info) {
        ContactsDetailFragment fragment = new ContactsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onItemClick(String phone) {
        requestCallPermission();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contacts_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 保存到手机
         */
        case R.id.contacts_save_to_phone:
            requestWriteContactsPermission();
            return true;

        /*
         * 生成二维码
         */
        case R.id.contacts_qr_code:
            Intent intent = new Intent(context, QrCodeCardActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("info", info);
            intent.putExtras(bundle);
            openActivity(intent);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         * 打电话
         */
        case REQUEST_PERMISSIONS_CODE:
            callPhone();
            break;

        /*
         * 添加联系人
         */
        case REQUEST_PERMISSIONS_ADD_CONTRACTS_CODE:
            addContracts();
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
    public void requestCallPermission() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.call_need_permissions),
                REQUEST_PERMISSIONS_CODE, Manifest.permission.CALL_PHONE);
    }

    @Override
    public void requestWriteContactsPermission() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.add_contracts_need_permissions),
                REQUEST_PERMISSIONS_ADD_CONTRACTS_CODE, Manifest.permission.WRITE_CONTACTS);
    }

    @Override
    public void createAppSettingDialog() {
       showAppSettingDialog();
    }

    @Override
    public void callPhone() {
        String phoneNumber = info.getPhone();
        Uri number = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent, 0);
        boolean isIntentSafe = activities != null && activities.size() > 0;
        if (isIntentSafe) {
            startActivity(callIntent);
        }
    }

    @Override
    public void addContracts() {
        showProgressDialog(getResources().getString(R.string.add_contracts));
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(PhoneUtils.addContacts(context, info));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {

                        if (!isActive()) {
                            return;
                        }

                        hideProgressDialog();
                        if (aBoolean) {
                            showToastMsgShort(getResources().getString(R.string.contract_add_success));
                        } else {
                            showToastMsgShort(getResources().getString(R.string.contract_add_failure));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        if (!isActive()) {
                            return;
                        }

                        hideProgressDialog();
                        showToastMsgShort(getResources().getString(R.string.contract_add_failure));
                    }
                });
    }
}
