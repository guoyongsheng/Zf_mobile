package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseBottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 底部弹出对话框
 */

public class AgencyMattersDialogFragment extends BaseBottomSheetDialogFragment implements
        AgencyMattersDialogAdapter.OnItemClickListener {

    private OnItemClickListener listener;
    List<String> list;
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected Dialog createBottomSheetDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.common_recycler_view, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);

        //manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.common_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        AgencyMattersDialogAdapter adapter;
        if(list!=null){
            adapter = new AgencyMattersDialogAdapter(context, list);
        }else {
            //adapter
             adapter = new AgencyMattersDialogAdapter(context, getDataSource());
        }
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        return bottomSheetDialog;
    }

    //获取数据源
    private List<String> getDataSource() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(" 我是item " + i);
        }
        return list;
    }

    public void setData(List<String> list){
        this.list=list;

    }


    public static AgencyMattersDialogFragment newInstance() {
        return new AgencyMattersDialogFragment();
    }

    @Override
    public void onItemClick(int position) {
        if (listener != null) {
            dismiss();
            listener.onItemClick(position);
        }
    }

    /**
     * 自定义的回调接口
     */
    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
