package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.entity.AgencyMattersInfo;
import com.zfsoftmh.entity.AgencyMattersItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.detail.AgencyMattersDetailActivity;
import com.zfsoftmh.ui.widget.OnScrollListener;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 待办事宜
 */

public class AgencyMattersFragment extends BaseFragment<AgencyMattersPresenter> implements
        AgencyMattersContract.View, SwipeRefreshLayout.OnRefreshListener, AgencyMattersAdapter.OnItemClickListener {

    private static final String TAG = "AgencyMattersFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private AgencyMattersAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isInit; //是否已经初始化了
    private boolean isLoading; //数据是否正在加载
    private boolean isLoaded; //数据是否加载过了
    private boolean isRefreshing; //是否正在刷新

    private int start = Config.LOADMORE.START_PAGE; //起始页

    @Override
    protected void initVariables() {
        adapter = new AgencyMattersAdapter(context);
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

        loadData(start, 1);
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void loadMore(int currentPage) {
                setIsLoaded(false);
                loadData(currentPage, 0);
            }
        });
    }

    public static AgencyMattersFragment newInstance() {
        return new AgencyMattersFragment();
    }

    @Override
    public void onRefresh() {
        setIsLoaded(false);
        loadData(start, 1);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData(start, 1);
        }
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
    public boolean checkIsCanLoadData() {
        return isInit && !isLoading && !isLoaded && !isRefreshing && getUserVisibleHint();
    }

    @Override
    public void loadData(int start, int flag) {
        if (checkIsCanLoadData()) {
            setIsLoading(true);
            setIsLoaded(false);
            adapter.setFlag(flag);
            if (flag == 1) {
                setIsRefreshing(true);
                swipeRefreshLayout.setRefreshing(true);
            }
        }
    }

    @Override
    public void loadSuccess(AgencyMattersInfo agencyMattersInfo) {
        swipeRefreshLayout.setRefreshing(false);
        setIsLoading(false);
        setIsLoaded(true);
        setIsRefreshing(false);
        if (agencyMattersInfo != null) {
            adapter.setDataSource(agencyMattersInfo.getTodoTaskList());
        }
    }

    @Override
    public void loadFailure(String errorMsg) {
        swipeRefreshLayout.setRefreshing(false);
        setIsLoading(false);
        setIsLoaded(false);
        setIsRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setIsInit(false);
        setIsLoading(false);
        setIsLoaded(false);
        setIsRefreshing(false);
    }

    @Override
    public void onItemClick(AgencyMattersItemInfo agencyMattersInfo) {
        Intent intent = new Intent(context, AgencyMattersDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("currentItem", 0);
        bundle.putParcelable("data", agencyMattersInfo);
        intent.putExtras(bundle);
        openActivity(intent);
    }
}
