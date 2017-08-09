package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.CloudNoteUtils;

/**
 * @author wangshimei
 * @date: 17/5/25
 * @Description: 云笔记标签颜色选择框
 */

public class ColorPopupWindow extends PopupWindow {
    private View view1, view2, view3, view4;
    private View view5, view6, view7, view8;
    private View mMenuView;

    public ColorPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popup_window_view, null);
        view1 = mMenuView.findViewById(R.id.color_popup_view1);
        view2 = mMenuView.findViewById(R.id.color_popup_view2);
        view3 = mMenuView.findViewById(R.id.color_popup_view3);
        view4 = mMenuView.findViewById(R.id.color_popup_view4);
        view5 = mMenuView.findViewById(R.id.color_popup_view5);
        view6 = mMenuView.findViewById(R.id.color_popup_view6);
        view7 = mMenuView.findViewById(R.id.color_popup_view7);
        view8 = mMenuView.findViewById(R.id.color_popup_view8);
        view1.setOnClickListener(itemsOnClick);
        view2.setOnClickListener(itemsOnClick);
        view3.setOnClickListener(itemsOnClick);
        view4.setOnClickListener(itemsOnClick);
        view5.setOnClickListener(itemsOnClick);
        view6.setOnClickListener(itemsOnClick);
        view7.setOnClickListener(itemsOnClick);
        view8.setOnClickListener(itemsOnClick);

        // 设置背景颜色
        CloudNoteUtils.getColorView(view1).setColor(CloudNoteUtils.getColorValue("FF3220"));
        CloudNoteUtils.getColorView(view2).setColor(CloudNoteUtils.getColorValue("FFBB1A"));
        CloudNoteUtils.getColorView(view3).setColor(CloudNoteUtils.getColorValue("2ED19F"));
        CloudNoteUtils.getColorView(view4).setColor(CloudNoteUtils.getColorValue("C4F602"));
        CloudNoteUtils.getColorView(view5).setColor(CloudNoteUtils.getColorValue("3FDAFF"));
        CloudNoteUtils.getColorView(view6).setColor(CloudNoteUtils.getColorValue("0981FD"));
        CloudNoteUtils.getColorView(view7).setColor(CloudNoteUtils.getColorValue("D021FF"));
        CloudNoteUtils.getColorView(view8).setColor(CloudNoteUtils.getColorValue("FF2295"));

        // 设置FirstPagePopupWindow的View
        this.setContentView(mMenuView);
        // 设置FirstPagePopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置FirstPagePopupWindow弹出窗口的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.anim_popup_bottom);
        // this.setBackgroundDrawable(R.color.color_main_transparent);
        // 实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0xb01C94EA);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.color_popup).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }

        });
    }
}
