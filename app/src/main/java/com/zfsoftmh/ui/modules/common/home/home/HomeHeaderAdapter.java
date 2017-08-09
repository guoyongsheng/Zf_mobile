package com.zfsoftmh.ui.modules.common.home.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.AppCenterItemInfo;

import java.util.ArrayList;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 适配器
 */

class HomeHeaderAdapter extends RecyclerView.Adapter<HomeHeaderAdapter.ItemViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AppCenterItemInfo> list;
    private boolean isLogin; //用户是否登录
    private OnItemClickListener listener;
    private String more;
    private RecyclerView.LayoutParams params;

    HomeHeaderAdapter(Context context, boolean isLogin, int itemHeight) {
        this.context = context;
        this.isLogin = isLogin;
        if (context != null) {
            inflater = LayoutInflater.from(context);

            more = context.getResources().getString(R.string.more);

            params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, itemHeight);
        }
    }

    void setDataSource(ArrayList<AppCenterItemInfo> list) {
        if (list != null) {
            this.list = list;
            if (isLogin) {
                AppCenterItemInfo itemInfo = new AppCenterItemInfo();
                itemInfo.setName(more);
                this.list.add(itemInfo);
            }
            notifyDataSetChanged();
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_app_center_content, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null || context == null) {
            return;
        }

        final AppCenterItemInfo itemInfo = list.get(position);
        String url = itemInfo.getIcon(); // 图标
        String name = itemInfo.getName(); //名称
        holder.tv_name.setText(name);

        if (isLogin && position == list.size() - 1) {
            holder.iv_icon.setImageResource(R.mipmap.ic_icon_more);
        } else {
            ImageLoaderHelper.loadImage(context, holder.iv_icon, url);
        }

        holder.item_rl.setLayoutParams(params);
        holder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (isLogin) {
                        if (position == list.size() - 1) {
                            listener.onItemClick(take(list));
                        } else {
                            listener.onItemClick(itemInfo);
                        }
                    } else {
                        listener.onItemClick(itemInfo);
                    }
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
        private RelativeLayout item_rl; //布局

        ItemViewHolder(View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.fragment_app_center_icon);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_app_center_content);
            item_rl = (RelativeLayout) itemView.findViewById(R.id.fragment_app_center_item);
        }
    }

    //截取前面7个
    private ArrayList<AppCenterItemInfo> take(ArrayList<AppCenterItemInfo> list) {

        ArrayList<AppCenterItemInfo> itemList = new ArrayList<>();
        if (list == null) {
            return itemList;
        }

        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            AppCenterItemInfo info = list.get(i);
            if (info != null) {
                itemList.add(info);
            }
        }
        return itemList;
    }


    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(AppCenterItemInfo itemInfo);


        void onItemClick(ArrayList<AppCenterItemInfo> list);
    }
}
