package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

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
 * 创建时间： 2017/5/11 0011
 * 编写人：王世美
 * 功能描述：笔记标签列表
 */

public class NoteLabelActivity extends BaseActivity {

    private FragmentManager manager;
    private NoteLabelFragment fragment;
    private TextView common_subtitle;
    private String from; // 上一个页面跳转标志

    @Inject
    NoteLabelPresenter presenter;

    @Override
    protected void initVariables() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        from = bundle.getString("from");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.label_title);
        setDisplayHomeAsUpEnabled(true);
        common_subtitle = (TextView) findViewById(R.id.common_subtitle);
        common_subtitle.setVisibility(View.VISIBLE);
        common_subtitle.setText(Constant.edit);
        fragment = (NoteLabelFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = NoteLabelFragment.newInstance(from);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }
    }

    @Override
    protected void setUpInject() {
        DaggerNoteLabelComponent.builder()
                .appComponent(getAppComponent())
                .noteLabelPresenterModule(new NoteLabelPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        common_subtitle.setOnClickListener(onceClickListener);
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
                    fragment.skipEditLabelPage();
                    break;
            }
        }
    };

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

}
