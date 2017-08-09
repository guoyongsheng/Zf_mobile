package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.DisCoveryTypeInfo;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.looking_for_notice.LookingForNoticeFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.looking_for_notice.LookingForNoticePresenter;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.looking_for_notice.LookingForNoticePresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found.LostAndFoundFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found.LostAndFoundPresenter;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found.LostAndFoundPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 发布信息界面
 */

public class ReleaseNewsActivity extends BaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private TextView tv_lost_and_found; //失物招领
    private TextView tv_look_for_notice; //寻物启事
    private ViewPager viewPager;
    private ReleaseNewsPagerAdapter adapter; //适配器
    private LostAndFoundFragment lostAndFoundFragment; //失物招领
    private LookingForNoticeFragment lookingForNoticeFragment; //寻物启事
    private int currentItem; // 0: 失物招领 1: 寻物启事

    @Inject
    LostAndFoundPresenter lostAndFoundPresenter;

    @Inject
    LookingForNoticePresenter lookingForNoticePresenter;

    @Override
    protected void initVariables() {
        adapter = new ReleaseNewsPagerAdapter(getSupportFragmentManager());
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_release_news;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.release_news);
        setDisplayHomeAsUpEnabled(true);

        tv_lost_and_found = (TextView) findViewById(R.id.release_news_lost_and_found);
        tv_look_for_notice = (TextView) findViewById(R.id.release_news_look_for_notice);
        viewPager = (ViewPager) findViewById(R.id.release_news_view_pager);
        viewPager.setAdapter(adapter);

        lostAndFoundFragment = LostAndFoundFragment.newInstance();
        lookingForNoticeFragment = LookingForNoticeFragment.newInstance();
        List<Fragment> list = new ArrayList<>();
        list.add(lostAndFoundFragment);
        list.add(lookingForNoticeFragment);
        adapter.setDataSource(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setUpInject() {
        DaggerReleaseNewsComponent.builder()
                .appComponent(getAppComponent())
                .lostAndFoundPresenterModule(new LostAndFoundPresenterModule(lostAndFoundFragment))
                .lookingForNoticePresenterModule(new LookingForNoticePresenterModule(lookingForNoticeFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        tv_lost_and_found.setOnClickListener(this);
        tv_look_for_notice.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    public void onClick(View view) {

        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         *  失物招领
         */
        case R.id.release_news_lost_and_found:
            if (currentItem == 0) {
                return;
            }
            updateUI(0);
            break;

        /*
         * 寻物启事
         */
        case R.id.release_news_look_for_notice:
            if (currentItem == 1) {
                return;
            }
            updateUI(1);
            break;

        default:
            break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateUI(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //跟新ui
    private void updateUI(int currentItem) {
        this.currentItem = currentItem;
        switch (currentItem) {
        /*
         * 失物招领
         */
        case 0:
            tv_lost_and_found.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            tv_lost_and_found.setBackgroundResource(R.drawable.drawable_release_news_lost_found_selected);
            tv_look_for_notice.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tv_look_for_notice.setBackgroundResource(R.drawable.drawable_release_news_look_for_notice_no_selected);
            break;


        /*
         * 寻物启事
         */
        case 1:
            tv_lost_and_found.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tv_lost_and_found.setBackgroundResource(R.drawable.drawable_release_news_lost_found_no_selected);
            tv_look_for_notice.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            tv_look_for_notice.setBackgroundResource(R.drawable.drawable_release_news_look_for_notice_selected);
            break;

        default:
            break;
        }
        viewPager.setCurrentItem(currentItem, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lost_and_found, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 有学校招领处
         */
        case R.id.lost_and_found_select_type_have:
            updateDisCoveryTypeInfo(1);
            return true;

        /*
         * 没有学校招领处
         */
        case R.id.lost_and_found_select_type_no:
            updateDisCoveryTypeInfo(2);
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    //跟新数据
    private void updateDisCoveryTypeInfo(int type) {
        DisCoveryTypeInfo info = new DisCoveryTypeInfo();
        info.setType(type);
        DbHelper.saveValueBySharedPreferences(this, Config.DB.DISCOVERY_TYPE_NAME, Config.DB.DISCOVERY_TYPE_KEY, info);

        lostAndFoundFragment.updateUI();
        lookingForNoticeFragment.updateUI();
    }
}
