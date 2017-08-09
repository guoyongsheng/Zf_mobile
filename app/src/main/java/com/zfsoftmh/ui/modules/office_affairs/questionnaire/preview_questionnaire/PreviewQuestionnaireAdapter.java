package com.zfsoftmh.ui.modules.office_affairs.questionnaire.preview_questionnaire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireDetailInfo;
import com.zfsoftmh.entity.QuestionnaireItemInfo;

import io.realm.RealmList;

/**
 * @author wesley
 * @date: 2017-6-7
 * @Description: 适配器
 */

class PreviewQuestionnaireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_0 = 0; // 标题的布局
    private static final int TYPE_1 = 1; //简答题
    private static final int TYPE_2 = 2; //打分题
    private static final int TYPE_3 = 3; //单选题
    private static final int TYPE_4 = 4; //多选题
    private LayoutInflater inflater;
    private RealmList<QuestionnaireDetailInfo> list;
    private QuestionnaireItemInfo info;
    private String short_answers; //简答题
    private String hit_the_titles; //打分题

    PreviewQuestionnaireAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);

            short_answers = "(" + context.getResources().getString(R.string.short_answer) + ")";
            hit_the_titles = "(" + context.getResources().getString(R.string.hit_the_title) + ")";
        }
    }

    public void setDataSource(QuestionnaireItemInfo info) {
        this.info = info;
        if (info != null) {
            list = info.getList();
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }

        View view;
        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_0:
            view = inflater.inflate(R.layout.item_edit_questionnaire_header, parent, false);
            return new HeaderViewHolder(view);

        /*
         * 简答题
         */
        case TYPE_1:
            view = inflater.inflate(R.layout.item_preview_questionnaire_short_answer, parent, false);
            return new ShortAnswerViewHolder(view);

        /*
         * 打分题
         */
        case TYPE_2:
            view = inflater.inflate(R.layout.item_preview_questionnaire_hit_the_title, parent, false);
            return new HitTheTitleViewHolder(view);

        /*
         * 单选题
         */
        case TYPE_3:
            view = inflater.inflate(R.layout.item_preview_questionnaire_single_choice, parent, false);
            return new SingleChoiceViewHolder(view);

        /*
         * 多选题
         */
        case TYPE_4:
            view = inflater.inflate(R.layout.item_preview_questionnaire_muti_choice, parent, false);
            return new MutiChoiceViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder == null) {
            return;
        }

        int viewType = getItemViewType(position);
        switch (viewType) {
        /*
         * 头部
         */
        case TYPE_0:
            if (info == null || !(holder instanceof HeaderViewHolder)) {
                return;
            }
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.tv_title.setText(info.getTitle());
            break;

        /*
         * 简答题
         */
        case TYPE_1:
            int newPosition_short_answer = position - 1;
            if (list == null || list.size() <= newPosition_short_answer || !(holder instanceof ShortAnswerViewHolder)
                    || list.get(newPosition_short_answer) == null || newPosition_short_answer < 0) {
                return;
            }
            ShortAnswerViewHolder shortAnswerViewHolder = (ShortAnswerViewHolder) holder;
            QuestionnaireDetailInfo short_answer = list.get(newPosition_short_answer);
            String title_short_answer = short_answer.getTitle(); //标题
            shortAnswerViewHolder.tv_title.setText(title_short_answer + short_answers);
            break;

        /*
         * 打分题
         */
        case TYPE_2:
            int newPosition_hit = position - 1;
            if (list == null || list.size() <= newPosition_hit || !(holder instanceof HitTheTitleViewHolder)
                    || list.get(newPosition_hit) == null || newPosition_hit < 0) {
                return;
            }
            HitTheTitleViewHolder hitTheTitleViewHolder = (HitTheTitleViewHolder) holder;
            QuestionnaireDetailInfo hit_the_title = list.get(newPosition_hit);
            String hit_title = hit_the_title.getTitle(); //标题
            hitTheTitleViewHolder.tv_title.setText(hit_title + hit_the_titles);
            break;

        /*
         * 单选题
         */
        case TYPE_3:
            break;

        /*
         * 多选题
         */
        case TYPE_4:
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
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_0;
        }
        int newPosition = position - 1;
        if (list != null && list.size() > newPosition && list.get(newPosition) != null && newPosition >= 0) {
            int type = list.get(newPosition).getType();
            switch (type) {
            /*
             * 单选
             */
            case 0:
                return TYPE_3;

            /*
             * 多选
             */
            case 1:
                return TYPE_4;

            /*
             * 简答题
             */
            case 2:
                return TYPE_1;

            /*
             * 打分
             */
            case 3:
                return TYPE_2;

            default:
                break;
            }
        }
        return super.getItemViewType(position);
    }


    //头部
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title; //标题

        HeaderViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_questionnaire_head_title);
        }
    }


    //简答题
    private static class ShortAnswerViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title; //标题
        private EditText et_value; //值

        ShortAnswerViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_preview_questionnaire_title);
            et_value = (EditText) itemView.findViewById(R.id.item_preview_questionnaire_et);
        }
    }

    //打分题
    private static class HitTheTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title; //标题
        private RatingBar ratingBar;

        HitTheTitleViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.item_preview_questionnaire__hit_the_title);
            ratingBar = (RatingBar) itemView.findViewById(R.id.item_preview_questionnaire_rating);
        }
    }

    //单选题
    private static class SingleChoiceViewHolder extends RecyclerView.ViewHolder {

        SingleChoiceViewHolder(View itemView) {
            super(itemView);
        }
    }

    //多选题
    private static class MutiChoiceViewHolder extends RecyclerView.ViewHolder {

        MutiChoiceViewHolder(View itemView) {
            super(itemView);
        }
    }

}
