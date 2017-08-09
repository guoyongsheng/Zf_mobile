package com.zfsoftmh.ui.modules.school_portal.school_map.poi_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.bumptech.glide.Glide;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.List;

/**
 * Created by ljq on 2017/7/7.
 */

public class PoiDetailActivity extends BaseActivity{

    Button back;
    ImageView photo;
    TextView poi_name;
    TextView poi_address;
    PoiItem poiItem;
    String url;
    String poiname;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
         Bundle b=getIntent().getExtras();
         poiItem=b.getParcelable("poiitem");
        poiname=b.getString("poiname");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_poidetail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        back= (Button) findViewById(R.id.poidetail_back);
        photo= (ImageView) findViewById(R.id.photo);
        poi_name= (TextView) findViewById(R.id.poi_name);
        poi_address= (TextView) findViewById(R.id.poi_address);
        poi_name.setText(poiname);
        poi_address.setText(poiItem.getSnippet());
        List<Photo> photos=poiItem.getPhotos();
        if(photos.size()>0){
            Photo photo =photos.get(0);
            url=photo.getUrl();
        }else{
        /*    PoiSearch.Query query=new PoiSearch.Query(poiname,);

            PoiSearch search=new PoiSearch(this,)*/
        }
        if(url!=null&&url!=""){
            setPhoto();
        }
    }

    private void setPhoto(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) photo.getLayoutParams();
        params.width=width;
        params.height=height/3;//设置当前控件布局的高度
        photo.setLayoutParams(params);
        Glide.with(PoiDetailActivity.this).load(url).into(photo);
    }



    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
