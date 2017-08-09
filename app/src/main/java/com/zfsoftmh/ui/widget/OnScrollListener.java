package com.zfsoftmh.ui.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zfsoftmh.common.config.Config;

/**
 * @author wesley
 * @date: 2017/4/1
 * @Description: RecyclerView滚动加载
 */

public abstract class OnScrollListener extends RecyclerView.OnScrollListener {

    private boolean isLoading; //是否正在加载
    private int currentPage = 1; //当前页
    private int tempItemCount;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy <= 0) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)) {
            return;
        }

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int totalItemCount = linearLayoutManager.getItemCount(); //总数
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition(); //最后一个可见的位置

        if (isLoading) {
            if (totalItemCount > tempItemCount) {
                tempItemCount = totalItemCount;
                isLoading = false;
            }
        }

        if (!isLoading && (lastVisibleItemPosition + Config.LOADMORE.VISIBLE_THRESHOLD >= totalItemCount)) {
            currentPage++;
            isLoading = true;
            loadMore(currentPage);
        }
    }

    public void reset() {
        currentPage = 1;
        tempItemCount = 0;
    }

    public abstract void loadMore(int currentPage);
}
