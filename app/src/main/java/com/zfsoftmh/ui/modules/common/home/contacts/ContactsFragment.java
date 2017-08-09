package com.zfsoftmh.ui.modules.common.home.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.PhoneUtils;
import com.zfsoftmh.entity.ContactsInfo;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts.OfficeContactsActivity;
import com.zfsoftmh.ui.modules.personal_affairs.contacts.phone_contacts.PhoneContactsActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;

import java.util.ArrayList;
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
 * @date: 2017/3/21
 * @Description: 通讯录的ui
 */

public class ContactsFragment extends BaseFragment<ContactsPresenter> implements ContactsContract.View,
        ContactsAdapter.OnItemClickListener, EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_PHONE_CONTACTS = 1; //手机联系人请求码
    private static final int REQUEST_CODE_LOGIN = 2; //用户没有登录的请求码

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        ContactsAdapter adapter = new ContactsAdapter(context, getDataSource());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    //获取数据源
    private List<ContactsInfo> getDataSource() {
        List<ContactsInfo> list = new ArrayList<>();

        //办公通讯录
        ContactsInfo info_office = new ContactsInfo();
        info_office.setType(0);
        info_office.setName(getResources().getString(R.string.office_contacts));
        info_office.setImageId(R.mipmap.ic_icon_office_contacts);
        list.add(info_office);

        //手机通讯录
        ContactsInfo info_phone = new ContactsInfo();
        info_phone.setType(0);
        info_phone.setImageId(R.mipmap.ic_icon_phone_contacts);
        info_phone.setName(getResources().getString(R.string.phone_contacts));
        list.add(info_phone);

        //黄页通讯录
        ContactsInfo info_yellow = new ContactsInfo();
        info_yellow.setType(0);
        info_yellow.setImageId(R.mipmap.ic_icon_yellow_contacts);
        info_yellow.setName(getResources().getString(R.string.yellow_contacts));
        list.add(info_yellow);

        //常用联系人
        ContactsInfo info_often = new ContactsInfo();
        info_often.setType(1);
        info_often.setImageId(R.mipmap.ic_icon_offen_contacts);
        info_often.setName(getResources().getString(R.string.often_contacts));
        list.add(info_often);

        //特別关注
        ContactsInfo info_attention = new ContactsInfo();
        info_attention.setType(1);
        info_attention.setImageId(R.mipmap.ic_icon_attentijon_contract);
        info_attention.setName(getResources().getString(R.string.attention_contacts));
        list.add(info_attention);

        return list;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
        /*
         * 办公通讯录
         */
        case 0:
            startActivity(OfficeContactsActivity.class);
            break;

        /*
         * 手机通讯录
         */
        case 1:
            requestPermission();
            break;

        /*
         * 黄页通讯录
         */
        case 2:
            Intent intent = new Intent(context, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Config.WEB.URL, Config.URL.DEPARTMENT_YELLOW_URL);
            bundle.putString(Config.WEB.TITLE, getResources().getString(R.string.yellow_contacts));
            intent.putExtras(bundle);
            openActivity(intent);
            break;


        default:
            break;
        }
    }

    @Override
    public void onItemClick(ContactsItemInfo contactsItemInfo) {
        showToastMsgShort(contactsItemInfo.getName());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        switch (requestCode) {
        /*
         * 手机联系人授权成功
         */
        case REQUEST_CODE_PHONE_CONTACTS:
            getPhoneContacts();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean checkUserIsLogin() {
        return DbHelper.checkUserIsLogin(context);
    }

    @Override
    public void requestPermission() {
        EasyPermissions.requestPermissions(this,
                getResources().getString(R.string.phone_contacts_request_permission),
                REQUEST_CODE_PHONE_CONTACTS, Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void createAppSettingDialog() {
        showAppSettingDialog();
    }

    @Override
    public void getPhoneContacts() {

        showProgressDialog(getResources().getString(R.string.phone_contacts_getting));
        Observable.create(new ObservableOnSubscribe<ArrayList<ContactsItemInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<ContactsItemInfo>> e) throws Exception {
                e.onNext(PhoneUtils.getPhoneContacts(context));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<ContactsItemInfo>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull ArrayList<ContactsItemInfo> list) throws Exception {
                        if (!isActive()) {
                            return;
                        }
                        hideProgressDialog();
                        Intent intent = new Intent(context, PhoneContactsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("list", list);
                        intent.putExtras(bundle);
                        openActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        if (!isActive()) {
                            return;
                        }

                        hideProgressDialog();
                        showToastMsgShort(getResources().getString(R.string.phone_contacts_get_failure));
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 用户登录后返回
         */
        case REQUEST_CODE_LOGIN:
            break;


        default:
            break;
        }
    }


}
