package com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.ScheduleManagementInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.schedule_management.schedule_detail.ScheduleDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableRange;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wesley
 * @date: 2017-5-25
 * @Description: ui
 */

public class ScheduleListFragment extends BaseFragment implements ScheduleListAdapter.OnItemClickListener,
        ScheduleListContract.View {

    private static final String TAG = "ScheduleListFragment";
    private static final int REQUEST_CODE_SHEDULE_DETAIL = 1; //跳转到详情界面的请求码
    private ScheduleListAdapter adapter; //适配器

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        adapter = new ScheduleListAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(itemDecoration);

        //HeaderDecoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        recyclerView.setHasFixedSize(true);
        //adapter
        recyclerView.setAdapter(adapter);

        List<ScheduleManagementInfo> list = getAllData();
        if (list == null || list.size() == 0) {
            showToastMsgShort(getResources().getString(R.string.no_shedule_info));
            return;
        }
        refreshUI(list);
    }

    @Override
    protected void initListener() {

    }

    public static ScheduleListFragment newInstance() {
        return new ScheduleListFragment();
    }

    @Override
    public void onItemClick(long id) {
        Intent intent = new Intent(context, ScheduleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivityForResult(intent, REQUEST_CODE_SHEDULE_DETAIL);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 详情界面返回
         */
        case REQUEST_CODE_SHEDULE_DETAIL:
            refreshUI(getAllData());
            break;

        default:
            break;
        }
    }

    @Override
    public List<ScheduleManagementInfo> getAllData() {
        return getAppComponent().getRealmHelper().queryAllScheduleInfo();
    }

    @Override
    public void refreshUI(final List<ScheduleManagementInfo> list) {

        ObservableRange.just(list)
                .subscribeOn(Schedulers.io())
                .map(new Function<List<ScheduleManagementInfo>, List<ScheduleManagementInfo>>() {
                    @Override
                    public List<ScheduleManagementInfo> apply(@NonNull List<ScheduleManagementInfo> scheduleManagementInfos) throws Exception {

                        if (scheduleManagementInfos == null) {
                            return null;
                        }
                        Map<String, List<ScheduleManagementInfo>> map = new HashMap<>();
                        int size = scheduleManagementInfos.size();
                        for (int i = 0; i < size; i++) {
                            ScheduleManagementInfo info = scheduleManagementInfos.get(i);
                            if (info != null) {
                                String headerTime = info.getHead_time();
                                if (map.containsKey(headerTime)) {
                                    List<ScheduleManagementInfo> list = map.get(headerTime);
                                    list.add(info);
                                    map.put(headerTime, list);
                                } else {
                                    List<ScheduleManagementInfo> list = new ArrayList<>();
                                    list.add(info);
                                    map.put(headerTime, list);
                                }
                            }
                        }
                        Map<String, List<ScheduleManagementInfo>> treeMap = new TreeMap<>(map);
                        List<String> listKey = new ArrayList<>();
                        for (String key : treeMap.keySet()) {
                            listKey.add(key);
                        }

                        int temp = 0;
                        List<ScheduleManagementInfo> listMap = new ArrayList<>();
                        int sizeKey = listKey.size();
                        for (int i = sizeKey - 1; i >= 0; i--) {
                            String key = listKey.get(i);
                            if (map.containsKey(key)) {
                                List<ScheduleManagementInfo> list = map.get(key);
                                if (list != null) {
                                    int count = list.size();
                                    for (int j = 0; j < count; j++) {
                                        ScheduleManagementInfo info = list.get(j);
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
                .subscribe(new Consumer<List<ScheduleManagementInfo>>() {
                    @Override
                    public void accept(@NonNull List<ScheduleManagementInfo> scheduleManagementInfos) throws Exception {
                        adapter.setDataSource(scheduleManagementInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LoggerHelper.e(TAG, " refreshUI " + throwable.getMessage());
                    }
                });
    }

}
