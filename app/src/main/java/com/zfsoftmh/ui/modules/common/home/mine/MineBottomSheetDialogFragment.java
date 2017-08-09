package com.zfsoftmh.ui.modules.common.home.mine;

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
 * @date: 2017/3/27
 * @Description: 上传头像的对话框
 */

public class MineBottomSheetDialogFragment extends BaseBottomSheetDialogFragment implements
        View.OnClickListener {

    private OnViewClickListener listener;

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    protected Dialog createBottomSheetDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_sheet_mine_fragment, null);
        TextView tv_select_from_album = (TextView) view.findViewById(R.id.select_from_album);
        TextView tv_take_pictures = (TextView) view.findViewById(R.id.take_pictures);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        tv_select_from_album.setOnClickListener(this);
        tv_take_pictures.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        dialog.setContentView(view);
        return dialog;
    }

    public static MineBottomSheetDialogFragment newInstance() {
        return new MineBottomSheetDialogFragment();
    }


    public void setOnViewClickListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        if (view == null || listener == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /*
         * 从相册中选取
         */
        case R.id.select_from_album:
            listener.onSelectFromAlbumClick();
            break;

        /*
         * 拍照
         */
        case R.id.take_pictures:
            listener.onTakePicturesClick();
            break;

        /*
         * 取消
         */
        case R.id.cancel:
            break;


        default:
            break;
        }

        dismiss();
    }

    /**
     * 自定义回调接口
     */
    public interface OnViewClickListener {

        /**
         * 从相册中选取
         */
        void onSelectFromAlbumClick();

        /**
         * 拍照
         */
        void onTakePicturesClick();
    }
}
