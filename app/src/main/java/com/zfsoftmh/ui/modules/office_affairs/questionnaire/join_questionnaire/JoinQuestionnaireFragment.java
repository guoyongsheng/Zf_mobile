package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import android.app.Activity;
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
import com.zfsoftmh.entity.QuestionInfo;
import com.zfsoftmh.entity.QuestionnaireQuestionInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-8
 * @Description: 参与问卷调查的ui
 */

public class JoinQuestionnaireFragment extends BaseFragment<JoinQuestionnairePresenter> implements
        JoinQuestionnaireContract.View {

    private ArrayList<QuestionnaireQuestionInfo> list;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        list = bundle.getParcelableArrayList("list");
    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //divider
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(itemDecoration);

        //adapter
        JoinQuestionnaireAdapter adapter = new JoinQuestionnaireAdapter(context);
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(list);
    }

    @Override
    protected void initListener() {

    }

    public static JoinQuestionnaireFragment newInstance(ArrayList<QuestionnaireQuestionInfo> list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        JoinQuestionnaireFragment fragment = new JoinQuestionnaireFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void joinSuccess() {
        ((JoinQuestionnaireActivity) context).setResult(Activity.RESULT_OK);
        ((JoinQuestionnaireActivity) context).finish();
    }

    @Override
    public void joinFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
        ((JoinQuestionnaireActivity) context).finish();
    }

    @Override
    public void joinQuestionnaire() {

        if (checkHasQuestionNoAnswer()) {
            showToastMsgShort(getResources().getString(R.string.please_answer_the_question));
            return;
        }
        presenter.joinQuestionnaire(getValue(list));
    }

    @Override
    public boolean checkHasQuestionNoAnswer() {
        if (list == null) {
            return true;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            QuestionnaireQuestionInfo info = list.get(i);
            if (info != null) {
                String result = info.getResult();
                if (result == null || result.length() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getValue(List<QuestionnaireQuestionInfo> list) {
        if (list == null) {
            return null;
        }

        List<QuestionInfo> questionInfos = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            QuestionnaireQuestionInfo info = list.get(i);
            if (info != null) {
                QuestionInfo questionInfo = new QuestionInfo();
                String id = info.getQuestionid();
                String result = info.getResult();
                questionInfo.setKey(id);
                questionInfo.setValue(result);
                questionInfos.add(questionInfo);
            }
        }
        return getAppComponent().getGson().toJson(questionInfos);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_join_questionnaire, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 完成
         */
        case R.id.menu_join_questionnaire_done:
            joinQuestionnaire();
            return true;


        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
