package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.entity.MeetingManagementInfo;
import com.zfsoftmh.ui.base.BaseListFragment;

/**
 * @author wesley
 * @date: 2017-6-14
 * @Description: 会议管理ui
 */

public class MeetingManagementFragment extends BaseListFragment<MeetingManagementPresenter,
        MeetingManagementInfo> implements MeetingManagementContract.View {

    @Override
    protected RecyclerArrayAdapter<MeetingManagementInfo> getAdapter() {
        return new MeetingManagementAdapter(context);
    }

    @Override
    protected void loadData() {
        presenter.loadData(start_page, PAGE_SIZE, 1);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static MeetingManagementFragment newInstance() {
        return new MeetingManagementFragment();
    }
}
