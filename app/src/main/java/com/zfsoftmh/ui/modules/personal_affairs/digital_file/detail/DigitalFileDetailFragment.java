package com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail;

import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.entity.DigitalFileItemInfo;
import com.zfsoftmh.ui.base.BaseListFragment;

import javax.inject.Inject;

/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 数字档案详情ui
 */

public class DigitalFileDetailFragment extends BaseListFragment<DigitalFileDetailPresenter, DigitalFileItemInfo>
        implements DigitalFileDetailContract.View {

    @Inject
    DigitalFileDetailPresenter presenter;

    private String id;

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutResID() {
        inject();
        return super.getLayoutResID();
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected RecyclerArrayAdapter<DigitalFileItemInfo> getAdapter() {
        return new DigitalFileDetailAdapter(context);
    }

    @Override
    protected void loadData() {
        presenter.getDigitalFileItemInfo(id, start_page, PAGE_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    public static DigitalFileDetailFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        DigitalFileDetailFragment fragment = new DigitalFileDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void inject() {
        DaggerDigitalFileDetailComponent.builder()
                .appComponent(getAppComponent())
                .digitalFileDetailPresenterModule(new DigitalFileDetailPresenterModule(this))
                .build()
                .inject(this);
    }
}
