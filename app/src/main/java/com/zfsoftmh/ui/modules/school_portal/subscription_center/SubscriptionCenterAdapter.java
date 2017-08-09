package com.zfsoftmh.ui.modules.school_portal.subscription_center;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.AppCenterItemInfo;
import com.zfsoftmh.ui.widget.itemtouchhelper.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/17
 * @Description: 适配器
 */

class SubscriptionCenterAdapter extends Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {

    final static int TYPE_SELECTED_HEADER = 0; //已选应用的头部
    private final static int TYPE_SELECTED = 1; //已选应用
    final static int TYPE_OTHER_HEADER = 2; //其他应用的头部
    private final static int TYPE_OTHER = 3; //其他应用
    private Context context;
    private LayoutInflater inflater;
    private ItemTouchHelper itemTouchHelper;
    private ArrayList<AppCenterItemInfo> listSelected; //已选应用
    private ArrayList<AppCenterItemInfo> listOther;
    private ArrayList<AppCenterItemInfo> list = new ArrayList<>();
    private OnMenuItemTitleChangeListener listener;
    private OnItemClickListener onItemClickListener;
    private boolean isEditMode; //是否是编辑模式
    private long startTime;
    private static final long SPACE_TIME = 100;

    SubscriptionCenterAdapter(Context context, ItemTouchHelper itemTouchHelper, ArrayList<AppCenterItemInfo> list) {
        this.context = context;
        this.itemTouchHelper = itemTouchHelper;
        this.listSelected = list;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }

