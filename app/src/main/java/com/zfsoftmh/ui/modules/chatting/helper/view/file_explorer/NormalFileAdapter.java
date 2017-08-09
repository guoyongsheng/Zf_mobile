package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.FileInfo;
import com.zfsoftmh.ui.widget.OnceClickListener;

import java.util.List;

/**
 * Created by sy
 * on 2017/7/7.
 * <p>office文档适配器</p>
 */

class NormalFileAdapter extends ExpandableRecyclerAdapter<FileParent,FileInfo,NormalFileAdapter.TitleHolder,
        NormalFileAdapter.ContentHolder>{

    NormalFileAdapter(@NonNull List<FileParent> parentList) {
        super(parentList);
    }

    @NonNull
    @Override
    public TitleHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.item_parent_content_office_contacts,parentViewGroup,false);
        return new TitleHolder(view);
    }

    @NonNull
    @Override
    public ContentHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(childViewGroup.getContext())
                .inflate(R.layout.file_item_content,childViewGroup,false);
        return new ContentHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull TitleHolder parentViewHolder, int parentPosition, @NonNull FileParent parent) {
        parentViewHolder.tv_name.setText(parent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(@NonNull ContentHolder childViewHolder, int parentPosition, int childPosition, @NonNull final FileInfo child) {
        childViewHolder.tvTitle.setText(child.getFileName());
        childViewHolder.tvSize.setText(child.getFileSize());
        switch (child.getSuffixType()){
            case FileInfo.TYPE_WORD:
                childViewHolder.fileIv.setBackgroundResource(R.mipmap.word);
                break;
            case FileInfo.TYPE_PPT:
                childViewHolder.fileIv.setBackgroundResource(R.mipmap.ppt);
                break;
            case FileInfo.TYPE_PDF:
                childViewHolder.fileIv.setBackgroundResource(R.mipmap.pdf);
                break;
            case FileInfo.TYPE_XLS:
                childViewHolder.fileIv.setBackgroundResource(R.mipmap.excel);
                break;
        }
        childViewHolder.itemView.setOnClickListener(new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                if (mFileClickListener!=null)
                    mFileClickListener.onFileClick(child);
            }
        });
    }


    private FileClickListener mFileClickListener;
    void setFileClickListener(FileClickListener listener){
        this.mFileClickListener = listener;
    }

    @Override
    public int getParentViewType(int parentPosition) {
        return 1;
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == 1;
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return 2;
    }

    class TitleHolder extends ParentViewHolder{

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        private TextView tv_name; //名称
        private ImageView iv_arrow; //箭头

        TitleHolder(@NonNull View itemView) {
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

    class ContentHolder extends ChildViewHolder{

        ImageView fileIv;
        TextView tvTitle,tvSize;

        ContentHolder(@NonNull View itemView) {
            super(itemView);
            fileIv = (ImageView) itemView.findViewById(R.id.file_item_typed_iv);
            tvTitle = (TextView) itemView.findViewById(R.id.file_item_tv_title);
            tvSize = (TextView) itemView.findViewById(R.id.file_item_tv_size);
        }
    }
}
