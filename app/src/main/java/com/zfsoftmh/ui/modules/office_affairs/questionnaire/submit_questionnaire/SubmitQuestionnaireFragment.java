package com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.QuestionnaireActivity;

import io.realm.Realm;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: ui
 */

public class SubmitQuestionnaireFragment extends BaseFragment<SubmitQuestionnairePresenter>
        implements SubmitQuestionnaireContract.View, View.OnClickListener {

    private long id;
    private EditText et_note; //备注
    private CheckBox checkBox;
    private Button btn_submit; //提交
    private QuestionnaireItemInfo info;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        id = bundle.getLong("id", 0);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_submit_questionnaire;
    }

    @Override
    protected void initViews(View view) {
        et_note = (EditText) view.findViewById(R.id.submit_questionnaire_note);
        checkBox = (CheckBox) view.findViewById(R.id.submit_questionnaire_check);
        btn_submit = (Button) view.findViewById(R.id.submit_questionnaire_submit);

        info = getAppComponent().getRealmHelper().questionnaireItemInfoById(id);
    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(this);
    }

    public static SubmitQuestionnaireFragment newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        SubmitQuestionnaireFragment fragment = new SubmitQuestionnaireFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 提交
         */
        case R.id.submit_questionnaire_submit:
            submit();
            break;

        default:
            break;
        }
    }

    @Override
    public String getNote() {
        return et_note.getText().toString();
    }

    @Override
    public boolean isChecked() {
        return checkBox.isChecked();
    }

    @Override
    public void submit() {
        final String remark = et_note.getText().toString();
        getAppComponent().getRealmHelper().getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                QuestionnaireItemInfo itemInfo = realm.copyFromRealm(info);
                itemInfo.setQn_marking(remark);
                if (checkBox.isChecked()) {
                    itemInfo.setQn_owner(1);
                } else {
                    itemInfo.setQn_owner(0);
                }
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String value = gson.toJson(itemInfo);
                presenter.submitQuestionnaire(value);
            }
        });
    }

    @Override
    public QuestionnaireItemInfo getQuestionnaireItemInfoById(long id) {
        return getAppComponent().getRealmHelper().questionnaireItemInfoById(id);
    }

    @Override
    public void submitSuccess() {
        getAppComponent().getRealmHelper().deleteQuestionnaireItemInfo(id);
        openActivity();
    }

    @Override
    public void submitFailure(String errorMsg) {
        showToastMsgShort(errorMsg);
        openActivity();
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
    public void openActivity() {
        Intent intent = new Intent(context, QuestionnaireActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openActivity(intent);
    }
}
