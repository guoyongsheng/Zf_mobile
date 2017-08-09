package com.zfsoftmh.ui.modules.office_affairs.schedule_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jeek.calendar.widget.calendar.OnCalendarClickListener;
import com.jeek.calendar.widget.calendar.schedule.ScheduleLayout;
import com.jeek.calendar.widget.calendar.schedule.ScheduleRecyclerView;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule.AddScheduleActivity;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail.ScheduleDetailActivity;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_list.ScheduleListActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.zfsoftmh.R.string.year;


/**
 * @author wesley
 * @date: 2017-5-22
 * @Description: ui
 */

public class ScheduleManagementFragment extends BaseFragment<ScheduleManagementPresenter>
        implements ScheduleManagementContract.View, OnCalendarClickListener, View.OnClickListener,
        ScheduleManagementAdapter.OnItemClickListener {

    private final static String PATTERN = "yyyy-MM-dd";
    private static final int REQUEST_CODE_SCHEDULE_DETAIL = 1; //跳转到详情界面的请求码
    private static final int REQUEST_CODE_SCHEDULE_ADD = 2; //跳转到新增界面的请求码
    private ScheduleLayout scheduleLayout;
    private OnTitleChangeListener listener;
    private String YEAR; //年
    private String MONTH; //月

    private LinearLayout ll_mine; //我的
    private LinearLayout ll_today; //今天
    private LinearLayout ll_new; //新建

    private ScheduleManagementAdapter adapter; //适配器

    @Override
    protected void initVariables() {
        YEAR = context.getResources().getString(year);
        MONTH = context.getResources().getString(R.string.month);

        adapter = new ScheduleManagementAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_schedule_management;
    }

    @Override
    protected void initViews(View view) {
        scheduleLayout = (ScheduleLayout) view.findViewById(R.id.slSchedule);
        ll_mine = (LinearLayout) view.findViewById(R.id.schedule_mine);
        ll_today = (LinearLayout) view.findViewById(R.id.schedule_today);
        ll_new = (LinearLayout) view.findViewById(R.id.schedule_new);

        int year = scheduleLayout.getCurrentSelectYear();
        int month = scheduleLayout.getCurrentSelectMonth();
        int day = scheduleLayout.getCurrentSelectDay();
        refreshUI(year, month, day);

        ScheduleRecyclerView recyclerView = scheduleLayout.getSchedulerRecyclerView();
        //LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        //divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        scheduleLayout.setOnCalendarClickListener(this);
        ll_mine.setOnClickListener(this);
        ll_today.setOnClickListener(this);
        ll_new.setOnClickListener(this);
    }

    public static ScheduleManagementFragment newInstance() {
        return new ScheduleManagementFragment();
    }

    @Override
    public void onClickDate(int year, int month, int day) {
        refreshUI(year, month, day);
    }

    @Override
    public void onPageChange(int year, int month, int day) {

    }

    @Override
    public void onTitleChanged(String title) {
        if (listener != null) {
            listener.onTitleChanged(title);
        }
    }

    @Override
    public void setCurrentItem(int year, int month, int day) {
        scheduleLayout.initData(year, month, day);
    }

    @Override
    public void refreshData(List<ScheduleManagementInfo> list) {
        adapter.setDataSource(list);
    }

    @Override
    public List<ScheduleManagementInfo> getDataSource(int year, int month, int day) {
        return getAppComponent().getRealmHelper().queryScheduleByTime(parseTime(year, month, day));
    }

    @Override
    public void refreshUI(int year, int month, int day) {
        onTitleChanged(year + YEAR + (month + 1) + MONTH);
        refreshData(getDataSource(year, month, day));
    }

    @Override
    public String parseTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        return format.format(calendar.getTime());
    }

    public void setOnTitleChangeListener(OnTitleChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         *  我的---跳转到日程列表界面
         */
        case R.id.schedule_mine:
            startActivity(ScheduleListActivity.class);
            break;

        /*
         *  今天
         */
        case R.id.schedule_today:
            scheduleLayout.getMonthCalendar().setTodayToView();
            break;

        /*
         *  新建
         */
        case R.id.schedule_new:
            Intent intent = new Intent(context, AddScheduleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_edit", false);
            bundle.putLong("id", 0);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_SCHEDULE_ADD);
            break;

        default:
            break;
        }
    }

    @Override
    public void onItemClick(long id) {
        Intent intent = new Intent(context, ScheduleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivityForResult(intent, REQUEST_CODE_SCHEDULE_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 详情界面返回 | 新增界面返回
         */
        case REQUEST_CODE_SCHEDULE_DETAIL:
        case REQUEST_CODE_SCHEDULE_ADD:
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    int year = bundle.getInt("year");
                    int month = bundle.getInt("month");
                    int day = bundle.getInt("day");
                    setCurrentItem(year, month, day);
                    refreshData(getDataSource(year, month, day));
                }
            }
            break;

        default:
            break;
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnTitleChangeListener {

        void onTitleChanged(String title);
    }
}
