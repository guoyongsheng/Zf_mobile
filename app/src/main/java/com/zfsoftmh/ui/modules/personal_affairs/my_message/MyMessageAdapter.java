package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.MyMessageItemInfo;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

public class MyMessageAdapter extends RecyclerArrayAdapter<MyMessageItemInfo> {
    private Context context;

    public MyMessageAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(parent, R.layout.item_my_message);
    }

    private class ItemHolder extends BaseViewHolder<MyMessageItemInfo> {
        private CircleImageView my_message_iv; // 类型图标
        private TextView my_message_type_tv; // 消息类型(邮件消息,待办消息,其他)
        private TextView sender_tv, sent_time_tv, notification_title_tv; // 发送人，发送时间，消息标题

        public ItemHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            my_message_iv = $(R.id.my_message_iv);
            my_message_type_tv = $(R.id.my_message_type_tv);
            sender_tv = $(R.id.sender_tv);
            sent_time_tv = $(R.id.sent_time_tv);
            notification_title_tv = $(R.id.notification_title_tv);
        }

        @Override
        public void setData(MyMessageItemInfo data) {
            super.setData(data);
            String type = data.func_type; // 消息类型
            String sender = data.tsry; // 发件人
            String time = data.tssjStr; // 发送时间
            String title = data.tsnr; // 消息标题

            if (type != null && !type.equals("")) {
                if (type.equals("301")) { // 通知公告
                    ImageLoaderHelper.loadDefConfCirCleImage(context, my_message_iv, "", R.mipmap.announcement);
                    my_message_type_tv.setText("通知公告");
                } else if (type.equals("302")) { // 邮件消息
                    ImageLoaderHelper.loadDefConfCirCleImage(context, my_message_iv, "", R.mipmap.email);
                    my_message_type_tv.setText("邮件消息");
                } else if (type.equals("306")) { // 待办消息
                    ImageLoaderHelper.loadDefConfCirCleImage(context, my_message_iv, "", R.mipmap.to_do);
                    my_message_type_tv.setText("待办消息");
                } else if (type.equals("jw")) { // 教务
                    ImageLoaderHelper.loadDefConfCirCleImage(context, my_message_iv, "", R.mipmap.my_subject);
                    my_message_type_tv.setText("教务消息");
                } else if (type.equals("oa")) { // 办公消息
                    ImageLoaderHelper.loadDefConfCirCleImage(context, my_message_iv, "", R.mipmap.my_subject);
                    my_message_type_tv.setText("办公消息");
                } else {
                    ImageLoaderHelper.loadDefConfCirCleImage(context, my_message_iv, "", R.mipmap.other);
                    my_message_type_tv.setText("其他消息");
                }
            }

            if (sender != null)
                sender_tv.setText(sender);
            if (time != null)
                sent_time_tv.setText(time);
            if (title != null)
                notification_title_tv.setText(title);
        }
    }
}
