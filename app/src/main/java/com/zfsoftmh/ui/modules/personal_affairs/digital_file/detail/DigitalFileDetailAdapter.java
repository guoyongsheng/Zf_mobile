package com.zfsoftmh.ui.modules.personal_affairs.digital_file.detail;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.DigitalFileItemInfo;



/**
 * @author wesley
 * @date: 2017-6-16
 * @Description: 数字档案详情适配器
 */

class DigitalFileDetailAdapter extends RecyclerArrayAdapter<DigitalFileItemInfo> {

    DigitalFileDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.item_digital_file);
    }


    //静态内部类
    private static class ItemViewHolder extends BaseViewHolder<DigitalFileItemInfo> {

        public ItemViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }
    }
}
