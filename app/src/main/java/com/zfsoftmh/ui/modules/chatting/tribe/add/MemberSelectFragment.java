package com.zfsoftmh.ui.modules.chatting.tribe.add;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.camnter.easyrecyclerviewsidebar.EasyRecyclerViewSidebar;
import com.camnter.easyrecyclerviewsidebar.sections.EasyImageSection;
import com.camnter.easyrecyclerviewsidebar.sections.EasySection;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.chatting.contact.ChatContact;
import com.zfsoftmh.ui.modules.chatting.contact.FriendsContract;
import com.zfsoftmh.ui.modules.chatting.contact.FriendsPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sy
 * on 2017/5/16.
 * <p>成员选择</p>
 */
public class MemberSelectFragment extends BaseFragment<FriendsPresenter> implements EasyRecyclerViewSidebar.OnTouchSectionListener, FriendsContract.View {

    private List<EasySection> easySectionList;
    private List<String> sections = new ArrayList<>(); //存放字母
    private Map<String, Integer> sectionsMap = new HashMap<>(); //第一个字母在列表中的位置

    private EasyRecyclerViewSidebar easyRecyclerViewSidebar;
    private TextView tv_float_view;
    private RecyclerView recyclerView;

    private MemberSelectAdapter adapter;
    private ArrayList<ChatContact> dataListSort;
    private ArrayList<ChatContact> mContacts;

    FriendsPresenter mPresenter;

    public static MemberSelectFragment newInstance(ArrayList<String> memberIDs){
        MemberSelectFragment fragment = new MemberSelectFragment();
        if (memberIDs != null) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("memberIDs", memberIDs);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    private OnMemberSelectCallback selectCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectCallback = (OnMemberSelectCallback) context;
    }

    @Override
    protected void initVariables() {
        this.dataListSort = new ArrayList<>();
        this.mContacts = new ArrayList<>();
        mPresenter = new FriendsPresenter(this);
        setHasOptionsMenu(true);
    }

    private ArrayList<String> members;
    @Override
    protected void handleBundle(Bundle bundle) {
        members = bundle.getStringArrayList("memberIDs");
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
        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new MemberSelectAdapter(getActivity());
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);
        recyclerView.setAdapter(adapter);

        mPresenter.IOGetContacts();
    }

    private void initAlphaContacts() {
        easySectionList = new ArrayList<>();
        dataListSort.clear();
        char A = 'A';
        for (int i = 0; i < 26; i++) {
            String section = String.valueOf((char) (A + i));
            sortData(section, i);
        }
        sectionsMap.put("#", dataListSort.size());
        sections.add("#");
        EasySection easySection = new EasySection("#");
        easySectionList.add(easySection);
        for (int j = 0; j < mContacts.size(); j++) {
            ChatContact contact = mContacts.get(j);
            String firstLetter = contact.getFirstChar().toUpperCase();
            if(!adapter.isAtoZ(firstLetter)){
                mContacts.get(j).setHeaderId(26);
                dataListSort.add(mContacts.get(j));
            }
        }
        handler.postDelayed(runnable,200);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            easyRecyclerViewSidebar.setSections(easySectionList);
            adapter.setDataList(dataListSort, members);
        }
    };

    @Override
    protected void initListener() {
        easyRecyclerViewSidebar.setOnTouchSectionListener(this);
    }

    @Override
    public void onTouchImageSection(int sectionIndex, EasyImageSection imageSection) { }

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
    public void loading(int pro) { }

    @Override
    public void stopLoading() { }

    @Override
    public void showErrorMsg(String errorMsg) { }

    @Override
    public void onGetContactsSuccess(ArrayList<ChatContact> localContactList) {
        mContacts.clear();
        mContacts.addAll(localContactList);
        initAlphaContacts();
    }

    //排序
    private void sortData(String section, int i) {
        sectionsMap.put(section, dataListSort.size());
        sections.add(section);
        EasySection easySection = new EasySection(section);
        easySectionList.add(easySection);
        for (int j = 0; j < mContacts.size(); j++) {
            ChatContact contact = mContacts.get(j);
            String firstLetter = contact.getFirstChar().toUpperCase();
            if (firstLetter.equals(section)){
                mContacts.get(j).setHeaderId(i);
                dataListSort.add(mContacts.get(j));
            }
        }
    }

    @Override
    public void onContactChange() {  }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_sure, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id._sure){
            selectCallback.OnMemberSelect(adapter.getSelectContacts());
        }
        return super.onOptionsItemSelected(item);
    }



}
