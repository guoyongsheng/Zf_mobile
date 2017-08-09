package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.chatting.location.LocationActivity;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.EateryDetail.EateryDetailActivity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ljq
 * on 2017/7/19.
 */

public class SchoolEateryFragment extends BaseListFragment<SchoolEateryPresenter,EateryInfo> implements SchoolEateryContract.View, AMapLocationListener, View.OnClickListener {



    private SchoolEateryAdapter adapter;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLonPoint point;
    private TextView location;
    private ImageView iv_location;
    private boolean isCanLoadData=false;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static SchoolEateryFragment newInstance(){
        return  new SchoolEateryFragment();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_schooleatery;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        location= (TextView) view.findViewById(R.id.eatery_location_name);
        iv_location= (ImageView) view.findViewById(R.id.eatery_location_iv);
        location();


    }


   //获取学校食堂的信息
    private void getSchoolEatery(int start){
            Map<String, String> params = new LinkedHashMap<>();
            params.put("start",String.valueOf(start));
            params.put("size", String.valueOf(10));
            params.put("canteenname", "");
       if(point!=null){
            params.put("latitude",String.valueOf(point.getLatitude()));
            params.put("longitude",String.valueOf(point.getLongitude()));
         }else {
            params.put("latitude", "0");
            params.put("longitude", "0");
        }
            presenter.loadData(params);
    }


    @Override
    protected void initListener() {
        super.initListener();
        location.setOnClickListener(this);
        iv_location.setOnClickListener(this);
    }

    @Override
    protected RecyclerArrayAdapter<EateryInfo> getAdapter() {
        adapter=new SchoolEateryAdapter(getContext());
        return adapter ;
    }

    @Override
    protected void loadData() {
        if(isCanLoadData){
            getSchoolEatery(start_page);
        }
    }

    @Override
    public void loadSuccess(ResponseListInfo<EateryInfo> info) {
        super.loadSuccess(info);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(getActivity(),EateryDetailActivity.class);
        intent.putExtra("eatery",adapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void location() {
        mlocationClient=new AMapLocationClient(getContext());
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);//只定位一次
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();//启动定位
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null){
            if (aMapLocation.getErrorCode() == 0) {
              point=new LatLonPoint(aMapLocation.getLongitude(),aMapLocation.getLatitude());
                String address= aMapLocation.getAddress();
                String poiname=aMapLocation.getPoiName();
                location.setText(poiname);
                isCanLoadData=true;
                loadData();
            }else{
                showToastMsgShort("定位失败，将返回所有的食堂");
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.eatery_location_iv:
                startActivityForResult(LocationActivity.class,0);
                break;
            case R.id.eatery_location_name:
                startActivityForResult(LocationActivity.class,0);
                break;
            default:
                break;
        }
    }

}
