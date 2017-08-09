package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.ScheduleManagementInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: 适配器
 */

class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ItemViewHolder>
        implements StickyRecyclerHeadersAdapter<ScheduleListAdapter.HeaderViewHolder> {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String TAG = "ScheduleListAdapter";
    private LayoutInflater inflater;
    private List<ScheduleManagementInfo> list;
    private OnItemClickListener listener;

    ScheduleListAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_schedule_list, parent, false);
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

        holder.tv_day.setText(getDay(start_time));
        holder.tv_week.setText(getWeek(start_time));
        holder.tv_title.setText(title);
        holder.tv_time.setText(getHour(start_time) + ":" + getMinute(start_time) + ":" + getSecond(start_time));

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
    public long getHeaderId(int position) {
        if (list != null && list.size() > position && list.get(position) != null) {
            return list.get(position).getHeaderId();
        }
        return -1;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_phone_contacts_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        ScheduleManagementInfo info = list.get(position);
        String header_time = info.getHead_time();
        holder.tv_time.setText(header_time);
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

    /**
     * 静态内部类
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //布局
        private TextView tv_day; //一个月的多少号
        private TextView tv_week; //周几
        private TextView tv_title; //标题
        private TextView tv_time; //时间

        ItemViewHolder(View itemView) {
            super(itemView);

            tv_day = (TextView) itemView.findViewById(R.id.item_schedule_list_day);
            tv_week = (TextView) itemView.findViewById(R.id.item_schedule_list_week);
            tv_title = (TextView) itemView.findViewById(R.id.item_schedule_list_title);
            tv_time = (TextView) itemView.findViewById(R.id.item_schedule_list_time);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_schedule_list_rl);
        }
    }

    /**
     * 头部
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_time; // 时间

        HeaderViewHolder(View itemView) {
            super(itemView);

            tv_time = (TextView) itemView.findViewById(R.id.item_phone_contacts_head_value);
        }
    }

    /**
     * 根据开始时间获取是多少号
     *
     * @param value 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 天
     */
    private String getDay(String value) {

        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        try {
            Date date = format.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH) + "";
        } catch (ParseException e) {
            LoggerHelper.e(TAG, " getDay " + e.getMessage());
        }
        return "1";
    }

    /**
     * 根据开始时间获取是星期几
     *
     * @param value 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 星期几
     */
    private String getWeek(String value) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        Date date;
        try {
            date = simpleDateFormat.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (week_index < 0) {
                week_index = 0;
            }
            return weeks[week_index];
        } catch (ParseException e) {
            LoggerHelper.e(TAG, " getWeek " + e.getMessage());
        }
        return "1";

    }


    /**
     * 根据开始时间获取是小时
     *
     * @param value 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 小时
     */
    private String getHour(String value) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        Date date;
        try {
            date = simpleDateFormat.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR_OF_DAY) + "";
        } catch (ParseException e) {
            LoggerHelper.e(TAG, " getHour " + e.getMessage());
        }
        return "00";
    }


    /**
     * 根据开始时间获取分钟
     *
     * @param value 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 分钟
     */
    private String getMinute(String value) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        Date date;
        try {
            date = simpleDateFormat.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MINUTE) + "";
        } catch (ParseException e) {
            LoggerHelper.e(TAG, " getMinute " + e.getMessage());
        }
        return "00";
    }


    /**
     * 根据开始时间获取秒钟
     *
     * @param value 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 秒钟
     */
    private String getSecond(String value) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        Date date;
        try {
            date = simpleDateFormat.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.SECOND) + "";
        } catch (ParseException e) {
            LoggerHelper.e(TAG, " getSecond " + e.getMessage());
        }
        return "00";
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(long id);
    }
}
