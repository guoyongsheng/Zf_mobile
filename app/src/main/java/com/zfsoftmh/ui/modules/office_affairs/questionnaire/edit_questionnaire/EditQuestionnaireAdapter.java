package com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireDetailInfo;
import com.zfsoftmh.entity.QuestionnaireItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: 编辑问卷调查的适配器
 */

class EditQuestionnaireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;

    private LayoutInflater inflater;
    private QuestionnaireItemInfo itemInfo;
    private OnItemClickListener listener;
    private List<QuestionnaireDetailInfo> list;

    private String single_choice; //单选题
    private String muli_choice; // 多选题
    private String short_answer; //简答题
    private String hit_the_title; //打分题

    EditQuestionnaireAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);

            single_choice = context.getResources().getString(R.string.single_choice);
            muli_choice = context.getResources().getString(R.string.muti_choice);
            short_answer = context.getResources().getString(R.string.short_answer);
            hit_the_title = context.getResources().getString(R.string.hit_the_title);
        }
    }


    public void setDataSource(QuestionnaireItemInfo itemInfo) {
        this.itemInfo = itemInfo;
        if (itemInfo != null) {
            this.list = new ArrayList<>(itemInfo.getList());
        }
        notifyDataSetChanged();
    }

    public void setDataSource(QuestionnaireItemInfo itemInfo, List<QuestionnaireDetailInfo> list) {
        this.itemInfo = itemInfo;
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }

        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_0:
            View headerView = inflater.inflate(R.layout.item_edit_questionnaire_header, parent, false);
            return new HeaderViewHolder(headerView);

        /*
         * 内容
         */
        case TYPE_1:
            View itemView = inflater.inflate(R.layout.item_edit_questionnaire, parent, false);
            return new ItemViewHolder(itemView);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null || itemInfo == null) {
            return;
        }

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_0:
            if (!(holder instanceof HeaderViewHolder)) {
                return;
            }
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            String title = itemInfo.getTitle(); //标题
            headerViewHolder.tv_title.setText(title);
            break;

        /*
         * 内容
         */
        case TYPE_1:
            int newPosition = position - 1;
            if (!(holder instanceof ItemViewHolder) || list == null || list.size() <= newPosition
                    || list.get(newPosition) == null || newPosition < 0) {
                return;
            }
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            QuestionnaireDetailInfo info = list.get(newPosition);
            String titles = info.getTitle(); //标题
            int type = info.getType(); //类型
            int tag = info.getTag();
            itemViewHolder.tv_title.setText(titles);
            if (type == 0) {
                itemViewHolder.tv_type.setText(single_choice);
            } else if (type == 1) {
                itemViewHolder.tv_type.setText(muli_choice);
            } else if (type == 2) {
                itemViewHolder.tv_type.setText(short_answer);
            } else if (type == 3) {
                itemViewHolder.tv_type.setText(hit_the_title);
            }

            if (tag == 0) {
                itemViewHolder.ll_edit.setVisibility(View.GONE);
            } else if (tag == 1) {
                itemViewHolder.ll_edit.setVisibility(View.VISIBLE);
                itemViewHolder.ll_join.setOnClickListener(new OnViewClickListener(newPosition));
                itemViewHolder.ll_delete.setOnClickListener(new OnViewClickListener(newPosition));
            }
            itemViewHolder.ll_item.setOnClickListener(new OnViewClickListener(newPosition));
            break;


        default:
            break;
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size() + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_0;
        }
        return TYPE_1;
    }

    public QuestionnaireItemInfo getItemInfo() {
        return itemInfo;
    }

    List<QuestionnaireDetailInfo> getDetailItemInfo() {
        return list;
    }

    //静态内部类
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title; //标题

        HeaderViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_questionnaire_head_title);
        }
    }

    //静态内部类
    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title; //标题
        private TextView tv_type; //类型
        private LinearLayout ll_edit; //编辑的布局
        private LinearLayout ll_join; //参与的布局
        private LinearLayout ll_delete; //删除的布局
        private LinearLayout ll_item; //整个布局

        public ItemViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_edit_questionnaire_title);
            tv_type = (TextView) itemView.findViewById(R.id.item_edit_questionnaire_type);
            ll_edit = (LinearLayout) itemView.findViewById(R.id.item_edit_ll);
            ll_join = (LinearLayout) itemView.findViewById(R.id.item_edit_join);
            ll_delete = (LinearLayout) itemView.findViewById(R.id.item_edit_delete);
            ll_item = (LinearLayout) itemView.findViewById(R.id.item_ll);
        }
    }


    //内部类
    private class OnViewClickListener implements View.OnClickListener {

        private int position;

        public OnViewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (view == null || listener == null) {
                return;
            }

            int key = view.getId();
            switch (key) {
            /*
             * 编辑
             */
            case R.id.item_edit_join:
                listener.onItemJoin(position);
                break;

            /*
             * 删除
             */
            case R.id.item_edit_delete:
                listener.onItemDelete(position);
                break;

            /*
             * 整个布局
             */
            case R.id.item_ll:
                listener.onItemClicked(position);
                break;

            default:
                break;
            }
        }
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {


        void onItemClicked(int position);


        void onItemDelete(int position);


        void onItemJoin(int position);
    }
}
