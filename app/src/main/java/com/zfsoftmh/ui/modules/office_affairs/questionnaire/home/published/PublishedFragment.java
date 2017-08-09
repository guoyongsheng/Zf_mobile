package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.entity.QuestionnairePublishedInfo;
import com.zfsoftmh.entity.QuestionnaireQuestionInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire.JoinQuestionnaireActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 已发布ui
 */

public class PublishedFragment extends BaseListFragment<PublishedPresenter, QuestionnairePublishedInfo> implements
        PublishedContract.View, PublishedAdapter.OnItemClickListener {

    private static final int REQUEST_CODE_JOIN = 1;
    private PublishedAdapter adapter;

    @Override
    protected RecyclerArrayAdapter<QuestionnairePublishedInfo> getAdapter() {
        adapter = new PublishedAdapter(context);
        adapter.setOnItemClickListener((PublishedAdapter.OnItemClickListener) this);
        return adapter;
    }

    @Override
    protected void loadData() {
        presenter.loadData(start_page, PAGE_SIZE);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static PublishedFragment newInstance() {
        return new PublishedFragment();
    }

    @Override
    public void onItemClick(int position) {
        List<QuestionnairePublishedInfo> list = adapter.getAllData();
        if (list == null || list.size() <= position) {
            return;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            QuestionnairePublishedInfo info = list.get(i);
            if (i == position) {
                int tag = info.getTag();
                if (tag == 0) {
                    info.setTag(1);
                } else {
                    info.setTag(0);
                }
            } else {
                info.setTag(0);
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemJoin(QuestionnairePublishedInfo info) {

        if (checkHasJoined(info)) {
            showToastMsgShort(getResources().getString(R.string.you_have_joined));
            return;
        }

        if (checkHasFinished(info)) {
            showToastMsgShort(getResources().getString(R.string.questionnaire_has_finished));
            return;
        }

        if (!checkHasQuestion(info)) {
            showToastMsgShort(getResources().getString(R.string.questionnaire_question_content_is_null));
            return;
        }

        Intent intent = new Intent(context, JoinQuestionnaireActivity.class);
        ArrayList<QuestionnaireQuestionInfo> list = new ArrayList<>(info.getQuestionList());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        intent.putExtras(bundle);
        openActivityForResult(intent, REQUEST_CODE_JOIN);
    }

    @Override
    public void onItemLook(QuestionnairePublishedInfo info) {

        if (checkHasPermission(info)) {
            showToastMsgShort(getResources().getString(R.string.no_permission_to_look_questionnaire));
            return;
        }

        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Config.WEB.URL, info.getResultUrl());
        bundle.putString(Config.WEB.TITLE, info.getPapermainname());
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public boolean checkHasPermission(QuestionnairePublishedInfo info) {

        if (info == null) {
            return true;
        }
        String qn_owner = info.getQn_owner();
        String creater = info.getCreater();
        return qn_owner != null && qn_owner.equals("1") && (creater == null || !creater.equals(getUserId()));
    }

    @Override
    public boolean checkHasJoined(QuestionnairePublishedInfo info) {

        if (info == null) {
            return true;
        }

        String flag = info.getFlag();
        return flag != null && flag.equals("1");
    }

    @Override
    public boolean checkHasFinished(QuestionnairePublishedInfo info) {
        if (info == null) {
            return true;
        }

        String startTime = info.getStarttimeStr();
        String endTime = info.getEndtimeStr();

        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return true;
        }

        String pattern = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date dateStart = format.parse(startTime);
            Date dateEnd = format.parse(endTime);
            if (dateStart == null || dateEnd == null) {
                return true;
            }
            long current = System.currentTimeMillis();
            //long start = dateStart.getTime();
            long end = dateEnd.getTime();
            return end < current;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean checkHasQuestion(QuestionnairePublishedInfo info) {
        if (info == null) {
            return false;
        }

        List<QuestionnaireQuestionInfo> list = info.getQuestionList();
        return !(list == null || list.size() == 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 參與
         */
        case REQUEST_CODE_JOIN:
            onRefresh();
            break;


        default:
            break;
        }
    }
}
