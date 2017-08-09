package com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire;

import android.app.Activity;
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
import com.zfsoftmh.entity.QuestionnaireDetailInfo;
import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.hit_the_title.HitTheTitleActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.preview_questionnaire.PreviewQuestionnaireActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.short_answer.ShortAnswerActivity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-2
 * @Description: 编辑问卷调查的UI
 */

public class EditQuestionnaireFragment extends BaseFragment implements
        EditQuestionnaireContract.View, EditQuestionnaireDialogFragment.OnItemClickListener, EditQuestionnaireAdapter.OnItemClickListener {

    private static final int REQUEST_CODE_SINGLE_CHOICE = 0; //单选
    private static final int REQUEST_CODE_MUTI_CHOICE = 1; //多选
    private static final int REQUEST_CODE_SHORT_ANSWER = 2; //简答
    private static final int REQUEST_CODE_HINT_THE_TITLE = 3; //打分
    private static final String TAG = "EditQuestionnaireFragment";
    private long id; //id
    private QuestionnaireItemInfo info;
    private EditQuestionnaireAdapter adapter;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {
        adapter = new EditQuestionnaireAdapter(context);
        adapter.setOnItemClickListener(this);
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
        info = getItemInfoById(id);

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

    public static EditQuestionnaireFragment newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        EditQuestionnaireFragment fragment = new EditQuestionnaireFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit_questionnaire, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 保存
         */
        case R.id.menu_edit_questionnaire_save:
            Intent intent = new Intent(context, PreviewQuestionnaireActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            intent.putExtras(bundle);
            openActivity(intent);
            return true;

        /*
         * 添加題目
         */
        case R.id.menu_edit_questionnaire_add:
            EditQuestionnaireDialogFragment fragment = EditQuestionnaireDialogFragment.newInstance();
            fragment.setOnItemClickListener(this);
            fragment.show(getChildFragmentManager(), TAG);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

        switch (position) {
        /*
         * 单选题
         */
        case 0:

            break;

        /*
         * 多选题
         */
        case 1:

            break;

        /*
         * 简答题
         */
        case 2:
            Intent intent_short_answer = new Intent(context, ShortAnswerActivity.class);
            Bundle bundle_short_answer = new Bundle();
            bundle_short_answer.putInt("type", 0);
            intent_short_answer.putExtras(bundle_short_answer);
            openActivityForResult(intent_short_answer, REQUEST_CODE_SHORT_ANSWER);
            break;

        /*
         * 打分题
         */
        case 3:
            Intent intent_hit_the_title = new Intent(context, HitTheTitleActivity.class);
            Bundle bundle_hit_the_title = new Bundle();
            bundle_hit_the_title.putInt("type", 0);
            intent_hit_the_title.putExtras(bundle_hit_the_title);
            openActivityForResult(intent_hit_the_title, REQUEST_CODE_HINT_THE_TITLE);
            break;

        default:
            break;
        }
    }

    @Override
    public void onItemClicked(int position) {
        QuestionnaireItemInfo info = adapter.getItemInfo();
        List<QuestionnaireDetailInfo> detailList = adapter.getDetailItemInfo();
        if (detailList != null && detailList.size() > position) {
            int size = detailList.size();
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    if (detailList.get(i).getTag() == 0) {
                        detailList.get(i).setTag(1);
                    } else {
                        detailList.get(i).setTag(0);
                    }
                } else {
                    detailList.get(i).setTag(0);
                }
            }
            adapter.setDataSource(info, detailList);
        }
    }

    @Override
    public void onItemDelete(int position) {
        QuestionnaireItemInfo info = adapter.getItemInfo();
        List<QuestionnaireDetailInfo> detailList = adapter.getDetailItemInfo();
        if (detailList != null && detailList.size() > position) {
            long detailId = detailList.get(position).getId();
            detailList.remove(position);
            adapter.setDataSource(info, detailList);
            getAppComponent().getRealmHelper().deleteQuestionnaireDetailInfo(detailId);
        }
    }

    @Override
    public void onItemJoin(int position) {
        List<QuestionnaireDetailInfo> detailList = adapter.getDetailItemInfo();
        if (detailList != null && detailList.size() > position) {
            long detailId = detailList.get(position).getId();
            int type = detailList.get(position).getType();

            switch (type) {
            /*
             *  单选
             */
            case 0:

                break;

            /*
             * 多选题
             */
            case 1:

                break;

            /*
             * 简答题
             */
            case 2:
                Intent intent_short_answer = new Intent(context, ShortAnswerActivity.class);
                Bundle bundle_short_answer = new Bundle();
                bundle_short_answer.putInt("type", 1);
                bundle_short_answer.putLong("id", detailId);
                intent_short_answer.putExtras(bundle_short_answer);
                openActivityForResult(intent_short_answer, REQUEST_CODE_SHORT_ANSWER);
                break;

            /*
             * 打分题
             */
            case 3:
                Intent intent_hit_the_title = new Intent(context, HitTheTitleActivity.class);
                Bundle bundle_hit_the_title = new Bundle();
                bundle_hit_the_title.putInt("type", 1);
                bundle_hit_the_title.putLong("id", detailId);
                intent_hit_the_title.putExtras(bundle_hit_the_title);
                openActivityForResult(intent_hit_the_title, REQUEST_CODE_HINT_THE_TITLE);
                break;

            default:
                break;
            }
        }
    }

    @Override
    public QuestionnaireItemInfo getItemInfoById(long id) {
        return getAppComponent().getRealmHelper().questionnaireItemInfoById(id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
        /*
         * 单选
         */
        case REQUEST_CODE_SINGLE_CHOICE:
            break;

        /*
         * 多选
         */
        case REQUEST_CODE_MUTI_CHOICE:
            break;

        /*
         * 简答
         */
        case REQUEST_CODE_SHORT_ANSWER:
            if (data != null) {
                Bundle bundle_short_answer = data.getExtras();
                if (bundle_short_answer != null) {
                    info = getItemInfoById(id);
                    String title = bundle_short_answer.getString("title");
                    int type = bundle_short_answer.getInt("type", 0);
                    long detailId = bundle_short_answer.getLong("id");
                    QuestionnaireDetailInfo detailInfo = new QuestionnaireDetailInfo();
                    detailInfo.setType(2);
                    detailInfo.setTag(0);
                    detailInfo.setTitle(title);
                    if (type == 1) {
                        detailInfo.setId(detailId);
                    } else {
                        detailInfo.setId(System.currentTimeMillis());
                    }
                    getAppComponent().getRealmHelper().insert(info, detailInfo, type);
                    adapter.setDataSource(getItemInfoById(id));
                }
            }
            break;

        /*
         * 打分
         */
        case REQUEST_CODE_HINT_THE_TITLE:
            if (data != null) {
                Bundle bundle_short_answer = data.getExtras();
                if (bundle_short_answer != null) {
                    info = getItemInfoById(id);
                    String title = bundle_short_answer.getString("title");
                    int type = bundle_short_answer.getInt("type", 0);
                    long detailId = bundle_short_answer.getLong("id");
                    QuestionnaireDetailInfo detailInfo = new QuestionnaireDetailInfo();
                    detailInfo.setType(3);
                    detailInfo.setTag(0);
                    detailInfo.setTitle(title);
                    if (type == 1) {
                        detailInfo.setId(detailId);
                    } else {
                        detailInfo.setId(System.currentTimeMillis());
                    }
                    getAppComponent().getRealmHelper().insert(info, detailInfo, type);
                    adapter.setDataSource(getItemInfoById(id));
                }
            }
            break;


        default:
            break;
        }
    }
}
