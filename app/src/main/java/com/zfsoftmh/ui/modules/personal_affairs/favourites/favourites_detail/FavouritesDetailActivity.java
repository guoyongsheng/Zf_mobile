package com.zfsoftmh.ui.modules.personal_affairs.favourites.favourites_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.entity.FavouritesListInfo;
import com.zfsoftmh.ui.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author wangshimei
 * @date: 17/6/27
 * @Description:
 */

public class FavouritesDetailActivity extends BaseActivity {
    private CircleImageView favourites_avatar; // 头像
    private TextView favourites_custom_tv; // 收藏用户名
    private TextView favourites_date_tv; // 收藏时间
    private TextView favourites_detail_text; // 收藏文本内容
    private ImageView favourites_detail_image; // 收藏内容
    private FavouritesListInfo info;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        info = bundle.getParcelable("favouritesList");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_favourites_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setToolbarTitle(R.string.favourites_detail_title);
        setDisplayHomeAsUpEnabled(true);

        favourites_avatar = (CircleImageView) findViewById(R.id.favourites_avatar);
        favourites_custom_tv = (TextView) findViewById(R.id.favourites_custom_tv);
        favourites_date_tv = (TextView) findViewById(R.id.favourites_date_tv);
        favourites_detail_text = (TextView) findViewById(R.id.favourites_detail_text);
        favourites_detail_image = (ImageView) findViewById(R.id.favourites_detail_image);

        setData();
    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    /**
     * 设置数据
     */
    private void setData() {
        if (info != null) {
            if (info.favouritesort.equals("1")) {
                favourites_detail_text.setVisibility(View.VISIBLE);
                favourites_detail_image.setVisibility(View.GONE);

                if (info.favouritecontent != null)
                    favourites_detail_text.setText(info.favouritecontent);

            } else if (info.favouritesort.equals("2")) {
                favourites_detail_text.setVisibility(View.GONE);
                favourites_detail_image.setVisibility(View.VISIBLE);

                if (info.favouritecontent != null)
                    ImageLoaderHelper.loadImage(this, favourites_detail_image, info.favouritecontent);
            }
            if (info.favouriteavatar != null)
                ImageLoaderHelper.loadImage(getApplicationContext(), favourites_avatar, info.favouriteavatar);
            if (info.favouritecustom != null)
                favourites_custom_tv.setText(info.favouritecustom);
            if (info.favouritedateStr != null)
                favourites_date_tv.setText("收藏于" + info.favouritedateStr);

        }

    }
}
