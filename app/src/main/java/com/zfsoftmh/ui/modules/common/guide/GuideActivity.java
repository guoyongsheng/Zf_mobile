package com.zfsoftmh.ui.modules.common.guide;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.Once;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.common.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: 引导页
 */
public class GuideActivity extends BaseActivity implements GuideAdapter.onBeginClickListener{

    private GuideAdapter adapter;
    @Override
    protected void initVariables() {
        setFirstTimeInIsFalse();
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.guide_image_icon_one);
        list.add(R.mipmap.guide_image_icon_two);
        list.add(R.mipmap.guide_image_icon_three);
        list.add(R.mipmap.guide_image_icon_four);
        adapter = new GuideAdapter(this, list);
        adapter.setOnBeginClickListener(this);
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.actiivty_guide;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ViewPager view_pager = (ViewPager) findViewById(R.id.guide_view_pager);
        view_pager.setAdapter(adapter);
        // 默认字体大小
        SharedPreferenceUtils.write(GuideActivity.this, "fontSize", "fontSize", (float) 1.00);
        // 默认字体坐标
        SharedPreferenceUtils.write(GuideActivity.this, "mCurrentIndex", "mCurrentIndex", 2);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBeginClick() {
        startActivity(HomeActivity.class);
        finish();
    }

    //改变是否是第一次登录的状态
    private void setFirstTimeInIsFalse() {
        Once once = new Once();
        once.setFirstTimeIn(false);
        DbHelper.saveValueBySharedPreferences(this, Config.DB.IS_FIRST_TIME_IN_NAME, Config.DB.IS_FIRST_TIME_IN_KEY, once);
    }

    @Override
    protected boolean immersionEnabled() {
        return false;
    }

}
