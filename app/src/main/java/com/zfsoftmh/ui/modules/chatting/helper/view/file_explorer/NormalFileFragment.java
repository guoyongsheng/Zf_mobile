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
import java.util.List;

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
 * <p>office文档</p>
 *  为了保证来回切换的时候不重新加载fragment
 *  我在viewpager的适配器重写了item的插入和删除方法
 *  所以其实是并没有执行原来的插入和删除 而是做了显示和隐藏
 *  所以加载数据写在 onActivityCreated 中　只加载一次数据　
 */

public class NormalFileFragment extends BaseFragment{

    public static NormalFileFragment newInstance(){
        return new NormalFileFragment();
    }

    private NormalFileAdapter adapter;
    private List<FileParent> data;
    @Override
    protected void initVariables() {
        data = new ArrayList<>();
        adapter = new NormalFileAdapter(data);
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

    FileSender fileSender;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fileSender = (FileSender) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showProgressDialog("正在查询...");
        loadData();
    }

    @Override
    protected void initListener() {
        adapter.setFileClickListener(new FileClickListener() {
            @Override
            public void onFileClick(FileInfo info) {
                fileSender.sendFile(1,info);
            }
        });
    }


    private final String []target = {".doc",".docx",".ppt",".pptx",".xls",".pdf"};

    public void loadData() {
        Observable.create(new ObservableOnSubscribe<ArrayList<FileInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<FileInfo>> e) throws Exception {
                e.onNext(FileUtils.getSpecificTypeOfFile(context, target));
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<ArrayList<FileInfo>>() {
              @Override
              public void accept(@NonNull ArrayList<FileInfo> fileInfos) throws Exception {
                  if (!isActive()) return;
                  formatFiles(fileInfos);
              }
          });
    }

    private void formatFiles(@NonNull ArrayList<FileInfo> fileInfos) {
        hideProgressDialog();
        ArrayList<FileInfo> list_word = new ArrayList<>();
        ArrayList<FileInfo> list_xls = new ArrayList<>();
        ArrayList<FileInfo> list_ppt = new ArrayList<>();
        ArrayList<FileInfo> list_pdf = new ArrayList<>();
        for (FileInfo info:fileInfos){
            switch (info.getSuffixType()){
                case FileInfo.TYPE_WORD:
                    list_word.add(info);
                    break;
                case FileInfo.TYPE_PPT:
                    list_ppt.add(info);
                    break;
                case FileInfo.TYPE_XLS:
                    list_xls.add(info);
                    break;
                case FileInfo.TYPE_PDF:
                    list_pdf.add(info);
                    break;
            }
        }
        data.add(new FileParent("WORD"));
        data.add(new FileParent("PPT"));
        data.add(new FileParent("XLS"));
        data.add(new FileParent("PDF"));
        adapter.notifyParentDataSetChanged(false);
        modifyData(0,list_word);
        modifyData(1,list_ppt);
        modifyData(2,list_xls);
        modifyData(3,list_pdf);
    }

    private void modifyData(int i, ArrayList<FileInfo> infoList){
        if (data.size() >= i+1){
            data.get(i).setDataList(infoList);
            adapter.notifyChildRangeChanged(i,0,data.get(i).getChildList().size());
        }
    }
}
