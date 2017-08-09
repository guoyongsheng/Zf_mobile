package com.zfsoftmh.ui.modules.personal_affairs.set.feedback.suggestions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/6/6
 * @Description: 我要吐槽
 */

public class SuggestionsActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager manager;
    private SuggestionsFragment fragment;
    private TextView common_subtitle;

    @Inject
    SuggestionsPresenter presenter;

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
        setToolbarTitle(R.string.shits);
        setDisplayHomeAsUpEnabled(true);

        common_subtitle = (TextView) findViewById(R.id.common_subtitle);
        common_subtitle.setVisibility(View.VISIBLE);
        common_subtitle.setText(Constant.commit);

        fragment = (SuggestionsFragment) manager.findFragmentById(R.id.common_content);
        if (fragment == null) {
            fragment = SuggestionsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.common_content);
        }

    }

    @Override
    protected void setUpInject() {
        DaggerSuggestionsComponent.builder()
                .appComponent(getAppComponent())
                .suggestionsPresenterModule(new SuggestionsPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        common_subtitle.setOnClickListener(this);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int key = v.getId();
        switch (key) {
            /**
             * 完成按钮（上传笔记）
             */
            case R.id.common_subtitle:
                fragment.commitSuggestions();
                break;
        }
    }
}
