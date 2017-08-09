package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail;

import android.view.View;

/**
 * Created by ljq
 * on 2017/7/28.
 * 用于右边的食品列表，食品的的计数器
 * -和+的监听
 */

public interface Counter {
    void addFood(View v, int pos);
    void removeFood(View v,int pos);

}
