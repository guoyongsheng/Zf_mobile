package com.zfsoftmh.ui.modules.common.home.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.MyPortalItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/20
 * @Description: 我的界面 RecyclerView的适配器
 */
class MineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1; //头部的view
    private static final int TYPE_NORMAL = 2; //普通item的类型---不可变的
    private static final int TYPE_NORMAL_VARIABLE = 3; //普通的item类型---可变的
    private Context context;
    private LayoutInflater inflater;
    private List<Integer> types = new ArrayList<>();  //保存所有的item
    private SparseIntArray array = new SparseIntArray();
    private List<MyPortalItemInfo> listItem; //不可变的item
    private List<MyPortalItemInfo> listVariableItem; //可变的item
    private View headerView; //头部
    private OnItemClickListener listener;

    MineAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    void initItem(List<MyPortalItemInfo> listItem) {
        this.listItem = listItem;

        if (listItem != null && listItem.size() > 0) {
            int positionStart = getItemCount();
            array.put(TYPE_NORMAL, types.size());
            int size = listItem.size();
            for (int i = 0; i < size; i++) {
                types.add(TYPE_NORMAL);
            }
            notifyItemRangeInserted(positionStart, size);
        }
    }

    void initVariableItem(List<MyPortalItemInfo> listVariableItem) {
        this.listVariableItem = listVariableItem;

        if (listVariableItem != null && listVariableItem.size() > 0) {
            int positionStart = getItemCount();
            array.put(TYPE_NORMAL_VARIABLE, types.size());
            int size = listVariableItem.size();
            for (int i = 0; i < size; i++) {
                types.add(TYPE_NORMAL_VARIABLE);
            }

            notifyItemRangeInserted(positionStart, size);
        }
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_HEADER:
            return new ItemViewHolder(headerView);

        /*
         * item类型---不可变的
         */
        case TYPE_NORMAL:
            if (inflater == null) {
                return null;
            }
            View view_item = inflater.inflate(R.layout.item_fragment_mine, parent, false);
            return new ItemViewHolder(view_item);

        /*
         * item类型---可变的
         */
        case TYPE_NORMAL_VARIABLE:
            if (inflater == null) {
                return null;
            }
            View view_item_variable = inflater.inflate(R.layout.item_variable_fragment_mine, parent, false);
            return new ItemVariableViewHolder(view_item_variable);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_HEADER:
            return;

        /*
         * item类型---不可变的
         */
        case TYPE_NORMAL:
            final int rel_position = position - array.get(viewType) - 1;
            if (holder == null || !(holder instanceof ItemViewHolder) || listItem == null
                    || listItem.size() <= rel_position || listItem.get(rel_position) == null || rel_position < 0) {
                return;
            }
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            MyPortalItemInfo itemInfo = listItem.get(rel_position);
            String name = itemInfo.getName(); //名称
            int resId = itemInfo.getResId(); //图标
            itemViewHolder.tv_name.setText(name);
            itemViewHolder.iv_icon.setImageResource(resId);

            itemViewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(rel_position);
                    }
                }
            });
            break;

        /*
         * item类型---可变的
         */
        case TYPE_NORMAL_VARIABLE:
            int rel_variable_position = position - array.get(viewType) - 1;
            if (holder == null || !(holder instanceof ItemVariableViewHolder) || listVariableItem == null
                    || listVariableItem.size() <= rel_variable_position
                    || listVariableItem.get(rel_variable_position) == null
                    || rel_variable_position < 0 || context == null) {
                return;
            }

            ItemVariableViewHolder itemVariableViewHolder = (ItemVariableViewHolder) holder;
            final MyPortalItemInfo info = listVariableItem.get(rel_variable_position);
            String name_variable = info.getName(); //名称
            String icon_variable = info.getIcon(); //图标
            itemVariableViewHolder.tv_name.setText(name_variable);
            ImageLoaderHelper.loadImage(context, itemVariableViewHolder.iv_icon, icon_variable);

            if (rel_variable_position == 0) {
                itemVariableViewHolder.view_divider.setVisibility(View.VISIBLE);
            } else {
                itemVariableViewHolder.view_divider.setVisibility(View.GONE);
            }

            itemVariableViewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(info);
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
        if (headerView == null) {
            if (types != null) {
                return types.size();
            }
            return 0;
        } else {
            if (types != null) {
                return types.size() + 1;
            }
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null) {
            if (types != null && types.size() > position && position >= 0) {
                return types.get(position);
            }
            return -1;
        }

        if (position == 0) {
            return TYPE_HEADER;
        }

        int relPosition = position - 1;
        if (types != null && types.size() > relPosition && relPosition >= 0) {
            return types.get(relPosition);
        }
        return -1;
    }

    //item类型---不可变的
    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //整个布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称

        public ItemViewHolder(View itemView) {
            super(itemView);

            if (itemView == headerView) {
                return;
            }

            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_fragment_mine_rl);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_fragment_mine_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_fragment_mine_name);
        }
    }

    //item类型---可变的
    private class ItemVariableViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //整个布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private TextView tv_number; //数量
        private View view_divider; //分割线

        ItemVariableViewHolder(View itemView) {
            super(itemView);

            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_variable_fragment_mine_rl);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_variable_fragment_mine_iv);
            tv_name = (TextView) itemView.findViewById(R.id.item_variable_fragment_mine_name);
            tv_number = (TextView) itemView.findViewById(R.id.item_variable_fragment_mine_number);
            view_divider = itemView.findViewById(R.id.item_variable_divider);
        }
    }


    /**
     * 自定义回调接口
     */
    public interface OnItemClickListener {

        void onItemClick(int position);

        void onItemClick(MyPortalItemInfo itemInfo);
    }
}
