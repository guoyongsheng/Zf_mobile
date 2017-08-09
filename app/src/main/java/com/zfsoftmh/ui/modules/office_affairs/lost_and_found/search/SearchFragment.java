package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.LostAndFoundAdapter;
import com.zfsoftmh.ui.modules.web.WebActivity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 失物招领ui
 */

public class SearchFragment extends BaseListFragment<SearchPresenter, LostAndFoundItemInfo> implements SearchContract.View,
        SearchView.OnQueryTextListener {

    private int currentItem; // 0: 未招领 1：已招领
    private LostAndFoundAdapter adapter;

    @Override
    protected void initVariables() {
        setHasOptionsMenu(true);
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        currentItem = bundle.getInt("currentItem");
    }

    @Override
    protected RecyclerArrayAdapter<LostAndFoundItemInfo> getAdapter() {
        setProgressEnable(false);
        setRefreshEnable(false);
        adapter = new LostAndFoundAdapter(context, getUserId());
        return adapter;
    }

    @Override
    protected void loadData() {

    }

    public static SearchFragment newInstance(int currentItem) {
        Bundle bundle = new Bundle();
        bundle.putInt("currentItem", currentItem);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_forward_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        textView.setHintTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        switch (currentItem) {
        /*
         * 未招领
         */
        case 0:
            textView.setHint(R.string.search_lost_and_found);
            break;

        /*
         * 已招领
         */
        case 1:
            textView.setHint(R.string.search_has_been_accepted);
            break;

        default:
            break;
        }
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 搜索
         */
        case R.id.menu_forward_search:
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (checkTitleIsNull(query)) {
            showToastMsgShort(getResources().getString(R.string.please_input_search_content));
            return false;
        }

        presenter.loadData(start_page, PAGE_SIZE, query, currentItem, getUserId(), DbHelper.getAppToken(context));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(int position) {
        List<LostAndFoundItemInfo> list = adapter.getAllData();
        if (list != null && list.size() > position && list.get(position) != null) {
            String url = list.get(position).getDetaiURL();
            Intent intent = new Intent(context, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Config.WEB.URL, url);
            bundle.putString(Config.WEB.TITLE, getResources().getString(R.string.lost_found));
            intent.putExtras(bundle);
            openActivity(intent);
        }
    }

    @Override
    public boolean checkTitleIsNull(String title) {
        return title == null || title.length() == 0;
    }

    @Override
    public void showDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }
}
