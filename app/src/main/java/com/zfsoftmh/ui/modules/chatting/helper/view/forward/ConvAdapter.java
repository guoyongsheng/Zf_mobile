package com.zfsoftmh.ui.modules.chatting.helper.view.forward;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy
 * on 2017/5/25.
 */

class ConvAdapter extends BaseAdapter implements Filterable{

    private List<ForwardBean> dataList;
    private LayoutInflater inflater;
    private Context mContext;
    private String titleStr;
    private String CurrStr;

    ConvAdapter(List<ForwardBean> data, Context context, int type){
        mContext = context;
        dataList = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type){
            case 0:
                titleStr = "从好友中选择";
                CurrStr = "最近会话";
                break;
            case 1:
                titleStr = "从群组中选择";
                CurrStr = "好友";
                break;
            case 2:
                titleStr = null;
                CurrStr = "群组";
                break;
        }
    }

    public ForwardBean getForwardBean(int pos){
        return dataList.get(pos - 1);
    }

    @Override
    public int getCount() {
        return dataList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0){
            TitleHolder holder;
            if (convertView == null){
                holder = new TitleHolder();
                convertView = inflater.inflate(R.layout.lay_forward_type,parent,false);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.item_forward_title);
                holder.tvCurrent = (TextView) convertView.findViewById(R.id.item_forward_curr);
                convertView.setTag(holder);
            }else{
                holder = (TitleHolder) convertView.getTag();
            }
            if (titleStr == null)
                holder.tvTitle.setVisibility(View.GONE);
            else{
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(titleStr);
            }
            holder.tvCurrent.setText(CurrStr);
        }else{
            ConvHolder holder;
            if (convertView == null){
                holder = new ConvHolder();
                convertView = inflater.inflate(R.layout.item_chat_friends,parent,false);
                holder.tv_name = (TextView) convertView.findViewById(R.id.item_chat_contacts_name);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.item_chat_contacts_icon);
                convertView.setTag(holder);
            }else{
                holder = (ConvHolder) convertView.getTag();
            }
            ForwardBean bean = dataList.get(position - 1);
            if (bean.contact == null && bean.tribe != null){
                holder.tv_name.setText(bean.tribe.getTribeName());
                ImageLoaderHelper.loadDefConfCirCleImage(mContext, holder.iv_icon,
                        bean.tribe.getTribeIcon(), R.drawable.aliwx_tribe_head_default);
            }else if(bean.contact != null && bean.tribe == null){
                holder.tv_name.setText(bean.contact.getShowName());
                ImageLoaderHelper.loadDefConfCirCleImage(mContext, holder.iv_icon,
                        bean.contact.getAvatarPath(), R.drawable.aliwx_head_default);
            }
        }
        return convertView;
    }

    private class ConvHolder{
        ImageView iv_icon; //头像
        TextView tv_name; //名称
    }

    private class TitleHolder{
        TextView tvTitle,tvCurrent;
    }

    // 自定义的过滤器
    private SearchFilter mFilter;
    @Override
    public Filter getFilter() {
        if (mFilter == null)
            mFilter = new SearchFilter();
        return mFilter;
    }

    private List<ForwardBean> mOriginalValues;
    private final Object mLock = new Object();
    private class SearchFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(dataList);
                }
            }
            if (TextUtils.isEmpty(prefix)) {
                synchronized (mLock) {
                    ArrayList<ForwardBean> list = new ArrayList<>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            }else{
                String prefixString = prefix.toString().toLowerCase();
                final List<ForwardBean> values = mOriginalValues;// 声明一个临时的集合对象 将原始数据赋给这个临时变量
                final int count = values.size();
                List<ForwardBean> newValues = new ArrayList<>(count);// 新的集合对象
                for (int i = 0; i < count; i++) {
                    ForwardBean bean = values.get(i);
                    if (bean.contact == null && bean.tribe != null){
                        if (bean.tribe.getNameSpell().startsWith(prefixString)
                                || bean.tribe.getShortName().contains(prefixString)
                                || bean.tribe.getTribeName().contains(prefixString))
                            newValues.add(bean);
                    }else if(bean.contact != null && bean.tribe == null){
                        if (bean.contact.getShortName().contains(prefixString)
                                || bean.contact.getNameSpell().startsWith(prefixString)
                                || bean.contact.getShowName().contains(prefixString))
                            newValues.add(bean);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList = (List<ForwardBean>) results.values;
            if (results.count > 0)
                notifyDataSetChanged();
             else
                notifyDataSetInvalidated();
        }
    }

}
