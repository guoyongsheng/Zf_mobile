package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ActivityUtils;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.PersonalFilesInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.List;
import java.util.Map;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: ui展示
 */

public class PersonalFilesFragment extends BaseFragment<PersonalFilesPresenter>
        implements PersonalFilesContract.View, PersonalFilesAdapter.OnViewClickListener {

    private RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private PersonalFilesAdapter adapter;
    private PersonalFilesDetailFragment fragment;
    private FragmentManager manager;
    private int currentItem; //当前点击的位置
    private String userId; //用户登录id

    @Override
    protected void initVariables() {
        layoutManager = new LinearLayoutManager(context);
        dividerItemDecoration = new DividerItemDecoration(context, LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        adapter = new PersonalFilesAdapter(context);
        adapter.setOnViewClickListener(this);

        manager = getChildFragmentManager();
        userId = getUserId();
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_personal_files;
    }

    @Override
    protected void initViews(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.personal_files_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        fragment = (PersonalFilesDetailFragment) manager.findFragmentById(R.id.personal_files_detail_frame);
        if (fragment == null) {
            fragment = PersonalFilesDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.personal_files_detail_frame);
        }
        loadData();
    }

    @Override
    protected void initListener() {

    }

    public static PersonalFilesFragment newInstance() {
        return new PersonalFilesFragment();
    }

    @Override
    public void loadData() {
        presenter.loadData();
    }

    @Override
    public String getUserId() {
        return DbHelper.getUserId(context);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showToastMsgShort(errorMsg);
    }

    @Override
    public void showData(List<PersonalFilesInfo> data) {
        if (data != null && data.size() > 0 && data.get(0) != null) {
            fragment.showDetailData(data.get(0).getInformationList());
        }
        adapter.setDataSource(data);
    }

    @Override
    public void loadDetailData(String id, String account) {
        presenter.loadDetailData(id, account, currentItem);
    }

    @Override
    public void showDetailData(List<List<Map<String, String>>> data, int position) {
        if (currentItem != position) {
            return;
        }
        fragment.showDetailData(data);
    }

    @Override
    public void onViewClick(int position) {
        if (adapter.checkItemIsChecked(position)) {
            return;
        }
        currentItem = position;
        adapter.setItemIsChecked(position);
        fragment.clearData();
        String id = adapter.getId(position);
        loadDetailData(id, userId);
    }
}
