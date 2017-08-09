package com.zfsoftmh.ui.modules.office_affairs.questionnaire.join_questionnaire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireQuestionInfo;

import java.util.ArrayList;

/**
 * @author wesley
 * @date: 2017-6-19
 * @Description: 适配器
 */

class JoinQuestionnaireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_0 = 0; //0: 单选题
    private static final int TYPE_1 = 1; //1: 多选题
    private static final int TYPE_2 = 2; //2: 简答题
    private static final int TYPE_3 = 3; //3: 打分题
    private ArrayList<QuestionnaireQuestionInfo> list;
    private LayoutInflater inflater;
    private String short_answers; //简答题
    private String hit_the_titles; //打分题

    JoinQuestionnaireAdapter(Context context) {
        if (context != null) {
            inflater = LayoutInflater.from(context);

            short_answers = "(" + context.getResources().getString(R.string.short_answer) + ")";
            hit_the_titles = "(" + context.getResources().getString(R.string.hit_the_title) + ")";
        }
    }

    void setDataSource(ArrayList<QuestionnaireQuestionInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null) {
            return null;
        }

        View view;
        switch (viewType) {

        /*
         * 单选题
         */
        case TYPE_0:
            view = inflater.inflate(R.layout.item_preview_questionnaire_single_choice, parent, false);
            return new SingleChoiceViewHolder(view);

        /*
         * 多选题
         */
        case TYPE_1:
            view = inflater.inflate(R.layout.item_preview_questionnaire_muti_choice, parent, false);
            return new MutiChoiceViewHolder(view);

        /*
         * 简答题
         */
        case TYPE_2:
            view = inflater.inflate(R.layout.item_preview_questionnaire_short_answer, parent, false);
            return new ShortAnswerViewHolder(view);

        /*
         * 打分体
         */
        case TYPE_3:
            view = inflater.inflate(R.layout.item_preview_questionnaire_hit_the_title, parent, false);
            return new HitTheTitleViewHolder(view);

        default:
            break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (list == null || list.size() <= position || list.get(position) == null) {
            return;
        }

        int viewType = getItemViewType(position);
        switch (viewType) {

        /*
         * 单选题
         */
        case TYPE_0:
            break;

        /*
         * 多选题
         */
        case TYPE_1:
            break;

        /*
         * 简答题
         */
        case TYPE_2:
            if (holder == null || !(holder instanceof ShortAnswerViewHolder)) {
                return;
            }
            ShortAnswerViewHolder shortAnswerViewHolder = (ShortAnswerViewHolder) holder;
            QuestionnaireQuestionInfo short_answer = list.get(position);
            String title_short_answer = short_answer.getTitle(); //标题
            shortAnswerViewHolder.tv_title.setText(title_short_answer + short_answers);
            shortAnswerViewHolder.et_value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        list.get(position).setResult(s.toString());
                    }
                }
            });
            break;

        /*
         * 打分体
         */
        case TYPE_3:
            if (holder == null || !(holder instanceof HitTheTitleViewHolder)) {
                return;
            }
            HitTheTitleViewHolder hitTheTitleViewHolder = (HitTheTitleViewHolder) holder;
            QuestionnaireQuestionInfo hit_the_title = list.get(position);
            String hit_title = hit_the_title.getTitle(); //标题
            hitTheTitleViewHolder.tv_title.setText(hit_title + hit_the_titles);
            hitTheTitleViewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    list.get(position).setResult(String.valueOf(ratingBar.getRating()));
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
            try {
                return Integer.parseInt(list.get(position).getType());
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
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
