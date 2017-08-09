package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zfsoftmh.R;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 底部对话框适配器
 */

class AgencyMattersDialogAdapter extends RecyclerView.Adapter<AgencyMattersDialogAdapter.ViewHolder> {

    private List<String> list;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    AgencyMattersDialogAdapter(Context context, List<String> list) {
        this.list = list;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_bottom_sheet_dialog_agency_matters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }
        holder.tv_value.setText(list.get(position));
        holder.tv_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
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
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_value;

        ViewHolder(View itemView) {
            super(itemView);

            tv_value = (TextView) itemView.findViewById(R.id.item_bottom_text);
        }
    }

    /**
     * 自定义的回调接口
     */
    interface OnItemClickListener {

        void onItemClick(int position);
    }
}
