package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done;

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
 * @Description: 已办事宜
 */

public class HasBeenDoneMattersFragment extends BaseFragment<HasBeenDoneMattersPresenter>
        implements HasBeenDoneMattersContract.View, SwipeRefreshLayout.OnRefreshListener,
        HasBeenDoneMattersAdapter.OnItemClickListener {

    private static final String TAG = "HasBeenDoneMattersFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private HasBeenDoneMattersAdapter adapter;
    private boolean isInit; //是否已经初始化
    private boolean isLoading; //是否正在加载
    private boolean isLoaded; //是否已经加载过了

    private int start = Config.LOADMORE.START_PAGE;

    @Override
    protected void initVariables() {
        adapter = new HasBeenDoneMattersAdapter(context);
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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setHasFixedSize(true);

        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public static HasBeenDoneMattersFragment newInstance() {
        return new HasBeenDoneMattersFragment();
    }

    @Override
    public void onRefresh() {
        setIsLoaded(false);
        loadData(start, 1);
    }

    @Override
    public void loadData(int start, int flag) {
        if (isCanLoadData()) {
            setIsLoading(true);
            setIsLoaded(false);
            setIsRefreshing(true);
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
        return isInit && !isLoading && !isLoaded && getUserVisibleHint();
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
    public void loadSuccess(List<AgencyMattersInfo> list) {
        setIsLoading(false);
        setIsLoaded(true);
        setIsRefreshing(false);
        adapter.setDataSource(list);
    }

    @Override
    public void loadFailure(String errorMsg) {
        setIsLoading(false);
        setIsLoaded(false);
        setIsRefreshing(false);
        showToastMsgShort(errorMsg);
    }

    @Override
    public void setIsRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setIsInit(false);
        setIsLoading(false);
        setIsLoaded(false);
    }

    @Override
    public void onItemClick(AgencyMattersInfo AgencyMattersInfo) {

    }
}
