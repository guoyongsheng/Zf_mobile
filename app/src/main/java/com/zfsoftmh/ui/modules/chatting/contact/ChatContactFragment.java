package com.zfsoftmh.ui.modules.chatting.contact;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.mobileim.channel.WxThreadHandler;
import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.gingko.presenter.contact.IContactProfileUpdateListener;
import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.PhoneUtils;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.helper.MsgType;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeActivity;
import com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts.OfficeContactsActivity;
import com.zfsoftmh.ui.modules.personal_affairs.contacts.phone_contacts.PhoneContactsActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sy
 * on 2017/5/2.
 */
public class ChatContactFragment extends BaseFragment<FriendsPresenter> implements FriendsContract.View, EasyPermissions.PermissionCallbacks,
        EasyRecyclerViewSidebar.OnTouchSectionListener, ChatContactAdapter.RecyclerItemClickListener, IYWContactCacheUpdateListener {

    private static final int REQUEST_CODE_PHONE_CONTACTS = 1; //手机联系人请求码

    private List<EasySection> easySectionList;
    private List<String> sections = new ArrayList<>(); //存放字母
    private Map<String, Integer> sectionsMap = new HashMap<>(); //第一个字母在列表中的位置

    private EasyRecyclerViewSidebar easyRecyclerViewSidebar;
    private TextView tv_float_view;
    private RecyclerView recyclerView;
    private ChatContactAdapter adapter;
    private ZHandler handler;

    private IContactProfileUpdateListener mContactProfileUpdateListener;
    private Set<String> mContactUserIdSet;
    private Runnable reindexRunnable;
    private ArrayList<ChatContact> dataListSort;
    private ArrayList<ChatContact> mContacts;

    public static ChatContactFragment newInstance(){
        return  new ChatContactFragment();
    }

    @Override
    protected void initVariables() {
        handler = new ZHandler(this);
        this.mLocalContactsChangeTimeStamp = System.currentTimeMillis();

        this.mContactUserIdSet = new HashSet<>();
        this.reindexRunnable = new Runnable() {
            public void run() {
                presenter.IOGetContacts();
            }
        };
        initContactProfileUpdateListener();
        this.dataListSort = new ArrayList<>();
        this.mContacts = new ArrayList<>();
    }

    @Override
    protected void handleBundle(Bundle bundle) { }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_phone_contacts;
    }

    @Override
    protected void initViews(View view) {
        easyRecyclerViewSidebar = (EasyRecyclerViewSidebar) view.findViewById(R.id.section_sidebar);
        tv_float_view = (TextView) view.findViewById(R.id.section_float_view);
        easyRecyclerViewSidebar.setFloatView(tv_float_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new ChatContactAdapter(getActivity());
        adapter.setItemClickListener(this);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);
        recyclerView.setAdapter(adapter);

        presenter.IOGetContacts();
    }

    @Override
    public void onResume() {
        super.onResume();
        addListeners();
        checkAndUpdateContacts();
    }

    @Override
    public void onPause() {
        super.onPause();
        removeListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        WxThreadHandler.getInstance().getHandler().removeCallbacks(this.reindexRunnable);
    }

    private void addListeners() {
        IYWContactService contactService = presenter.getContactService();
        if(contactService != null) {
            contactService.addContactCacheUpdateListener(this);
            contactService.addProfileUpdateListener(this.mContactProfileUpdateListener);
        }
    }

    private void removeListeners() {
        IYWContactService contactService = presenter.getContactService();
        if(contactService != null) {
            contactService.removeContactCacheUpdateListener(this);
            contactService.removeProfileUpdateListener(this.mContactProfileUpdateListener);
        }
    }

    public long mLocalContactsChangeTimeStamp;
    private void checkAndUpdateContacts() {
        long contactsChangeTimeStamp = presenter.getIMKit().getIMCore().getWXContactManager().getContactsChangeTimeStamp();
        if(this.mLocalContactsChangeTimeStamp < contactsChangeTimeStamp) {
            this.mLocalContactsChangeTimeStamp = contactsChangeTimeStamp;
            presenter.netIOGetContacts();
        }
    }


    @Override
    public void onGetContactsSuccess(ArrayList<ChatContact> localContactList) {
        mContacts.clear();
        mContacts.addAll(localContactList);
        mContactUserIdSet.clear();
        for (ChatContact aLocalContactList : localContactList) {
            this.mContactUserIdSet.add(aLocalContactList.getUserId());
        }
        initAlphaContacts();
    }


    private void initAlphaContacts() {
        easySectionList = new ArrayList<>();
        dataListSort.clear();
        dataListSort.add(new ChatContact());
        dataListSort.add(new ChatContact());
        dataListSort.add(new ChatContact());
        dataListSort.add(new ChatContact());
        dataListSort.add(new ChatContact());

        sectionsMap.put("↑", 0);
        sections.add("↑");
        EasySection easy = new EasySection("↑");
        easySectionList.add(easy);

        char A = 'A';
        for (int i = 0; i < 26; i++) {
            String section = String.valueOf((char) (A + i));
            sortData(section, i);
        }

        sectionsMap.put("#", dataListSort.size());
        sections.add("#");
        EasySection easySection = new EasySection("#");
        easySectionList.add(easySection);
        for (int j = 0; j < mContacts.size(); j++) {
            ChatContact contact = mContacts.get(j);
            String firstLetter = contact.getFirstChar().toUpperCase();
            if(!adapter.isAtoZ(firstLetter)){
                mContacts.get(j).setHeaderId(26);
                dataListSort.add(mContacts.get(j));
            }
        }
        handler.postDelayed(runnable,200);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            easyRecyclerViewSidebar.setSections(easySectionList);
            adapter.setDataList(dataListSort);
        }
    };

    @Override
    public void onFriendCacheUpdate(String s, String s1) {
        presenter.IOGetContacts();
    }

    private void initContactProfileUpdateListener() {
        this.mContactProfileUpdateListener = new IContactProfileUpdateListener() {
            public void onProfileUpdate(String userId, String appKey) {
                if(mContactUserIdSet.contains(userId)) {
                    WxThreadHandler.getInstance().getHandler().removeCallbacks(ChatContactFragment.this.reindexRunnable);
                    WxThreadHandler.getInstance().getHandler().postDelayed(ChatContactFragment.this.reindexRunnable, 500L);
                }
            }

            public void onProfileUpdate() {

            }
        };
    }

    @Override
    public void onRecyclerItemClick(int pos, ChatContact contact) {
        switch (pos){
            case 0://系统消息
                IMKitHelper.getInstance().showSystemMessage(getActivity(),
                        MsgType.SYSTEM_FRIEND_REQ_CONVERSATION);
                break;
            case 1://群组
                startActivity(TribeActivity.class);
                break;
            case 2:
                startActivity(OfficeContactsActivity.class);
                break;
            case 3:
                EasyPermissions.requestPermissions(this,
                        getResources().getString(R.string.phone_contacts_request_permission),
                        REQUEST_CODE_PHONE_CONTACTS, Manifest.permission.READ_CONTACTS);
                break;
            case 4:
                break;
            default://好友
                IMKitHelper.getInstance().startChatting(getActivity(),contact.getUserId());
                break;
        }
    }

    @Override
    public void onRecyclerItemLongClick(ChatContact contact) {
        if(contact == null) return;
        presenter.showSwitch(context, contact);
    }

    @Override
    protected void initListener() {
        easyRecyclerViewSidebar.setOnTouchSectionListener(this);
    }

    @Override
    public void onTouchImageSection(int sectionIndex, EasyImageSection imageSection) { }

    @Override
    public void onTouchLetterSection(int sectionIndex, EasySection letterSection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            easyRecyclerViewSidebar.setBackground(null);
        }
        tv_float_view.setVisibility(View.VISIBLE);
        if (easySectionList != null && easySectionList.size() > sectionIndex) {
            String letter = easySectionList.get(sectionIndex).letter;
            tv_float_view.setText(letter);

            if (sections != null && sections.size() > sectionIndex) {
                String section = sections.get(sectionIndex);
                if (sectionsMap != null && sectionsMap.containsKey(section)) {
                    int position = sectionsMap.get(section);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
                    }
                }
            }
        }
    }

    /**
     * 加载动画
     * @param pro 百分比
     */
    @Override
    public void loading(int pro) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = getResources().getString(R.string.chat_contact_sync) + pro + "%";
        handler.sendMessage(msg);
    }

    /**
     * Handler弱引用
     */
    private static ProgressDialog dialog;

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            /*
             * 手机联系人授权成功
             */
            case REQUEST_CODE_PHONE_CONTACTS:
                getPhoneContacts();
                break;
        }
    }

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

    private static class ZHandler extends Handler{
        private WeakReference<ChatContactFragment> fragment;
        ZHandler(ChatContactFragment f) {
            fragment = new WeakReference<>(f);
        }
        @Override
        public void handleMessage(Message msg) {
            ChatContactFragment fmg = fragment.get();
            if (fmg != null){
                switch (msg.what){
                    case 1:
                        dialog = new ProgressDialog(BaseApplication.getContext());
                        dialog.setMessage((CharSequence) msg.obj);
                        dialog.show();
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        }
    }

    /**
     * 停止加载动画
     */
    @Override
    public void stopLoading() {
        if(dialog != null && dialog.isShowing())
        dialog.dismiss();
    }

    /**
     * 提示错误信息
     * @param errorMsg 错误信息
     */
    @Override
    public void showErrorMsg(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    //排序
    private void sortData(String section, int i) {
        sectionsMap.put(section, dataListSort.size());
        sections.add(section);
        EasySection easySection = new EasySection(section);
        easySectionList.add(easySection);
        for (int j = 0; j < mContacts.size(); j++) {
            ChatContact contact = mContacts.get(j);
            String firstLetter = contact.getFirstChar().toUpperCase();
            if (firstLetter.equals(section)){
                mContacts.get(j).setHeaderId(i);
                dataListSort.add(mContacts.get(j));
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    /**
     * 联系人需要改变的时候
     * <p>比如切换了用户</p>
     */
    @Override
    public void onContactChange() {

    }
}
