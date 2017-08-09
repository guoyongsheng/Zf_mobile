package com.zfsoftmh.ui.modules.chatting.tribe.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.mobileim.channel.constant.YWProfileSettingsConstants;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.fundamental.widget.WxAlertDialog;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;


/**
 * Created by sy
 * on 2017/5/15.
 * <p>群信息</p>
 */
public class TribeInfoActivity extends BaseActivity implements TribeInfoContract.View{

    private TribeInfoPresenter mPresenter;
    @Override
    protected void initVariables() { }

    private long mTribeId;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        mTribeId = getIntent().getLongExtra(TribeKeys.TRIBE_ID, 0);
        mPresenter = new TribeInfoPresenter(this, mTribeId);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_tribe_info;
    }

    private TextView tribeInfoName, myTribeNick,clearTribeMsg;
    private ToggleButton tbTop, tribeMsgRecType;
    private Button btnQuit;
    private FrameLayout layTribeName,layNick,layZx;
    private TribeMemberAdapter adapter;
    private View mView;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle("群聊信息");
        setDisplayHomeAsUpEnabled(true);
        mView = findViewById(R.id.tribe_info_parent);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.tribe_members);
        tribeInfoName = (TextView) findViewById(R.id.tribe_info_name);
        tribeMsgRecType = (ToggleButton) findViewById(R.id.tribe_msg_rec_type);
        myTribeNick = (TextView) findViewById(R.id.my_tribe_nick);
        clearTribeMsg = (TextView) findViewById(R.id.clear_tribe_msg);
        tbTop = (ToggleButton) findViewById(R.id.tribe_tb_top);
        btnQuit = (Button) findViewById(R.id.quite_tribe);
        layTribeName = (FrameLayout) findViewById(R.id.tribe_name_layout);
        layNick = (FrameLayout) findViewById(R.id.tribe_nick_layout);
        layZx = (FrameLayout) findViewById(R.id.tribe_zx_layout);
        recycler.setLayoutManager(new GridLayoutManager(this, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter = new TribeMemberAdapter(this,mTribeId);
        recycler.setAdapter(adapter);

        mPresenter.initTribeInfo();
    }

    @Override
    public void onProfileUpdate(YWTribe tribe) {
        tribeInfoName.setText(tribe.getTribeName());
        int msgFlag = tribe.getMsgRecType();
        tribeMsgRecType.setChecked(msgFlag == YWProfileSettingsConstants.TRIBE_MSG_REC_NOT_REMIND);
        myTribeNick.setText(mPresenter.getLoginUserTribeNick());
        tbTop.setChecked(mPresenter.getConversation().isTop());
        if (mPresenter.isTribeManager())
            btnQuit.setText(R.string.destroy_tribe);
        else
            btnQuit.setText(R.string.quite_tribe);
    }

    @Override
    public void updateMembersView(List<IYWContact> list) {
        adapter.setData(list);
    }

    private boolean hasQuited = false;//防止群主解散群自己调用了两次quitTribe
    @Override
    public void onQuitTribe() {
        if (!hasQuited){
            hasQuited = true;
            Intent intent = new Intent(TribeKeys.FILTER_TRIBE_KILL);
            sendBroadcast(intent);
            finish();
        }
    }

    @Override
    public void onTribeInfoModify(String newName, String newNick) {
        if(newName != null)
            tribeInfoName.setText(newName);
        if (newNick != null)
            myTribeNick.setText(newNick);
    }

    @Override
    protected void setUpInject() { }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == TribeKeys.TRIBE_EDIT_NAME){
            String tribeName = data.getStringExtra(TribeKeys.TRIBE_SET_RESULT);
            if(!TextUtils.equals(tribeName,tribeInfoName.getText().toString())){
                mPresenter.modifyTribeInfo(tribeName);
                Intent intent = new Intent(TribeKeys.FILTER_TRIBE_KILL);
                sendBroadcast(intent);
            }
        }else if(requestCode == 1 && resultCode == TribeKeys.TRIBE_EDIT_NICK){
            String nick = data.getStringExtra(TribeKeys.TRIBE_SET_RESULT);
            if(!TextUtils.equals(nick,myTribeNick.getText().toString()))
                mPresenter.modifyTribeNick(nick);
        }
    }

    private OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            switch (v.getId()){
                case R.id.tribe_name_layout://群名称
                    skipActivity(TribeKeys.TRIBE_EDIT_NAME, tribeInfoName.getText().toString());
                    break;
                case R.id.tribe_nick_layout://群昵称
                    skipActivity(TribeKeys.TRIBE_EDIT_NICK, myTribeNick.getText().toString());
                    break;
                case R.id.tribe_zx_layout://群二维码
                    skipActivity(TribeKeys.TRIBE_EDIT_ZX, "");
                    break;
                case R.id.clear_tribe_msg://清聊天记录
                    clearMsgRecord();
                    break;
                case R.id.quite_tribe://退出群
                    mPresenter.quitTribe();
                    break;
            }
        }
    };

    private void clearMsgRecord(){
        String message = "清空的消息再次漫游时不会出\n你确定要清空聊天消息吗？";
        AlertDialog.Builder builder = new WxAlertDialog.Builder(TribeInfoActivity.this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mPresenter.getConversation().getMessageLoader().deleteAllMessage();
                                showErrMsg("消息记录已清空");
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        builder.create().show();
    }

    private void skipActivity(int key, String content){
        Intent intent = new Intent(TribeInfoActivity.this,TribeSetActivity.class);
        intent.putExtra(TribeKeys.TRIBE_ID,mTribeId);
        intent.putExtra(TribeKeys.TRIBE_EDIT, key);
        intent.putExtra(TribeKeys.TRIBE_SET_CONTENT, content);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void initListener() {
        layTribeName.setOnClickListener(clickListener);
        layNick.setOnClickListener(clickListener);
        layZx.setOnClickListener(clickListener);
        btnQuit.setOnClickListener(clickListener);
        clearTribeMsg.setOnClickListener(clickListener);
        tbTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.setConversation(isChecked);
            }
        });
        tribeMsgRecType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.setMsgNotify(isChecked);
            }
        });
    }

    @Override
    public void showErrMsg(String msg) {
        Snackbar.make(mView,msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
