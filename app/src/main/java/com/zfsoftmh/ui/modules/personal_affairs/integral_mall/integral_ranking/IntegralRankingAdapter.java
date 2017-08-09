package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.integral_ranking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.IntegralRankingItemInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wangshimei
 * @date: 17/7/25
 * @Description:
 */

public class IntegralRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1; // 头部的view
    private static final int TYPE_NORMAL = 2; // 列表view
    private Context context;
    private LayoutInflater inflater;
    private View headerView; //头部
    private List<IntegralRankingItemInfo> dataList; // item数据
    private List<Integer> types = new ArrayList<>();  //保存所有的item
    private SparseIntArray array = new SparseIntArray();


    public IntegralRankingAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    /**
     * 添加头布局视图
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public void initItemList(List<IntegralRankingItemInfo> listItem) {
        this.dataList = listItem;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            /**
             * 头部
             */
            case TYPE_HEADER:
                return new ItemViewHolder(headerView);
            /**
             * item类型
             */
            case TYPE_NORMAL:
                if (inflater == null) {
                    return null;
                }
                View view_item = inflater.inflate(R.layout.item_integral_ranking, parent, false);
                return new ItemViewHolder(view_item);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            /**
             * 头部
             */
            case TYPE_HEADER:
                return;
            /**
             * item类型
             */
            case TYPE_NORMAL:
                final int rel_position = position - array.get(viewType) - 1;
                if (holder == null || !(holder instanceof ItemViewHolder) || dataList == null
                        || dataList.size() <= rel_position || dataList.get(rel_position) == null || rel_position < 0) {
                    return;
                }
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                IntegralRankingItemInfo itemInfo = dataList.get(rel_position);
                String name = itemInfo.xm; // 姓名
                String header = itemInfo.wjlj; // 头像
                String ranking = String.valueOf(itemInfo.ranking); // 排名
                String source = itemInfo.source; // 积分数值
                if (name != null)
                    itemViewHolder.item_ranking_name.setText(name);
                if (header != null)
                    ImageLoaderHelper.loadImage(context, itemViewHolder.item_ranking_header, header);
                if (ranking != null)
                    itemViewHolder.item_ranking_number.setText(ranking);
                if (source != null)
                    itemViewHolder.item_ranking_integral.setText(source);
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

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView item_ranking_number; // 排名
        private CircleImageView item_ranking_header; // 头像
        private TextView item_ranking_name; // 姓名
        private TextView item_ranking_integral; // 积分

        public ItemViewHolder(View itemView) {
            super(itemView);

            if (itemView == headerView) {
                return;
            }
            item_ranking_number = (TextView) itemView.findViewById(R.id.item_ranking_number);
            item_ranking_header = (CircleImageView) itemView.findViewById(R.id.item_ranking_header);
            item_ranking_name = (TextView) itemView.findViewById(R.id.item_ranking_name);
            item_ranking_integral = (TextView) itemView.findViewById(R.id.item_ranking_integral);

        }


    }
}
