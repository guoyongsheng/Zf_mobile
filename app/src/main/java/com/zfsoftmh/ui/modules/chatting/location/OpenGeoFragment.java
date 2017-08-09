package com.zfsoftmh.ui.modules.chatting.location;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.zfsoftmh.R;

import java.io.File;
import java.net.URISyntaxException;


/**
 * Created by li
 * on 2017/6/7.
 */

public class OpenGeoFragment extends Fragment implements View.OnClickListener {

    private MapView mMapView;
    private AMap aMap;
    private LatLng latLng;
    private String address;
    private TextView tv_address;
    private TextView tv_address_info;
    private ImageView goto_gaode;
    private  String info;

    public static OpenGeoFragment newInstance(){
        return  new OpenGeoFragment();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_opengeo,null);
        mMapView= (MapView) v.findViewById(R.id.map_geo_open);
        mMapView.onCreate(savedInstanceState);
        aMap=mMapView.getMap();
        tv_address= (TextView) v.findViewById(R.id.tv_info_poi);
        tv_address_info= (TextView) v.findViewById(R.id.tv_info_poi_info);
        goto_gaode= (ImageView) v.findViewById(R.id.iv_goto_gaode);
        goto_gaode.setOnClickListener(this);
        Bundle bundle=getArguments();
        if(bundle!=null){
            double latitude=bundle.getDouble("latitude");
            double longitude=bundle.getDouble("longitude");
            address=bundle.getString("address");
            latLng =new LatLng(latitude,longitude);
            int index=address.indexOf("n");
            tv_address.setText(address.substring(0,index));
            tv_address_info.setText(address.substring(index+1));
            info=address.substring(0,index);
            MoveCameratToLatLng();
        }
        return v;
    }


       private void MoveCameratToLatLng(){
           CameraPosition position =  CameraPosition.builder().target(latLng).zoom(20f).build();
           aMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
           MarkerOptions options=new MarkerOptions();
           options.position(latLng);
           aMap.clear();
           aMap.addMarker(options);
   }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }


    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.iv_goto_gaode){
            openGaoDeMap(getActivity(),info,latLng.latitude+"",latLng.longitude+"");
        }

    }



    /**
     * 打开高德地图
     * @param activity
     * @param name 目的地名称
     * @param lat 纬度
     * @param log 经度
     */
    public static void openGaoDeMap(Activity activity, String name, String lat, String log)
    {
        try
        {
            if(isInstallByread(AMAP_PACKAGENAME)) {
                Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=移动校园&poiname=" + name + "&lat=" + lat + "&lon=" + log + "&dev=0");
                activity.startActivity(intent);
            }else {
                Toast.makeText(activity.getApplicationContext(),"您还没有安装高德地图客户端，无法打开高德地图",Toast.LENGTH_SHORT).show();
           //     Log.e("GasStation", "没有安装高德地图客户端") ;
            }
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    //高德地图应用包名
    public static final String AMAP_PACKAGENAME = "com.autonavi.minimap";

    /**
     * 判断是否安装了某应用
     * @param packageName
     * @return
     */
    public static boolean isInstallByread(String packageName) {

        return new File("/data/data/" + packageName).exists();
    }
}
