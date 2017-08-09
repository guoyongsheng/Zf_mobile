package com.zfsoftmh.ui.modules.common.home.home;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherLive;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.TimeUtils;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.entity.SemesterWeekInfo;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wesley
 * @date: 2017-6-12
 * @Description: 首页适配器
 */

class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0; //header
    private static final int TYPE_ITEM_REMIND_TODAY = 1; //今日提醒
    private static final int TYPE_ITEM_SEMESTER_WEEK = 2; //学期周
    private static final int TYPE_ITEM_WEATHER_TODAY = 3; //今日天气
    private static final int TYPE_ITEM_MY_SCHEDULE_HEADER = 4; //我的日程的header
    private static final int TYPE_ITEM_MY_SCHEDULE = 5; //我的日程

    private ArrayList<Integer> types = new ArrayList<>(); //所有的类型
    private SparseIntArray array = new SparseIntArray(); //

    private Context context;
    private LayoutInflater inflater;
    private View headView; //头部的view

    private SemesterWeekInfo semesterWeekInfo; //学期周的实体类
    private LocalWeatherLive localWeatherLive; //今日天气的实体类
    private List<ScheduleManagementInfo> listMySchedule; //我的日程的集合

    private OnItemClickListener listener;

    private RecyclerView.LayoutParams params;
    private RecyclerView.LayoutParams params_margin;

    HomeAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);

            params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
            params_margin = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);

            params.bottomMargin = 0;
            params_margin.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //初始化头部的view
    void initHeaderView(View headView) {
        this.headView = headView;
        initRemindToday();
        initSemesterWeek();
        initWeatherToday();
        notifyItemRangeInserted(0, 4);
    }

    //初始化今日提醒
    private void initRemindToday() {
        array.put(TYPE_ITEM_REMIND_TODAY, types.size());
        types.add(TYPE_ITEM_REMIND_TODAY);
    }

    //初始化学期周
    private void initSemesterWeek() {
        array.put(TYPE_ITEM_SEMESTER_WEEK, types.size());
        types.add(TYPE_ITEM_SEMESTER_WEEK);
    }

    //初始化今日天气
    private void initWeatherToday() {
        array.put(TYPE_ITEM_WEATHER_TODAY, types.size());
        types.add(TYPE_ITEM_WEATHER_TODAY);
    }

    //获取类型集合
    private List<Integer> getAllTypeScheduleList(int type) {
        List<Integer> list = new ArrayList<>();

        switch (type) {
        /*
         * 学期周
         */
        case TYPE_ITEM_SEMESTER_WEEK:
            list.add(TYPE_ITEM_SEMESTER_WEEK);
            return list;

        /*
         * 今日天气
         */
        case TYPE_ITEM_WEATHER_TODAY:
            list.add(TYPE_ITEM_WEATHER_TODAY);
            return list;

        /*
         *  我的日程的header
         */
        case TYPE_ITEM_MY_SCHEDULE_HEADER:
            list.add(TYPE_ITEM_MY_SCHEDULE_HEADER);
            return list;

        /*
         * 我的日程
         */
        case TYPE_ITEM_MY_SCHEDULE:
            if (listMySchedule == null || listMySchedule.size() == 0) {
                return list;
            }
            int size = listMySchedule.size();
            for (int i = 0; i < size; i++) {
                list.add(TYPE_ITEM_MY_SCHEDULE);
            }
            return list;

        default:
            break;
        }
        return list;
    }


    //初始化我的日程
    void initMySchedule(List<ScheduleManagementInfo> listMySchedule) {
        this.listMySchedule = listMySchedule;
        types.removeAll(getAllTypeScheduleList(TYPE_ITEM_MY_SCHEDULE_HEADER));
        types.removeAll(getAllTypeScheduleList(TYPE_ITEM_MY_SCHEDULE));
        if (listMySchedule != null && listMySchedule.size() > 0) {
            array.put(TYPE_ITEM_MY_SCHEDULE_HEADER, types.size());
            types.add(TYPE_ITEM_MY_SCHEDULE_HEADER);

            array.put(TYPE_ITEM_MY_SCHEDULE, types.size());
            int size = listMySchedule.size();
            for (int i = 0; i < size; i++) {
                types.add(TYPE_ITEM_MY_SCHEDULE);
            }
        }
        notifyDataSetChanged();
    }

    void updateItemSemesterWeek(SemesterWeekInfo semesterWeekInfo) {
        this.semesterWeekInfo = semesterWeekInfo;
        notifyItemChanged(2);
    }

    void updateItemWeatherToday(LocalWeatherLive localWeatherLive) {
        this.localWeatherLive = localWeatherLive;
        notifyItemChanged(3);
    }

    void removeItem(int position) {
        if (headView != null) {
            position = position - 1;
        }

        if (position >= 0 && types != null && types.size() > position) {
            int viewType = types.get(position);
            switch (viewType) {
            /*
             * 学期周
             */
            case TYPE_ITEM_SEMESTER_WEEK:
                int sizeSemester = array.get(TYPE_ITEM_MY_SCHEDULE);
                List<Integer> listSemester = getAllTypeScheduleList(viewType);
                if (types.removeAll(listSemester)) {
                    array.put(TYPE_ITEM_MY_SCHEDULE, sizeSemester - listSemester.size());
                    array.delete(TYPE_ITEM_SEMESTER_WEEK);
                    notifyDataSetChanged();
                }
                break;

            /*
             * 今日天气
             */
            case TYPE_ITEM_WEATHER_TODAY:
                int sizeWeather = array.get(TYPE_ITEM_MY_SCHEDULE);
                List<Integer> listWeather = getAllTypeScheduleList(viewType);
                if (types.removeAll(listWeather)) {
                    array.put(TYPE_ITEM_MY_SCHEDULE, sizeWeather - listWeather.size());
                    array.delete(TYPE_ITEM_WEATHER_TODAY);
                    notifyDataSetChanged();
                }
                break;

            /*
             *  我的日程的header
             */
            case TYPE_ITEM_MY_SCHEDULE_HEADER:
                array.delete(TYPE_ITEM_MY_SCHEDULE_HEADER);
                array.delete(TYPE_ITEM_MY_SCHEDULE);
                types.removeAll(getAllTypeScheduleList(viewType));
                types.removeAll(getAllTypeScheduleList(TYPE_ITEM_MY_SCHEDULE));
                listMySchedule = null;
                notifyDataSetChanged();
                break;

            default:
                break;
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }

        switch (viewType) {
        /*
         * header
         */
        case TYPE_HEADER:
            return new RemindTodayViewHolder(headView);

        /*
         * 今日提醒
         */
        case TYPE_ITEM_REMIND_TODAY:
            View view_today_remind = inflater.inflate(R.layout.item_home_today_remind, parent, false);
            return new RemindTodayViewHolder(view_today_remind);

        /*
         * 学期周
         */
        case TYPE_ITEM_SEMESTER_WEEK:
            View view_semester_week = inflater.inflate(R.layout.item_home_semester_week, parent, false);
            return new SemesterWeekViewHolder(view_semester_week);

        /*
         * 今日天气
         */
        case TYPE_ITEM_WEATHER_TODAY:
            View view_weather_today = inflater.inflate(R.layout.item_home_weather_today, parent, false);
            return new WeatherTodayViewHolder(view_weather_today);


        /*
         *  我的日程的header
         */
        case TYPE_ITEM_MY_SCHEDULE_HEADER:
            View view_my_schedule_header = inflater.inflate(R.layout.item_home_my_schedule_header, parent, false);
            return new MyScheduleHeaderViewHolder(view_my_schedule_header);

        /*
         * 我的日程
         */
        case TYPE_ITEM_MY_SCHEDULE:
            View view_my_schedule = inflater.inflate(R.layout.item_home_my_schedule, parent, false);
            return new MyScheduleViewHolder(view_my_schedule);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         * header
         */
        case TYPE_HEADER:
            break;

        /*
         * 今日提醒
         */
        case TYPE_ITEM_REMIND_TODAY:
            if (holder == null || !(holder instanceof RemindTodayViewHolder) || context == null) {
                return;
            }
            RemindTodayViewHolder remindTodayViewHolder = (RemindTodayViewHolder) holder;
            remindTodayViewHolder.tv_name.setText(context.getResources().getString(R.string.remind_today));
            break;

        /*
         * 学期周
         */
        case TYPE_ITEM_SEMESTER_WEEK:
            if (holder == null || !(holder instanceof SemesterWeekViewHolder) || semesterWeekInfo == null) {
                return;
            }

            SemesterWeekViewHolder semesterWeekViewHolder = (SemesterWeekViewHolder) holder;
            semesterWeekViewHolder.tv_time.setText(semesterWeekInfo.getTime());
            semesterWeekViewHolder.tv_lunar.setText(semesterWeekInfo.getLunar());
            semesterWeekViewHolder.tv_week.setText(semesterWeekInfo.getWeek());
            semesterWeekViewHolder.tv_semester_week.setText(semesterWeekInfo.getSemesterWeek());
            semesterWeekViewHolder.iv_arrow.setOnClickListener(new OnClickListener(0, position));
            break;


        /*
         * 今日天气
         */
        case TYPE_ITEM_WEATHER_TODAY:
            if (holder == null || !(holder instanceof WeatherTodayViewHolder) || localWeatherLive == null) {
                return;
            }
            WeatherTodayViewHolder weatherTodayViewHolder = (WeatherTodayViewHolder) holder;
            weatherTodayViewHolder.tv_weather.setText(localWeatherLive.getWeather());
            weatherTodayViewHolder.tv_temperature.setText(localWeatherLive.getTemperature() + "°");
            String windDirection = localWeatherLive.getWindDirection();
            String windPower = localWeatherLive.getWindPower();
            String humidity = localWeatherLive.getHumidity();
            String time = localWeatherLive.getReportTime();
            weatherTodayViewHolder.tv_humidity.setText(windDirection + "风 " + windPower + "级" + " 湿度 " + humidity + "%");
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            weatherTodayViewHolder.tv_time.setText(TimeUtils.millis2String(TimeUtils.string2Millis(time), format));
            weatherTodayViewHolder.iv_arrow.setOnClickListener(new OnClickListener(1, position));
            break;

        /*
         *  我的日程的header
         */
        case TYPE_ITEM_MY_SCHEDULE_HEADER:
            if (holder == null || !(holder instanceof MyScheduleHeaderViewHolder)) {
                return;
            }
            MyScheduleHeaderViewHolder myScheduleHeaderViewHolder = (MyScheduleHeaderViewHolder) holder;
            myScheduleHeaderViewHolder.iv_arrow.setOnClickListener(new OnClickListener(2, position));
            break;

        /*
         * 我的日程
         */
        case TYPE_ITEM_MY_SCHEDULE:
            final int relPosition = position - array.get(viewType) - 1;
            if (holder == null || !(holder instanceof MyScheduleViewHolder) || relPosition < 0 ||
                    listMySchedule == null || listMySchedule.size() <= relPosition ||
                    listMySchedule.get(relPosition) == null) {
                return;
            }
            MyScheduleViewHolder myScheduleViewHolder = (MyScheduleViewHolder) holder;
            myScheduleViewHolder.tv_title.setText(listMySchedule.get(relPosition).getTitle());
            myScheduleViewHolder.tv_time.setText(listMySchedule.get(relPosition).getStart_time());
            myScheduleViewHolder.constraintLayout.setOnClickListener(new OnClickListener(
                    listMySchedule.get(relPosition).getId()));
            if (relPosition == listMySchedule.size() - 1) {
                myScheduleViewHolder.itemView.setLayoutParams(params_margin);
            } else {
                myScheduleViewHolder.itemView.setLayoutParams(params);
            }
            break;


        default:
            break;
        }
    }

    @Override
    public int getItemCount() {
        if (headView == null) {
            if (types == null) {
                return 0;
            }
            return types.size();
        }
        if (types == null) {
            return 1;
        }
        return types.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (headView == null) {
            if (types != null && types.size() > position) {
                return types.get(position);
            }
        }

        if (position == 0) {
            return TYPE_HEADER;
        }

        int realPosition = position - 1;
        if (types != null && types.size() >= realPosition && realPosition >= 0) {
            return types.get(realPosition);
        }
        return -1;
    }

    //今日提醒
    private class RemindTodayViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name; //名称

        RemindTodayViewHolder(View itemView) {
            super(itemView);

            if (itemView == headView) {
                return;
            }

            tv_name = (TextView) itemView.findViewById(R.id.item_home_today_remind_name);
        }
    }

    //学期周
    private static class SemesterWeekViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_time; //时间
        private TextView tv_lunar; //农历
        private TextView tv_week; //周几
        private TextView tv_semester_week; //学期周
        private ImageView iv_arrow; //更多

        SemesterWeekViewHolder(View itemView) {
            super(itemView);

            tv_time = (TextView) itemView.findViewById(R.id.item_home_semester_week_time);
            tv_lunar = (TextView) itemView.findViewById(R.id.item_home_semester_week_lunar);
            tv_week = (TextView) itemView.findViewById(R.id.item_home_semester_week);
            tv_semester_week = (TextView) itemView.findViewById(R.id.item_home_semester_number);
            iv_arrow = (ImageView) itemView.findViewById(R.id.item_home_semester_week_arrow);
        }
    }


    //今日天气
    private static class WeatherTodayViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_weather; //天气
        private TextView tv_temperature; //温度
        private TextView tv_humidity; //湿度
        private TextView tv_time; //时间
        private ImageView iv_arrow; //更多

        WeatherTodayViewHolder(View itemView) {
            super(itemView);

            tv_weather = (TextView) itemView.findViewById(R.id.item_home_weather_today_weather);
            tv_temperature = (TextView) itemView.findViewById(R.id.item_home_weather_today_temperature);
            tv_humidity = (TextView) itemView.findViewById(R.id.item_home_weather_today_humidity);
            tv_time = (TextView) itemView.findViewById(R.id.item_home_weather_today_time);
            iv_arrow = (ImageView) itemView.findViewById(R.id.item_home_weather_today_arrow);
        }
    }


    //我的日程的头部
    private static class MyScheduleHeaderViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_arrow; //更多

        MyScheduleHeaderViewHolder(View itemView) {
            super(itemView);

            iv_arrow = (ImageView) itemView.findViewById(R.id.item_home_my_schedule_arrow);
        }
    }

    //我的日程
    private static class MyScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title; //标题
        private TextView tv_time; //时间
        private ConstraintLayout constraintLayout;

        MyScheduleViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_home_my_schedule_tile);
            tv_time = (TextView) itemView.findViewById(R.id.item_home_my_schedule_times);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item_home_my_schedule);
        }
    }


    //静态内部类
    private class OnClickListener extends OnceClickListener {

        private int type; //类型 0：日历的 1：今日天气 2：我的日程
        private int position; //位置
        private long id;

        public OnClickListener(long id) {
            this.id = id;
        }

        public OnClickListener(int type, int position) {
            this.type = type;
            this.position = position;
        }

        @Override
        public void onOnceClick(View view) {
            if (view == null || listener == null) {
                return;
            }

            int key = view.getId();
            switch (key) {
            /*
             * 跳转到日程详情界面
             */
            case R.id.item_home_my_schedule:
                listener.onMyScheduleItemClick(id);
                break;

            /*
             * 更多
             */
            case R.id.item_home_my_schedule_arrow:
            case R.id.item_home_weather_today_arrow:
            case R.id.item_home_semester_week_arrow:
                listener.onItemMoreClick(type, position);
                break;


            default:
                break;
            }
        }
    }


    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onMyScheduleItemClick(long id);

        void onItemMoreClick(int type, int position);
    }

}
