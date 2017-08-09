package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.PushUtils;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule.AddScheduleActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: ui
 */

public class ScheduleDetailFragment extends BaseFragment implements ScheduleDetailContract.View,
        View.OnClickListener {

    private static final String TAG = "ScheduleDetailFragment";
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final int REQUEST_CODE_SCHEDULE_ADD = 1; //跳转到新增界面的请求码
    private long id;
    private int from; // 1: 点击通知栏

    private TextView tv_title; //标题
    private TextView tv_start_time; // 开始时间
    private TextView tv_end_time; // 结束时间
    private TextView tv_hint_time; // 提醒时间
    private TextView tv_repeat_times; //重复次数
    private Button btn_no_hint; //不再提醒

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        id = bundle.getLong("id");
        from = bundle.getInt("from", 0);
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(from == 0);
        return R.layout.fragment_schedule_detail;
    }

    @Override
    protected void initViews(View view) {

        tv_title = (TextView) view.findViewById(R.id.schedule_detail_title);
        tv_start_time = (TextView) view.findViewById(R.id.schedule_detail_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.schedule_detail_end_time);
        tv_hint_time = (TextView) view.findViewById(R.id.schedule_detail_hint_time);
        tv_repeat_times = (TextView) view.findViewById(R.id.schedule_detail_repeat_time);
        btn_no_hint = (Button) view.findViewById(R.id.schedule_detail_no_hint);

        refreshUI(id);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_schedule_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 刪除
         */
        case R.id.schedule_detail_delete:
            deleteDataFromRealm(id);
            break;

        /*
         * 编辑
         */
        case R.id.schedule_detail_edit:
            Intent intent = new Intent(context, AddScheduleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_edit", true);
            bundle.putLong("id", id);
            intent.putExtras(bundle);
            openActivityForResult(intent, REQUEST_CODE_SCHEDULE_ADD);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initListener() {
        btn_no_hint.setOnClickListener(this);
    }

    public static ScheduleDetailFragment newInstance(long id, int from) {
        ScheduleDetailFragment fragment = new ScheduleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putInt("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         *  编辑返回
         */
        case REQUEST_CODE_SCHEDULE_ADD:
            from = 0;
            refreshUI(id);
            break;

        default:
            break;
        }
    }

    @Override
    public ScheduleManagementInfo getScheduleManagementInfo(long id) {

        return getAppComponent().getRealmHelper().queryScheduleById(id);
    }

    @Override
    public String getHintTimeByHintType(int type) {

        String value = "";
        switch (type) {
        /*
         * 提前10分钟提醒
         */
        case 0:
            value = getResources().getString(R.string.dialog_hint_before_10);
            break;

        /*
         *  提前30分钟提醒
         */
        case 1:
            value = getResources().getString(R.string.dialog_hint_before_30);
            break;

        /*
         *  提前5分钟提醒
         */
        case 2:
            value = getResources().getString(R.string.dialog_hint_before_5);
            break;

        /*
         *  准时提醒
         */
        case 3:
            value = getResources().getString(R.string.dialog_hint_before_0);
            break;

        /*
         *  不提醒
         */
        case 4:
            value = getResources().getString(R.string.dialog_hint_no);
            break;

        default:
            break;

        }
        return value;
    }

    @Override
    public String getRepeatTimeByRepeatType(int type) {

        String value = "";
        switch (type) {
        /*
         * 提醒1次
         */
        case 0:
            value = getResources().getString(R.string.dialog_repeat_times_1);
            break;

        /*
         * 每5分钟提醒一次
         */
        case 1:
            value = getResources().getString(R.string.dialog_repeat_times_5);
            break;

        /*
         * 每10分钟提醒一次
         */
        case 2:
            value = getResources().getString(R.string.dialog_repeat_times_10);
            break;

        /*
         *  每15分钟提醒一次
         */
        case 3:
            value = getResources().getString(R.string.dialog_repeat_times_15);
            break;

        default:
            break;

        }
        return value;
    }

    @Override
    public void deleteDataFromRealm(long id) {
        PushUtils.removeLocalNotification(context, getScheduleManagementInfo(id));
        getAppComponent().getRealmHelper().deleteScheduleById(id);
        onBackPressed();
    }

    @Override
    public void refreshUI(long id) {

        ScheduleManagementInfo info = getScheduleManagementInfo(id);
        if (info == null) {
            return;
        }

        String title = info.getTitle(); //标题
        String start_time = info.getStart_time(); //开始时间
        String end_time = info.getEnd_time(); //结束时间
        int hint_type = info.getHint_type(); //提醒类型
        int repeat_type = info.getRepeat_type(); //重复类型

        tv_title.setText(title);
        tv_start_time.setText(start_time);
        tv_end_time.setText(end_time);
        tv_hint_time.setText(getHintTimeByHintType(hint_type));
        tv_repeat_times.setText(getRepeatTimeByRepeatType(repeat_type));

        if (from == 1) {
            if (checkIsHint(hint_type, repeat_type)) {
                btn_no_hint.setVisibility(View.VISIBLE);
            } else {
                btn_no_hint.setVisibility(View.GONE);
            }
        } else {
            btn_no_hint.setVisibility(View.GONE);
        }
    }

    @Override
    public String getStartTime() {
        return tv_start_time.getText().toString();
    }

    @Override
    public int getYear(String value) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        try {
            Date date = format.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " getYear " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int getMonth(String value) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        try {
            Date date = format.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " getMonth " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int getDay(String value) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        try {
            Date date = format.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " getDay " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        String start_time = getStartTime();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("year", getYear(start_time));
        bundle.putInt("month", getMonth(start_time));
        bundle.putInt("day", getDay(start_time));
        intent.putExtras(bundle);
        ((ScheduleDetailActivity) context).setResult(Activity.RESULT_OK, intent);
        ((ScheduleDetailActivity) context).finish();
    }

    @Override
    public boolean checkIsHint(int hintType, int repeatType) {
        return !(hintType == 4 || repeatType == 0);
    }

    public void dealWithBack() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         *  不再提醒
         */
        case R.id.schedule_detail_no_hint:
            PushUtils.removeLocalNotification(context, getScheduleManagementInfo(id));
            ((ScheduleDetailActivity) context).finish();
            break;

        default:
            break;
        }
    }
}
