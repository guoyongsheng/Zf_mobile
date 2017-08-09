package com.zfsoftmh.ui.modules.office_affairs.questionnaire.preview_questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire.SubmitQuestionnaireActivity;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: ui
 */

public class PreviewQuestionnaireFragment extends BaseFragment implements PreviewQuestionnaireContract.View {

    private QuestionnaireItemInfo info;
    private long id;

    private PreviewQuestionnaireAdapter adapter; //适配器

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        adapter = new PreviewQuestionnaireAdapter(context);
    }

    @Override
    protected void handleBundle(Bundle bundle) {
        id = bundle.getLong("id");
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {
        info = getQuestionnaireInfoById(id);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(itemDecoration);

        //adapter
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(info);
    }

    @Override
    protected void initListener() {

    }

    public static PreviewQuestionnaireFragment newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        PreviewQuestionnaireFragment fragment = new PreviewQuestionnaireFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public QuestionnaireItemInfo getQuestionnaireInfoById(long id) {
        return getAppComponent().getRealmHelper().questionnaireItemInfoById(id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_preview_questionnaire, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 发布
         */
        case R.id.menu_preview_release:
            Intent intent = new Intent(context, SubmitQuestionnaireActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            intent.putExtras(bundle);
            openActivity(intent);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
