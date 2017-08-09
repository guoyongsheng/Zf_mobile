package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 办结事宜
 */

public class SettlementMattersFragment extends BaseFragment<SettlementMattersPresenter> implements
        SettlementMattersContract.View, SwipeRefreshLayout.OnRefreshListener,
        SettlementMattersAdapter.OnItemClickListener {

    private static final String TAG = "SettlementMattersFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SettlementMattersAdapter adapter;
    private RecyclerView recyclerView;
    private OnScrollListener onScrollListener;

    private boolean isInit; //是否已经初始化
    private boolean isLoading; // 是否正在加载
    private boolean isLoaded; //是否已经加载过了
    private boolean isRefreshing; //是否正在刷新
    private int start = Config.LOADMORE.START_PAGE;

    @Override
    protected void initVariables() {
        adapter = new SettlementMattersAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_refreshing_view;
    }

    @Override
    protected void initViews(View view) {
        setIsInit(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.common_swipe_refresh_layout);
        setSwipeRefreshLayoutColor(swipeRefreshLayout);

        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setHasFixedSize(true);

        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        recyclerView.setAdapter(adapter);

        onScrollListener = new OnScrollListener();
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public static SettlementMattersFragment newInstance() {
        return new SettlementMattersFragment();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadData(int start, int flag) {
        if (isCanLoadData()) {
            presenter.loadData(start, Config.LOADMORE.PAGE_SIZE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData(start, 1);
        }
    }

    @Override
    public boolean isCanLoadData() {
        return isInit && !isLoading && !isLoaded && !isRefreshing && getUserVisibleHint();
    }

    @Override
    public void setIsInit(boolean isInit) {
        this.isInit = isInit;
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
    }

    @Override
    public void loadSuccess(List<AgencyMattersInfo> list) {

    }

    @Override
    public void loadFailure(String errorMsg) {

    }

    @Override
    public void onItemClick(AgencyMattersInfo agencyMattersInfo) {

    }


    //内部类---加载更多
    private class OnScrollListener extends com.zfsoftmh.ui.widget.OnScrollListener {

        @Override
        public void loadMore(int currentPage) {

        }
    }
}
