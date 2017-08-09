package com.zfsoftmh.ui.modules.personal_affairs.personal_files;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.PersonalFilesInfo;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/29
 * @Description: 适配器
 */

class PersonalFilesAdapter extends RecyclerView.Adapter<PersonalFilesAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private OnViewClickListener listener;
    private int color_checked; //选中
    private int color_un_checked; //未选中
    private List<PersonalFilesInfo> list;

    PersonalFilesAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);

            color_checked = ContextCompat.getColor(context, R.color.colorWhite);
            color_un_checked = ContextCompat.getColor(context, R.color.color_ecf3f6);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_personal_files, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        PersonalFilesInfo personalFilesInfo = list.get(position);
        String name = personalFilesInfo.getInformationname(); //名称
        String url = personalFilesInfo.getInformationico(); //url
        final boolean isChecked = personalFilesInfo.isChecked(); //是否选中

        holder.tv_name.setText(name);
        ImageLoaderHelper.loadImage(context, holder.iv_icon, url);

        if (isChecked) {
            holder.ll_item.setBackgroundColor(color_checked);
        } else {
            holder.ll_item.setBackgroundColor(color_un_checked);
        }

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onViewClick(position);
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

    void setDataSource(List<PersonalFilesInfo> data) {
        this.list = data;
        if (list != null && list.size() > 0) {
            list.get(0).setChecked(true);
            notifyDataSetChanged();
        }
    }

    void setOnViewClickListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    /**
     * 判断item是否已经被选中
     *
     * @param position 当前的位置
     * @return true: 选中  false: 没有选中
     */
    boolean checkItemIsChecked(int position) {
        return !(list != null && list.size() > position && list.get(position) != null) || list.get(position).isChecked();
    }

    /**
     * 设置当前的item为选中状态
     *
     * @param position 当前的位置
     */
    void setItemIsChecked(int position) {
        if (list != null && list.size() > position) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    list.get(i).setChecked(true);
                } else {
                    list.get(i).setChecked(false);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 获取当前位置的id
     *
     * @param position 当前位置
     * @return id
     */
    public String getId(int position) {
        if (list != null && list.size() > position && list.get(position) != null) {
            return list.get(position).getInformationid();
        }
        return "";
    }

    /**
     * 静态内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private LinearLayout ll_item; //布局

        ViewHolder(View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.item_personal_files_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_personal_files_name);
            ll_item = (LinearLayout) itemView.findViewById(R.id.item_personal_files);
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnViewClickListener {

        void onViewClick(int position);
    }
}
