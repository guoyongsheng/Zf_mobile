package com.zfsoftmh.ui.modules.personal_affairs.contacts.qr_code_card;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.ContactsItemInfo;
import com.zfsoftmh.entity.QrCodeData;
import com.zfsoftmh.entity.ZxCode;
import com.zfsoftmh.ui.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;



/**
 * @author wesley
 * @date: 2017/4/12
 * @Description: ui
 */

public class QrCodeCardFragment extends BaseFragment {


    private ContactsItemInfo info;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        info = bundle.getParcelable("info");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_qr_code_card;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initViews(View view) {

        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.fragment_qr_code_icon);
        TextView tv_name = (TextView) view.findViewById(R.id.name);
        ImageView iv_qr_code = (ImageView) view.findViewById(R.id.qr_code);

        if (info == null) {
            return;
        }

        String url = info.getPhotoUri();
        String name = info.getName();
        ImageLoaderHelper.loadImage(context, circleImageView, url);
        tv_name.setText(name);

        QrCodeData info = new QrCodeData(ZxCode.CODE_TEXT);
        info.setContent(name);


        int width = ScreenUtils.getScreenWidth(context) / 2;
        Bitmap bitmap = CodeUtils.createImage(GsonHelper.objectToString(info), width, width, BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        iv_qr_code.setImageBitmap(bitmap);
    }

    @Override
    protected void initListener() {

    }

    public static QrCodeCardFragment newInstance(ContactsItemInfo info) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", info);
        QrCodeCardFragment fragment = new QrCodeCardFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
