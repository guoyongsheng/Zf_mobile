package com.zfsoftmh.ui.modules.office_affairs.cloud_notes.edit_note_label;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zfsoftmh.R;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.common.utils.CloudNoteUtils;
import com.zfsoftmh.entity.NoteLabelItemInfo;

import java.util.ArrayList;
import java.util.List;

import static com.zfsoftmh.R.id.item_edit_label_view;

/**
 * @author wangshimei
 * @date: 17/5/23
 * @Description: 标签编辑页
 */

public class EditNoteLabelAdapter extends RecyclerView.Adapter<EditNoteLabelAdapter.EditNoteLabelHolder> {
    private Context mContext;
    private LayoutInflater inflater;

    private ArrayList<NoteLabelItemInfo> dataList;
    private NoteLabelItemInfo tempLabel;

    private final int TYPE_FOOTER = 1;
    private final int TYPE_NORMAL = 2;

    public EditNoteLabelAdapter(Context context) {
        tempLabel = new NoteLabelItemInfo();
        tempLabel.catalogColor = "FF3220";
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 添加数据
     *
     * @param dataList
     */
    public void setData(ArrayList<NoteLabelItemInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 删除标签
     *
     * @param pos
     */
    public void removeItem(int pos) {
        dataList.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null && dataList.size() > 0 ? dataList.size() + 1 : 1;
    }

    @Override
    public EditNoteLabelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.item_edit_note_label, parent, false);
        return new EditNoteLabelHolder(view, mSureClickListener);
    }

