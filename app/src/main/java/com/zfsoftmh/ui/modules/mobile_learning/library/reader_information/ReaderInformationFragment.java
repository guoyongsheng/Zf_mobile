package com.zfsoftmh.ui.modules.mobile_learning.library.reader_information;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: ui
 */

public class ReaderInformationFragment extends BaseFragment<ReaderInformationPresenter> implements
        ReaderInformationContract.View {

    private ReaderInformationAdapter adapter; //适配器

    @Override
    protected void initVariables() {
        adapter = new ReaderInformationAdapter(context);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setHasFixedSize(true);

        //adapter
        recyclerView.setAdapter(adapter);

        loadData();
    }

    @Override
    protected void initListener() {

    }

    public static ReaderInformationFragment newInstance() {
        return new ReaderInformationFragment();
    }

    @Override
    public void loadData() {
        presenter.loadData();
    }
}
