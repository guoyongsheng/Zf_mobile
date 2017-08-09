package com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.common.utils.PushUtils;
import com.zfsoftmh.common.utils.TimeUtils;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author wesley
 * @date: 2017-5-24
 * @Description: 新建我的日程ui
 */

public class AddScheduleFragment extends BaseFragment implements AddScheduleContract.View,
        View.OnClickListener, AddScheduleDialogFragment.OnHintTimeClickListener,
        AddScheduleDialogFragment.OnRepeatTimesClickListener, AddSchedulePickerDialogFragment.OnTimePickerListener {

    private static final String TAG = "AddScheduleFragment";
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_ONE = "yyyy年MM月";
    private static final String PATTERN_TWO = "yyyy-MM-dd";
    private EditText et_title; //我的日程标题
    private RelativeLayout rl_start_time; //开始时间的布局
    private TextView tv_start_time; //开始时间
    private RelativeLayout rl_end_time; //结束时间的布局
    private TextView tv_end_time; //结束时间
    private RelativeLayout rl_hint_time; //提醒时间的布局
    private TextView tv_hint_time; //提醒时间的布局
    private RelativeLayout rl_repeat_time; //重复次数的布局
    private TextView tv_repeat_time; //重复次数

    private boolean isEdit; //是否是编辑状态
    private long id;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        isEdit = bundle.getBoolean("is_edit");
        id = bundle.getLong("id");
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.fragment_add_schedule;
    }

    @Override
    protected void initViews(View view) {
        et_title = (EditText) view.findViewById(R.id.schedule_title);
        rl_start_time = (RelativeLayout) view.findViewById(R.id.relative_start_time);
        tv_start_time = (TextView) view.findViewById(R.id.schedule_start_time);
        rl_end_time = (RelativeLayout) view.findViewById(R.id.relative_end_time);
        tv_end_time = (TextView) view.findViewById(R.id.schedule_end_time);
        rl_hint_time = (RelativeLayout) view.findViewById(R.id.relative_hint_time);
        tv_hint_time = (TextView) view.findViewById(R.id.schedule_hint_time);
        rl_repeat_time = (RelativeLayout) view.findViewById(R.id.relative_repeat_time);
        tv_repeat_time = (TextView) view.findViewById(R.id.schedule_repeat_time);

        if (isEdit) {
            initEditMode();
        } else {
            initAddMode();
        }
    }

    @Override
    protected void initListener() {
        rl_start_time.setOnClickListener(this);
        rl_end_time.setOnClickListener(this);
        rl_hint_time.setOnClickListener(this);
        rl_repeat_time.setOnClickListener(this);
    }

    public static AddScheduleFragment newInstance(boolean isEdit, long id) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_edit", isEdit);
        bundle.putLong("id", id);
        AddScheduleFragment fragment = new AddScheduleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 开始时间
         */
        case R.id.relative_start_time:
            showStartTimeDialog();
            break;

        /*
         * 结束时间
         */
        case R.id.relative_end_time:
            showEndTimeDialog();
            break;

        /*
         * 提醒时间
         */
        case R.id.relative_hint_time:
            showHintTimeDialog();
            break;

        /*
         * 重复次数
         */
        case R.id.relative_repeat_time:
            showRepeatTimesDialog();
            break;

        default:
            break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_schedult, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 完成
         */
        case R.id.add_schedule:
            onDoneClick();
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initAddMode() {
        tv_start_time.setText(getCurrentTime());
        tv_end_time.setText(getAfterDay());
        tv_hint_time.setText(R.string.befor_ten_minute_hint);
        tv_repeat_time.setText(R.string.just_repeat_once);
    }

    @Override
    public void initEditMode() {
        ScheduleManagementInfo info = getScheduleManagementInfo(id);
        if (info == null) {
            return;
        }
        String title = info.getTitle(); //标题
        String start_time = info.getStart_time(); //开始时间
        String end_time = info.getEnd_time(); //结束时间
        int hint_type = info.getHint_type(); //提醒类型
        int repeat_type = info.getRepeat_type(); //重复类型

        et_title.setText(title);
        tv_start_time.setText(start_time);
        tv_end_time.setText(end_time);
        tv_hint_time.setText(getHintTimeByHintType(hint_type));
        tv_repeat_time.setText(getRepeatTimeByRepeatType(repeat_type));
        tv_hint_time.setTag(hint_type);
        tv_repeat_time.setTag(repeat_type);
    }

    @Override
    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        Date date = new Date();
        return format.format(date);
    }

    @Override
    public String getAfterDay() {
        String currentTime = tv_start_time.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = format.parse(currentTime);
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day + 1);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " getAfterDay " + e.getMessage());
        }
        return currentTime;
    }

    @Override
    public String parseDate(SelectedDate selectedDate, int hourOfDay, int minute) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        Calendar calendar = selectedDate.getStartDate();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return format.format(calendar.getTime());
    }

    @Override
    public long stringToLong(String value) {
        if (TimeUtils.is24(context)) {
            return TimeUtils.string2Millis(value);
        }

        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return TimeUtils.string2Millis(value, format);
    }

    @Override
    public void showStartTimeDialog() {
        AddSchedulePickerDialogFragment fragment = AddSchedulePickerDialogFragment.newInstance(0, getStartTime());
        fragment.setOnTimePickerListener(this);
        fragment.show(getChildFragmentManager(), "showStartTimeDialog");
    }

    @Override
    public void showEndTimeDialog() {
        AddSchedulePickerDialogFragment fragment = AddSchedulePickerDialogFragment.newInstance(1, getEndTime());
        fragment.setOnTimePickerListener(this);
        fragment.show(getChildFragmentManager(), "showEndTimeDialog");
    }

    @Override
    public void showHintTimeDialog() {
        AddScheduleDialogFragment fragment = AddScheduleDialogFragment.newInstance(0);
        fragment.setOnHintTimeClickListener(this);
        fragment.show(getChildFragmentManager(), "showHintTimeDialog");
    }

    @Override
    public void showRepeatTimesDialog() {
        AddScheduleDialogFragment fragment = AddScheduleDialogFragment.newInstance(1);
        fragment.setOnRepeatTimesClickListener(this);
        fragment.show(getChildFragmentManager(), "showRepeatTimesDialog");
    }

    @Override
    public void onDoneClick() {
        if (checkTitleIsNull(getScheduleTitle())) {
            showToastMsgShort(getResources().getString(R.string.please_input_title));
            return;
        }

        if (checkStartTimeIsLessCurrentTime(getStartTime(), System.currentTimeMillis())) {
            showToastMsgShort(getResources().getString(R.string.start_time_no_less_than_current_time));
            return;
        }

        if (checkEndTimeIsLessStartTime(getStartTime(), getEndTime())) {
            showToastMsgShort(getResources().getString(R.string.end_time_no_less_than_start_time));
            return;
        }

        ScheduleManagementInfo info = new ScheduleManagementInfo();
        info.setTitle(getScheduleTitle());
        info.setStart_time(getStartTime());
        info.setEnd_time(getEndTime());
        info.setHint_type(getHintType());
        info.setRepeat_type(getRepeatType());
        info.setHead_time(startTimeParseTime(getStartTime(), 0));
        info.setTime(startTimeParseTime(getStartTime(), 1));
        if (isEdit) {
            info.setId(id);
            update(info);
        } else {
            info.setId(System.currentTimeMillis());
            insert(info);
        }
    }

    @Override
    public String getScheduleTitle() {
        return et_title.getText().toString();
    }

    @Override
    public boolean checkStartTimeIsLessCurrentTime(String startTime, long currentTime) {
        long start = stringToLong(startTime);
        return start < currentTime;
    }

    @Override
    public boolean checkEndTimeIsLessStartTime(String startTime, String endTime) {
        long start = stringToLong(startTime);
        long end = stringToLong(endTime);
        return end <= start;
    }

    @Override
    public String getStartTime() {
        return tv_start_time.getText().toString();
    }

    @Override
    public String getEndTime() {
        return tv_end_time.getText().toString();
    }

    @Override
    public boolean checkTitleIsNull(String title) {
        return title == null || title.length() == 0;
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
    public void insert(ScheduleManagementInfo info) {
        if (info == null) {
            return;
        }
        String start_time = info.getStart_time();
        getAppComponent().getRealmHelper().insert(info);
        addNotification(info);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("year", getYear(start_time));
        bundle.putInt("month", getMonth(start_time));
        bundle.putInt("day", getDay(start_time));
        intent.putExtras(bundle);
        ((AddScheduleActivity) context).setResult(Activity.RESULT_OK, intent);
        ((AddScheduleActivity) context).finish();
    }

    @Override
    public void update(ScheduleManagementInfo info) {
        if (info == null) {
            return;
        }
        getAppComponent().getRealmHelper().insert(info);
        PushUtils.removeLocalNotification(context, info);
        addNotification(info);
        ((AddScheduleActivity) context).setResult(Activity.RESULT_OK);
        ((AddScheduleActivity) context).finish();
    }

    @Override
    public int getHintType() {
        String value = tv_hint_time.getTag().toString();
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            LoggerHelper.e(TAG, " getHintType " + e.getMessage());
        }

        return 0;
    }

    @Override
    public int getRepeatType() {
        String value = tv_repeat_time.getTag().toString();
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            LoggerHelper.e(TAG, " getRepeatType " + e.getMessage());
        }

        return 0;
    }

    @Override
    public String startTimeParseTime(String startTime, int type) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        try {
            Date date = format.parse(startTime);

            switch (type) {
            case 0:
                SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_ONE);
                return dateFormat.format(date);

            case 1:
                SimpleDateFormat dateFormatTwo = new SimpleDateFormat(PATTERN_TWO);
                return dateFormatTwo.format(date);

            default:
                break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " startTimeParseTime " + e.getMessage());
        }
        return "";
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
    public void addNotification(final ScheduleManagementInfo info) {

        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        PushUtils.addLocalNotification(context, info);
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onHintTimeClick(int type) {
        tv_hint_time.setTag(type);
        tv_hint_time.setText(getHintTimeByHintType(type));
    }

    @Override
    public void onRepeatTimesClick(int type) {
        tv_repeat_time.setTag(type);
        tv_repeat_time.setText(getRepeatTimeByRepeatType(type));
    }

    @Override
    public void onTimePick(SelectedDate selectedDate, int hourOfDay, int minute,
            SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule, int type) {

        switch (type) {
        /*
         * 开始时间
         */
        case 0:
            tv_start_time.setText(parseDate(selectedDate, hourOfDay, minute));
            break;

        /*
         * 结束时间
         */
        case 1:
            if (checkEndTimeIsLessStartTime(getStartTime(), parseDate(selectedDate, hourOfDay, minute))) {
                showToastMsgShort(getResources().getString(R.string.end_time_no_less_than_start_time));
                return;
            }
            tv_end_time.setText(parseDate(selectedDate, hourOfDay, minute));
            break;

        default:
            break;
        }
    }
}
