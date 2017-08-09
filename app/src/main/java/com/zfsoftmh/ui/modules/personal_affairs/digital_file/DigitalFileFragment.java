package com.zfsoftmh.ui.modules.personal_affairs.digital_file;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.DigitalFileDepartInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-15
 * @Description: 数字档案ui
 */

public class DigitalFileFragment extends BaseFragment<DigitalFilePresenter> implements DigitalFileContract.View {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_digital_file;
    }

    @Override
    protected void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.digital_file_tab);
        viewPager = (ViewPager) view.findViewById(R.id.digital_view_pager);

        getDigitalFileDepartInfo();
    }

    @Override
    protected void initListener() {

    }

    public static DigitalFileFragment newInstance() {
        return new DigitalFileFragment();
    }

    private List<String> getTitleList() {
        List<String> list = new ArrayList<>();
        list.add("android");
        list.add("kotlin");
        list.add("dagger");
        list.add("retrofit");
        list.add("rxjava");
        list.add("mvp");
        list.add("material design");
        list.add("okhttp");
        return list;
    }

    @Override
    public void getDigitalFileDepartInfo() {
        presenter.getDigitalFileDepartInfo();
    }

    @Override
    public void loadSuccess(List<DigitalFileDepartInfo> data) {

    }

    @Override
    public void loadFailure(String errorMsg) {
        showToastMsgShort(errorMsg);

        List<String> listTitle = getTitleList();
        int size = listTitle.size();
        for (int i = 0; i < size; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(i)));
        }
        DigitalFilePageAdapter adapter = new DigitalFilePageAdapter(getChildFragmentManager(), listTitle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        listTitle.add("校园风景");
        adapter.setDataSource(listTitle);
    }
}
