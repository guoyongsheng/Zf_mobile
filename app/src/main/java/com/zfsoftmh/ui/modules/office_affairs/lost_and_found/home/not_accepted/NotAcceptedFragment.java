package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home.not_accepted;

import android.content.Intent;
import android.os.Bundle;

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
 * @date: 2017-5-27
 * @Description: 未招领ui
 */

public class NotAcceptedFragment extends BaseListFragment<NotAcceptedPresenter, LostAndFoundItemInfo> implements
        NotAcceptedContract.View {

    private LostAndFoundAdapter adapter;

    @Override
    protected RecyclerArrayAdapter<LostAndFoundItemInfo> getAdapter() {
        adapter = new LostAndFoundAdapter(context, getUserId());
        return adapter;
    }

    @Override
    protected void loadData() {
        presenter.loadData(start_page, PAGE_SIZE, 0, DbHelper.getAppToken(context));
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
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static NotAcceptedFragment newInstance() {
        return new NotAcceptedFragment();
    }
}
