package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.PersonalFilesDetailInfo;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/30
 * @Description: 个人档案详情适配器
 */

class PersonalFilesDetailAdapter extends RecyclerView.Adapter<PersonalFilesDetailAdapter.ViewHolder> {

    private List<PersonalFilesDetailInfo> list;
    private LayoutInflater inflater;

    PersonalFilesDetailAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_personal_files_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        int id = list.get(position).getId();
        String key = list.get(position).getKey();
        String value = list.get(position).getValue();
        holder.tv_key.setText(key);
        holder.tv_value.setText(value);

        handleHeader(position, holder.item_view);
        int nextPosition = position + 1;
        if (list.size() > nextPosition) {
            holder.view.setVisibility(View.GONE);
            int nextId = list.get(nextPosition).getId();
            if (id == nextId) {
                holder.middle_view.setVisibility(View.GONE);
                holder.foot_view.setVisibility(View.GONE);
            } else {
                holder.foot_view.setVisibility(View.VISIBLE);
                holder.middle_view.setVisibility(View.VISIBLE);
            }
        } else {
            holder.foot_view.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    void setDataSource(List<PersonalFilesDetailInfo> listDetail) {
        this.list = listDetail;
        notifyDataSetChanged();
    }

    //处理头部
    private void handleHeader(int position, View item_view) {
        switch (position) {
        case 0:
            item_view.setVisibility(View.VISIBLE);
            break;

        default:
            item_view.setVisibility(View.GONE);
            break;
        }
    }


    /**
     * 镜头内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_key; //key
        private TextView tv_value; //value
        private View item_view;
        private View foot_view; //尾部的线
        private View middle_view; //中间的线

        private View view; //最底部的线

        ViewHolder(View itemView) {
            super(itemView);

            tv_key = (TextView) itemView.findViewById(R.id.item_personal_files_detail_key);
            tv_value = (TextView) itemView.findViewById(R.id.item_personal_files_detail_value);
            item_view = itemView.findViewById(R.id.item_personal_files_view);
            foot_view = itemView.findViewById(R.id.item_personal_files_detail_foot_line);
            middle_view = itemView.findViewById(R.id.item_personal_files_middle_line);
            view = itemView.findViewById(R.id.item_personal_files_foot_line);
        }
    }
}
