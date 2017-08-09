package com.zfsoftmh.ui.modules.chatting.tribe.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.kit.common.YWAsyncBaseAdapter;
import com.alibaba.mobileim.lib.model.message.YWSystemMessage;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;

/**
 * Created by sy
 * on 2017/5/23.
 */
class TribeSysMsgAdapter extends YWAsyncBaseAdapter {

    private LayoutInflater mInflater;
    private List<YWMessage> mMessageList;
    private SysTribeReqCallback mSysTribeReqCallback;

    TribeSysMsgAdapter(Context context,List<YWMessage> messages,SysTribeReqCallback callback) {
        mMessageList = messages;
        mSysTribeReqCallback = callback;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void refreshData(List<YWMessage> data){
        mMessageList = data;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView showName;
        TextView message;
        TextView agreeButton, refuseButton;
        TextView result;
        ImageView head;
        LinearLayout reLayout;
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
            holder.reLayout = (LinearLayout) convertView.findViewById(R.id.button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final YWMessage msg = mMessageList.get(position);
        final YWSystemMessage message = (YWSystemMessage) msg;
        long tid = Long.valueOf(message.getAuthorId());
        YWTribe tribe = IMKitHelper.getInstance().getIMKit().getTribeService().getTribe(tid);
        if (tribe != null) {
            holder.showName.setText(tribe.getTribeName());
        }
        holder.agreeButton.setText("接受");
        holder.refuseButton.setText("拒绝");
        holder.message.setText(message.getMessageBody().getContent());
        holder.agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSysTribeReqCallback.onAnswerClick(true,msg);
            }
        });
        holder.refuseButton.setOnClickListener(new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                mSysTribeReqCallback.onAnswerClick(false,msg);
            }
        });
        holder.reLayout.setVisibility(View.VISIBLE);
        if (message.isAccepted()){
            holder.agreeButton.setVisibility(View.GONE);
            holder.refuseButton.setVisibility(View.GONE);
            holder.result.setVisibility(View.VISIBLE);
            holder.result.setText("已同意");
        } else if (message.isIgnored()){
            holder.agreeButton.setVisibility(View.GONE);
            holder.refuseButton.setVisibility(View.GONE);
            holder.result.setVisibility(View.VISIBLE);
            holder.result.setText("已忽略");
        } else if (message.getSubType() == YWSystemMessage.SYSMSG_ASK_JOIN_TRIBE || message.getSubType() == YWSystemMessage.SYSMSG_TYPE_TRIBE){
            holder.agreeButton.setVisibility(View.VISIBLE);
            holder.refuseButton.setVisibility(View.VISIBLE);
            holder.result.setVisibility(View.GONE);
        } else {
            holder.reLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    interface SysTribeReqCallback{
        void onAnswerClick(boolean agree, YWMessage message);
    }

    @Override
    public void loadAsyncTask() { }
}
