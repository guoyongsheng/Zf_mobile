package com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.ui.base.BaseDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wesley
 * @date: 2017-5-24
 * @Description: 选择时间的对话框
 */

public class AddSchedulePickerDialogFragment extends BaseDialogFragment {

    private int type;
    private String time;
    private static final String TAG = "AddSchedulePickerDialogFragment";
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private OnTimePickerListener onTimePickerListener;

    @Override
    public void onDetach() {
        super.onDetach();
        this.onTimePickerListener = null;
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        type = bundle.getInt("type");
        time = bundle.getString("time");
    }

    @Override
    protected Dialog createDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_time_picker, null);
        SublimePicker sublimePicker = (SublimePicker) view.findViewById(R.id.dialog_sublime);
        SublimeOptions options = new SublimeOptions();
        options.setDisplayOptions(SublimeOptions.ACTIVATE_DATE_PICKER | SublimeOptions.ACTIVATE_TIME_PICKER);
        Calendar calendar = stringToCalendar(time);
        options.setDateParams(calendar);
        options.setTimeParams(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        options.setAnimateLayoutChanges(true);
        sublimePicker.initializePicker(options, listener);
        return new AlertDialog
                .Builder(context)
                .setView(view)
                .create();
    }

    public static AddSchedulePickerDialogFragment newInstance(int type, String time) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("time", time);
        AddSchedulePickerDialogFragment fragment = new AddSchedulePickerDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setOnTimePickerListener(OnTimePickerListener onTimePickerListener) {
        this.onTimePickerListener = onTimePickerListener;
    }

    //内部类
    private SublimeListenerAdapter listener = new SublimeListenerAdapter() {
        @Override
        public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker,
                SelectedDate selectedDate, int hourOfDay, int minute,
                SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {

            if (onTimePickerListener != null) {
                onTimePickerListener.onTimePick(selectedDate, hourOfDay, minute, recurrenceOption, recurrenceRule, type);
            }
            dismiss();
        }

        @Override
        public void onCancelled() {
            dismiss();
        }
    };

    //yyyy-MM-dd hh:mm:ss格式转换为calendar
    private Calendar stringToCalendar(String value) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = format.parse(value);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " stringToCalendar " + e.getMessage());
        }
        return calendar;
    }

    /**
     * 自定义回调接口
     */
    interface OnTimePickerListener {

        void onTimePick(SelectedDate selectedDate, int hourOfDay, int minute,
                SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule, int type);
    }
}
