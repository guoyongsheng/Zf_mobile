package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.EateryInfo;

/**
 * Created by ljq
 * on 2017/7/19.
 */

public class SchoolEateryAdapter extends RecyclerArrayAdapter<EateryInfo> {

     private Context context;

     SchoolEateryAdapter( Context context){
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent,R.layout.schooleatery_item) ;
    }


    class MyViewHolder extends BaseViewHolder <EateryInfo> {
        ImageView iv;
        TextView tv_name;
        TextView tv_charge;

         MyViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            iv= (ImageView) itemView.findViewById(R.id.iv_schooleatety);
            tv_name= (TextView) itemView.findViewById(R.id.eatery_name);
            tv_charge= (TextView) itemView.findViewById(R.id.eatery_charge);
        }

        @Override
        public void setData(EateryInfo data) {
            super.setData(data);
            tv_name.setText(data.getCanteenName());
            ImageLoaderHelper.loadImage(context,iv,data.getPicPath());
        }

    }



}
