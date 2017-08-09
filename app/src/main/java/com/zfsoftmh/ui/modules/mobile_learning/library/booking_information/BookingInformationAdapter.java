package com.zfsoftmh.ui.modules.mobile_learning.library.booking_information;

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

class BookingInformationAdapter extends RecyclerView.Adapter<BookingInformationAdapter.ItemViewHolder> {

    private LayoutInflater inflater;

    BookingInformationAdapter(Context context) {
        if (context != null) {
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

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
