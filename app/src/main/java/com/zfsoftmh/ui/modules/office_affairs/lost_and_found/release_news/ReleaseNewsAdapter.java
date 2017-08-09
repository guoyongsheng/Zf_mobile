package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wesley
 * @date: 2017-6-22
 * @Description: 适配器
 */

public class ReleaseNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private LayoutInflater inflater;
    private List<String> list = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private Context context;
    private OnItemClickListener listener;

    public ReleaseNewsAdapter(Context context) {
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);

            int screenWidth = ScreenUtils.getScreenWidth(context);
            int margin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
            int itemHeight = (screenWidth - 6 * margin) / 3;
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, itemHeight);
            params.setMargins(margin, margin, margin, 0);
        }
    }

    public void insertItem(String path) {
        list.add(path);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (list != null && list.size() > position) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeAllItems() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public List<String> getAllItems() {
        return list;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }

        View view;
        switch (viewType) {
        case TYPE_0:
            view = inflater.inflate(R.layout.item_lost_and_found_image_footer, parent, false);
            return new FooterViewHolder(view);


        case TYPE_1:
            view = inflater.inflate(R.layout.item_lost_and_found_image, parent, false);
            return new ItemViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder == null) {
            return;
        }

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         *  footer
         */
        case TYPE_0:
            if (!(holder instanceof FooterViewHolder)) {
                return;
            }

            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.iv_icon_footer.setLayoutParams(params);
            footerViewHolder.iv_icon_footer.setImageResource(R.mipmap.iv_add);
            footerViewHolder.iv_icon_footer.setOnClickListener(new OnceClickListener() {
                @Override
                public void onOnceClick(View v) {
                    if (listener != null) {
                        listener.onItemAdd();
                    }
                }
            });
            break;

        /*
         *  item
         */
        case TYPE_1:
            if (list == null || list.size() <= position || list.get(position) == null ||
                    !(holder instanceof ItemViewHolder) || context == null) {
                return;
            }

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.iv_icon.setLayoutParams(params);
            ImageLoaderHelper.loadImage(context, itemViewHolder.iv_icon, list.get(position));
            itemViewHolder.iv_icon.setOnClickListener(new OnceClickListener() {
                @Override
                public void onOnceClick(View v) {
                    if (listener != null) {
                        listener.onItemRemove(position);
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
        if (list != null) {
            int size = list.size();
            if (size >= 2) {
                return 3;
            }
            return size + 1;
        }
        return 1;
    }


    @Override
    public int getItemViewType(int position) {

        if (list == null || list.size() == 0) {
            return TYPE_0;
        }
        int size = list.size();
        if (size >= 3) {
            return TYPE_1;
        }

        if (position == size) {
            return TYPE_0;
        }

        return TYPE_1;
    }

    //静态内部类
    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;

        public ItemViewHolder(View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.item_lost_and_found_image);
        }
    }

    //静态内部类
    private static class FooterViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon_footer;

        FooterViewHolder(View itemView) {
            super(itemView);

            iv_icon_footer = (ImageView) itemView.findViewById(R.id.item_lost_and_found_image_found);
        }
    }

    /**
     * 自定义回调接口
     */
    public interface OnItemClickListener {

        /**
         * 删除
         *
         * @param position 在列表中的位置
         */
        void onItemRemove(int position);


        /**
         * 添加
         */
        void onItemAdd();

    }

}
