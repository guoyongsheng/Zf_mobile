package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note.AddCloudNoteActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import javax.inject.Inject;

/**
 * 创建时间： 2017/5/2
 * 编写人： 王世美
 * 功能描述： 云笔记列表
 */

public class CloudNoteListActivity extends BaseActivity implements SearchView.OnQueryTextListener, CloudNoteListFragment.deleteNoteListener {

    private FragmentManager manager;
    private CloudNoteListFragment fragment;
    private ImageView insert_note_iv, delete_note_iv; // 添加笔记，删除笔记按钮
    private MenuItem editMenuItem;
    private static final int REQUEST_CODE_ADD_NOTE = 7; //添加笔记请求码


    @Inject
    CloudNoteListPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_note_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.cloud_note_title);
        setDisplayHomeAsUpEnabled(true);

        fragment = (CloudNoteListFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = CloudNoteListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
            fragment.setDeleteNoteListener(this);
        }
        insert_note_iv = (ImageView) findViewById(R.id.insert_note_iv);
        delete_note_iv = (ImageView) findViewById(R.id.delete_note_iv);
    }

    @Override
    protected void setUpInject() {
        DaggerCloudNoteListComponent.builder()
                .appComponent(getAppComponent())
                .cloudNoteListPresenterModule(new CloudNoteListPresenterModule(fragment))
                .build()
                .inject(this);
    }


    @Override
    protected void initListener() {
        insert_note_iv.setOnClickListener(clickListener);
        delete_note_iv.setOnClickListener(clickListener);
    }

    /**
     * 单次点击监听
     */
    private OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            if (v == null) {
                return;
            }
            int key = v.getId();
            switch (key) {
                /**
                 * 添加笔记
                 */
                case R.id.insert_note_iv:
                    Intent addIntent = new Intent(CloudNoteListActivity.this, AddCloudNoteActivity.class);
                    startActivityForResult(addIntent, REQUEST_CODE_ADD_NOTE);
                    break;
                /**
                 * 删除笔记
                 */
                case R.id.delete_note_iv:
                    fragment.deleteNote();
                    break;
            }
        }
    };

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            /**
             * 刷新笔记列表
             */
            case REQUEST_CODE_ADD_NOTE:
                fragment.onRefresh();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cloud_note_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_note);
        editMenuItem = menu.findItem(R.id.menu_edit_note);
        SearchView searchView = (SearchView) menuItem.getActionView();
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        textView.setHintTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            /**
             * 搜索笔记
             */
            case R.id.menu_search_note:
                return true;

            /**
             * 编辑笔记列表
             */
            case R.id.menu_edit_note:
                if (fragment.checkIsEditMode(item.getTitle().toString())) { // 编辑完成状态
                    fragment.noEditMode();
                    updateMenuItemTitle(getResources().getString(R.string.edit));
                    insert_note_iv.setVisibility(View.VISIBLE);
                    delete_note_iv.setVisibility(View.GONE);
                } else {
                    fragment.editMode();
                    updateMenuItemTitle(getResources().getString(R.string.done));
                    insert_note_iv.setVisibility(View.GONE);
                    delete_note_iv.setVisibility(View.VISIBLE);
                }
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 点击按钮搜索
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * 输入搜索
     *
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        fragment.searchNote(newText);
        return false;
    }

    /**
     * 设置编辑Menu标题
     *
     * @param title 标题
     */
    private void updateMenuItemTitle(String title) {
        if (editMenuItem != null) {
            editMenuItem.setTitle(title);
        }
    }

    @Override
    public void success() {
        updateMenuItemTitle(getResources().getString(R.string.edit));
        insert_note_iv.setVisibility(View.VISIBLE);
        delete_note_iv.setVisibility(View.GONE);
    }
}
