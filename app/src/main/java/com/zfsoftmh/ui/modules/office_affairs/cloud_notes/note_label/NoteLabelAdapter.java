package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.note_label;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.entity.NoteLabelItemInfo;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/5/11 0011
 * 编写人： 王世美
 * 功能描述：笔记标签适配器
 */

public class NoteLabelAdapter extends RecyclerView.Adapter<NoteLabelAdapter.NoteLabelHolder> {
    private LabelOnClickListener listener;
    private List<NoteLabelItemInfo> dataList;
    private Context context;
    private LayoutInflater inflater;

    public NoteLabelAdapter(Context context) {
        this.context = context;
        dataList = new ArrayList<NoteLabelItemInfo>();
        inflater = LayoutInflater.from(context);
    }

    public void addData(List<NoteLabelItemInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(LabelOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public NoteLabelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_note_label, parent, false);
        return new NoteLabelHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(NoteLabelHolder holder, int position) {
        if (dataList.get(position).memoCatalogName != null) {
            holder.note_label_name_tv.setText(dataList.get(position).memoCatalogName);
        }

        if (dataList.get(position).catalogColor != null && !dataList.get(position).catalogColor.equals("")) {
            CloudNoteUtils.getColorView(holder.item_note_label_view)
                    .setColor(CloudNoteUtils.getColorValue(dataList.get(position).catalogColor));
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null && dataList.size() > 0 ? dataList.size() : 0;
    }


    class NoteLabelHolder extends RecyclerView.ViewHolder {
        ImageView check_mark_iv; // 选中图标
        View item_note_label_view; // 标签颜色
        TextView note_label_name_tv; // 标签类别名
        LabelOnClickListener listener;

        public NoteLabelHolder(View itemView, LabelOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            check_mark_iv = (ImageView) itemView.findViewById(R.id.check_mark_iv);
            item_note_label_view = itemView.findViewById(R.id.item_note_label_view);
            note_label_name_tv = (TextView) itemView.findViewById(R.id.note_label_name_tv);
            itemView.setOnClickListener(onceClickListener);

        }

        private OnceClickListener onceClickListener = new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                if (listener != null) {
                    listener.labelOnClick(getPosition(), dataList.get(getPosition()));
                }
            }
        };

    }

    /**
     * 自定义接口
     */
    interface LabelOnClickListener {
        void labelOnClick(int position, NoteLabelItemInfo bean);
    }
}
