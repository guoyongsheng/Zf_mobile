package com.zfsoftmh.ui.modules.mobile_learning.library.reader_information;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.ReaderInformationInfo;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 读者信息的适配器
 */

class ReaderInformationAdapter extends RecyclerView.Adapter<ReaderInformationAdapter.ItemViewHolder> {

    private LayoutInflater inflater;
    private List<ReaderInformationInfo> list;

    ReaderInformationAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(inflater == null){
            return null;
        }
        View view = inflater.inflate(R.layout.item_reader_information, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    /**
     * 静态内部类
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_key; //key
        private TextView tv_value; //value

        public ItemViewHolder(View itemView) {
            super(itemView);

            tv_key = (TextView) itemView.findViewById(R.id.item_reader_information_key);
            tv_value = (TextView) itemView.findViewById(R.id.item_reader_information_value);
        }
    }
}
