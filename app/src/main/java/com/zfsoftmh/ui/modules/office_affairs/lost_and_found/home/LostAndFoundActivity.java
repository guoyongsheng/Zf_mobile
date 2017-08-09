package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.has_been_accepted.HasBeenAcceptedFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.has_been_accepted.HasBeenAcceptedPresenter;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.has_been_accepted.HasBeenAcceptedPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.not_accepted.NotAcceptedFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.not_accepted.NotAcceptedPresenter;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.not_accepted.NotAcceptedPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased.MyReleasedActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.ReleaseNewsActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 失物招领界面
 */

public class LostAndFoundActivity extends BaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, FloatingActionMenu.MenuStateChangeListener {
    private ViewPager viewPager;
    private ImageView floatingActionButton;
    private LostAndFoundPagerAdapter adapter;
    private NotAcceptedFragment notAcceptedFragment; //未招領
    private HasBeenAcceptedFragment hasBeenAcceptedFragment; //已招領
    private TextView tv_not_accepted; //未招領
    private TextView tv_has_been_accepted; //已招領
    private int currentItem = 0;
    private FloatingActionMenu actionMenu;

    @Inject
    NotAcceptedPresenter notAcceptedPresenter;

    @Inject
    HasBeenAcceptedPresenter hasBeenAcceptedPresenter;

    @Override
    protected void initVariables() {
        FragmentManager manager = getSupportFragmentManager();
        adapter = new LostAndFoundPagerAdapter(manager);
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_lost_and_found;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("");
        setDisplayHomeAsUpEnabled(true);

        tv_not_accepted = (TextView) findViewById(R.id.lost_and_found_no_found);
        tv_has_been_accepted = (TextView) findViewById(R.id.lost_and_found_found);
        viewPager = (ViewPager) findViewById(R.id.lost_and_found_view_pager);
        viewPager.setAdapter(adapter);

        List<Fragment> list = new ArrayList<>();
        notAcceptedFragment = NotAcceptedFragment.newInstance();
        hasBeenAcceptedFragment = HasBeenAcceptedFragment.newInstance();
        list.add(notAcceptedFragment);
        list.add(hasBeenAcceptedFragment);
        adapter.setDataSource(list);

        initFloatActionMenu();
    }

    //初始化FloatActionMenu
    private void initFloatActionMenu() {
        int actionButtonSize = getResources().getDimensionPixelSize(R.dimen.common_float_action_button_size_normal);
        int margin = getResources().getDimensionPixelSize(R.dimen.common_margin_left);
        int menuSize = getResources().getDimensionPixelSize(R.dimen.common_float_action_button_size_min);
        floatingActionButton = new ImageView(this);
        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_icon_lost_and_foundadd));
        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(actionButtonSize, actionButtonSize);
        starParams.setMargins(margin, margin, margin, margin);
        floatingActionButton.setLayoutParams(starParams);
        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(actionButtonSize, actionButtonSize);
        //fabIconStarParams.setMargins(margin, margin, margin, margin);
        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton =
                new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton
                        .Builder(this)
                        .setContentView(floatingActionButton, fabIconStarParams)
                        .setLayoutParams(starParams)
                        .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        //blueContentParams.setMargins(margin, margin, margin, margin);
        itemBuilder.setLayoutParams(blueContentParams);
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(menuSize, menuSize);
        itemBuilder.setLayoutParams(blueParams);

        //search
        ImageView itemSearch = new ImageView(this);
        itemSearch.setImageResource(R.mipmap.ic_icon_lost_and_found_search);
        SubActionButton btn_search = itemBuilder.setContentView(itemSearch, blueContentParams).build();
        onSearchClick(itemSearch);

        //release_news
        ImageView itemReleaseNews = new ImageView(this);
        itemReleaseNews.setImageResource(R.mipmap.ic_icon_lost_and_found_edit);
        SubActionButton btn_release_news = itemBuilder.setContentView(itemReleaseNews, blueContentParams).build();
        onReleasedNewsClick(itemReleaseNews);

        //my_released
        ImageView itemMyReleased = new ImageView(this);
        itemMyReleased.setImageResource(R.mipmap.ic_icon_lost_and_found_list);
        SubActionButton btn_my_released = itemBuilder.setContentView(itemMyReleased, blueContentParams).build();
        onMyReleasedClick(itemMyReleased);

        actionMenu = new FloatingActionMenu
                .Builder(this)
                .addSubActionView(btn_my_released)
                .addSubActionView(btn_release_news)
                .addSubActionView(btn_search)
                .attachTo(actionButton)
                .build();

        actionMenu.setStateChangeListener(this);
    }

    @Override
    protected void setUpInject() {
        DaggerLostAndFoundComponent.builder()
                .appComponent(getAppComponent())
                .hasBeenAcceptedPresenterModule(new HasBeenAcceptedPresenterModule(hasBeenAcceptedFragment))
                .notAcceptedPresenterModule(new NotAcceptedPresenterModule(notAcceptedFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        tv_not_accepted.setOnClickListener(this);
        tv_has_been_accepted.setOnClickListener(this);
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
         * 未招領
         */
        case R.id.lost_and_found_no_found:
            if (currentItem == 0) {
                return;
            }
            onItemSelected(0);
            break;

        /*
         * 已招領
         */
        case R.id.lost_and_found_found:
            if (currentItem == 1) {
                return;
            }
            onItemSelected(1);
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
        onItemSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //选中item
    private void onItemSelected(int currentItem) {
        this.currentItem = currentItem;
        switch (currentItem) {
        /*
         *  未招领
         */
        case 0:
            notAcceptedSelected();
            break;

        /*
         *  已招領
         */
        case 1:
            hasBeenAcceptedSelected();
            break;

        default:
            break;
        }
        viewPager.setCurrentItem(currentItem, true);
    }

    //点击了未选中
    private void notAcceptedSelected() {
        tv_not_accepted.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tv_has_been_accepted.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        tv_not_accepted.setBackgroundResource(R.drawable.drawable_lost_and_found_not_found_select);
        tv_has_been_accepted.setBackgroundResource(R.drawable.drawable_lost_and_found_no_select);
    }

    //点击了以选中
    private void hasBeenAcceptedSelected() {
        tv_not_accepted.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        tv_has_been_accepted.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tv_not_accepted.setBackgroundResource(R.drawable.drawable_lost_and_found_not_found_no_select);
        tv_has_been_accepted.setBackgroundResource(R.drawable.drawable_lost_and_found_select);
    }

    //点击了搜索
    private void onSearchClick(ImageView iv_search) {
        if (iv_search == null) {
            return;
        }

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
                Intent intent = new Intent(LostAndFoundActivity.this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("currentItem", currentItem);
                intent.putExtras(bundle);
                openActivity(intent);
            }
        });
    }

    //点击了发布信息
    private void onReleasedNewsClick(ImageView iv_released) {
        if (iv_released == null) {
            return;
        }

        iv_released.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
                startActivity(ReleaseNewsActivity.class);
            }
        });
    }

    //点击了我的发布
    private void onMyReleasedClick(ImageView iv_my_released) {
        if (iv_my_released == null) {
            return;
        }

        iv_my_released.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
                startActivity(MyReleasedActivity.class);
            }
        });
    }

    //关闭菜单
    private void closeMenu() {
        if (actionMenu != null && actionMenu.isOpen()) {
            actionMenu.close(true);
        }
    }

    @Override
    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
        floatingActionButton.setRotation(0);
        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(floatingActionButton, pvhR);
        animation.start();
    }

    @Override
    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
        floatingActionButton.setRotation(45);
        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(floatingActionButton, pvhR);
        animation.start();
    }
}
