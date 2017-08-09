package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.UserAddressInfo;

/**
 * Created by ljq
 * on 2017/7/20.
 */

class AddressAdapter extends RecyclerArrayAdapter<UserAddressInfo> {
    private OnMyItemClickListener listener;
    private Boolean isChoice=false;
    private int pos=-1;

    AddressAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent,R.layout.addressmanager_item);
    }

    class ViewHolder extends BaseViewHolder<UserAddressInfo> implements View.OnClickListener {
        TextView tv_purchaser;
        TextView tv_purchaser_phone;
        TextView tv_purchaser_address;
        ImageView iv_edit;
        ImageView iv_choice;


     public ViewHolder(ViewGroup parents,int res){
         super(parents,res);

         tv_purchaser= $(R.id.purchaser);
         tv_purchaser_address= $(R.id.purchaser_address);
         tv_purchaser_phone= $(R.id.purchaser_phone);
         iv_edit= $(R.id.purchaser_address_edit);
         iv_choice=$(R.id.iv_select_address);
     }


        @Override
        public void setData(UserAddressInfo data) {
            super.setData(data);
            tv_purchaser.setText(data.getName());
            tv_purchaser_address.setText(data.getSpecificAddress());
            tv_purchaser_phone.setText(data.getMobilePhone());
            if(isChoice){
                iv_edit.setVisibility(View.GONE);
                iv_choice.setVisibility(View.VISIBLE);
                iv_choice.setOnClickListener(this);
                if(pos!=getLayoutPosition()){
                    iv_choice.setBackgroundResource(R.drawable.drawable_circle_);
                }else{
                    iv_choice.setBackgroundResource(R.drawable.drawable_circle_bule);
                }
            }else{
                iv_choice.setVisibility(View.GONE);
                iv_edit.setOnClickListener(this);
                iv_edit.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {

            if(listener!=null) {
                listener.setOnMyItemClick(v, getLayoutPosition(),isChoice);
            }

        }
    }

    public interface OnMyItemClickListener{
         void setOnMyItemClick(View view,int pos,boolean isChoice);
    }

    public void setListener(OnMyItemClickListener listener ){
        this.listener=listener;
    }



    public void setIschoice(Boolean ischoice){
        this.isChoice=ischoice;

    }


    public void setPoschangecolor(int pos){
        this.pos=pos;
        notifyDataSetChanged();
    }

}
