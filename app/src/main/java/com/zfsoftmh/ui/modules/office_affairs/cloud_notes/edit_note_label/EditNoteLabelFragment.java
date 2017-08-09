package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * @author wangshimei
 * @date: 17/5/23
 * @Description: 云笔记编辑标签
 */

public class EditNoteLabelFragment extends BaseFragment<EditNoteLabelPresenter> implements EditNoteLabelContract.View, EditNoteLabelAdapter.SureClickListener, EditNoteLabelAdapter.OnItemLongListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditNoteLabelAdapter adapter;
    private ArrayList<NoteLabelItemInfo> dataList = new ArrayList<NoteLabelItemInfo>();

    public static EditNoteLabelFragment newInstance(ArrayList<NoteLabelItemInfo> dataList) {
        EditNoteLabelFragment fragment = new EditNoteLabelFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dataList", dataList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        dataList = bundle.getParcelableArrayList("dataList");
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

        adapter = new EditNoteLabelAdapter(context);
        recyclerView.setAdapter(adapter);
        if (dataList != null && dataList.size() > 0) {
            adapter.setData(dataList);
        } else {
            loadData();
        }
    }

    @Override
    protected void initListener() {
        adapter.setSureClickListener(this);
        adapter.setOnLongListener(this);
    }

    @Override
    public void showLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideLoadingDialog() {
        hideProgressDialog();
    }

    @Override
    public void loadData() {
        presenter.loadData();

    }

    @Override
    public void setDataList(ArrayList<NoteLabelItemInfo> list) {
        if (list != null && list.size() > 0) {
            dataList = list;
            adapter.setData(list);
        }
    }

    @Override
    public void submitData(String memoCatalogNameList, String memoCatalogColorList) {
        presenter.submitData(memoCatalogNameList, memoCatalogColorList);
    }

    @Override
    public void skipLabelPage() {
        getActivity().finish();
    }

    @Override
    public void onSureClick(String nameListStr, String colorListStr) {
        submitData(nameListStr, colorListStr);
    }

    @Override
    public void onItemLongOnClick(View view, int pos) {
        if (pos > 4) {
            showPopMenu(view, pos);
        } else {
            showToastMsgLong(Constant.default_label_not_delete);
        }
    }

    public void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_delete_label,
                popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String memoCatalogNameList = "", memoCatalogColorList = "";
                memoCatalogNameList = getNameListStr();
                memoCatalogColorList = getColorListStr();
                if (memoCatalogNameList != null && memoCatalogColorList != null) {
                    submitData(memoCatalogNameList, memoCatalogColorList);
                    adapter.removeItem(pos);
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                showToastMsgLong(Constant.close_delete_menu);

            }
        });
        popupMenu.show();
    }

    private String getNameListStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 5; i < dataList.size() - 1; i++) {
            builder.append(dataList.get(i).memoCatalogName);
            builder.append(",");
        }
        if (dataList.size() > 5) {
            builder.append(dataList.get(dataList.size() - 1).memoCatalogName);
        }

        return builder.toString();
    }

    private String getColorListStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 5; i < dataList.size() - 1; i++) {
            builder.append(dataList.get(i).catalogColor);
            builder.append(",");
        }
        if (dataList.size() > 5) {
            builder.append(dataList.get(dataList.size() - 1).catalogColor);
        }
        return builder.toString();
    }
}
