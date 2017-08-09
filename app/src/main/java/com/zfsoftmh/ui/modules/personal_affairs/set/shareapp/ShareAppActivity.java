package com.zfsoftmh.ui.modules.personal_affairs.set.shareapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zfsoftmh.R;
import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.QRCodeUtil;
import com.zfsoftmh.ui.base.BaseActivity;

/**
 * Created by ljq on 2017/7/12.
 */

public class ShareAppActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout share_qq;// 分享app到qq
    private LinearLayout share_qq_zone;// 分享app到qq空间
    private LinearLayout share_weixin;// 分享app到微信
    private LinearLayout share_firendcircle;// 分享app到朋友圈
    private LinearLayout share_copynet;// 分享app——复制地址
    private ImageView QRCode;// 分享app的二维码


    final static int TAG_QQ = 0;
    final static int TAG_QQ_ZONE = 1;
    final static int TAG_WEIXIN = 2;
    final static int TAG_FIREND = 3;
    final static int TAG_COPY = 4;


    private String shareurl = "";


    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_shareapp;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.shareapp);
        setDisplayHomeAsUpEnabled(true);

        share_qq = (LinearLayout) findViewById(R.id.share_app_qq);
        share_qq_zone = (LinearLayout) findViewById(R.id.share_app_qqzone);
        share_weixin = (LinearLayout) findViewById(R.id.share_app_weixin);
        share_firendcircle = (LinearLayout) findViewById(R.id.share_app_friendcircle);
        share_copynet = (LinearLayout) findViewById(R.id.share_app_copynet);
        QRCode = (ImageView) findViewById(R.id.shareapp_shareqrcode);
        GetUrl();
    }


    private void GetUrl(){
       shareurl= DbHelper.getValueBySharedPreferences(this, Config.DB.ShareAppUrl_NAME,Config.DB.ShareAppUrl_KEY,String.class);
        if(TextUtils.isEmpty(shareurl)){
           showToastMsgShort("没有获取到该APP的URL请联系管理员");
            this.finish();
        }else{
            setQRCodeImage();
        }
    }
    private void setQRCodeImage() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.logo);
        Bitmap QRcode = QRCodeUtil
                .createQRImage(shareurl, 200, 200, bitmap, "");
        QRCode.setImageBitmap(QRcode);
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

        share_qq.setOnClickListener(this);
        share_qq_zone.setOnClickListener(this);
        share_weixin.setOnClickListener(this);
        share_firendcircle.setOnClickListener(this);
        share_copynet.setOnClickListener(this);


        share_qq.setTag(TAG_QQ);
        share_qq_zone.setTag(TAG_QQ_ZONE);
        share_weixin.setTag(TAG_WEIXIN);
        share_firendcircle.setTag(TAG_FIREND);
        share_copynet.setTag(TAG_COPY);
    }

    @Override
    public void onClick(View v) {
        int i = (int) v.getTag();
        switch (i) {
            case TAG_QQ:
                share(SHARE_MEDIA.QQ);
                break;
            case TAG_QQ_ZONE:
                share(SHARE_MEDIA.QZONE);
                break;
            case TAG_WEIXIN:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case TAG_FIREND:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case TAG_COPY:
                copynet();
                break;

            default:
                break;
        }
    }
    private String descriptionHint = "  ";

    private void share(SHARE_MEDIA media) {
        String titleStr = "移动校园分享";
        UMWeb web = new UMWeb(shareurl);
        int id = getResources().getIdentifier("ico", "drawable",
                getPackageName());
        web.setThumb(new UMImage(this, id));
        web.setTitle(titleStr);
        web.setDescription(descriptionHint);

        new ShareAction(ShareAppActivity.this).setPlatform(media)
                .withMedia(web).setCallback(umShareListener).share();

    }

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA arg0) {

        }

        @Override
        public void onResult(SHARE_MEDIA arg0) {
            Toast.makeText(ShareAppActivity.this, "分享成功", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            String str = "分享失败";
            if (arg0 == SHARE_MEDIA.WEIXIN_CIRCLE || arg0 == SHARE_MEDIA.WEIXIN) {
                if (arg1.getMessage().trim().matches("错误码：2008(.*)")) {
                    str = "抱歉,您未安装微信客户端,无法进行微信分享";
                }
            } else if (arg0 == SHARE_MEDIA.QQ || arg0 == SHARE_MEDIA.QZONE) {
                if (arg1.getMessage().trim().matches("错误码：2008(.*)")) {
                    str = "抱歉,您未安装QQ客户端,无法进行QQ分享";
                }
            }
            Toast.makeText(ShareAppActivity.this, str, Toast.LENGTH_SHORT)
                    .show();
            Log.e("share", arg1.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA arg0) {

        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void copynet() {

        // 获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", shareurl);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Toast.makeText(this, "复制成功！", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

}
