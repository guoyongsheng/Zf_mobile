package com.zfsoftmh.ui.modules.chatting.tribe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.add.CreateTribeActivity;
import com.zfsoftmh.ui.widget.ZRecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/12.
 */
public class TribeFragment extends BaseFragment<TribePresenter> implements TribeContract.View, ZRecyclerItemClickListener<YWTribe> {

    public static TribeFragment newInstance(){
        return new TribeFragment();
    }

    @Override
    protected void initVariables() {
        setHasOptionsMenu(true);
    }

    @Override
    protected void handleBundle(Bundle bundle) {  }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_tribe;
    }

    private View mView;
    private TribeListAdapter adapter;
    @Override
    protected void initViews(View view) {
        mView = view;
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.tribe_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TribeListAdapter(getActivity());
        adapter.setItemClickListener(this);
        recycler.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recycler.addItemDecoration(dividerItemDecoration);

        presenter.getAllTribesFromServer();
        initReceiver();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onLoading() {
        showProgressDialog("正在同步群组...");
    }

    @Override
    public void onStopLoading() {
        hideProgressDialog();
    }

    @Override
    public void refreshAdapter(ArrayList<YWTribe> dataList) {
        adapter.setTribeData(dataList);
    }

    @Override
    public void showErrMsg(String msg) {
        Snackbar.make(mView,msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tribe, menu);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  TribeKeys.ON_TRIBES_MODIFY && resultCode ==  TribeKeys.ON_TRIBES_MODIFY){
            presenter.getAllTribesFromServer();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id._tribe_create:
                Intent intent = new Intent(getActivity(), CreateTribeActivity.class);
                intent.putExtra("page_tribe",true);
                startActivityForResult(intent, TribeKeys.ON_TRIBES_MODIFY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerItemClick(int pos, YWTribe ywTribe) {
        IMKitHelper.getInstance().startTribeChatting(getActivity(),ywTribe.getTribeId());
    }

    private BroadcastReceiver mReceiver;
    private void initReceiver(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.getAllTribesFromServer();
            }
        };
        IntentFilter filter = new IntentFilter(TribeKeys.FILTER_TRIBE_KILL);
        getActivity().registerReceiver(mReceiver,filter);
    }

}
