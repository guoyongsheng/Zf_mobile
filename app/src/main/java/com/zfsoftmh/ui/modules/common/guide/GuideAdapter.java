package com.zfsoftmh.ui.modules.common.guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/14
 * @Description: ViewPager适配器
 */
class GuideAdapter extends PagerAdapter implements View.OnClickListener {

    private List<View> list = new ArrayList<>();
    private LayoutInflater inflater;
    private onBeginClickListener onBeginClickListener;
    private List<Integer> listImage;

    GuideAdapter(Context context, List<Integer> listImage) {
        this.listImage = listImage;
        if (context != null) {
            inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        if (listImage != null) {
            return listImage.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (inflater == null || listImage == null || listImage.size() <= position || container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_guide, null);
        ImageView iv_guide = (ImageView) view.findViewById(R.id.guide_iv);
        TextView tv_begin = (TextView) view.findViewById(R.id.guide_tv_begin);
        iv_guide.setImageResource(listImage.get(position));
        if (position == listImage.size() - 1) {
            tv_begin.setVisibility(View.VISIBLE);
            tv_begin.setOnClickListener(this);
        } else {
            tv_begin.setVisibility(View.GONE);
        }
        container.addView(view);
        list.add(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (list == null || list.size() <= position || container == null) {
            return;
        }
        container.removeView(list.get(position));
    }

    void setOnBeginClickListener(onBeginClickListener onBeginClickListener) {
        this.onBeginClickListener = onBeginClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onBeginClickListener != null) {
            onBeginClickListener.onBeginClick();
        }
    }

    //内部接口
    interface onBeginClickListener {
        void onBeginClick();
    }
}
