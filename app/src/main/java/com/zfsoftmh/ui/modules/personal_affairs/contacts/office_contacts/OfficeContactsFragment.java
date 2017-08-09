package com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.entity.OfficeContactsInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: ui
 */

public class OfficeContactsFragment extends BaseFragment<OfficeContactsPresenter> implements
        OfficeContactsContract.View, OfficeContactsAdapter.OnItemClickListener {

    private OfficeContactsAdapter adapter; //适配器
    private List<OfficeContactsInfo> list = new ArrayList<>();

    @Override
    protected void initVariables() {
        initParentHeader();
        adapter = new OfficeContactsAdapter(context, list);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adapter
        recyclerView.setAdapter(adapter);

        initParentContent();
        adapter.notifyParentInserted(1);
        adapter.notifyChildRangeInserted(1, 0, list.get(1).getChildList().size());
    }

    @Override
    protected void initListener() {

    }

    public static OfficeContactsFragment newInstance() {
        return new OfficeContactsFragment();
    }

    @Override
    public void onItemClick() {
        showToastMsgShort(getResources().getString(R.string.organization));
    }

    @Override
    public void onItemClick(ContactsItemInfo contactsItemInfo) {

    }

    //初始化parent_header
    private void initParentHeader() {
        OfficeContactsInfo contactsInfo = new OfficeContactsInfo();
        list.add(contactsInfo);
    }

    //初始化parent_content
    private void initParentContent() {
        OfficeContactsInfo info = new OfficeContactsInfo();
        info.setName("技术中心-移动校园产品部");

        List<ContactsItemInfo> childList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            ContactsItemInfo itemInfo = new ContactsItemInfo();
            itemInfo.setName("张三" + i);
            childList.add(itemInfo);
        }
        info.setList(childList);
        list.add(info);
    }

}
