package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.FileInfo;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/7/10.
 * <p>视频文件适配器</p>
 */

class VideoFileAdapter extends RecyclerView.Adapter<VideoFileAdapter.VideoFileHolder> {

    private Context mContext;
    private int mWidth;
    private FileClickListener mFileClickListener;
    VideoFileAdapter(Context context,FileClickListener l){
        mWidth = context.getResources().getDisplayMetrics().widthPixels / 2;
        mContext = context;
        dataList = new ArrayList<>();
        mFileClickListener = l;
    }

    private ArrayList<FileInfo> dataList;
    void setData(ArrayList<FileInfo> data){
        dataList = data;
        notifyDataSetChanged();
    }

    @Override
    public VideoFileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_video_content,parent,false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mWidth,mWidth + 24);
        view.setLayoutParams(params);
        return new VideoFileHolder(view,mFileClickListener);
    }

    @Override
    public void onBindViewHolder(VideoFileHolder holder, int position) {
        ImageLoaderHelper.loadImage(mContext,holder.thumb,dataList.get(position).getThumbPath());
        holder.size.setText(dataList.get(position).getFileSize());
        holder.duration.setText(dataList.get(position).getDuration());
        holder.title.setText(dataList.get(position).getFileName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class VideoFileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumb;
        TextView size,duration,title;
        FileClickListener mListener;
        VideoFileHolder(View itemView,FileClickListener l) {
            super(itemView);
            mListener = l;
            thumb = (ImageView) itemView.findViewById(R.id.file_video_thumb);
            size = (TextView) itemView.findViewById(R.id.file_video_size);
            duration = (TextView) itemView.findViewById(R.id.file_video_duration);
            title = (TextView) itemView.findViewById(R.id.file_video_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener!=null)
                mListener.onFileClick(dataList.get(getAdapterPosition()));
        }
    }
}