        initHeaderSelected();
        initSelected();
    }

    //初始化已选应用的头部
    private void initHeaderSelected() {
        AppCenterItemInfo itemInfo = new AppCenterItemInfo();
        itemInfo.setLocalType(0);
        list.add(itemInfo);
    }

    //初始化已选应用
    private void initSelected() {
        if (listSelected != null) {
            int size = listSelected.size();
            for (int i = 0; i < size; i++) {
                AppCenterItemInfo itemInfo = listSelected.get(i);
                if (itemInfo != null) {
                    itemInfo.setLocalType(1);
                    list.add(itemInfo);
                }
            }
        }
    }

    //初始化其他应用
    private void initOther() {
        if (listOther != null && list != null) {
            list.addAll(listOther);
        }
    }

    //重新设值
    private void reset() {
        if (list != null) {
            list.clear();
            initHeaderSelected();
            initSelected();
            initOther();
            notifyDataSetChanged();
        }
    }

    //判断当前点击的item是否已经选中
    private boolean itemIsChecked(String id) {
        if (listSelected != null) {
            int size = listSelected.size();
            for (int i = 0; i < size; i++) {
                AppCenterItemInfo info = listSelected.get(i);
                if (info != null) {
                    String itemId = info.getId();
                    if (itemId != null && itemId.equals(id)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //判断是否已经添加了7个应用
    private boolean checkServiceNumberIsMoreThanSeven() {
        if (listSelected != null) {
            int size = listSelected.size();
            if (size >= 7) {
                return true;
            }
        }
        return false;
    }

    //添加item
    private void insertItem(AppCenterItemInfo itemInfo) {
        if (listSelected != null && itemInfo != null) {
            AppCenterItemInfo info = new AppCenterItemInfo();
            info.setId(itemInfo.getId());
            info.setName(itemInfo.getName());
            info.setIcon(itemInfo.getIcon());
            info.setServiceCode(itemInfo.getServiceCode());
            listSelected.add(info);
            reset();
        }
    }

    //刪除item
    private void removeItem(AppCenterItemInfo itemInfo) {
        if (listSelected != null && itemInfo != null) {
            listSelected.remove(itemInfo);
            reset();
        }
    }

    void setOnMenuItemTitleChangeListener(OnMenuItemTitleChangeListener listener) {
        this.listener = listener;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    void setListOther(ArrayList<AppCenterItemInfo> listOther) {
        if (listOther != null) {
            this.listOther = listOther;
            list.addAll(listOther);
            notifyDataSetChanged();
        }
    }

    void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    boolean isEditMode() {
        return isEditMode;
    }

    boolean checkHasSelectService() {
        return listSelected != null && listSelected.size() > 0;
    }

    ArrayList<AppCenterItemInfo> getSelectedService() {
        return listSelected;
    }

    List<String> getServiceCode() {
        List<String> list = new ArrayList<>();
        if (listSelected != null) {
            int size = listSelected.size();
            for (int i = 0; i < size; i++) {
                AppCenterItemInfo itemInfo = listSelected.get(i);
                if (itemInfo != null) {
                    String serviceCode = itemInfo.getServiceCode();
                    list.add(serviceCode);
                }
            }
        }
        return list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }

        View view;
        switch (viewType) {
        /*
         * 已选应用的头部
         */
        case TYPE_SELECTED_HEADER:
            view = inflater.inflate(R.layout.item_subscription_center_selected_header, parent, false);
            return new SelectedHeaderViewHolder(view);

        /*
         * 已选应用
         */
        case TYPE_SELECTED:
            view = inflater.inflate(R.layout.item_subscription_center_selected, parent, false);
            return new SelectViewHolder(view);

        /*
         * 其他应用的头部
         */
        case TYPE_OTHER_HEADER:
            view = inflater.inflate(R.layout.item_subcription_center_more_header, parent, false);
            return new OtherHeaderViewHolder(view);

        /*
         * 其他应用
         */
        case TYPE_OTHER:
            view = inflater.inflate(R.layout.item_subcription_center_more_item, parent, false);
            return new OtherViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null || list == null || list.size() <= position || list.get(position) == null
                || context == null) {
            return;
        }

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         * 已选应用的头部
         */
        case TYPE_SELECTED_HEADER:
            if (!(holder instanceof SelectedHeaderViewHolder)) {
                return;
            }
            SelectedHeaderViewHolder selectedHeaderViewHolder = (SelectedHeaderViewHolder) holder;
            if (isEditMode) {
                selectedHeaderViewHolder.tv_value.setVisibility(View.VISIBLE);
            } else {
                selectedHeaderViewHolder.tv_value.setVisibility(View.GONE);
            }
            break;

        /*
         * 已选应用
         */
        case TYPE_SELECTED:
            if (!(holder instanceof SelectViewHolder) || itemTouchHelper == null) {
                return;
            }
            final SelectViewHolder selectViewHolder = (SelectViewHolder) holder;
            final AppCenterItemInfo itemInfo = list.get(position);
            String icon = itemInfo.getIcon(); //图标
            String name = itemInfo.getName(); //名称
            selectViewHolder.tv_name.setText(name);
            ImageLoaderHelper.loadImage(context, selectViewHolder.iv_icon, icon);

            if (isEditMode) {
                selectViewHolder.iv_status_icon.setVisibility(View.VISIBLE);
                selectViewHolder.rl_item.setBackgroundResource(R.drawable.drawable_feed_back);
            } else {
                selectViewHolder.iv_status_icon.setVisibility(View.GONE);
                selectViewHolder.rl_item.setBackgroundResource(R.drawable.drawable_subscription_center_no_selected);
            }
            selectViewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditMode) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(itemInfo);
                        }
                        return;
                    }
                    removeItem(itemInfo);
                }
            });

            selectViewHolder.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isEditMode && listener != null) {
                        setEditMode(true);
                        listener.onMenuItemTitleChange(context.getResources().getString(R.string.done));
                        notifyDataSetChanged();
                    }
                    itemTouchHelper.startDrag(selectViewHolder);
                    return true;
                }
            });

            selectViewHolder.rl_item.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isEditMode) {
                        switch (MotionEventCompat.getActionMasked(event)) {
                        case MotionEvent.ACTION_DOWN:
                            startTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (System.currentTimeMillis() - startTime > SPACE_TIME && itemTouchHelper != null) {
                                itemTouchHelper.startDrag(selectViewHolder);
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            startTime = 0;
                            break;
                        }
                    }
                    return false;
                }
            });
            break;

        /*
         * 其他应用的头部
         */
        case TYPE_OTHER_HEADER:
            if (!(holder instanceof OtherHeaderViewHolder)) {
                return;
            }
            OtherHeaderViewHolder otherHeaderViewHolder = (OtherHeaderViewHolder) holder;
            AppCenterItemInfo info = list.get(position);
            String otherHeaderName = info.getLocalName(); //名称
            otherHeaderViewHolder.tv_value.setText(otherHeaderName);
            break;

        /*
         * 其他应用
         */
        case TYPE_OTHER:
            if (!(holder instanceof OtherViewHolder)) {
                return;
            }
            OtherViewHolder otherViewHolder = (OtherViewHolder) holder;
            final AppCenterItemInfo otherItemInfo = list.get(position);
            String id = otherItemInfo.getId(); //id
            String otherIcon = otherItemInfo.getIcon(); //图标
            String otherName = otherItemInfo.getName(); //名称
            otherViewHolder.tv_name.setText(otherName);
            final boolean isChecked = itemIsChecked(id);
            ImageLoaderHelper.loadImage(context, otherViewHolder.iv_icon, otherIcon);
            if (isEditMode) {
                otherViewHolder.iv_status_icon.setVisibility(View.VISIBLE);
                otherViewHolder.rl_item.setBackgroundResource(R.drawable.drawable_feed_back);
                if (isChecked) {
                    otherViewHolder.iv_status_icon.setImageResource(R.mipmap.ic_icon_checked);
                } else {
                    otherViewHolder.iv_status_icon.setImageResource(R.mipmap.ic_icon_add);
                }
            } else {
                otherViewHolder.iv_status_icon.setVisibility(View.GONE);
                otherViewHolder.rl_item.setBackgroundResource(R.drawable.drawable_subscription_center_no_selected);
            }

            otherViewHolder.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isEditMode && listener != null) {
                        setEditMode(true);
                        listener.onMenuItemTitleChange(context.getResources().getString(R.string.done));
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });

            otherViewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditMode) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(otherItemInfo);
                        }
                        return;
                    }

                    if (isChecked) {
                        return;
                    }

                    if (checkServiceNumberIsMoreThanSeven()) {
                        Toast.makeText(context, context.getResources().
                                getString(R.string.most_to_select_seven), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    insertItem(otherItemInfo);
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
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null && list.size() > position && list.get(position) != null) {
            return list.get(position).getLocalType();
        }
        return -1;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        int position = fromPosition - 1;
        if (listSelected != null && listSelected.size() > position && listSelected.get(position) != null) {
            AppCenterItemInfo itemInfo = listSelected.get(position);
            listSelected.remove(position);
            if (listSelected.size() >= (toPosition - 1)) {
                listSelected.add(toPosition - 1, itemInfo);
                notifyItemMoved(fromPosition, toPosition);
            }
        }
    }

    //已选应用头部的ViewHolder
    private static class SelectedHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_value;

        SelectedHeaderViewHolder(View itemView) {
            super(itemView);

            tv_value = (TextView) itemView.findViewById(R.id.item_header_selected);
        }
    }

    //已选应用的ViewHolder
    private static class SelectViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //整个布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private ImageView iv_status_icon; //状态图标

        SelectViewHolder(View itemView) {
            super(itemView);

            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_subscription_center_select_rl);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_subscription_center_select_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_subscription_center_select_name);
            iv_status_icon = (ImageView) itemView.findViewById(R.id.item_subscription_center_select_ic_icon);
        }
    }

    //其他应用头部的ViewHolder
    private static class OtherHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_value; //其他应用的头部的值

        OtherHeaderViewHolder(View itemView) {
            super(itemView);
            tv_value = (TextView) itemView.findViewById(R.id.item_subscription_center_other_header_value);
        }
    }

    //其他应用的ViewHolder
    private static class OtherViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_item; //布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private ImageView iv_status_icon; //状态图标

        OtherViewHolder(View itemView) {
            super(itemView);

            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_subscription_center_other_rl);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_subscription_center_other_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_subscription_center_other_name);
            iv_status_icon = (ImageView) itemView.findViewById(R.id.item_subscription_center_other_ic_icon);
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnMenuItemTitleChangeListener {

        void onMenuItemTitleChange(String title);
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(AppCenterItemInfo itemInfo);
    }
}
