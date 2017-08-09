package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.AMapUtil;
import com.zfsoftmh.ui.modules.school_portal.school_map.Overlay.BusRouteOverlay;

import java.util.List;




/**
 * Created by ljq on 2017/7/5.
 * 公交路线规划页面
 */
public class BusRoutesActivity extends Activity implements View.OnClickListener {


    private TextView busName;
    private TextView busInfo;
    private String info;
    private LinearLayout bus_detail;


    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private BusPath busPath;
    private MapView mMapView;
    private AMap aMap;
    private Button back;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_busroute);
        mMapView = (MapView) findViewById(R.id.busroute_map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mMapView.getMap();
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        Intent intent=getIntent();
        mStartPoint=intent.getParcelableExtra("startpoint");
        mEndPoint=intent.getParcelableExtra("endpoint");
        busPath=intent.getParcelableExtra("buspath");
        busName= (TextView) findViewById(R.id.name_bus);
        busInfo= (TextView) findViewById(R.id.name_info);
        bus_detail= (LinearLayout) findViewById(R.id.detail);
        bus_detail.setOnClickListener(this);
        back= (Button) findViewById(R.id.busroute_back);
        back.setOnClickListener(this);
        CameraPosition position =  CameraPosition.builder().target(AMapUtil.convertToLatLng(mStartPoint)).zoom(12f).build();
        LatLng temp=new LatLng(mStartPoint.getLatitude(),mStartPoint.getLongitude());
        MarkerOptions markerOption=new MarkerOptions();
        markerOption.position(temp);
        aMap.clear();
        markerOption.visible(true);
        aMap.addMarker(markerOption);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        aMap.getUiSettings().setZoomControlsEnabled(false);
        BusRouteOverlay overlay= new BusRouteOverlay(this,aMap,busPath,mStartPoint,mEndPoint);
        overlay.removeFromMap();
        overlay.addToMap();
        overlay.zoomToSpan();
        getinfo();
    }
    private  void getinfo() {
        float cost=busPath.getCost();
        float distance=busPath.getWalkDistance();
        List<BusStep> busStepList = busPath.getSteps();
        float time = 0;
        String BusName = "";
        /***某条路线的公交路线的N个路段***/
        for (int i = 0; i < busStepList.size(); i++) {
            BusStep step = busPath.getSteps().get(i);
            List<RouteBusLineItem> lineItems = step.getBusLines();
            /****在某个路段里面的具体公交路线信息****/
            for (int z = 0; z < lineItems.size(); z++) {
                RouteBusLineItem route = step.getBusLines().get(z);
                String busname = route.getBusLineName();
                int index = busname.indexOf("(");
                busname = busname.substring(0, index);
                if (z > 0) {
                    BusName = BusName + "/" + busname;
                } else {
                    if (BusName == "") {
                        BusName = BusName + busname;
                    } else {
                        BusName = BusName + " > " + busname;
                    }
                }
                time = time + route.getDuration();
            }
            info=cost+"元"+"-"+"步行"+distance+"米";
            busInfo.setText(info);
            busName.setText(BusName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.detail) {
            Intent intent = new Intent();
            intent.setClass(this, BusDetailActivity.class);
            intent.putExtra("buspath", busPath);
            startActivity(intent);
        }
        if(i==R.id.busroute_back){
            this.finish();
        }
    }

}
