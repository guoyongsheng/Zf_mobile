package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published;

import android.content.Intent;
import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.common.utils.LoggerHelper;
import com.zfsoftmh.entity.QuestionnaireItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire.EditQuestionnaireActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.preview_questionnaire.PreviewQuestionnaireActivity;
import com.zfsoftmh.ui.modules.office_affairs.questionnaire.submit_questionnaire.SubmitQuestionnaireActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wesley
 * @date: 2017-6-1
 * @Description: 未发布ui
 */

public class UnPublishedFragment extends BaseListFragment<UnPublishedPresenter, QuestionnaireItemInfo> implements
        UnPublishedContract.View, UnPublishedAdapter.OnItemClickListener {

    private static final String TAG = "UnPublishedFragment";
    private UnPublishedAdapter adapter; //适配器
    private List<QuestionnaireItemInfo> list;

    @Override
    protected RecyclerArrayAdapter<QuestionnaireItemInfo> getAdapter() {
        adapter = new UnPublishedAdapter(context);
        adapter.setOnItemClickListener((UnPublishedAdapter.OnItemClickListener) this);
        return adapter;
    }

    @Override
    protected void loadData() {
        list = getAppComponent().getRealmHelper().queryAllQuestionnaireItemInfo();
        getItemInfo(start_page);
    }

    @Override
    public void onItemClick(int position) {
        List<QuestionnaireItemInfo> list = adapter.getAllData();
        if (list == null || list.size() <= position) {
            return;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i == position) {
                if (list.get(i).getTag() == 1) {
                    list.get(i).setTag(0);
                } else {
                    list.get(i).setTag(1);
                }
            } else {
                list.get(i).setTag(0);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static UnPublishedFragment newInstance() {
        return new UnPublishedFragment();
    }

    @Override
    public void getItemInfo(final int currentPage) {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Function<Long, ResponseListInfo<QuestionnaireItemInfo>>() {
                    @Override
                    public ResponseListInfo<QuestionnaireItemInfo> apply(@NonNull Long aLong) throws Exception {
                        ResponseListInfo<QuestionnaireItemInfo> info = new ResponseListInfo<>();
                        if (list == null) {
                            return info;
                        }
                        int size = list.size();
                        List<QuestionnaireItemInfo> listItem = new ArrayList<>();
                        for (int i = (currentPage - 1) * PAGE_SIZE; i < size; i++) {
                            QuestionnaireItemInfo questionnaireItemInfo = list.get(i);
                            listItem.add(questionnaireItemInfo);
                            if (listItem.size() == PAGE_SIZE) {
                                break;
                            }
                        }
                        info.setItemList(listItem);
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseListInfo<QuestionnaireItemInfo>>() {
                    @Override
                    public void accept(@NonNull ResponseListInfo<QuestionnaireItemInfo> listItem) throws Exception {
                        loadSuccess(listItem);
                    }
                });

    }

    @Override
    public void onItemDelete(QuestionnaireItemInfo info) {
        if (info == null) {
            LoggerHelper.e(TAG, " onItemDelete 删除失败 失败信息 info =" + null);
            return;
        }
        int position = adapter.getPosition(info);
        adapter.notifyItemRemoved(position);
        getAppComponent().getRealmHelper().deleteQuestionnaireItemInfo(info.getId());
        onRefresh();
    }

    @Override
    public void onItemEdit(long id) {
        Intent intent = new Intent(context, EditQuestionnaireActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public void onItemLook(long id) {
        Intent intent = new Intent(context, PreviewQuestionnaireActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivity(intent);
    }

    @Override
    public void onItemPublished(long id) {
        Intent intent = new Intent(context, SubmitQuestionnaireActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        intent.putExtras(bundle);
        openActivity(intent);
    }
}
