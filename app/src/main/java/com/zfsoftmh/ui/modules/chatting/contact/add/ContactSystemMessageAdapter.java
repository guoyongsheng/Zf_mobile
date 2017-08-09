package com.zfsoftmh.ui.modules.chatting.contact.add;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.kit.common.YWAsyncBaseAdapter;
import com.alibaba.mobileim.kit.contact.ContactHeadLoadHelper;
import com.alibaba.mobileim.lib.model.message.YWSystemMessage;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;

/**
 * Created by sy
 * on 2017/5/8.
 */

class ContactSystemMessageAdapter extends YWAsyncBaseAdapter {

    private LayoutInflater mInflater;
    private List<YWMessage> mMessageList;
    private ContactHeadLoadHelper mContactHeadLoadHelper;
    private String mAppKey;
    private SysFriReqCallback mSysFriReqCallback;

    ContactSystemMessageAdapter(Context context,
                                       List<YWMessage> messages,
                                       SysFriReqCallback callback) {
        this.mSysFriReqCallback = callback;
        mMessageList = messages;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        YWIMKit ywimKit = IMKitHelper.getInstance().getIMKit();
        mContactHeadLoadHelper = new ContactHeadLoadHelper((Activity)context, null, ywimKit.getUserContext());
        mAppKey = IMKitHelper.APP_KEY;
    }

    private class ViewHolder {
        TextView showName;
        TextView message;
        TextView agreeButton, refuseButton;
        TextView result;
        ImageView head;
    }

    void refreshData(List<YWMessage> list){
        mMessageList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_contact_system_message, parent, false);
            holder = new ViewHolder();
            holder.showName = (TextView) convertView.findViewById(R.id.contact_title);
            holder.head = (ImageView) convertView.findViewById(R.id.head);
            holder.message = (TextView) convertView.findViewById(R.id.invite_message);
            holder.agreeButton = (TextView) convertView.findViewById(R.id.agree);
            holder.refuseButton = (TextView) convertView.findViewById(R.id.refuse);
            holder.result = (TextView) convertView.findViewById(R.id.result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mMessageList != null) {
            final YWMessage msg = mMessageList.get(position);
            final YWSystemMessage message = (YWSystemMessage) msg;
            String authorUserId = message.getAuthorUserId();
            String str;
            str = authorUserId + " 申请加你为好友";
            holder.showName.setText(str);
            str = "备注: " + message.getMessageBody().getContent();
            holder.message.setText(str);
            holder.agreeButton.setText("接受");
            holder.refuseButton.setText("拒绝");
            holder.agreeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSysFriReqCallback.onAnswerClick(true, msg);
                }
            });
            holder.refuseButton.setOnClickListener(new OnceClickListener() {
                @Override
                public void onOnceClick(View v) {
                    mSysFriReqCallback.onAnswerClick(false, msg);
                }
            });

            if (message.isAccepted()){
                holder.agreeButton.setVisibility(View.GONE);
                holder.refuseButton.setVisibility(View.GONE);
                holder.result.setVisibility(View.VISIBLE);
                holder.result.setText("已添加");
            } else if (message.isIgnored()){
                holder.agreeButton.setVisibility(View.GONE);
                holder.refuseButton.setVisibility(View.GONE);
                holder.result.setVisibility(View.VISIBLE);
                holder.result.setText("已拒绝");
            } else {
                holder.agreeButton.setVisibility(View.VISIBLE);
                holder.refuseButton.setVisibility(View.VISIBLE);
                holder.result.setVisibility(View.GONE);
            }
            mContactHeadLoadHelper.setHeadView(holder.head, authorUserId, mAppKey,true);
        }
        return convertView;
    }

    interface SysFriReqCallback{
        void onAnswerClick(boolean agree, YWMessage message);
    }

    @Override
    public void loadAsyncTask() { }
}
