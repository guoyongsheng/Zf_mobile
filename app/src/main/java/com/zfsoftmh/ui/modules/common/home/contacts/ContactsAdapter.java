package com.zfsoftmh.ui.modules.common.home.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.ContactsInfo;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.ui.widget.textdrawable.ColorGenerator;
import com.zfsoftmh.ui.widget.textdrawable.TextDrawable;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/10
 * @Description: 通讯录的适配器
 */

class ContactsAdapter extends ExpandableRecyclerAdapter<ContactsInfo, ContactsItemInfo,
        ParentViewHolder, ContactsAdapter.ChildViewHolder> {

    private static final int TYPE_0 = 0; //不能展开
    private static final int TYPE_1 = 1; //可以展开
    private static final int TYPE_2 = 2; //展开后的类型

    private LayoutInflater inflater;
    private Context context;
    private List<ContactsInfo> parentList;
    private ColorGenerator colorGenerator;

    private OnItemClickListener listener;

    ContactsAdapter(Context context, List<ContactsInfo> parentList) {
        super(parentList);
        this.parentList = parentList;
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);
            colorGenerator = ColorGenerator.MATERIAL;
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public com.bignerdranch.expandablerecyclerview.ParentViewHolder onCreateParentViewHolder(@NonNull
            ViewGroup parentViewGroup, int viewType) {

        if (inflater == null) {
            return null;
        }

        View view;
        switch (viewType) {
        /*
         * 不展开
         */
        case TYPE_0:
            view = inflater.inflate(R.layout.item_contacts_parent_no_expand, parentViewGroup, false);
            return new ParentViewNoExpandedHolder(view);

        /*
         * 展开
         */
        case TYPE_1:
            view = inflater.inflate(R.layout.item_contacts_parent_expand, parentViewGroup, false);
            return new ParentViewHolder(view);

        default:
            break;
        }

        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public ChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        if (inflater == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.item_contacts_child, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull final com.bignerdranch.expandablerecyclerview.ParentViewHolder parentViewHolder, final int parentPosition,
            @NonNull ContactsInfo parent) {

        int imageId = parent.getImageId(); //图片资源id
        final String name = parent.getName(); //名称

        int viewType = getParentViewType(parentPosition);
        switch (viewType) {
        /*
         *  不能展开
         */
        case TYPE_0:
            if (parentViewHolder instanceof ParentViewNoExpandedHolder) {
                ParentViewNoExpandedHolder holder = (ParentViewNoExpandedHolder) parentViewHolder;
                holder.iv_icon.setImageResource(imageId);
                holder.tv_name.setText(name);
                holder.ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(parentPosition);
                        }
                    }
                });
            }
            break;


        /*
         * 可以展开
         */
        case TYPE_1:
            if (parentViewHolder instanceof ParentViewHolder) {
                ParentViewHolder holder = (ParentViewHolder) parentViewHolder;
                holder.iv_icon.setImageResource(imageId);
                holder.tv_name.setText(name);
            }
            break;

        default:
            break;
        }

    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildViewHolder childViewHolder, int parentPosition,
            int childPosition, @NonNull final ContactsItemInfo child) {

        if (context == null || colorGenerator == null) {
            return;
        }

        String name = child.getName();
        if (TextUtils.isEmpty(name)) {
            name = context.getResources().getString(R.string.no_name);
        }

        childViewHolder.tv_name.setText(name);
        int start = name.length() - 2;
        if (start < 0) {
            start = 0;
        }
        int end = name.length();
        TextDrawable drawable = TextDrawable.builder().buildRound(name.substring(start, end), colorGenerator.getColor(name));
        childViewHolder.iv_icon.setImageDrawable(drawable);

        childViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(child);
                }
            }
        });
    }

    @Override
    public int getParentViewType(int parentPosition) {
        if (parentList != null && parentList.size() > parentPosition) {
            return parentList.get(parentPosition).getType();
        }
        return super.getParentViewType(parentPosition);
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == TYPE_0 || viewType == TYPE_1;
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return TYPE_2;
    }

    //静态内部类---可以展开的
    private static class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder {

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        private ImageView iv_icon; // 图标
        private TextView tv_name; //名称
        private ImageView iv_arrow; //指示箭头

        ParentViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.contacts_parent_expanded_icon);
            tv_name = (TextView) itemView.findViewById(R.id.contacts_parent_expanded_text);
            iv_arrow = (ImageView) itemView.findViewById(R.id.contacts_parent_expanded_arrow);
        }

        @SuppressLint("ObsoleteSdkInt")
        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (expanded) {
                    iv_arrow.setRotation(ROTATED_POSITION);
                } else {
                    iv_arrow.setRotation(INITIAL_POSITION);
                }
            }
        }

        @SuppressLint("ObsoleteSdkInt")
        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                RotateAnimation rotateAnimation;
                if (expanded) { // rotate clockwise
                    rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else { // rotate counterclockwise
                    rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                iv_arrow.startAnimation(rotateAnimation);
            }
        }

    }

    //静态内部类---不能展开的
    private static class ParentViewNoExpandedHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder {

        private LinearLayout ll_item; //布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称

        ParentViewNoExpandedHolder(@NonNull View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.contacts_parent_icon);
            tv_name = (TextView) itemView.findViewById(R.id.contacts_parent_text);
            ll_item = (LinearLayout) itemView.findViewById(R.id.contacts_item);
        }
    }

    //静态内部类
    static class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ChildViewHolder {

        private LinearLayout ll_item; //布局
        private TextView tv_name; //名称
        private ImageView iv_icon; //图标

        ChildViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.item_contacts_child_text);
            ll_item = (LinearLayout) itemView.findViewById(R.id.item_child_contacts);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_child_icon);
        }
    }

    /**
     * 自定义接口回调
     */
    interface OnItemClickListener {

        void onItemClick(int position);


        void onItemClick(ContactsItemInfo contactsItemInfo);
    }
}
