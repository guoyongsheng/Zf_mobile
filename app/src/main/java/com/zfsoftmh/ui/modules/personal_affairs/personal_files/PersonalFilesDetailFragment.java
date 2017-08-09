package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.PersonalFilesDetailInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: 个人档案详情界面
 */

public class PersonalFilesDetailFragment extends BaseFragment {

    private RecyclerView.LayoutManager layoutManager;
    private PersonalFilesDetailAdapter adapter;
    private TextView tv_no_data;
    private RecyclerView recyclerView;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        layoutManager = new LinearLayoutManager(context);
        adapter = new PersonalFilesDetailAdapter(context);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_personal_files_detail;
    }

    @Override
    protected void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        tv_no_data = (TextView) view.findViewById(R.id.personal_files_detail_no_data);
    }

    @Override
    protected void initListener() {

    }

    public static PersonalFilesDetailFragment newInstance() {
        return new PersonalFilesDetailFragment();
    }

    public void showDetailData(List<List<Map<String, String>>> informationList) {

        if (informationList == null || informationList.size() == 0) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        List<PersonalFilesDetailInfo> listDetail = new ArrayList<>();
        int size = informationList.size();
        for (int i = 0; i < size; i++) {
            List<Map<String, String>> list = informationList.get(i);
            if (list != null) {
                int childSize = list.size();
                for (int j = 0; j < childSize; j++) {
                    Map<String, String> map = list.get(j);
                    Set<String> set = map.keySet();
                    for (String key : set) {
                        PersonalFilesDetailInfo info = new PersonalFilesDetailInfo();
                        info.setKey(key);
                        info.setValue(map.get(key));
                        info.setId(i);
                        listDetail.add(info);
                    }
                }
            }
        }
        adapter.setDataSource(listDetail);
    }

    //显示无数据的布局
    private void showEmptyView() {
        tv_no_data.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    //显示有数据的布局
    private void hideEmptyView() {
        tv_no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void clearData() {
        tv_no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
}
