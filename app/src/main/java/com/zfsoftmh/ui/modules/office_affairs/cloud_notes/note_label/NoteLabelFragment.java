package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label.EditNoteLabelActivity;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.mobileim.gingko.presenter.contact.cache.ContactsCache.getUserId;

/**
 * 创建时间： 2017/5/11 0011
 * 编写人： 王世美
 * 功能描述：笔记标签列表
 */

public class NoteLabelFragment extends BaseFragment<NoteLabelPresenter> implements NoteLabelContract.View, NoteLabelAdapter.LabelOnClickListener {

    private NoteLabelAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private String from; // 跳转页面标志
    private ArrayList<NoteLabelItemInfo> dataList;


    public static NoteLabelFragment newInstance(String from) {
        NoteLabelFragment fragment = new NoteLabelFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        from = bundle.getString("from");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        //LayoutManager
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteLabelAdapter(context);
        recyclerView.setAdapter(adapter);
//        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(this);
    }

    /**
     * 显示加载框
     *
     * @param msg
     */
    @Override
    public void showLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    /**
     * 隐藏加载框
     */
    @Override
    public void hideLoadingDialog() {
        hideProgressDialog();
    }

    /**
     * 加载失败提示框
     *
     * @param errMsg
     */
    @Override
    public void loadFailure(String errMsg) {
        showToastMsgShort(errMsg);
    }

    /**
     * 成功获取数据
     *
     * @param data
     */
    @Override
    public void loadData(List<NoteLabelItemInfo> data) {
        if (data != null && data.size() > 0) {
            adapter.addData(data);
            dataList = (ArrayList<NoteLabelItemInfo>) data;
        }
    }

    /**
     * 获取标签列表数据
     */
    @Override
    public void loadData() {
        presenter.loadData();
    }

    @Override
    public void skipEditLabelPage() {
        Intent intent = new Intent(getActivity(), EditNoteLabelActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dataList", dataList);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    /**
     * 点击标签跳转事件
     *
     * @param position
     * @param bean
     */
    @Override
    public void labelOnClick(int position, NoteLabelItemInfo bean) {
        if (bean != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("label", bean);

            if (from != null) {
                Intent labelIntent = new Intent();
                labelIntent.putExtras(bundle);
                getActivity().setResult(Activity.RESULT_OK, labelIntent); // intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                getActivity().finish();// 此处一定要调用finish()方法
            }
        }
    }
}
