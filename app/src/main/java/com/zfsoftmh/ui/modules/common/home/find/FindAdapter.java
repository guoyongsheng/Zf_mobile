package com.zfsoftmh.ui.modules.common.home.find;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.FindItemInfo;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;


/**
 * @author wesley
 * @date: 2017-6-29
 * @Description: 适配器
 */

class FindAdapter extends RecyclerView.Adapter<FindAdapter.ItemViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private LayoutInflater inflater;
    private List<FindItemInfo> list;
    private View headView;
    private OnItemClickListener listener;

    FindAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    void setHeaderView(View headerView) {
        this.headView = headerView;
        notifyItemInserted(0);
    }

    void insertItems(List<FindItemInfo> list) {
        int startPosition = getItemCount();
        this.list = list;
        if (list != null) {
            notifyItemRangeInserted(startPosition, list.size());
        }
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    private int getRealPosition(int position) {
        if (headView == null) {
            return position;
        }

        return position - 1;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_HEADER:
            return new ItemViewHolder(headView);

        /*
         * item
         */
        case TYPE_NORMAL:
            if (inflater == null) {
                return null;
            }
            View view = inflater.inflate(R.layout.item_find, parent, false);
            return new ItemViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_HEADER:
            break;

        /*
         * item
         */
        case TYPE_NORMAL:
            final int realPosition = getRealPosition(position);
            if (holder == null || list == null || list.size() <= realPosition || realPosition < 0
                    || list.get(realPosition) == null) {
                return;
            }
            holder.iv_icon.setImageResource(list.get(realPosition).getResId());
            holder.tv_name.setText(list.get(realPosition).getName());

            if (realPosition == 3) {
                holder.view_divider.setVisibility(View.VISIBLE);
            } else {
                holder.view_divider.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new OnceClickListener() {
                @Override
                public void onOnceClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(realPosition);
                    }
                }
            });
            break;

        default:
            break;
        }
    }

    @Override
    public int getItemCount() {
        if (headView == null) {
            if (list != null) {
                return list.size();
            }
            return 0;
        }
        if (list != null) {
            return list.size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView == null) {
            return TYPE_NORMAL;
        }

        if (position == 0) {
            return TYPE_HEADER;
        }

        return TYPE_NORMAL;
    }

    /**
     * 静态内部类
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon; //图标
        private TextView tv_name; //名称

        private View view_divider; //分割线

        public ItemViewHolder(View itemView) {
            super(itemView);

            if (itemView == headView) {
                return;
            }

            iv_icon = (ImageView) itemView.findViewById(R.id.item_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_name);
            view_divider = itemView.findViewById(R.id.item_divider);
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(int position);
    }
}
