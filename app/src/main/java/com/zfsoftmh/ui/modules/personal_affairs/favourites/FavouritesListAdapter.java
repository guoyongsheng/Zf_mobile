package com.zfsoftmh.ui.modules.personal_affairs.favourites;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.FavouritesListInfo;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description: 我的收藏列表
 */

public class FavouritesListAdapter extends RecyclerArrayAdapter<FavouritesListInfo> {
    private static final int TYPE_TEXT = 1; // 文本
    private static final int TYPE_IMAGE = 2; // 图片
    private static final int TYPE_VIDEO = 3; // 视频
    private static final int TYPE_WEBSITE = 4; // 网址
    private static final int TYPE_ATTACHMENT = 5; // 附件
    private Context context;


    public FavouritesListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void remove(FavouritesListInfo object) {
        super.remove(object);
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position).favouritesort != null) {
            int sort = Integer.valueOf(getItem(position).favouritesort);
            switch (sort) {
                case 1: // 文本
                    return TYPE_TEXT;
                case 2: // 图片
                    return TYPE_IMAGE;
                case 3: // 视频
                    return TYPE_VIDEO;
                case 4: // 网页
                    return TYPE_WEBSITE;
                case 5: // 附件
                    return TYPE_ATTACHMENT;
            }
        }
        return -1;
    }

    @Override
    public FavouritesListInfo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            /**
             * 文本
             */
            case TYPE_TEXT:
                return new TextViewHolder(parent, R.layout.item_favourites_text_view);

            /**
             * 图片
             */
            case TYPE_IMAGE:
                return new ImageViewHolder(parent, R.layout.item_favourites_image_or_video_view);

            /**
             * 视频
             */
            case TYPE_VIDEO:
                return new ImageViewHolder(parent, R.layout.item_favourites_image_or_video_view);

            /**
             * 网页
             */
            case TYPE_WEBSITE:
                return new WebsiteViewHolder(parent, R.layout.item_favourites_website_view);

            /**
             * 附件
             */
            case TYPE_ATTACHMENT:
                return new AttachmentViewHolder(parent, R.layout.item_favourites_attachment_view);
        }
        return null;
    }


    // 只有文本的ViewHolder
    private class TextViewHolder extends BaseViewHolder<FavouritesListInfo> {
        private CircleImageView favourites_avatar; // 收藏人头像
        private TextView favourites_custom_tv, favourites_date_tv; // 收藏人名，收藏日期
        private TextView favourites_text_content_tv; // 收藏文本内容
        private LinearLayout item_text_layout; // 行布局

        public TextViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            favourites_avatar = $(R.id.favourites_avatar);
            favourites_custom_tv = $(R.id.favourites_custom_tv);
            favourites_date_tv = $(R.id.favourites_date_tv);
            favourites_text_content_tv = $(R.id.favourites_text_content_tv);
            item_text_layout = $(R.id.item_text_layout);
        }

        public void setData(final FavouritesListInfo data) {
            super.setData(data);

            String avatar = data.favouriteavatar;
            String custom = data.favouritecustom;
            String date = data.favouritedateStr;
            String content = data.favouritecontent;

            if (avatar != null)
                ImageLoaderHelper.loadImage(context, favourites_avatar, avatar);
            if (custom != null)
                favourites_custom_tv.setText(custom);
            if (date != null)
                favourites_date_tv.setText(date);
            if (content != null)
                favourites_text_content_tv.setText(content);

            // 长按监听
            if (longListener != null) {
                item_text_layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        longListener.onItemLongListener(item_text_layout, data);
                        return false;
                    }
                });
            }
        }
    }

    // 图片或者视频ViewHolder
    private class ImageViewHolder extends BaseViewHolder<FavouritesListInfo> {
        private CircleImageView favourites_avatar; // 收藏人头像
        private TextView favourites_custom_tv, favourites_date_tv; // 收藏人名，收藏日期
        private ImageView favourites_video_iv; // 收藏的图片或者视频
        private ImageView media_play; // 视频播放按钮
        private LinearLayout item_image_or_video_layout; // 行布局

        public ImageViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            favourites_avatar = $(R.id.favourites_avatar);
            favourites_custom_tv = $(R.id.favourites_custom_tv);
            favourites_date_tv = $(R.id.favourites_date_tv);
            favourites_video_iv = $(R.id.favourites_video_iv);
            media_play = $(R.id.media_play);
            item_image_or_video_layout = $(R.id.item_image_or_video_layout);
        }

        @Override
        public void setData(final FavouritesListInfo data) {
            super.setData(data);

            String sort = data.favouritesort;
            String avatar = data.favouriteavatar;
            String custom = data.favouritecustom;
            String date = data.favouritedateStr;
            String content = data.favouritecontent;
//            String preUri = data.preUri;

            if (avatar != null)
                ImageLoaderHelper.loadImage(context, favourites_avatar, avatar);
            if (custom != null)
                favourites_custom_tv.setText(custom);
            if (date != null)
                favourites_date_tv.setText(date);
            if (sort.equals("3")) {
                media_play.setVisibility(View.VISIBLE);
            } else {
                media_play.setVisibility(View.GONE);
            }
            if (content != null)
                ImageLoaderHelper.loadImage(context, favourites_video_iv, content);

            // 长按监听
            if (longListener != null) {
                item_image_or_video_layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        longListener.onItemLongListener(item_image_or_video_layout, data);
                        return false;
                    }
                });
            }
        }
    }

    // 网址ViewHolder
    private class WebsiteViewHolder extends BaseViewHolder<FavouritesListInfo> {
        private CircleImageView favourites_avatar; // 收藏人头像
        private TextView favourites_custom_tv, favourites_date_tv; // 收藏人名，收藏日期
        private ImageView favourites_website_image_iv; // 网址内容缩略图
        private TextView favourites_title_tv; // 网址标题
        private LinearLayout item_website_layout; // 行布局

        public WebsiteViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            favourites_avatar = $(R.id.favourites_avatar);
            favourites_custom_tv = $(R.id.favourites_custom_tv);
            favourites_date_tv = $(R.id.favourites_date_tv);
            favourites_website_image_iv = $(R.id.favourites_website_image_iv);
            favourites_title_tv = $(R.id.favourites_title_tv);
            item_website_layout = $(R.id.item_website_layout);
        }

        @Override
        public void setData(final FavouritesListInfo data) {
            super.setData(data);

            String avatar = data.favouriteavatar;
            String custom = data.favouritecustom;
            String date = data.favouritedateStr;
            String title = data.favouritetitle;
            String image = data.attachmentPath;

            if (avatar != null)
                ImageLoaderHelper.loadImage(context, favourites_avatar, avatar);
            if (custom != null)
                favourites_custom_tv.setText(custom);
            if (date != null)
                favourites_date_tv.setText(date);
            if (title != null)
                favourites_title_tv.setText(title);
            if (image != null)
                ImageLoaderHelper.loadImage(context, favourites_website_image_iv, image);

            if (longListener != null) {
                item_website_layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        longListener.onItemLongListener(item_website_layout, data);
                        return false;
                    }
                });
            }

        }
    }

    // 附件ViewHolder
    private class AttachmentViewHolder extends BaseViewHolder<FavouritesListInfo> {
        private CircleImageView favourites_avatar; // 收藏人头像
        private TextView favourites_custom_tv, favourites_date_tv; // 收藏人名，收藏日期
        private ImageView favourites_attachment_sort; // 附件标志图
        private TextView favourites_attachment_title, favourites_attachment_sort_tag; // 附件标题，种类
        private TextView favourites_attachment_sort_size; // 附件大小
        private LinearLayout item_attachment_layout; // 行布局

        public AttachmentViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            favourites_avatar = $(R.id.favourites_avatar);
            favourites_custom_tv = $(R.id.favourites_custom_tv);
            favourites_date_tv = $(R.id.favourites_date_tv);
            favourites_attachment_sort = $(R.id.favourites_attachment_sort);
            favourites_attachment_title = $(R.id.favourites_attachment_title);
            favourites_attachment_sort_tag = $(R.id.favourites_attachment_sort_tag);
            favourites_attachment_sort_size = $(R.id.favourites_attachment_sort_size);
            item_attachment_layout = $(R.id.item_attachment_layout);
        }

        @Override
        public void setData(final FavouritesListInfo data) {
            super.setData(data);

            String avatar = data.favouriteavatar;
            String custom = data.favouritecustom;
            String date = data.favouritedateStr;
            String title = data.favouritetitle;
            String sort = data.favouriteattachmentsort;
            String size = data.favouriteattachmentsize;

            if (avatar != null)
                ImageLoaderHelper.loadImage(context, favourites_avatar, avatar);
            if (custom != null)
                favourites_custom_tv.setText(custom);
            if (date != null)
                favourites_date_tv.setText(date);
            if (sort != null) {
                favourites_attachment_sort_tag.setText(sort);
                if (sort.equals("excel")) {
                    favourites_attachment_sort.setBackgroundResource(R.mipmap.excel);
                } else if (sort.equals("ppt")) {
                    favourites_attachment_sort.setBackgroundResource(R.mipmap.ppt);
                } else if (sort.equals("pdf")) {
                    favourites_attachment_sort.setBackgroundResource(R.mipmap.pdf);
                } else if (sort.equals("word")) {
                    favourites_attachment_sort.setBackgroundResource(R.mipmap.word);
                }
            }

            if (title != null) {
                favourites_attachment_title.setText(title);
            }

            if (size != null) {
                favourites_attachment_sort_size.setText(size);
            }

            if (longListener != null) {
                item_attachment_layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        longListener.onItemLongListener(item_attachment_layout, data);
                        return false;
                    }
                });
            }

        }
    }


    public interface OnItemLongListener {
        /**
         * 长按监听
         *
         * @param view
         * @param info
         */
        void onItemLongListener(View view, FavouritesListInfo info);
    }

    private OnItemLongListener longListener;

    public void setLongListener(OnItemLongListener listener) {
        this.longListener = listener;
    }

}
