package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.FileInfo;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/7/10.
 * <p>音频文件适配器</p>
 */

class MusicFileAdapter extends RecyclerView.Adapter<MusicFileAdapter.MediaFileHolder> {

    private FileClickListener mFileClickListener;
    MusicFileAdapter(FileClickListener listener){
        dataList = new ArrayList<>();
        this.mFileClickListener = listener;
    }

    private ArrayList<FileInfo> dataList;
    public void setData(ArrayList<FileInfo> data){
        dataList = data;
        notifyDataSetChanged();
    }


    @Override
    public MediaFileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item_content,parent,false);
        return new MediaFileHolder(view, mFileClickListener);
    }

    @Override
    public void onBindViewHolder(MediaFileHolder holder, int position) {
        holder.fileIv.setBackgroundResource(R.mipmap.music);
        holder.tvTitle.setText(dataList.get(position).getFileName());
        holder.tvSize.setText(dataList.get(position).getFileSize());
        holder.tvDuration.setText(dataList.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MediaFileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView fileIv;
        TextView tvTitle,tvSize,tvDuration;
        FileClickListener listener;

        MediaFileHolder(View itemView, FileClickListener l) {
            super(itemView);
            listener = l;
            fileIv = (ImageView) itemView.findViewById(R.id.file_item_typed_iv);
            tvTitle = (TextView) itemView.findViewById(R.id.file_item_tv_title);
            tvSize = (TextView) itemView.findViewById(R.id.file_item_tv_size);
            tvDuration = (TextView) itemView.findViewById(R.id.file_item_media_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null)
                listener.onFileClick(dataList.get(getAdapterPosition()));
        }
    }
}
