package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.ScheduleManagementInfo;

import java.util.List;



/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: 适配器
 */

class ScheduleManagementAdapter extends RecyclerView.Adapter<ScheduleManagementAdapter.ItemViewHolder> {

    private List<ScheduleManagementInfo> list;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    ScheduleManagementAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_schedule_management, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        ScheduleManagementInfo info = list.get(position);
        final long id = info.getId(); //id
        String title = info.getTitle(); //标题
        String start_time = info.getStart_time(); //开始时间
        int repeat_time = info.getRepeat_type(); //重复类型

        holder.tv_title.setText(title);
        holder.tv_start_time.setText(start_time);

        switch (repeat_time) {
        /*
         * 提醒1次
         */
        case 0:
            holder.tv_repeat_time.setText(R.string.dialog_repeat_times_1);
            break;

        /*
         * 每5分钟提醒一次
         */
        case 1:
            holder.tv_repeat_time.setText(R.string.dialog_repeat_times_5);
            break;

        /*
         * 每10分钟提醒一次
         */
        case 2:
            holder.tv_repeat_time.setText(R.string.dialog_repeat_times_10);
            break;

        /*
         * 每15分钟提醒一次
         */
        case 3:
            holder.tv_repeat_time.setText(R.string.dialog_repeat_times_15);
            break;

        default:
            break;
        }


        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setDataSource(List<ScheduleManagementInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //静态内部类
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //布局
        private TextView tv_title; //我的日程标题
        private TextView tv_start_time; //开始时间
        private TextView tv_repeat_time; //重复次数

        ItemViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_schedule_manage_title);
            tv_start_time = (TextView) itemView.findViewById(R.id.item_schedule_manage_start_time);
            tv_repeat_time = (TextView) itemView.findViewById(R.id.item_schedule_manage_repeat_time);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_schedule_manage_rl);
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(long id);
    }
}
