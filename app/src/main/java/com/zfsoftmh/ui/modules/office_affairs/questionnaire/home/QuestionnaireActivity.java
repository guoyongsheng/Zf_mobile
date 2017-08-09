package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home;

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
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.add_questionnaire.AddQuestionnaireActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published.PublishedFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published.PublishedPresenter;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published.PublishedPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published.UnPublishedFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published.UnPublishedPresenter;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published.UnPublishedPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/5/8
 * @Description: 问卷调查界面
 */

public class QuestionnaireActivity extends BaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private TextView tv_published; //已发布
    private TextView tv_un_published; //未发布
    private ViewPager viewPager;
    private QuestionnairePagerAdapter adapter; //适配器
    private PublishedFragment publishedFragment; //已发布
    private UnPublishedFragment unPublishedFragment; //未发布
    private int currentItem; // 0: 已发布 1: 未发布

    @Inject
    PublishedPresenter publishedPresenter;

    @Inject
    UnPublishedPresenter unPublishedPresenter;

    @Override
    protected void initVariables() {
        adapter = new QuestionnairePagerAdapter(getSupportFragmentManager());
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

        tv_published = (TextView) findViewById(R.id.lost_and_found_no_found);
        tv_un_published = (TextView) findViewById(R.id.lost_and_found_found);
        viewPager = (ViewPager) findViewById(R.id.lost_and_found_view_pager);

        tv_published.setText(R.string.published);
        tv_un_published.setText(R.string.un_published);
        viewPager.setAdapter(adapter);

        publishedFragment = PublishedFragment.newInstance();
        unPublishedFragment = UnPublishedFragment.newInstance();
        List<Fragment> list = new ArrayList<>();
        list.add(publishedFragment);
        list.add(unPublishedFragment);
        adapter.setDataSource(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setUpInject() {
        DaggerQuestionnaireComponent.builder()
                .appComponent(getAppComponent())
                .publishedPresenterModule(new PublishedPresenterModule(publishedFragment))
                .unPublishedPresenterModule(new UnPublishedPresenterModule(unPublishedFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {
        tv_published.setOnClickListener(this);
        tv_un_published.setOnClickListener(this);
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
         * 已发布
         */
        case R.id.lost_and_found_no_found:
            if (currentItem == 0) {
                return;
            }
            refreshUI(0);
            break;

        /*
         * 未发布
         */
        case R.id.lost_and_found_found:
            if (currentItem == 1) {
                return;
            }
            refreshUI(1);
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
        refreshUI(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questionnaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 创建问卷调查
         */
        case R.id.menu_questionnaire_add:
            startActivity(AddQuestionnaireActivity.class);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    //刷新UI
    private void refreshUI(int currentItem) {
        this.currentItem = currentItem;
        switch (currentItem) {
        /*
         * 已发布
         */
        case 0:
            tv_published.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tv_un_published.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            tv_published.setBackgroundResource(R.drawable.drawable_lost_and_found_not_found_select);
            tv_un_published.setBackgroundResource(R.drawable.drawable_lost_and_found_no_select);
            break;

        /*
         * 未发布
         */
        case 1:
            tv_published.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            tv_un_published.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tv_published.setBackgroundResource(R.drawable.drawable_lost_and_found_not_found_no_select);
            tv_un_published.setBackgroundResource(R.drawable.drawable_lost_and_found_select);
            break;

        default:
            break;
        }
        viewPager.setCurrentItem(currentItem, true);
    }
}
