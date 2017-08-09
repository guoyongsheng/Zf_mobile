package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.entity.CloudNoteInfo;
import com.zfsoftmh.entity.CloudNoteListInfo;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_detail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description:云笔记列表
 */

public class CloudNoteListFragment extends BaseListFragment<CloudNoteListPresenter, CloudNoteListInfo>
        implements CloudNoteListContract.View, CloudNoteListAdapter.OnItemListener {
    private CloudNoteListAdapter adapter;
    private String title, memoCatalogName;// 用户ID,每页列表条数，搜索标题以及标签名
    List<NoteLabelItemInfo> labelList; // 标签列表
    private static final int REQUEST_CODE_ADD_NOTE = 7; //添加笔记请求码

    public static CloudNoteListFragment newInstance() {
        return new CloudNoteListFragment();
    }

    @Override
    protected void initVariables() {
        title = "";
        memoCatalogName = "";
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected RecyclerArrayAdapter<CloudNoteListInfo> getAdapter() {
        adapter = new CloudNoteListAdapter(context);
        adapter.setOnItemListener(this);
        return adapter;
    }

    @Override
    protected void loadData() {
        getLabelData();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void editMode() {
        // 编辑状态禁止刷新加载
        setRefreshEnabled(false);// 禁止刷新
        setLoadMoreEnabled(false); // 禁止加载更多
        adapter.setShowIcon(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noEditMode() {
        setRefreshEnabled(true);
        setLoadMoreEnabled(true);
        adapter.setShowIcon(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean checkIsEditMode(String title) {
        return title != null && title.equals(getResources().getString(R.string.done));
    }


    public void searchNote(String s) {
        title = s;
        onRefresh();
    }

    /**
     * 获取便签数据
     */
    @Override
    public void getLabelData() {
        presenter.loadLabel();
    }

    @Override
    public void createLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideUpLoadingDialog() {
        hideProgressDialog();
    }

    @Override
    public void setLabelData(List<NoteLabelItemInfo> data) {
        if (data != null && data.size() > 0) {
            labelList = data;
            NoteLabelItemInfo bean = new NoteLabelItemInfo();
            bean.memoCatalogId = data.get(0).memoCatalogId;
            bean.memoCatalogName = data.get(0).memoCatalogName;
            bean.catalogColor = data.get(0).catalogColor;
            DbHelper.saveValueBySharedPreferences(getActivity(), "NoteLabelItemInfo",
                    "NoteLabelItemInfo", bean);
            loadCloudNoteData(start_page, PAGE_SIZE);
        }
    }

    @Override
    public void loadCloudNoteData(int startPage, int PAGE_SIZE) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", String.valueOf(startPage));
        params.put("size", String.valueOf(PAGE_SIZE));
        params.put("title", title);
        params.put("memoCatalogName", memoCatalogName);
        presenter.loadCloudNoteList(params);
    }

    /**
     * 云笔记列表数据加载成功回调方法
     *
     * @param listData
     */
    @Override
    public void setNoteListData(final CloudNoteInfo listData) {

        if (labelList != null) {
            Observable.just(labelList)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<List<NoteLabelItemInfo>, ResponseListInfo<CloudNoteListInfo>>() {

                        @Override
                        public ResponseListInfo<CloudNoteListInfo> apply(@NonNull List<NoteLabelItemInfo> noteLabelInfo) throws Exception {
                            if (noteLabelInfo == null) {
                                return null;
                            }
                            ArrayList<CloudNoteListInfo> noteList = new ArrayList<CloudNoteListInfo>();
                            ResponseListInfo<CloudNoteListInfo> listInfo = new ResponseListInfo<CloudNoteListInfo>();
                            for (int i = 0; i < listData.memoList.size(); i++) {
                                CloudNoteListInfo bean = new CloudNoteListInfo();
                                String id = listData.memoList.get(i).memoCatalogId;
                                bean.memoCatalogId = listData.memoList.get(i).memoCatalogId;
                                bean.memoTitle = listData.memoList.get(i).memoTitle;
                                bean.memoFileName = listData.memoList.get(i).memoFileName;
                                bean.createTime = listData.memoList.get(i).createTime;
                                bean.memoPath = listData.memoList.get(i).memoPath;
                                bean.userName = listData.memoList.get(i).userName;
                                bean.contentFlag = listData.memoList.get(i).contentFlag;
                                bean.isSelect = false;
                                for (int j = 0; j < noteLabelInfo.size(); j++) {
                                    if (id.equals(noteLabelInfo.get(j).memoCatalogId)) {
                                        bean.catalogColor = noteLabelInfo.get(j).catalogColor;
                                        bean.memoCatalogName = noteLabelInfo.get(j).memoCatalogName;
                                    }
                                }
                                noteList.add(bean);

                            }
                            listInfo.setOvered(listData.isOvered);
                            listInfo.setItemList(noteList);
                            return listInfo;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseListInfo<CloudNoteListInfo>>() {
                        @Override
                        public void accept(@NonNull ResponseListInfo<CloudNoteListInfo> cloudNoteListInfo) throws Exception {
                                loadSuccess(cloudNoteListInfo);
                        }
                    });

        }
    }

    /**
     * 删除云笔记
     */
    @Override
    public void deleteNote() {
        List<CloudNoteListInfo> dataList = adapter.getAllData();

        if (dataList != null && dataList.size() > 0) {

            Observable.just(dataList)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<List<CloudNoteListInfo>, String>() {
                        @Override
                        public String apply(@NonNull List<CloudNoteListInfo> selected) throws Exception {
                            ArrayList<String> deleteName = new ArrayList<String>();
                            for (int i = 0; i < selected.size(); i++) {
                                if (selected.get(i).isSelect) {
                                    String name = selected.get(i).memoFileName;
                                    deleteName.add(name);
                                }
                            }
                            String memoFileNameList = ""; // 删除的列表名
                            if (deleteName != null && deleteName.size() > 0) {
                                memoFileNameList = deleteName.toString();
                                if (memoFileNameList != null
                                        && !memoFileNameList.equals("")) {
                                    memoFileNameList = memoFileNameList.replace("[", "")
                                            .replace("]", "").replace(" ", "");
                                }
                            }
                            return memoFileNameList;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            if (s != null && !s.equals("")) {
                                presenter.deleteNote(s);
                            } else {
                                showToastMsgShort(Constant.please_select_delete_note);
                            }

                        }
                    });

        } else {
            showToastMsgShort(Constant.no_data);
        }
    }

    /**
     * 笔记删除成功
     */
    @Override
    public void deleteNoteSuccess() {
        showToastMsgShort(Constant.delete_success);
        noEditMode();
        if (listener != null) {
            listener.success();
        }
        onRefresh();
    }


    @Override
    public void upLoadFailure(String errMsg) {
        if (errMsg.equals(Constant.note_list_failure)) { // 云笔记列表请求失败
        }
        showToastMsgShort(errMsg);
    }

    /**
     * 笔记详情跳转
     *
     * @param position
     * @param bean
     */
    @Override
    public void onClickItem(int position, CloudNoteListInfo bean) {
        if (bean != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("cloudNoteList", bean);
            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            /**
             * 刷新笔记列表
             */
            case REQUEST_CODE_ADD_NOTE:
                onRefresh();
                break;
        }
    }

    /**
     * 删除笔记回调方法
     */
    interface deleteNoteListener {
        // 删除笔记成功回调
        void success();

    }

    private deleteNoteListener listener;

    public void setDeleteNoteListener(deleteNoteListener deleteNoteListener) {
        listener = deleteNoteListener;
    }

}
