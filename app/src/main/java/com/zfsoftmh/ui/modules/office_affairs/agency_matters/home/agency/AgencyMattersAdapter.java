package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.AgencyMattersItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 适配器
 */

class AgencyMattersAdapter extends RecyclerView.Adapter<AgencyMattersAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<AgencyMattersItemInfo> list = new ArrayList<>();
    private OnItemClickListener listener;

    private int flag; // 0: 滚动加载  1: 下拉刷新

    AgencyMattersAdapter(Context context) {
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
        final AgencyMattersItemInfo agencyMattersInfo = list.get(position);
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


    void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 设置数据源
     *
     * @param list 待办事宜的集合
     */
    void setDataSource(List<AgencyMattersItemInfo> list) {

        switch (flag) {
        /*
         *  滚动加载
         */
        case 0:
            this.list.addAll(list);
            break;

        /*
         * 下拉刷新
         */
        case 1:
            this.list = list;
            break;

        default:
            break;
        }
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
        private RelativeLayout rl_item; //item的布局

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
     * 自定义回调接口
     */
    interface OnItemClickListener {
        void onItemClick(AgencyMattersItemInfo agencyMattersInfo);
    }
}
