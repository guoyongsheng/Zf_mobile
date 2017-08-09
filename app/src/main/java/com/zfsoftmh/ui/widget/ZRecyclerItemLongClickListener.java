package com.zfsoftmh.ui.widget;

/**
 * Created by sy
 * on 2017/5/15.
 * <p>
 *     一般的RecyclerView长按监听
 * </p>
 */

public interface ZRecyclerItemLongClickListener<T> {

    void onItemLongClick(int pos , T t);

}
