package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.published;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnairePublishedInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: 适配器
 */

class PublishedAdapter extends RecyclerArrayAdapter<QuestionnairePublishedInfo> {

    private OnItemClickListener listener;

    PublishedAdapter(Context context) {
        super(context);
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_questionnaire_published);
    }


    //静态内部类
    private class ItemViewHolder extends BaseViewHolder<QuestionnairePublishedInfo> {

        private TextView tv_title; //标题
        private TextView tv_description; //描述
        private TextView tv_time; //时间
        private TextView tv_status; //状态
        private LinearLayout ll_item; //隐藏的布局
        private LinearLayout ll_join; //参与
        private LinearLayout ll_look; //查看

        public ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            tv_title = $(R.id.questionnaire_published_title);
            tv_description = $(R.id.questionnaire_published_description);
            tv_time = $(R.id.questionnaire_published_time);
            ll_item = $(R.id.questionnaire_published_ll);
            ll_join = $(R.id.questionnaire_published_join);
            ll_look = $(R.id.questionnaire_published_look);
            tv_status = $(R.id.questionnaire_published_status);
        }

        @Override
        public void setData(final QuestionnairePublishedInfo data) {
            super.setData(data);

            String title = data.getPapermainname(); //标题
            String time = data.getCreatetimeStr(); //时间
            String description = data.getInstruction(); //描述
            int tag = data.getTag(); //标志位
            String startTime = data.getStarttimeStr(); //开始时间
            String endTime = data.getEndtimeStr(); //结束时间

            tv_title.setText(title);
            tv_time.setText(time);
            tv_description.setText(description);

            if (checkQuestionIsEnd(startTime, endTime)) {
                tv_status.setText(R.string.is_finished);
            } else {
                tv_status.setText(R.string.working);
            }

            switch (tag) {
            /*
             *  未点击
             */
            case 0:
                ll_item.setVisibility(View.GONE);
                break;

            /*
             * 点击了
             */
            case 1:
                ll_item.setVisibility(View.VISIBLE);
                ll_join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemJoin(data);
                        }
                    }
                });

                ll_look.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemLook(data);
                        }
                    }
                });
                break;

            default:
                break;
            }
        }
    }

    //判断问卷调查是否已经结束
    private boolean checkQuestionIsEnd(String startTime, String endTime) {

        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return true;
        }

        String pattern = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date dateStart = format.parse(startTime);
            Date dateEnd = format.parse(endTime);
            if (dateStart == null || dateEnd == null) {
                return true;
            }
            long current = System.currentTimeMillis();
            //long start = dateStart.getTime();
            long end = dateEnd.getTime();
            return end < current;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 自定义的回调接口
     */
    interface OnItemClickListener {

        /**
         * 参与
         *
         * @param info id
         */
        void onItemJoin(QuestionnairePublishedInfo info);

        /**
         * 查看
         *
         * @param info position
         */
        void onItemLook(QuestionnairePublishedInfo info);
    }


}
