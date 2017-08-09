package com.zfsoftmh.ui.modules.office_affairs.questionnaire.add_questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire.EditQuestionnaireActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 添加问卷调查的UI
 */

public class AddQuestionnaireFragment extends BaseFragment implements AddQuestionnaireContract.View,
        View.OnClickListener {

    private EditText et_title; //标题
    private EditText et_description;  //描述
    private Button btn_add; //创建

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_add_questionnaire;
    }

    @Override
    protected void initViews(View view) {
        et_title = (EditText) view.findViewById(R.id.add_questionnaire_title);
        et_description = (EditText) view.findViewById(R.id.add_questionnaire_description);
        btn_add = (Button) view.findViewById(R.id.add_questionnaires);
    }

    @Override
    protected void initListener() {
        btn_add.setOnClickListener(this);
    }

    public static AddQuestionnaireFragment newInstance() {
        return new AddQuestionnaireFragment();
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 创建
         */
        case R.id.add_questionnaires:
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
    public String getDescription() {
        return et_description.getText().toString();
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

        String description = getDescription();
        if (checkTitleIsNull(description)) {
            showToastMsgShort(getResources().getString(R.string.please_input_questionnaire_description));
            return;
        }

        long id = System.currentTimeMillis();
        QuestionnaireItemInfo info = new QuestionnaireItemInfo();
        info.setId(id);
        info.setTitle(title);
        info.setTime(parseTime(id));
        info.setDescription(description);
        getAppComponent().getRealmHelper().insert(info);

        Intent intent = new Intent(context, EditQuestionnaireActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public String parseTime(long currentTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date(currentTime);
        return format.format(date);
    }
}
