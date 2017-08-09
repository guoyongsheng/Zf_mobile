package com.zfsoftmh.ui.modules.office_affairs.questionnaire.short_answer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireDetailInfo;
import com.zfsoftmh.ui.base.BaseFragment;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 简答题ui
 */

public class ShortAnswerFragment extends BaseFragment implements View.OnClickListener, ShortAnswerContract.View {

    private EditText et_title; //标题
    private Button btn_ok; //确定
    private int type; // 0:添加 1: 编辑
    private long id;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        type = bundle.getInt("type", 0);
        id = bundle.getLong("id", 0);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_short_answer;
    }

    @Override
    protected void initViews(View view) {
        et_title = (EditText) view.findViewById(R.id.short_answer_title);
        btn_ok = (Button) view.findViewById(R.id.short_answer_ok);

        if (type == 1) {
            QuestionnaireDetailInfo info = getInfoById(id);
            if (info != null) {
                et_title.setText(info.getTitle());
            }
        }
    }

    @Override
    protected void initListener() {
        btn_ok.setOnClickListener(this);
    }

    public static ShortAnswerFragment newInstance(int type, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putLong("id", id);
        ShortAnswerFragment fragment = new ShortAnswerFragment();
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
         * 确定
         */
        case R.id.short_answer_ok:
            create();
            break;

        default:
            break;
        }
    }

    @Override
    public String getTitle() {
        return et_title.getText().toString();
    }

    @Override
    public boolean checkTitleIsNull(String title) {
        return title == null || title.length() == 0;
    }

    @Override
    public void create() {
        String title = getTitle();
        if (checkTitleIsNull(title)) {
            showToastMsgShort(getResources().getString(R.string.please_input_title));
            return;
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("type", type);
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        ((ShortAnswerActivity) context).setResult(Activity.RESULT_OK, intent);
        ((ShortAnswerActivity) context).finish();
    }

    @Override
    public QuestionnaireDetailInfo getInfoById(long id) {
        return getAppComponent().getRealmHelper().queryQuestionnaireDetailInfoInfoById(id);
    }
}
