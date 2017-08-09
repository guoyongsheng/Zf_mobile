package com.zfsoftmh.ui.modules.chatting.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.add.FriendAddActivity;
import com.zfsoftmh.ui.modules.chatting.tribe.add.CreateTribeActivity;
import com.zfsoftmh.ui.modules.personal_affairs.qr_code.ScanQrCodeActivity;

import java.lang.reflect.Field;

import javax.inject.Inject;

/**
 * Created by sy
 * on 2017/5/2.
 * <p>好友</p>
 */
public class ChatContactActivity extends BaseActivity{

    private FragmentManager manager;
    private ChatContactFragment fragment;
    private PopupMenu mPopupMenu;

    @Inject
    FriendsPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) { }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.friends_list);
        setDisplayHomeAsUpEnabled(true);
        fragment = (ChatContactFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null){
            fragment = ChatContactFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerFriendsComponent.builder()
                .appComponent(getAppComponent())
                .friendsPresenterModule(new FriendsPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        mPopupMenu = new PopupMenu(this, toolbar, Gravity.END);
        mPopupMenu.getMenuInflater().inflate(R.menu.popup_menu_contacts,mPopupMenu.getMenu());
        try {
            Field field = mPopupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(mPopupMenu);
            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id._friend_add_tribe_create:
                        startActivity(CreateTribeActivity.class);
                        break;
                    case R.id._friend_add_per:
                        startActivity(FriendAddActivity.class);
                        break;
                    case R.id._friend_add_scan:
                        startActivity(ScanQrCodeActivity.class);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friend_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id._friend_add)
            mPopupMenu.show();
        return super.onOptionsItemSelected(item);
    }
}
