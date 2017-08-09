package com.zfsoftmh.ui.modules.school_portal.school_map.search_map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.zfsoftmh.R;

import java.util.ArrayList;

/**
 * Created by ljq on 2017/6/29.
 * 搜索结果适配器
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>  {

    private PoiResult poiResult;
    private Context mContext;
    ArrayList<PoiItem> poiItems;
    MapItemClickListener mListener;

  public SearchResultAdapter(Context context, MapItemClickListener Listener){
      mContext=context;
      mListener=Listener;
 }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.search_item,null);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public int getItemCount() {
        int i= poiItems == null ? 0 : poiItems.size();
        return i;
    }

    public void addData( ArrayList<PoiItem> poiItems){
        this.poiItems=poiItems;
        notifyDataSetChanged();
}

   public void ClearData(){
       if(poiItems!=null&&poiItems.size()>0){
           poiItems.clear();
       }
   }




    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PoiItem item=poiItems.get(position);
        holder.title.setText(item.getTitle());
        if(item.getCityName()==null){
            holder.address.setText(item.getSnippet());
        }else {
            holder.address.setText(item.getCityName() + "-" + item.getAdName() + "-" + item.getSnippet());
        }
    }



    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
          TextView title;
          TextView address;
          MapItemClickListener itemClickListener;
          public MyViewHolder(View itemView,MapItemClickListener itemListener) {
              super(itemView);
              itemClickListener=itemListener;
              title= (TextView) itemView.findViewById(R.id.title);
              address= (TextView) itemView.findViewById(R.id.address);
              itemView.setOnClickListener(this);
          }

          @Override
          public void onClick(View v) {

              itemClickListener.onItemClick(v,getLayoutPosition());
          }
      }


    public interface MapItemClickListener {
        public void onItemClick(View view, int positon);
    }

}
