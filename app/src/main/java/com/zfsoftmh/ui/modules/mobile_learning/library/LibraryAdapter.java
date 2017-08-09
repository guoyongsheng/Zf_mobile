package com.zfsoftmh.ui.modules.mobile_learning.library;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.LibraryInfo;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 图书馆适配器
 */

class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ItemViewHolder> {

    private LayoutInflater inflater;
    private List<LibraryInfo> list;

    private CardView.LayoutParams params;
    private OnItemClickListener listener;

    LibraryAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);

            int screenWidth = ScreenUtils.getScreenWidth(context);
            int margin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
            int itemHeight = (screenWidth - 4 * margin) / 2;
            params = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, itemHeight);
        }
    }

    void setDataSource(List<LibraryInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_library, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        LibraryInfo libraryInfo = list.get(position);
        int resId = libraryInfo.getResId(); //图片资源id
        String name = libraryInfo.getName(); //名称

        holder.iv_icon.setImageResource(resId);
        holder.tv_name.setText(name);
        holder.item_ll.setLayoutParams(params);

        holder.item_ll.setOnClickListener(new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
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

    //静态内部类
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private LinearLayout item_ll; //布局

        public ItemViewHolder(View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.item_library_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_library_name);
            item_ll = (LinearLayout) itemView.findViewById(R.id.item_library_ll);
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(int position);
    }
}
