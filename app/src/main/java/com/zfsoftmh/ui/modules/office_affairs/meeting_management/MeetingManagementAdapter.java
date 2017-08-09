package com.zfsoftmh.ui.modules.office_affairs.meeting_management;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.MeetingManagementInfo;

/**
 * @author wesley
 * @date: 2017-6-14
 * @Description: 适配器
 */

class MeetingManagementAdapter extends RecyclerArrayAdapter<MeetingManagementInfo> {

    MeetingManagementAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_meeting_management);
    }

    //内部类
    private static class ItemViewHolder extends BaseViewHolder<MeetingManagementInfo> {

        private TextView tv_title; //标题
        private TextView tv_week; //周几
        private TextView tv_time; //时间

        public ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            tv_title = $(R.id.item_meeting_title);
            tv_week = $(R.id.item_meeting_week);
            tv_time = $(R.id.item_meeting_times);
        }

        @Override
        public void setData(MeetingManagementInfo data) {
            super.setData(data);
        }
    }
}