    @Override
    public void onBindViewHolder(final EditNoteLabelHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL:
                holder.iv_add.setVisibility(View.GONE);
                holder.iv_ic.setVisibility(View.VISIBLE);
                CloudNoteUtils.getColorView(holder.iv_ic).setColor(
                        CloudNoteUtils.getColorValue(dataList.get(position).catalogColor));
                holder.editText.setText(dataList.get(position).memoCatalogName);
//                if (position > 4) {
//                    holder.editText.requestFocus();
//                    holder.editText.setFocusable(true);
//                    holder.editText.setFocusableInTouchMode(true);
//                }

                // 长按监听
                if (onItemLongListener != null) {
                    holder.itemView
                            .setOnLongClickListener(new View.OnLongClickListener() {

                                @Override
                                public boolean onLongClick(View v) {
                                    onItemLongListener.onItemLongOnClick(
                                            holder.itemView, position);
                                    return false;
                                }
                            });
                }
                break;
            case TYPE_FOOTER:
                if (position > 4) {
                    holder.editText.requestFocus();
                    holder.editText.setFocusable(true);
                    holder.editText.setFocusableInTouchMode(true);
                }
                holder.iv_add.setVisibility(View.VISIBLE);
                holder.iv_ic.setVisibility(View.GONE);
                holder.editText.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 获得颜色值
     *
     * @param id
     * @return
     */
    private String getColor(int id) {
        String color = null;
        if (id == R.id.color_popup_view1) {
            color = "FF3220";
        } else if (id == R.id.color_popup_view2) {
            color = "FFBB1A";
        } else if (id == R.id.color_popup_view3) {
            color = "2ED19F";
        } else if (id == R.id.color_popup_view4) {
            color = "C4F602";
        } else if (id == R.id.color_popup_view5) {
            color = "3FDAFF";
        } else if (id == R.id.color_popup_view6) {
            color = "0981FD";
        } else if (id == R.id.color_popup_view7) {
            color = "D021FF";
        } else if (id == R.id.color_popup_view8) {
            color = "FF2295";
        }
        return color;
    }

    /**
     * 标签颜色选择框
     */
    private ColorPopupWindow colorPopupWindow;

    private void showPopWindow(final View v, final int pos) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        colorPopupWindow = new ColorPopupWindow(mContext,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String color = getColor(view.getId());
                        CloudNoteUtils.getColorView(v).setColor(
                                CloudNoteUtils.getColorValue(color));
                        if (pos >= dataList.size() - 1) {
                            tempLabel.catalogColor = color;
                        } else {
                            dataList.get(pos).catalogColor = color;
                        }
                        colorPopupWindow.dismiss();
                    }
                });
        colorPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                location[0] + 100, location[1] - colorPopupWindow.getHeight());
        colorPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (colorPopupWindow != null || colorPopupWindow.isShowing()) {
                    colorPopupWindow.dismiss();
                }
            }
        });

    }

    class EditNoteLabelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // 添加标签，取消，确定按钮
        ImageView iv_add, iv_cancel, iv_sure;
        // 标签颜色圆形视图
        private View iv_ic;
        // 标签名编写框
        EditText editText;
        // 添加标签视图
        FrameLayout frame_select;
        // 标签确定按钮监听
        SureClickListener listener;

        public EditNoteLabelHolder(View itemView, SureClickListener l) {
            super(itemView);
            listener = l;

            frame_select = (FrameLayout) itemView.findViewById(R.id.item_select_frame);
            editText = (EditText) itemView.findViewById(R.id.item_edit_label_name_et);
            iv_ic = itemView.findViewById(item_edit_label_view);

            iv_add = (ImageView) itemView.findViewById(R.id.item_edit_label_add);
            iv_cancel = (ImageView) itemView.findViewById(R.id.item_edit_label_cancel);
            iv_sure = (ImageView) itemView.findViewById(R.id.item_edit_label_sure);
            iv_add.setOnClickListener(this);
            iv_ic.setOnClickListener(this);
            iv_cancel.setOnClickListener(this);
            iv_sure.setOnClickListener(this);
            editText.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == null) {
                return;
            }

            int key = v.getId();
            switch (key) {
                /**
                 * 添加标签按钮
                 */
                case R.id.item_edit_label_add:
                    iv_add.setVisibility(View.GONE);
                    iv_ic.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    frame_select.setVisibility(View.VISIBLE);
                    break;
                /**
                 * 标签颜色圆形视图
                 */
                case R.id.item_edit_label_view:
                    if (getPosition() > 4) {
                        showPopWindow(v, getPosition());
                    }
                    break;
                /**
                 * 添加标签取消按钮
                 */
                case R.id.item_edit_label_cancel:
                    iv_add.setVisibility(View.VISIBLE);
                    iv_ic.setVisibility(View.GONE);
                    editText.setText("");
                    editText.setVisibility(View.GONE);
                    frame_select.setVisibility(View.GONE);
                    break;
                /**
                 * 添加标签确定按钮
                 */
                case R.id.item_edit_label_sure:
                    if (listener != null) {
                        if (TextUtils.isEmpty(editText.getText())) {
                            Toast.makeText(mContext, Constant.label_not_empty, Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        tempLabel.memoCatalogName = editText.getText().toString();
                        listener.onSureClick(getNameListStr(), getColorListStr());
                        frame_select.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }


    /**
     * 点击确定按钮监听事件
     */
    public interface SureClickListener {
        void onSureClick(String nameListStr, String colorListStr);
    }

    private SureClickListener mSureClickListener;

    public void setSureClickListener(SureClickListener l) {
        this.mSureClickListener = l;
    }

    /**
     * 长按监听事件
     */
    public interface OnItemLongListener {
        void onItemLongOnClick(View view, int pos);
    }

    private OnItemLongListener onItemLongListener;

    public void setOnLongListener(OnItemLongListener listener) {
        this.onItemLongListener = listener;
    }


    /**
     * 获取标签名
     *
     * @return
     */
    private String getNameListStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 5; i < dataList.size() - 1; i++) {
            builder.append(dataList.get(i).memoCatalogName);
            builder.append(",");
        }
        if (dataList.size() > 5) {
            builder.append(dataList.get(dataList.size() - 1).memoCatalogName);
        }

        if (!TextUtils.isEmpty(tempLabel.memoCatalogName)) {// 如果为空说明没有添加label
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(tempLabel.memoCatalogName);
        }

        return builder.toString();
    }

    /**
     * 获取标签颜色值
     *
     * @return
     */
    private String getColorListStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 5; i < dataList.size() - 1; i++) {
            builder.append(dataList.get(i).catalogColor);
            builder.append(",");
        }
        if (dataList.size() > 5) {
            builder.append(dataList.get(dataList.size() - 1).catalogColor);
        }
        if (!TextUtils.isEmpty(tempLabel.memoCatalogName)) {// 如果为空说明没有添加label
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(tempLabel.catalogColor);
        }
        return builder.toString();
    }
}
