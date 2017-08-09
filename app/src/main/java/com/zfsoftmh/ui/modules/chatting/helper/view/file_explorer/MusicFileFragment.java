package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.FileUtils;
import com.zfsoftmh.entity.FileInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sy
 * on 2017/7/7.
 * <p>音频文件</p>
 */

public class MusicFileFragment extends BaseFragment implements FileClickListener {

    public static MusicFileFragment newInstance(){
        return new MusicFileFragment();
    }

    private FileSender mFileSender;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFileSender = (FileSender) context;
    }

    private MusicFileAdapter adapter;
    @Override
    protected void initVariables() {
        adapter = new MusicFileAdapter(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) { }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Observable.create(new ObservableOnSubscribe<ArrayList<FileInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<FileInfo>> e) throws Exception {
                e.onNext(FileUtils.getMusicFiles(context));
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<ArrayList<FileInfo>>() {
              @Override
              public void accept(@NonNull ArrayList<FileInfo> fileInfos) throws Exception {
                  adapter.setData(fileInfos);
              }
          });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onFileClick(FileInfo info) {
        mFileSender.sendFile(2, info);
    }
}
