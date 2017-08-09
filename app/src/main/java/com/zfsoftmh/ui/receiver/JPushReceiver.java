package com.zfsoftmh.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.NotificationInfo;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail.ScheduleDetailActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * @author wesley
 * @date: 2017-5-19
 * @Description: 消息推送的广播接收者
 */

public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LoggerHelper.e(TAG, "接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LoggerHelper.e(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LoggerHelper.e(TAG, "接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LoggerHelper.e(TAG, "接收到推送下来的通知的ID: " + notifactionId);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                LoggerHelper.e(TAG, "用户点击打开了通知");

                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA); //额外的字段
                NotificationInfo info = GsonHelper.stringToObject(extra, NotificationInfo.class);
                if (info != null) {
                    int type = info.getType();
                    switch (type) {
                    /*
                     * 邮件
                     */
                    case 0:
                        break;

                    /*
                     *  待办事宜
                     */
                    case 1:
                        break;

                    /*
                     *   日程
                     */
                    case 2:
                        long id = info.getId();
                        Intent intent_detail = new Intent(context, ScheduleDetailActivity.class);
                        Bundle bundle_detail = new Bundle();
                        bundle_detail.putLong("id", id);
                        bundle_detail.putInt("from", 1);
                        intent_detail.putExtras(bundle_detail);
                        intent_detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent_detail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent_detail);
                        break;


                    default:
                        break;
                    }
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                LoggerHelper.e(TAG, "用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LoggerHelper.e(TAG, intent.getAction() + " connected state change to " + connected);
            } else {
                LoggerHelper.e(TAG, " Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            LoggerHelper.e(TAG, " onReceive " + e.getMessage());
        }
    }
}
