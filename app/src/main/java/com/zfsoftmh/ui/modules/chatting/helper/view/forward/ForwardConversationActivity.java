package com.zfsoftmh.ui.modules.chatting.helper.view.forward;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWCrossContactProfileCallback;
import com.alibaba.mobileim.contact.IYWDBContact;
import com.alibaba.mobileim.contact.YWContactFactory;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.alibaba.mobileim.fundamental.widget.WxAlertDialog;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeSe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/25.
 * <p>转发</p>
 */
public class ForwardConversationActivity extends BaseActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    @Override
    protected void initVariables() {
        ActivityCollector.getInstance().addActivity(this);
        mIMKit = IMKitHelper.getInstance().getIMKit();
    }

    private int _Type;
    private final int TYPE_CONV = 0;//从最近会话中选择
    private final int TYPE_FRIEND = 1;//从好友中选择
    private final int TYPE_TRIBE = 2;//从群组中选择

    private YWMessage mMessage;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        _Type = getIntent().getIntExtra("_Type",0);//默认从最近会话中选择
        mMessage = (YWMessage) getIntent().getSerializableExtra("forward_msg");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_forward;
    }

    private YWIMKit mIMKit;
    private ConvAdapter adapter;
    private View parent;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("转发到...");
        setDisplayHomeAsUpEnabled(true);
        enableTouchToHideKeyboard();
        List<ForwardBean> dataList = new ArrayList<>();

        List<YWConversation> mList = mIMKit.getConversationService().getConversationList();
        ListView mListView = (ListView) findViewById(R.id._forward_item_list);
        ForwardBean bean;
        parent = findViewById(R.id.forward_parent);
        if(_Type == TYPE_CONV){
            IYWCrossContactProfileCallback callback = mIMKit.getContactService().getCrossContactProfileCallback();
            for (YWConversation conversation: mList) {
                if(conversation.getConversationType() == YWConversationType.P2P){
                    YWP2PConversationBody body = (YWP2PConversationBody) conversation.getConversationBody();
                    IYWContact contact = body.getContact();
                    IYWContact iywContact = callback.onFetchContactInfo(contact.getUserId(),contact.getAppKey());
                    if (iywContact != null){
                        ChatContact mContact = new ChatContact(
                                iywContact.getUserId(),
                                iywContact.getAppKey(),
                                iywContact.getAvatarPath(),
                                iywContact.getShowName());
                        mContact.generateSpell();
                        bean = new ForwardBean(mContact, null);
                        dataList.add(bean);
                    }
                }else if(conversation.getConversationType() == YWConversationType.Tribe){
                    YWTribeConversationBody body = (YWTribeConversationBody) conversation.getConversationBody();
                    TribeSe tribeSe = new TribeSe(body.getTribe());
                    bean = new ForwardBean(null, tribeSe);
                    dataList.add(bean);
                }
            }
        }else if(_Type == TYPE_FRIEND){
            List<IYWDBContact> contacts = mIMKit.getContactService().getContactsFromCache();
            IYWCrossContactProfileCallback callback = mIMKit.getContactService().getCrossContactProfileCallback();
            for (IYWDBContact dbContact: contacts) {
                IYWContact iywContact = callback.onFetchContactInfo(dbContact.getUserId(),dbContact.getAppKey());
                if (iywContact != null){
                    ChatContact mContact = new ChatContact(
                            iywContact.getUserId(),
                            iywContact.getAppKey(),
                            iywContact.getAvatarPath(),
                            iywContact.getShowName());
                    mContact.generateSpell();
                    bean = new ForwardBean(mContact, null);
                    dataList.add(bean);
                }
            }
        }else if(_Type == TYPE_TRIBE){
            List<YWTribe> tribes= mIMKit.getTribeService().getAllTribes();
            for (YWTribe tribe:tribes) {
                TribeSe tribeSe = new TribeSe(tribe);
                bean = new ForwardBean(null, tribeSe);
                dataList.add(bean);
            }
        }
        adapter = new ConvAdapter(dataList, this, _Type);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.getInstance().removeActivity(this);
    }

    @Override
    protected void initListener() { }


    private ForwardBean bean;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            switch (_Type) {
                case TYPE_CONV:
                    Intent intent1 = new Intent(this, ForwardConversationActivity.class);
                    intent1.putExtra("_Type", TYPE_FRIEND);
                    intent1.putExtra("forward_msg",mMessage);
                    startActivity(intent1);
                    break;
                case TYPE_FRIEND:
                    Intent intent2 = new Intent(this, ForwardConversationActivity.class);
                    intent2.putExtra("_Type", TYPE_TRIBE);
                    intent2.putExtra("forward_msg",mMessage);
                    startActivity(intent2);
                    break;
                case TYPE_TRIBE:
                    break;
            }
        }else{
            bean = adapter.getForwardBean(position);
            if (bean == null) return;
            String str = null;
            if (bean.tribe == null && bean.contact != null){
                str = bean.contact.getShowName();
            }else if(bean.contact == null && bean.tribe != null){
                str = bean.tribe.getTribeName();
            }
            String msg = TextUtils.isEmpty(str)?"确认发送":"发送给 "+ str;
            new WxAlertDialog.Builder(this)
                    .setMessage(msg)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (bean.tribe == null && bean.contact != null){
                                IYWContact iywContact = YWContactFactory.createAPPContact(bean.contact.getUserId(),IMKitHelper.APP_KEY);
                                mIMKit.getConversationService().forwardMsgToContact(iywContact,mMessage,iWxCallback);
                            }else if(bean.contact == null && bean.tribe != null){
                                mIMKit.getConversationService().forwardMsgToTribe(bean.tribe.getTribeId(),mMessage,iWxCallback);
                            }
                        }
                    }).create().show();


        }

    }


    private Handler handler = new Handler(Looper.getMainLooper());

    private IWxCallback iWxCallback = new IWxCallback() {
        @Override
        public void onSuccess(Object... objects) {
            Snackbar.make(parent,"发送成功",Snackbar.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityCollector.getInstance().finishAll();
                }
            },1000);

        }

        @Override
        public void onError(int i, String s) {
            Snackbar.make(parent,"发送失败",Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onProgress(int i) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.menu_forward_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        EditText et = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn).setVisibility(View.GONE);
        et.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}
