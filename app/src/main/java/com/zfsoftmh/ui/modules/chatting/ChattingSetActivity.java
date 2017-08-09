package com.zfsoftmh.ui.modules.chatting;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * Created by sy
 * on 2017/5/26.
 * <p>聊天设置</p>
 */
public class ChattingSetActivity extends BaseActivity{

    @Override
    protected void initVariables() { }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) { }

    @Override
    protected int getLayoutResID() {
        return R.layout.common;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("聊天设置");
        setDisplayHomeAsUpEnabled(true);
        ChattingSetFragment fragment = ChattingSetFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.common_content,fragment).commit();
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void initListener() { }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
