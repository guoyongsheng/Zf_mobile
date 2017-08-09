package com.zfsoftmh.ui.modules.office_affairs.questionnaire.home.un_published;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.QuestionnaireItemInfo;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: 未发布的适配器
 */

class UnPublishedAdapter extends RecyclerArrayAdapter<QuestionnaireItemInfo> {

    private OnItemClickListener listener;

    UnPublishedAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_questionnaire_un_published);
    }


    //静态内部类
    private class ItemViewHolder extends BaseViewHolder<QuestionnaireItemInfo> {

        private TextView tv_title; //标题
        private TextView tv_time; //时间
        private TextView tv_description; //描述
        private LinearLayout ll_item; //隐藏的布局
        private LinearLayout ll_edit; //编辑
        private LinearLayout ll_publish; //发布
        private LinearLayout ll_look; //查看
        private LinearLayout ll_delete; //删除

        public ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            tv_title = $(R.id.questionnaire_un_published_title);
            tv_time = $(R.id.questionnaire_un_published_time);
            tv_description = $(R.id.questionnaire_un_published_description);
            ll_item = $(R.id.questionnaire_un_published_ll);
            ll_edit = $(R.id.questionnaire_un_published_edit);
            ll_publish = $(R.id.questionnaire_un_published_publish);
            ll_look = $(R.id.questionnaire_un_published_look);
            ll_delete = $(R.id.questionnaire_un_published_delete);
        }

        @Override
        public void setData(final QuestionnaireItemInfo data) {
            super.setData(data);

            final long id = data.getId(); //id
            String title = data.getTitle(); //标题
            String time = data.getTime(); //时间
            String description = data.getDescription(); //描述
            int tag = data.getTag(); // 标志位

            tv_title.setText(title);
            tv_time.setText(time);
            tv_description.setText(description);

            switch (tag) {
            /*
             * 未点击
             */
            case 0:
                ll_item.setVisibility(View.GONE);
                break;

            /*
             * 点击了
             */
            case 1:
                ll_item.setVisibility(View.VISIBLE);
                ll_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemEdit(id);
                        }
                    }
                });

                ll_publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemPublished(id);
                        }
                    }
                });

                ll_look.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemLook(id);
                        }
                    }
                });

                ll_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemDelete(data);
                        }
                    }
                });
                break;

            default:
                break;
            }
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        /**
         * 刪除item
         *
         * @param info 要删除的对象
         */
        void onItemDelete(QuestionnaireItemInfo info);

        /**
         * 编辑item
         *
         * @param id id
         */
        void onItemEdit(long id);

        /**
         * 查看
         *
         * @param id id
         */
        void onItemLook(long id);

        /**
         * 发布
         *
         * @param id id
         */
        void onItemPublished(long id);
    }
}
