package com.zfsoftmh.ui.modules.chatting.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.lib.presenter.conversation.CustomViewConversation;
import com.alibaba.mobileim.ui.IYWConversationFragment;
import com.zfsoftmh.R;

import java.lang.ref.WeakReference;

/**
 * Created by sy
 * on 2017/4/27.
 * <p>最近会话界面的定制点</p>
 */
public class ConversationList_UICustom extends IMConversationListUI {

    public ConversationList_UICustom(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 是否支持下拉刷新
     */
    @Override
    public  boolean getPullToRefreshEnabled(){
        return true;
    }

    /**
     * 自定义置顶回话的背景色(16进制字符串形式)
     */
    @Override
    public String getCustomTopConversationColor() {
        return "#e1f5fe";
    }

    /**
     * 搜索
     */
    @Override
    public boolean enableSearchConversations(Fragment fragment){
        return false;
    }

    /**
     * 构造一个会话列表为空时的展示View
     * @return
     *      empty view
     */
    @Override
    public View getCustomEmptyViewInConversationUI(Context context) {
        TextView textView = new TextView(context);
        textView.setText("还没有会话哦，快去找人聊聊吧!");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        return textView;
    }

    @Override
    public View getCustomConversationListTitle(Fragment fragment,
                                     final Context context, LayoutInflater inflater) {
        return null;
    }

    public boolean needHideTitleView(Fragment fragment){
        return true;
    }

    /**
     * 是否隐藏无网络提醒View
     * @return true: 隐藏无网络提醒，false：不隐藏无网络提醒
     */
    @Override
    public boolean needHideNullNetWarn(Fragment fragment) {
        return false;
    }

    /**
     * 会话列表onDestroy事件
     */
    @Override
    public void onDestroy(Fragment fragment) {
        super.onDestroy(fragment);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState, Fragment fragment) {
        super.onActivityCreated(savedInstanceState, fragment);
    }

    @Override
    public void onResume(Fragment fragment) {
        super.onResume(fragment);
    }

    /**
     * 会话列表初始化完成回调
     * @param fragment  会话列表Fragment
     */
    @Override
    public void onInitFinished(IYWConversationFragment fragment) {
        WeakReference<IYWConversationFragment> reference = new WeakReference<>(fragment);
        IYWConversationFragment mConversationFragment = reference.get();
        if (mConversationFragment != null){
            mConversationFragment.refreshAdapter();
        }
    }

    /**
     * 获取自定义会话要展示的自定义View，该方法的实现类完全似于BaseAdapter中getView()方法实现
     * @param conversation
     *          自定义展示View的会话
     * @param convertView
     *          {@link android.widget.BaseAdapter#getView(int, View, ViewGroup)}的第二个参数
     * @param parent
     *          {@link android.widget.BaseAdapter#getView(int, View, ViewGroup)}的第三个参数
     */
    @Override
    public View getCustomView(Context context, YWConversation conversation, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.conversation_custom_view_item, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.conversationName = (TextView)convertView.findViewById(R.id.conversation_name);
        viewHolder.conversationContent = (TextView)convertView.findViewById(R.id.conversation_content);
        CustomViewConversation customViewConversation = (CustomViewConversation)conversation;
        viewHolder.conversationName.setText(customViewConversation.getConversationName());
        viewHolder.conversationContent.setText(customViewConversation.getLatestContent());
        return convertView;
    }

    static class ViewHolder {
        TextView conversationName;
        TextView conversationContent;
    }
}
