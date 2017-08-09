package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.CloudNoteListInfo;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/5/26
 * @Description:
 */

public class NoteDetailActivity extends BaseActivity{

    private FragmentManager manager;
    private NoteDetailFragment fragment;
    private TextView common_subtitle;
    private CloudNoteListInfo info = null;

    @Inject
    NoteDetailPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        info = bundle.getParcelable("cloudNoteList");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.cloud_note_detail_title);
        setDisplayHomeAsUpEnabled(true);

        // 初始化完成按钮
        common_subtitle = (TextView) findViewById(R.id.common_subtitle);
        common_subtitle.setVisibility(View.VISIBLE);
        common_subtitle.setText(Constant.complete);
        fragment = (NoteDetailFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = NoteDetailFragment.newInstance(info);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }

    }

    @Override
    protected void setUpInject() {
        DaggerNoteDetailComponent.builder()
                .appComponent(getAppComponent())
                .noteDetailPresenterModule(new NoteDetailPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        common_subtitle.setOnClickListener(onceClickListener);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
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
                 * 完成按钮（上传笔记）
                 */
                case R.id.common_subtitle:
                    fragment.uploadNote();
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        info = null;
        fragment = null;
    }
}
