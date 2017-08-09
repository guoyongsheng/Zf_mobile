package com.zfsoftmh.ui.modules.office_affairs.questionnaire.edit_questionnaire;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseBottomSheetDialogFragment;

/**
 * @author wesley
 * @date: 2017-6-2
 * @Description: 底部对话框
 */

public class EditQuestionnaireDialogFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener {

    private OnItemClickListener listener;

    @Override
    protected Dialog createBottomSheetDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_questionnaire, null);
        TextView tv_single_choice = (TextView) view.findViewById(R.id.dialog_single_choice);
        TextView tv_muti_choice = (TextView) view.findViewById(R.id.dialog_muti_choice);
        TextView tv_short_answer = (TextView) view.findViewById(R.id.dialog_short_answer);
        TextView tv_hit_the_title = (TextView) view.findViewById(R.id.dialog_hit_the_title);
        TextView tv_cancel = (TextView) view.findViewById(R.id.dialog_cancel);

        tv_single_choice.setOnClickListener(this);
        tv_muti_choice.setOnClickListener(this);
        tv_short_answer.setOnClickListener(this);
        tv_hit_the_title.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        dialog.setContentView(view);
        return dialog;
    }

    public static EditQuestionnaireDialogFragment newInstance() {
        return new EditQuestionnaireDialogFragment();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View view) {

        if (view == null || listener == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 单选题
         */
        case R.id.dialog_single_choice:
            listener.onItemClick(0);
            break;

        /*
         * 多选题
         */
        case R.id.dialog_muti_choice:
            listener.onItemClick(1);
            break;

        /*
         * 简答题
         */
        case R.id.dialog_short_answer:
            listener.onItemClick(2);
            break;

        /*
         * 打分题
         */
        case R.id.dialog_hit_the_title:
            listener.onItemClick(3);
            break;

        /*
         * 取消
         */
        case R.id.dialog_cancel:
            break;

        default:
            break;
        }
        dismiss();
    }

    /**
     * 自定义回调接口
     */
    interface OnItemClickListener {

        void onItemClick(int position);
    }
}
