package com.zfsoftmh.ui.modules.personal_affairs.contacts.phone_contacts;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.contacts.contacts_detail.ContactsDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wesley
 * @date: 2017/4/11
 * @Description: ui
 */

public class PhoneContactsFragment extends BaseFragment implements EasyRecyclerViewSidebar.OnTouchSectionListener,
        PhoneContactsAdapter.OnItemClickListener {

    private ArrayList<ContactsItemInfo> list;
    private ArrayList<ContactsItemInfo> listSort; //排序后的list
    private EasyRecyclerViewSidebar easyRecyclerViewSidebar;
    private TextView tv_float_view;
    private List<EasySection> easySectionList;
    private List<String> sections = new ArrayList<>(); //存放字母
    private Map<String, Integer> sectionsMap = new HashMap<>(); //第一个字母在列表中的位置
    private RecyclerView recyclerView;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        list = bundle.getParcelableArrayList("list");
        easySectionList = getEasySectionsList();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_phone_contacts;
    }

    @Override
    protected void initViews(View view) {
        easyRecyclerViewSidebar = (EasyRecyclerViewSidebar) view.findViewById(R.id.section_sidebar);
        tv_float_view = (TextView) view.findViewById(R.id.section_float_view);
        easyRecyclerViewSidebar.setFloatView(tv_float_view);
        easyRecyclerViewSidebar.setSections(easySectionList);

        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //adapter
        PhoneContactsAdapter adapter = new PhoneContactsAdapter(context, listSort);
        adapter.setOnItemClickListener(this);

        //HeaderDecoration
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        recyclerView.setAdapter(adapter);
    }

    //获取数据源
    private List<EasySection> getEasySectionsList() {
        easySectionList = new ArrayList<>();
        if (list == null) {
            return easySectionList;
        }
        listSort = new ArrayList<>();
        int size = list.size();
        char A = 'A';
        for (int i = 0; i < 26; i++) {
            String section = (char) (A + i) + "";
            sortData(section, size, i);
        }
        sortData("#", size, 26);
        return easySectionList;
    }

    //排序
    private void sortData(String section, int size, int i) {
        sectionsMap.put(section, listSort.size());
        sections.add(section);
        EasySection easySection = new EasySection(section);
        easySectionList.add(easySection);
        for (int j = 0; j < size; j++) {
            ContactsItemInfo info = list.get(j);
            if (info != null) {
                String firstLetter = info.getFirstLetter();
                if (firstLetter != null && firstLetter.equals(section)) {
                    info.setHeaderId(i);
                    listSort.add(info);
                }
            }
        }
    }

    @Override
    protected void initListener() {
        easyRecyclerViewSidebar.setOnTouchSectionListener(this);
    }

    public static PhoneContactsFragment newInstance(ArrayList<ContactsItemInfo> list) {
        PhoneContactsFragment fragment = new PhoneContactsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onTouchImageSection(int sectionIndex, EasyImageSection imageSection) {

    }

    @Override
    public void onTouchLetterSection(int sectionIndex, EasySection letterSection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            easyRecyclerViewSidebar.setBackground(null);
        }
        tv_float_view.setVisibility(View.VISIBLE);
        if (easySectionList != null && easySectionList.size() > sectionIndex) {
            String letter = easySectionList.get(sectionIndex).letter;
            tv_float_view.setText(letter);

            if (sections != null && sections.size() > sectionIndex) {
                String section = sections.get(sectionIndex);
                if (sectionsMap != null && sectionsMap.containsKey(section)) {
                    int position = sectionsMap.get(section);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
                    }
                }
            }
        }
    }

    @Override
    public void onItemClick(ContactsItemInfo info) {
        Intent intent = new Intent(context, ContactsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", info);
        intent.putExtras(bundle);
        openActivity(intent);
    }
}
