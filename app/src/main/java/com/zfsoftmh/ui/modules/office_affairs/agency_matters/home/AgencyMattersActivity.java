package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency.AgencyMattersFragment;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency.AgencyMattersPresenter;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency.AgencyMattersPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done.HasBeenDoneMattersFragment;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done.HasBeenDoneMattersPresenter;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done.HasBeenDoneMattersPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement.SettlementMattersFragment;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement.SettlementMattersPresenter;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement.SettlementMattersPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017/3/30
 * @Description: 待办事宜界面
 */

public class AgencyMattersActivity extends BaseActivity implements SearchView.OnQueryTextListener,
        AgencyMattersDialogFragment.OnItemClickListener {

    private static final String TAG = "AgencyMattersActivity";
    private AppBarLayout.LayoutParams params;
    private List<String> listTitle;
    private List<Fragment> listFragment;
    private FragmentManager manager;
    private AgencyMattersFragment agencyMattersFragment; //待办事宜的Fragment
    private HasBeenDoneMattersFragment hasBeenDoneMattersFragment; //已办事宜
    private SettlementMattersFragment settlementMattersFragment; //办结事宜
    private int currentItem; //当前的item 0：待办事宜  1: 已办事宜  2: 办结事宜

    @Inject
    AgencyMattersPresenter agencyMattersPresenter;

    @Inject
    HasBeenDoneMattersPresenter hasBeenDoneMattersPresenter;

    @Inject
    SettlementMattersPresenter settlementMattersPresenter;

    @Override
    protected void initVariables() {
        params = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT,
                AppBarLayout.LayoutParams.WRAP_CONTENT);
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

        manager = getSupportFragmentManager();
        initPagerTitle();
    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        currentItem = bundle.getInt("currentItem", 0);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_agency_matters;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //toolbar.setLayoutParams(params);
        setToolbarTitle(R.string.agency_matters);
        setDisplayHomeAsUpEnabled(true);

        //初始化TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.agency_matters)); //代办事宜
        tabLayout.addTab(tabLayout.newTab().setText(R.string.has_been_done_affairs)); //已办事宜
        tabLayout.addTab(tabLayout.newTab().setText(R.string.finished_affairs)); // 办结事宜

        agencyMattersFragment = AgencyMattersFragment.newInstance();
        hasBeenDoneMattersFragment = HasBeenDoneMattersFragment.newInstance();
        settlementMattersFragment = SettlementMattersFragment.newInstance();

        initListFragment();

        //初始化ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_agency_view_pager);
        AgencyMattersPagerAdapter adapter = new AgencyMattersPagerAdapter(manager, listFragment, listTitle);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentItem);
        tabLayout.setupWithViewPager(viewPager);
    }

    //初始化ListFragment
    private void initListFragment() {
        listFragment = new ArrayList<>();
        listFragment.add(agencyMattersFragment);
        listFragment.add(hasBeenDoneMattersFragment);
        listFragment.add(settlementMattersFragment);
    }

    //初始化ListTitle
    private void initPagerTitle() {
        listTitle = new ArrayList<>();
        listTitle.add(getResources().getString(R.string.agency_matters));
        listTitle.add(getResources().getString(R.string.has_been_done_affairs));
        listTitle.add(getResources().getString(R.string.finished_affairs));
    }

    @Override
    protected void setUpInject() {
        DaggerAgencyMattersComponent.builder()
                .appComponent(getAppComponent())
                .agencyMattersPresenterModule(new AgencyMattersPresenterModule(agencyMattersFragment))
                .hasBeenDoneMattersPresenterModule(new HasBeenDoneMattersPresenterModule(hasBeenDoneMattersFragment))
                .settlementMattersPresenterModule(new SettlementMattersPresenterModule(settlementMattersFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agency_matters, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        SearchView.SearchAutoComplete textView = ( SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        case R.id.menu_search:
            return true;

        case R.id.menu_select:
            showBottomSheetDialog();
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    //显示底部对话框
    private void showBottomSheetDialog() {
        AgencyMattersDialogFragment fragment = AgencyMattersDialogFragment.newInstance();
        fragment.setOnItemClickListener(this);
        fragment.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onItemClick(int position) {
        showToastMsgShort(position + "");
    }
}
