package com.zfsoftmh.ui.base;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.entity.ResponseListInfo;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-2
 * @Description: 适用于界面上有下拉刷新和滚动加载的情况
 */

public abstract class BaseListFragment<T extends BasePresenter, K> extends BaseFragment<T> implements
        SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener,
        RecyclerArrayAdapter.OnErrorListener, RecyclerArrayAdapter.OnItemClickListener, BaseListView<T, K> {

    protected boolean loadMoreEnable = true; //是否可以加载更多
    protected boolean refreshEnable = true; //是否可以下拉刷新
    protected boolean progressEnable = true; //加载时是否显示progressView
    protected static final int IS_REFRESHING = 0; //下拉刷新
    protected static final int IS_LOAD_MORE = 1; //滚动加载
    protected int start_page = 1; //起始页
    protected static final int PAGE_SIZE = Config.LOADMORE.PAGE_SIZE; //每页加载多少条数据
    protected int status = IS_REFRESHING; //0：下拉刷新  1:滚动加载
    protected EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<K> adapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.common_easy_recycler_view;
    }

    @Override
    protected void initViews(View view) {
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.common_easy_recycler_view);
        recyclerView.setRefreshingColorResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

        //LayoutManager
        recyclerView.setLayoutManager(getLayoutManager());

        //ItemDecoration
        recyclerView.addItemDecoration(getItemDecoration());

        recyclerView.setEmptyView(R.layout.common_empty_view);
        recyclerView.setProgressView(R.layout.common_progress_view);
        recyclerView.setErrorView(R.layout.common_error_view);


        //adapter
        adapter = getAdapter();
        if (progressEnable) {
            recyclerView.setAdapterWithProgress(adapter);
        } else {
            recyclerView.setAdapter(adapter);
        }

        initHeaderView();
        initView();
        loadData();
    }

    @Override
    protected void initListener() {
        if (refreshEnable) {
            recyclerView.setRefreshListener(this);
        }
        adapter.setOnItemClickListener(this);
    }

    /**
     * 获取LayoutManager
     *
     * @return LayoutManager实例
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }

    /**
     * 获取分割线的实例
     *
     * @return ItemDecoration实例
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        return itemDecoration;
    }

    /**
     * 显示没有数据的布局
     */
    protected void showEmptyView() {
        if (recyclerView != null) {
            recyclerView.showEmpty();
        }
    }

    /**
     * 显示Progress的布局
     */
    protected void showProgressView() {
        if (recyclerView != null) {
            recyclerView.showProgress();
        }
    }

    /**
     * 显示加载错误的布局
     */
    protected void showErrorView() {
        if (recyclerView != null) {
            recyclerView.showError();
        }
    }

    /**
     * 显示RecyclerView的布局
     */
    protected void showRecyclerView() {
        if (recyclerView != null) {
            recyclerView.showRecycler();
        }
    }

    /**
     * 加载更多
     */
    public void onMoreShow() {
        if (!loadMoreEnable) {
            return;
        }
        start_page++;
        status = IS_LOAD_MORE;
        loadData();
    }

    /**
     * 点击更多的时候
     */
    public void onMoreClick() {

    }

    /**
     * 显示错误布局
     */
    public void onErrorShow() {

    }

    /**
     * 当点击错误布局的时候---重新开始加载
     */
    public void onErrorClick() {
        if (adapter != null) {
            start_page--;
            adapter.resumeMore();
        }
    }

    @Override
    public void onRefresh() {
        start_page = 1;
        status = IS_REFRESHING;
        loadData();
    }

    @Override
    public void loadSuccess(ResponseListInfo<K> info) {
        switch (status) {
        /*
         * 刷新状态
         */
        case IS_REFRESHING:
            recyclerView.setRefreshing(false);
            if (info == null) {
                showErrorView();
                return;
            }
            List<K> list = info.getItemList();
            if (checkDataIsNull(list)) {
                showEmptyView();
                return;
            }
            adapter.clear();
            adapter.addAll(list);
            break;

        /*
         * 滚动加载状态
         */
        case IS_LOAD_MORE:
            if (info == null) {
                adapter.pauseMore();
                return;
            }
            List<K> listMore = info.getItemList();
            if (checkDataIsNull(listMore)) {
                adapter.stopMore();
                return;
            }
            adapter.addAll(listMore);
            break;

        default:
            break;
        }
        boolean isOvered = info.isOvered();
        if (isOvered) {
            adapter.stopMore();
        }
    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
        switch (status) {
        /*
         * 刷新状态
         */
        case IS_REFRESHING:
            recyclerView.setRefreshing(false);
            showErrorView();
            break;

        /*
         * 滚动加载状态
         */
        case IS_LOAD_MORE:
            adapter.pauseMore();
            break;

        default:
            break;
        }
    }

    @Override
    public boolean checkDataIsNull(List<K> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 是否显示加载进度
     *
     * @param progressEnable true: 显示  false：不显示
     */
    protected void setProgressEnable(boolean progressEnable) {
        this.progressEnable = progressEnable;
    }

    /**
     * 设置是否可以下拉刷新 --- 用户初始化的时候
     *
     * @param refreshEnable true: 可以 false: 不可以
     */
    protected void setRefreshEnable(boolean refreshEnable) {
        this.refreshEnable = refreshEnable;
    }

    /**
     * 设置下拉刷新是否可用
     *
     * @param enabled true: 可以 false: 不可以
     */
    protected void setRefreshEnabled(boolean enabled) {
        if (recyclerView != null && recyclerView.getSwipeToRefresh() != null) {
            recyclerView.getSwipeToRefresh().setEnabled(enabled);
        }
    }

    /**
     * 设置是否可以滚动加载
     *
     * @param loadMoreEnabled true: 可以 false: 不可以
     */
    protected void setLoadMoreEnabled(boolean loadMoreEnabled) {
        if (adapter != null) {
            this.loadMoreEnable = loadMoreEnabled;
            if (loadMoreEnabled) {
                adapter.setMore(R.layout.common_load_more, this);
            } else {
                adapter.setMore(R.layout.common_mpty_load_more, this);
            }
        }
    }

    //初始化加载的各种状态
    private void initView() {
        adapter.setMore(R.layout.common_load_more, this);
        adapter.setNoMore(R.layout.common_no_more);
        adapter.setError(R.layout.common_error, this);
    }

    //初始化headerView
    protected void initHeaderView() {

    }


    /**
     * 获取RecyclerArrayAdapter实例
     *
     * @return RecyclerArrayAdapter实例
     */
    protected abstract RecyclerArrayAdapter<K> getAdapter();

    //加载数据
    protected abstract void loadData();

}
