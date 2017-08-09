package com.zfsoftmh.ui.modules.personal_affairs.one_card.consumer_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DateUtils;
import com.zfsoftmh.entity.OneCardItemDetailsInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wesley
 * @date: 2017/4/13
 * @Description: ui
 */

public class ConsumerDetailsFragment extends BaseFragment<ConsumerDetailsPresenter> implements
        ConsumerDetailsContract.View, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ConsumerDetailsAdapter adapter;
    final static int TYPE_REFRESHING = 0; //下拉刷新
    final static int TYPE_LOADING = 1; //滚动加载
    private final static int PAGE_SIZE = Config.LOADMORE.PAGE_SIZE; //每页加载多少条数据
    private int START_PAGE = 1; //当前页
    private int isLoadOrRefreshing;
    private String oneCardId; //一卡通的id

    private boolean isLoading; //是否正在加载
    private boolean isRefreshing; //是否正在刷新
    private boolean hasMore; //是否还有更多

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        oneCardId = bundle.getString("id");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_refreshing_view;
    }

    @Override
    protected void initViews(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.common_swipe_refresh_layout);
        setSwipeRefreshLayoutColor(swipeRefreshLayout);

        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //Divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adapter
        adapter = new ConsumerDetailsAdapter(context);

        //HeaderDecoration
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        recyclerView.setAdapter(adapter);

        loadData(START_PAGE, TYPE_REFRESHING);
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new OnScrollListener());
    }

    public static ConsumerDetailsFragment newInstance(String oneCardId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", oneCardId);
        ConsumerDetailsFragment fragment = new ConsumerDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        START_PAGE = 1;
        loadData(START_PAGE, TYPE_REFRESHING);
    }

    @Override
    public void loadData(int startPage, int isLoadOrRefreshing) {
        this.isLoadOrRefreshing = isLoadOrRefreshing;
        adapter.setIsLoadOrRefreshing(isLoadOrRefreshing);
        if (isLoadOrRefreshing == TYPE_REFRESHING) {
            startRefreshing();
        }
        isLoading = true;
        isRefreshing = true;
        presenter.loadData(startPage, PAGE_SIZE, oneCardId);
    }

    @Override
    public void loadSuccess(ResponseListInfo<OneCardItemDetailsInfo> info) {
        if (isLoadOrRefreshing == TYPE_REFRESHING) {
            stopRefreshing();
            if (info == null || info.getItemList() == null || info.getItemList().size() == 0) {
                showToastMsgShort(getResources().getString(R.string.no_data));
                return;
            }
        }
        if (info == null) {
            return;
        }
        isLoading = false;
        isRefreshing = false;
        hasMore = !info.isOvered();

        switch (isLoadOrRefreshing) {
        /*
         * 刷新
         */
        case TYPE_REFRESHING:
            mapDataInRefreshing(info.getItemList());
            break;


        /*
         * 滚动加载
         */
        case TYPE_LOADING:
            mapDataInLoading(info.getItemList());
            break;

        default:
            break;
        }
    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
        isLoading = false;
        isRefreshing = false;
        if (isLoadOrRefreshing == TYPE_REFRESHING) {
            stopRefreshing();
        }
    }

    @Override
    public void startRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void mapDataInRefreshing(List<OneCardItemDetailsInfo> list) {
        Observable.just(list)
                .subscribeOn(Schedulers.io())
                .map(new Function<List<OneCardItemDetailsInfo>, List<OneCardItemDetailsInfo>>() {
                    @Override
                    public List<OneCardItemDetailsInfo> apply(@NonNull List<OneCardItemDetailsInfo> list) throws Exception {
                        if (list == null) {
                            return null;
                        }

                        @SuppressLint("UseSparseArrays")
                        Map<Long, List<OneCardItemDetailsInfo>> map = new HashMap<>();
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            OneCardItemDetailsInfo info = list.get(i);
                            if (info != null) {
                                String time = info.getConsumetime(); //时间
                                String header = DateUtils.parseTime(time);
                                long headerTime = DateUtils.parseLong(header);
                                info.setHeader(header);
                                if (map.containsKey(headerTime)) {
                                    List<OneCardItemDetailsInfo> listMap = map.get(headerTime);
                                    listMap.add(info);
                                    map.put(headerTime, listMap);
                                } else {
                                    List<OneCardItemDetailsInfo> listMap = new ArrayList<>();
                                    listMap.add(info);
                                    map.put(headerTime, listMap);
                                }

                            }
                        }
                        int temp = 0;
                        List<OneCardItemDetailsInfo> listMap = new ArrayList<>();
                        Map<Long, List<OneCardItemDetailsInfo>> treeMap = new TreeMap<>(map);
                        List<Long> listKey = new ArrayList<>();
                        for (long key : treeMap.keySet()) {
                            listKey.add(key);
                        }

                        int sizeKey = listKey.size();
                        for (int i = sizeKey - 1; i >= 0; i--) {
                            long key = listKey.get(i);
                            if (treeMap.containsKey(key)) {
                                List<OneCardItemDetailsInfo> listHeader = map.get(key);
                                int sizeHeader = listHeader.size();
                                for (int j = 0; j < sizeHeader; j++) {
                                    OneCardItemDetailsInfo info = listHeader.get(j);
                                    if (info != null) {
                                        info.setHeaderId(temp);
                                        listMap.add(info);
                                    }
                                }
                                temp++;
                            }
                        }
                        return listMap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<OneCardItemDetailsInfo>>() {
                    @Override
                    public void accept(@NonNull List<OneCardItemDetailsInfo> list) throws Exception {
                        if (!isActive()) {
                            return;
                        }
                        adapter.setDataSource(list);
                    }
                });

    }

    @Override
    public void mapDataInLoading(List<OneCardItemDetailsInfo> list) {
        List<OneCardItemDetailsInfo> listBeforeLoad = adapter.getAllItems();
        if (list == null || listBeforeLoad == null) {
            return;
        }
        //int currentPosition = listBeforeLoad.size();
        listBeforeLoad.addAll(list);
        mapDataInRefreshing(listBeforeLoad);
    }

    //滚动加载
    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy < 0) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)) {
                return;
            }

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int totalItemCount = linearLayoutManager.getItemCount(); //总数
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition(); //最后一个可见的位置

            if (!isLoading && hasMore && !isRefreshing && (lastVisibleItemPosition + Config.LOADMORE.VISIBLE_THRESHOLD >= totalItemCount)) {
                START_PAGE++;
                loadData(START_PAGE, TYPE_LOADING);
            }
        }
    }
}
