package com.zfsoftmh.ui.modules.office_affairs.cloud_notes;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.entity.CloudNoteListInfo;
import com.zfsoftmh.ui.widget.OnceClickListener;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description: 云笔记列表
 */

public class CloudNoteListAdapter extends RecyclerArrayAdapter<CloudNoteListInfo> {
    private OnItemListener listener;
    private boolean show = false;

    public CloudNoteListAdapter(Context context) {
        super(context);

    }


    // 获取标签选中ID
    public void setShowIcon(Boolean showIcon) {
        this.show = showIcon;
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_cloud_note_list);
    }


    private class ItemViewHolder extends BaseViewHolder<CloudNoteListInfo> {
        private TextView note_content_tv, note_time_tv; // 笔记标题
        private CheckBox checkBox; // 笔记复选框
        private ImageView text_iv, photo_iv; // 笔记图文标记
        private View tag_view; // 标签颜色视图
        private LinearLayout item_layout;

        public ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            note_content_tv = $(R.id.note_content_tv);
            note_time_tv = $(R.id.note_time_tv);
            checkBox = $(R.id.selected_iv);
            text_iv = $(R.id.text_iv);
            photo_iv = $(R.id.photo_iv);
            tag_view = $(R.id.tag_view);
            item_layout = $(R.id.item_layout);
        }

        @Override
        public void setData(final CloudNoteListInfo data) {
            super.setData(data);

            String memoTitle = data.memoTitle; // 笔记标题
            String createTime = data.createTime; // 创建时间
            String contentFlag = data.contentFlag; // 图文标志
            String catalogColor = data.catalogColor; // 标签颜色值

            if (memoTitle != null)
                note_content_tv.setText(memoTitle);
            if (createTime != null)
                note_time_tv.setText(createTime.substring(0, 10));
            if (catalogColor != null)
                if (CloudNoteUtils.checkColorValue(catalogColor))
                    tag_view.setBackgroundColor(CloudNoteUtils.getColorValue(catalogColor));

            if (contentFlag.equals("1")) { // 文本
                if (photo_iv.getVisibility() == View.VISIBLE) {
                    photo_iv.setVisibility(View.GONE);
                }
                text_iv.setVisibility(View.VISIBLE);
            } else if (contentFlag.equals("2")) { // 图片
                if (text_iv.getVisibility() == View.VISIBLE) {
                    text_iv.setVisibility(View.GONE);
                }
                photo_iv.setVisibility(View.VISIBLE);
            } else if (contentFlag.equals("3")) { // 图文
                text_iv.setVisibility(View.VISIBLE);
                photo_iv.setVisibility(View.VISIBLE);
            }

            if (show) {
                if (checkBox.getVisibility() == View.INVISIBLE) {
                    checkBox.setVisibility(View.VISIBLE);
                }
                item_layout.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onOnceClick(View v) {
                            if (checkBox.isChecked()) {
                                checkBox.setChecked(false);
                                data.isSelect = false;
                            } else {
                                checkBox.setChecked(true);
                                data.isSelect = true;
                            }
                    }
                });
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            data.isSelect = isChecked;
                    }
                });
            } else {
                if (checkBox.getVisibility() == View.VISIBLE) {
                    checkBox.setVisibility(View.INVISIBLE);
                }
                data.isSelect = false;
                item_layout.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onOnceClick(View v) {
                        if (listener != null) {
                            listener.onClickItem(getPosition(), data);
                        }
                    }
                });

                checkBox.setOnCheckedChangeListener(null);
            }
            checkBox.setChecked(data.isSelect);
        }
    }


    public interface OnItemListener {
        /**
         * 行布局点击事件
         *
         * @param position
         * @param bean
         */
        void onClickItem(int position, CloudNoteListInfo bean);
    }


    public void setOnItemListener(OnItemListener onItemListener) {
        this.listener = onItemListener;
    }

}
