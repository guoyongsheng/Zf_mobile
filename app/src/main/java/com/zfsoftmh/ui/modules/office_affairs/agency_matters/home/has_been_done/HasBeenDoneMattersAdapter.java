package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.AgencyMattersInfo;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/1
 * @Description: 已办事宜的适配器
 */

class HasBeenDoneMattersAdapter extends RecyclerView.Adapter<HasBeenDoneMattersAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<AgencyMattersInfo> list;
    private OnItemClickListener listener;

    HasBeenDoneMattersAdapter(Context context) {
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
        View view = inflater.inflate(R.layout.item_agency_matters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        final AgencyMattersInfo agencyMattersInfo = list.get(position);
      /*  String reason = agencyMattersInfo.getReason();
        String name = agencyMattersInfo.getName();
        String time = agencyMattersInfo.getTime();
        String apply = agencyMattersInfo.getApply();

        holder.tv_reason.setText(reason);
        holder.tv_name.setText(name);
        holder.tv_time.setText(time);
        holder.tv_apply.setText(apply);*/

        //点击事件
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(agencyMattersInfo);
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
     * 设置数据源
     *
     * @param list AgencyMattersInfo集合
     */
    void setDataSource(List<AgencyMattersInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 静态内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_reason; //原因
        private TextView tv_name; //名字
        private TextView tv_time; //时间
        private TextView tv_apply; //申请
        private RelativeLayout rl_item; //item布局

        ViewHolder(View itemView) {
            super(itemView);

            tv_reason = (TextView) itemView.findViewById(R.id.item_agency_matters_reason);
            tv_name = (TextView) itemView.findViewById(R.id.item_agency_matters_name);
            tv_time = (TextView) itemView.findViewById(R.id.item_agency_matters_time);
            tv_apply = (TextView) itemView.findViewById(R.id.item_agency_matters_apply);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_agency_matters);
        }
    }

    /**
     * 自定义会调接口
     */
    interface OnItemClickListener {
        void onItemClick(AgencyMattersInfo AgencyMattersInfo);
    }
}
