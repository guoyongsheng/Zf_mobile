package com.zfsoftmh.ui.modules.chatting.tribe.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.QrCodeData;
import com.zfsoftmh.entity.ZxCode;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys;
import com.zfsoftmh.ui.widget.OnceClickListener;

import static com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys.TRIBE_EDIT;
import static com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys.TRIBE_EDIT_NAME;
import static com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys.TRIBE_ID;
import static com.zfsoftmh.ui.modules.chatting.tribe.TribeKeys.TRIBE_SET_CONTENT;

/**
 * Created by sy
 * on 2017/5/18.
 * <p>群信息修改</p>
 */
public class TribeSetActivity extends BaseActivity {

    @Override
    protected void initVariables() {
        enableTouchToHideKeyboard();
    }

    private int mType;
    private String mContent;
    private long mTribeID;
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        mTribeID = getIntent().getLongExtra(TRIBE_ID, 0);
        mType = getIntent().getIntExtra(TRIBE_EDIT, 0);
        mContent = getIntent().getStringExtra(TRIBE_SET_CONTENT);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.lay_tribe_set;
    }

    private EditText editText;
    private ImageView ivZx;
    private Button btnSure;
    private View mView;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        mView = findViewById(R.id.tribe_set_parent);
        editText = (EditText) findViewById(R.id.etContent);
        ivZx = (ImageView) findViewById(R.id.iv_zx);
        btnSure = (Button) findViewById(R.id.btn_sure);
        dealTyped();
        setToolbarTitle(toolbarTitle);
        setDisplayHomeAsUpEnabled(true);
        tvTitle.setText(tvTitleText);
    }

    private String toolbarTitle,tvTitleText;
    private void dealTyped() {
        switch (mType){
            case TRIBE_EDIT_NAME:
                toolbarTitle = getResources().getString(R.string.str_tribe_name);
                tvTitleText = "编辑群名称";
                editText.setVisibility(View.VISIBLE);
                editText.setText(mContent);
                editText.setSelection(mContent.length());
                break;
            case TribeKeys.TRIBE_EDIT_NICK:
                toolbarTitle = getResources().getString(R.string.my_tribe_card);
                tvTitleText = "编辑我的群名片";
                editText.setVisibility(View.VISIBLE);
                editText.setText(mContent);
                editText.setSelection(mContent.length());
                break;
            case TribeKeys.TRIBE_EDIT_ZX:
                toolbarTitle = getResources().getString(R.string.str_tribe_zx);
                tvTitleText = "扫一扫加群";
                ivZx.setVisibility(View.VISIBLE);
                int width = ScreenUtils.getScreenWidth(this) / 2 + 56;
                TribeZxInfo info = new TribeZxInfo();
                info.tribeID = mTribeID;
                info.appKey = IMKitHelper.APP_KEY;
                QrCodeData data = new QrCodeData(ZxCode.CODE_TRIBE_ADD);
                data.setContent(GsonHelper.objectToString(info));
                ivZx.setImageBitmap(CodeUtils.createImage(GsonHelper.objectToString(data), width, width, null));
                btnSure.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void setUpInject() {  }

    @Override
    protected void initListener() {
        btnSure.setOnClickListener(onceClickListener);
    }

    private OnceClickListener onceClickListener = new OnceClickListener() {
        @Override
        public void onOnceClick(View v) {
            switch (mType){
                case TRIBE_EDIT_NAME:
                    goBack("群名称不能为空",TribeKeys.TRIBE_EDIT_NAME);
                    break;
                case TribeKeys.TRIBE_EDIT_NICK:
                    goBack("我的群名片不能为空",TribeKeys.TRIBE_EDIT_NICK);
                    break;
            }
        }
    };

    private void goBack(String hint, int resultCode) {
        if(editText.getText().length() == 0){
            Snackbar.make(mView, hint, Snackbar.LENGTH_SHORT).show();
        }else{
            String data = editText.getText().toString();
            if (!mContent.equals(data)){
                Intent intent = new Intent();
                intent.putExtra(TribeKeys.TRIBE_SET_RESULT, data);
                setResult(resultCode, intent);
            }
            finish();
        }
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        onBackPressed();
    }
}
