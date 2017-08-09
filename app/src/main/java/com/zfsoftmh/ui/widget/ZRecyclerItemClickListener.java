package com.zfsoftmh.ui.widget;

/**
 * Created by sy
 * on 2017/5/15.
 * <p>
 *     一般的RecyclerView点击监听
 * </p>
 */

public interface ZRecyclerItemClickListener<T> {

    void onRecyclerItemClick(int pos,T t);

}
