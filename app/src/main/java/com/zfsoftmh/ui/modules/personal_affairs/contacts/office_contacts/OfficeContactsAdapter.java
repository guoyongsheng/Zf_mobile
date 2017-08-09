package com.zfsoftmh.ui.modules.personal_affairs.contacts.office_contacts;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.entity.OfficeContactsInfo;
import com.zfsoftmh.ui.widget.textdrawable.ColorGenerator;
import com.zfsoftmh.ui.widget.textdrawable.TextDrawable;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/11
 * @Description: 办公通讯录适配器
 */

class OfficeContactsAdapter extends ExpandableRecyclerAdapter<OfficeContactsInfo, ContactsItemInfo,
        ParentViewHolder, OfficeContactsAdapter.ChildContentViewHolder> {

    private static final int TYPE_0 = 0; //parent_header
    private static final int TYPE_1 = 1; //parent_content
    private static final int TYPE_2 = 2; //child_content

    private LayoutInflater inflater;
    private List<OfficeContactsInfo> parentList;

    private Context context;
    private ColorGenerator colorGenerator;
    private OnItemClickListener listener;


    private LinearLayout.LayoutParams params;

    public OfficeContactsAdapter(Context context, @NonNull List<OfficeContactsInfo> parentList) {
        super(parentList);
        this.parentList = parentList;
        this.context = context;
        if (context != null) {
            inflater = LayoutInflater.from(context);

            colorGenerator = ColorGenerator.MATERIAL;

            int size = context.getResources().getDimensionPixelSize(R.dimen.contacts_fragment_child_icon_size);
            params = new LinearLayout.LayoutParams(size, size);
            params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.common_margin_left);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public ParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {

        if (inflater == null) {
            return null;
        }

        View view;
        switch (viewType) {
        /*
         * parent_header
         */
        case TYPE_0:
            view = inflater.inflate(R.layout.item_parent_header_office_contacts, parentViewGroup, false);
            return new ParentHeaderViewHolder(view);

        /*
         * parent_content
         */
        case TYPE_1:
            view = inflater.inflate(R.layout.item_parent_content_office_contacts, parentViewGroup, false);
            return new ParentContentViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public ChildContentViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {

        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_contacts_child, childViewGroup, false);
        return new ChildContentViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentViewHolder parentViewHolder, int parentPosition,
            @NonNull OfficeContactsInfo parent) {

        int viewType = getParentViewType(parentPosition);
        switch (viewType) {
        /*
         *  parent_header
         */
        case TYPE_0:
            if (parentViewHolder instanceof ParentHeaderViewHolder) {
                ParentHeaderViewHolder headerViewHolder = (ParentHeaderViewHolder) parentViewHolder;
                headerViewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick();
                        }
                    }
                });
            }
            break;

        /*
         * parent_content
         */
        case TYPE_1:
            if (parentViewHolder instanceof ParentContentViewHolder) {
                ParentContentViewHolder contentViewHolder = (ParentContentViewHolder) parentViewHolder;
                String name = parent.getName();
                contentViewHolder.tv_name.setText(name);
            }
            break;


        default:
            break;
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildContentViewHolder childViewHolder, int parentPosition,
            int childPosition, @NonNull final ContactsItemInfo child) {

        if (colorGenerator == null || context == null) {
            return;
        }

        String name = child.getName(); //名称
        if (TextUtils.isEmpty(name)) {
            name = context.getResources().getString(R.string.no_name);
        }
        childViewHolder.tv_name.setText(name);
        int start = name.length() - 2;
        if (start < 0) {
            start = 0;
        }
        int end = name.length();
        childViewHolder.iv_icon.setLayoutParams(params);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(name.substring(start, end), colorGenerator.getColor(name));
        childViewHolder.iv_icon.setImageDrawable(drawable);
        childViewHolder.view_line.setVisibility(View.GONE);

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
            switch (parentPosition) {
            /*
             * parent_header
             */
            case 0:
                return TYPE_0;

            /*
             * parent_content
             */
            case 1:
                return TYPE_1;

            default:
                break;
            }
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

    //静态内部类---header
    private static class ParentHeaderViewHolder extends ParentViewHolder {

        private RelativeLayout rl_item; //布局

        ParentHeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            rl_item = (RelativeLayout) itemView.findViewById(R.id.item_parent_header_office_contacts);
        }
    }

    //静态内部类---content
    private static class ParentContentViewHolder extends ParentViewHolder {

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        private TextView tv_name; //名称
        private ImageView iv_arrow; //箭头

        ParentContentViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.item_parent_content_office_contacts_name);
            iv_arrow = (ImageView) itemView.findViewById(R.id.item_parent_content_office_arrow);
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

    //静态内部类---child
    static class ChildContentViewHolder extends ChildViewHolder {

        private LinearLayout ll_item; //布局
        private ImageView iv_icon; //图标
        private TextView tv_name; //名称
        private View view_line; //线

        ChildContentViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_item = (LinearLayout) itemView.findViewById(R.id.item_child_contacts);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_child_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_contacts_child_text);
            view_line = itemView.findViewById(R.id.item_child_line);
        }
    }


    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick();

        void onItemClick(ContactsItemInfo contactsItemInfo);
    }
}
