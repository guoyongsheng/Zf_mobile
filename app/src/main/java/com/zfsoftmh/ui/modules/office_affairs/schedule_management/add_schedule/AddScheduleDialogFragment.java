package com.zfsoftmh.ui.modules.office_affairs.schedule_management.add_schedule;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseBottomSheetDialogFragment;

/**
 * @author wesley
 * @date: 2017-5-24
 * @Description: 添加日程的对话框
 */

public class AddScheduleDialogFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener {

    private int type;
    private OnHintTimeClickListener onHintTimeClickListener;
    private OnRepeatTimesClickListener onRepeatTimesClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onHintTimeClickListener = null;
        this.onRepeatTimesClickListener = null;
    }

    @Override
    protected Dialog createBottomSheetDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        switch (type) {
        /*
         *  提醒时间
         */
        case 0:
            View view_hint_time = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_add_schedule_hint_time, null);
            dialog.setContentView(view_hint_time);

            TextView tv_before_30 = (TextView) view_hint_time.findViewById(R.id.dialog_hint_before_30);
            TextView tv_before_10 = (TextView) view_hint_time.findViewById(R.id.dialog_hint_before_10);
            TextView tv_before_5 = (TextView) view_hint_time.findViewById(R.id.dialog_hint_before_5);
            TextView tv_before_0 = (TextView) view_hint_time.findViewById(R.id.dialog_hint_before_0);
            TextView tv_before_no = (TextView) view_hint_time.findViewById(R.id.dialog_hint_before_no);
            TextView tv_cancel = (TextView) view_hint_time.findViewById(R.id.dialog_hint_cancel);

            tv_before_30.setOnClickListener(this);
            tv_before_10.setOnClickListener(this);
            tv_before_5.setOnClickListener(this);
            tv_before_0.setOnClickListener(this);
            tv_before_no.setOnClickListener(this);
            tv_cancel.setOnClickListener(this);
            break;

        /*
         * 重复次数
         */
        case 1:
            View view_repeat_time = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_add_schedule_repeat_time, null);
            dialog.setContentView(view_repeat_time);

            TextView tv_repeat_times_1 = (TextView) view_repeat_time.findViewById(R.id.dialog_repeat_1);
            TextView tv_repeat_times_5 = (TextView) view_repeat_time.findViewById(R.id.dialog_repeat_5);
            TextView tv_repeat_times_10 = (TextView) view_repeat_time.findViewById(R.id.dialog_repeat_10);
            TextView tv_repeat_times_15 = (TextView) view_repeat_time.findViewById(R.id.dialog_repeat_15);
            TextView tv_repeat_cancel = (TextView) view_repeat_time.findViewById(R.id.dialog_repeat_cancel);

            tv_repeat_times_1.setOnClickListener(this);
            tv_repeat_times_5.setOnClickListener(this);
            tv_repeat_times_10.setOnClickListener(this);
            tv_repeat_times_15.setOnClickListener(this);
            tv_repeat_cancel.setOnClickListener(this);
            break;

        default:
            break;
        }
        return dialog;
    }

    public static AddScheduleDialogFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        AddScheduleDialogFragment fragment = new AddScheduleDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnHintTimeClickListener(OnHintTimeClickListener onHintTimeClickListener) {
        this.onHintTimeClickListener = onHintTimeClickListener;
    }


    public void setOnRepeatTimesClickListener(OnRepeatTimesClickListener onRepeatTimesClickListener) {
        this.onRepeatTimesClickListener = onRepeatTimesClickListener;
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        if (type == 0) {
            if (onHintTimeClickListener == null) {
                return;
            }

            switch (key) {
            /*
             * 提前30分钟提醒
             */
            case R.id.dialog_hint_before_30:
                onHintTimeClickListener.onHintTimeClick(1);
                break;

            /*
             *  提前10分钟提醒
             */
            case R.id.dialog_hint_before_10:
                onHintTimeClickListener.onHintTimeClick(0);
                break;

            /*
             *  提前5分钟提醒
             */
            case R.id.dialog_hint_before_5:
                onHintTimeClickListener.onHintTimeClick(2);
                break;

            /*
             *  准时提醒
             */
            case R.id.dialog_hint_before_0:
                onHintTimeClickListener.onHintTimeClick(3);
                break;

            /*
             * 不提醒
             */
            case R.id.dialog_hint_before_no:
                onHintTimeClickListener.onHintTimeClick(4);
                break;

            /*
             * 取消
             */
            case R.id.dialog_hint_cancel:
                break;


            default:
                break;
            }

            dismiss();

        } else if (type == 1) {
            if (onRepeatTimesClickListener == null) {
                return;
            }

            switch (key) {
            /*
             *  提醒1次
             */
            case R.id.dialog_repeat_1:
                onRepeatTimesClickListener.onRepeatTimesClick(0);
                break;

            /*
             * 每5分钟提醒一次
             */
            case R.id.dialog_repeat_5:
                onRepeatTimesClickListener.onRepeatTimesClick(1);
                break;

            /*
             *  每10分钟提醒一次
             */
            case R.id.dialog_repeat_10:
                onRepeatTimesClickListener.onRepeatTimesClick(2);
                break;

            /*
             * 每15分钟提醒一次
             */
            case R.id.dialog_repeat_15:
                onRepeatTimesClickListener.onRepeatTimesClick(3);
                break;

            /*
             *  取消
             */
            case R.id.dialog_repeat_cancel:
                break;

            default:
                break;
            }

            dismiss();
        }
    }

    /**
     * 自定义回调接口 --- 提醒时间
     */
    interface OnHintTimeClickListener {

        void onHintTimeClick(int type);
    }

    /**
     * 自定义回调接口--- 重复次数
     */
    interface OnRepeatTimesClickListener {

        void onRepeatTimesClick(int type);
    }
}
