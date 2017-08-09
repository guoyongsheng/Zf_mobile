package com.zfsoftmh.ui.modules.chatting;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys;

/**
 * Created by sy
 * on 2017/5/8.
 * <p>聊天</p>
 */
public class ZChatActivity extends AppCompatActivity {

    Fragment mFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTint();
        setContentView(R.layout.lay_chatting);
        String targetID = getIntent().getStringExtra("targetID");
        long tribeID = getIntent().getLongExtra("tribeID",0);
        if(!TextUtils.isEmpty(targetID) && tribeID == 0){//单聊
            mFragment = IMKitHelper.getInstance().getIMKit().getChattingFragment(targetID, IMKitHelper.APP_KEY);
        }else{//群聊
            mFragment = IMKitHelper.getInstance().getIMKit().getTribeChattingFragment(tribeID);
            initReceiver();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.chat_frame, mFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragment != null)
            mFragment.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setStatusBarTint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(android.R.color.black);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

    /**
     * create by sy
     * <p>点击空白处收回软键盘</p>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private BroadcastReceiver receiver;
    private void initReceiver(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        IntentFilter filter = new IntentFilter(TribeKeys.FILTER_TRIBE_KILL);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null)
        unregisterReceiver(receiver);
    }
}
