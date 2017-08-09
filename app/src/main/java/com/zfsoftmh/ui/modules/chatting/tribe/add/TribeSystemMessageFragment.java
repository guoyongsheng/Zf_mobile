package com.zfsoftmh.ui.modules.chatting.tribe.add;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.IYWMessageListener;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWSystemConversation;
import com.alibaba.mobileim.lib.model.message.YWSystemMessage;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sy
 * on 2017/5/23.
 * <P>群系统消息</P>
 */
public class TribeSystemMessageFragment extends BaseFragment implements TribeSysMsgAdapter.SysTribeReqCallback {

    public static TribeSystemMessageFragment newInstance() {
        return new TribeSystemMessageFragment();
    }

    @Override
    protected void initVariables() { }

    @Override
    protected void handleBundle(Bundle bundle) { }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_contact_activity_system_message;
    }

    private YWIMKit mIMKit;
    private YWSystemConversation mConversation;
    private List<YWMessage> mList = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TribeSysMsgAdapter mAdapter;

    @Override
    protected void initViews(View view) {
        setHasOptionsMenu(true);
        mIMKit = IMKitHelper.getInstance().getIMKit();
        ListView mListView = (ListView) view.findViewById(R.id.message_list);
        mConversation = mIMKit.getConversationService().getSystemConversation();
        if(mConversation == null)
            mList = new ArrayList<>();
        else
            mList = mConversation.getMessageLoader().loadMessage(20, null);
        mAdapter = new TribeSysMsgAdapter(context, mList, this);
        mListView.setAdapter(mAdapter);
        mIMKit.getConversationService().markReaded(mConversation);
    }

    private IYWMessageListener mMessageListener;

    @Override
    protected void initListener() {
        if(mConversation == null) return;
        mMessageListener = new IYWMessageListener() {
            @Override
            public void onItemUpdated() {//消息列表变更，例如删除一条消息，修改消息状态，加载更多消息等等
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChangedWithAsyncLoad();
                    }
                });
            }

            @Override
            public void onItemComing() {//收到新消息
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChangedWithAsyncLoad();
                        if (mIMKit != null && mIMKit.getConversationService() != null)
                            mIMKit.getConversationService().markReaded(mConversation);
                    }
                });
            }

            @Override
            public void onInputStatus(byte b) {  }
        };
        //添加新消息到达监听,监听到有新消息到达的时候或者消息类别有变更的时候应该更新adapter
        mConversation.getMessageLoader().addMessageListener(mMessageListener);
    }

    private void refreshAdapter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.refreshData(mList);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mConversation != null)
            mConversation.getMessageLoader().removeMessageListener(mMessageListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_sys_msg, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id._sys_msg_clear)
            mConversation.getMessageLoader().deleteAllMessage();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnswerClick(boolean agree, YWMessage message) {
        if (agree)
            acceptToJoinTribe(message);
        else
            refuseToJoinTribe(message);
    }

    public void acceptToJoinTribe(YWMessage message) {
        YWSystemMessage msg = (YWSystemMessage) message;
        mConversation.accept(msg, new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                refreshAdapter();
            }
            @Override
            public void onError(int code, String info) { }
            @Override
            public void onProgress(int progress) { }
        });
    }

    public void refuseToJoinTribe(YWMessage message) {
        YWSystemMessage msg = (YWSystemMessage) message;
        mConversation.reject(msg, new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                refreshAdapter();
            }
            @Override
            public void onError(int code, String info) {  }
            @Override
            public void onProgress(int progress) { }
        });
    }

}
