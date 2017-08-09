package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.add_cloud_note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import javax.inject.Inject;

/**
 * 创建时间： 2017/5/2
 * 编写人： 王世美
 * 功能描述： 新建云笔记
 */

public class AddCloudNoteActivity extends BaseActivity implements
        AddCloudNoteFragment.UploadNoteListener {

    private FragmentManager manager;
    private AddCloudNoteFragment fragment;
    private TextView common_subtitle;

    @Inject
    AddCloudNotePresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.add_cloud_note_title);
        setDisplayHomeAsUpEnabled(true);

        // 初始化完成按钮
        common_subtitle = (TextView) findViewById(R.id.common_subtitle);
        common_subtitle.setVisibility(View.VISIBLE);
        common_subtitle.setText(Constant.complete);
        fragment = (AddCloudNoteFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = AddCloudNoteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
            fragment.setUploadNoteListener(this);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerAddCloudNoteComponent.builder().
                appComponent(getAppComponent()).
                addCloudNotePresenterModule(new AddCloudNotePresenterModule(fragment)).
                build().
                inject(this);
    }

    @Override
    protected void initListener() {
        common_subtitle.setOnClickListener(onceClickListener);
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
                 * 完成按钮（上传笔记）
                 */
                case R.id.common_subtitle:
                    fragment.uploadNote();
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
    public void success() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected boolean keyBoardEnabled() {
        return true;
    }
}
