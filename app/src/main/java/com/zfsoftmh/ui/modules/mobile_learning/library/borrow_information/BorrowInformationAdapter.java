package com.zfsoftmh.ui.modules.mobile_learning.library.borrow_information;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 适配器
 */

class BorrowInformationAdapter extends RecyclerView.Adapter<BorrowInformationAdapter.ItemViewHolder> {

    private LayoutInflater inflater;

    BorrowInformationAdapter(Context context){
        if(context != null){
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 静态内部类
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
