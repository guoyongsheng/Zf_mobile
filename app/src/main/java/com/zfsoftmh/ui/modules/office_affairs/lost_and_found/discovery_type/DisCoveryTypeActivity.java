package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.discovery_type;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.DisCoveryTypeInfo;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.LostAndFoundActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;

/**
 * @author wesley
 * @date: 2017-6-22
 * @Description: 招领类型界面
 */

public class DisCoveryTypeActivity extends BaseActivity {

    private TextView tv_have_discovery; //有学校招领处
    private TextView tv_no_discovery; //没有学校招领处

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {

        if (checkHasSelected()) {
            openActivity();
        }
        return R.layout.activity_discovery_type;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.discovery_type);
        setDisplayHomeAsUpEnabled(true);

        tv_have_discovery = (TextView) findViewById(R.id.discovery_type_have);
        tv_no_discovery = (TextView) findViewById(R.id.discovery_type_no);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {
        tv_have_discovery.setOnClickListener(new OnClickListener());
        tv_no_discovery.setOnClickListener(new OnClickListener());
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    //点击事件
    private class OnClickListener extends OnceClickListener {

        @Override
        public void onOnceClick(View view) {

            if (view == null) {
                return;
            }

            int key = view.getId();
            switch (key) {
            /*
             * 有学校招领处
             */
            case R.id.discovery_type_have:
                saveDisCoveryTypeInfo(1);
                openActivity();
                break;

            /*
             *  没有学校招领处
             */
            case R.id.discovery_type_no:
                saveDisCoveryTypeInfo(2);
                openActivity();
                break;

            default:
                break;
            }
        }
    }

    //保存类型
    private void saveDisCoveryTypeInfo(int type) {
        DisCoveryTypeInfo info = new DisCoveryTypeInfo();
        info.setType(type);
        DbHelper.saveValueBySharedPreferences(this, Config.DB.DISCOVERY_TYPE_NAME,
                Config.DB.DISCOVERY_TYPE_KEY, info);
    }

    //获取失物招领的类型
    private int getDisCoveryType() {
        DisCoveryTypeInfo info = DbHelper.getValueBySharedPreferences(this, Config.DB.DISCOVERY_TYPE_NAME,
                Config.DB.DISCOVERY_TYPE_KEY, DisCoveryTypeInfo.class);
        if (info == null) {
            return 0;
        }
        return info.getType();
    }

    //判断是否已经选择过了
    private boolean checkHasSelected() {
        int type = getDisCoveryType();
        return type != 0;
    }

    //界面跳转
    private void openActivity() {
        Intent intent = new Intent(DisCoveryTypeActivity.this, LostAndFoundActivity.class);
        openActivity(intent);
        finish();
    }
}
