package com.zfsoftmh.ui.modules.common.home.school_circle.interest_circle;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.InterestCircleItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * @author wesley
 * @date: 2017-6-29
 * @Description: ui
 */

public class InterestCircleFragment extends BaseListFragment<InterestCirclePresenter, InterestCircleItemInfo>
        implements InterestCircleContract.View, View.OnClickListener, InterestCircleAdapter.OnJoinClickListener,
        InterestCircleHeaderAdapter.OnMyCircleClickListener {

    protected static final int MOST_SHOW_ITEM_MY_CIRCLE = 3; //我的圈子最多显示多少条数据
    private InterestCircleAdapter adapter;
    private InterestCircleHeaderAdapter headerAdapter;

    private TextView tv_more; //更多
    private ImageView iv_arrow; //箭头

    @Inject
    InterestCirclePresenter presenter;

    @Override
    protected RecyclerArrayAdapter<InterestCircleItemInfo> getAdapter() {
        adapter = new InterestCircleAdapter(context);
        adapter.setOnJoinClickListener(this);
        return adapter;
    }

    @Override
    protected void initHeaderView() {
        super.initHeaderView();
        inject();

        RecyclerArrayAdapter.ItemView headerView = new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_interest_circle_header_view, null);
                initHeaderViewSearch(view);
                initHeaderViewTitle(view);
                initHeaderViewItem(view);
                initHeaderViewTitleInterest(view);
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        };
        adapter.addHeader(headerView);
    }

    @Override
    protected void loadData() {

        List<InterestCircleItemInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            InterestCircleItemInfo itemInfo = new InterestCircleItemInfo();
            itemInfo.setUrl("http://i3.hoopchina.com.cn/blogfile/201007/08/127855873993337.jpg");
            itemInfo.setName("佐助" + i);
            itemInfo.setDescription("佐助万花筒写轮眼...");
            list.add(itemInfo);
        }
        ResponseListInfo<InterestCircleItemInfo> response = new ResponseListInfo<>();
        response.setOvered(true);
        response.setItemList(list);
        loadSuccess(response);
    }

    @Override
    public void onItemClick(int position) {
        showToastMsgShort(String.valueOf(position));
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        setProgressEnable(false);
        setRefreshEnable(false);
        return super.getLayoutResID();
    }

    public static InterestCircleFragment newInstance() {
        return new InterestCircleFragment();
    }

    @Override
    public void initHeaderViewSearch(View view) {

    }

    @Override
    public void initHeaderViewTitle(View view) {
        if (view == null) {
            return;
        }
        tv_more = (TextView) view.findViewById(R.id.item_interest_circle_more);
        iv_arrow = (ImageView) view.findViewById(R.id.item_interest_circle_arrow);
    }

    @Override
    public void initHeaderViewItem(View view) {
        if (view == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        headerAdapter = new InterestCircleHeaderAdapter(context);
        recyclerView.setAdapter(headerAdapter);
        headerAdapter.setOnMyCircleClickListener(InterestCircleFragment.this);
        getHeaderItems();
    }

    @Override
    public void initHeaderViewTitleInterest(View view) {

    }


    @Override
    public void getHeaderItems() {

        ArrayList<InterestCircleItemInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            InterestCircleItemInfo itemInfo = new InterestCircleItemInfo();
            itemInfo.setUrl("http://img1.imgtn.bdimg.com/it/u=1727028796,1276732494&fm=214&gp=0.jpg");
            itemInfo.setName("漩涡鸣人..第七代火影");
            itemInfo.setNumber(i);
            list.add(itemInfo);
        }

        presenter.getMyCircle(1, PAGE_SIZE);
        headerItemsLoadSuccess(list);
    }

    @Override
    public void headerItemsLoadSuccess(ArrayList<InterestCircleItemInfo> list) {
        initMyCircleMore(list);
        headerAdapter.setDataSource(list);
    }

    @Override
    public void headerItemsLoadFailure(String errorMsg) {

    }

    @Override
    public void initMyCircleMore(ArrayList<InterestCircleItemInfo> list) {
        if (list != null && list.size() > MOST_SHOW_ITEM_MY_CIRCLE) {
            tv_more.setVisibility(View.VISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);

            tv_more.setOnClickListener(this);
        } else {
            tv_more.setVisibility(View.GONE);
            iv_arrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void inject() {

        DaggerInterestCircleComponent.builder()
                .appComponent(getAppComponent())
                .interestCirclePresenterModule(new InterestCirclePresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onClick(View view) {

        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 更多
         */
        case R.id.item_interest_circle_more:
            showToastMsgShort("more is click");
            break;

        default:
            break;
        }
    }

    @Override
    public void onItemJoin(InterestCircleItemInfo data) {
        showToastMsgShort(" on join click");
    }

    @Override
    public void onMyCircleItemClick(InterestCircleItemInfo itemInfo) {
        showToastMsgShort(" on my circle click ");
    }
}
