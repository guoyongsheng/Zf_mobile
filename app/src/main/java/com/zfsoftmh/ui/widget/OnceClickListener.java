package com.zfsoftmh.ui.widget;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 * Created by sy
 * on 2017/5/2.
 * <p>单次的点击监听</P>
 */
public abstract class OnceClickListener implements OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onOnceClick(v);
        }
    }

    public abstract void onOnceClick(View v);

}	
