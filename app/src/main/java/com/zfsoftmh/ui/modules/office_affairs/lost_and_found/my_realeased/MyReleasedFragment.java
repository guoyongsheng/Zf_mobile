package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.my_realeased;

import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.LostAndFoundItemInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.LostAndFoundAdapter;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: ui
 */

public class MyReleasedFragment extends BaseListFragment<MyReleasedPresenter, LostAndFoundItemInfo>
        implements MyReleasedContract.View {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected RecyclerArrayAdapter<LostAndFoundItemInfo> getAdapter() {
        return new LostAndFoundAdapter(context, getUserId());
    }

    @Override
    protected void loadData() {
        presenter.loadData(start_page, PAGE_SIZE, 1, getUserId(), DbHelper.getAppToken(context));
    }

    @Override
    public void onItemClick(int position) {

    }

    public static MyReleasedFragment newInstance() {
        return new MyReleasedFragment();
    }
}
