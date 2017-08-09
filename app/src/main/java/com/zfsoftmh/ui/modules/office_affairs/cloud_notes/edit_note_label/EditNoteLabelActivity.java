package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/5/23
 * @Description: 笔记标签编辑页
 */

public class EditNoteLabelActivity extends BaseActivity {
    private FragmentManager manager;
    private EditNoteLabelFragment fragment;
    private TextView common_subtitle;
    private ArrayList<NoteLabelItemInfo> dataList;

    @Inject
    EditNoteLabelPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        dataList = bundle.getParcelableArrayList("dataList");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.label_edit);
        setDisplayHomeAsUpEnabled(true);
        common_subtitle = (TextView) findViewById(R.id.common_subtitle);
        common_subtitle.setVisibility(View.VISIBLE);
        common_subtitle.setText(Constant.complete);
        fragment = (EditNoteLabelFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = EditNoteLabelFragment.newInstance(dataList);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerEditNoteLabelComponent.builder()
                .appComponent(getAppComponent())
                .editNoteLabelPresenterModule(new EditNoteLabelPresenterModule(fragment))
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
                 * 编辑按钮
                 */
                case R.id.common_subtitle:
                    fragment.skipLabelPage();
                    break;
            }
        }
    };

}
