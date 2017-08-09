package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.TextureMapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.modules.school_portal.school_map.Overlay.DrivingRouteOverlay;
import com.zfsoftmh.ui.modules.school_portal.school_map.Overlay.WalkRouteOverlay;

/**
 * Created by ljq on 2017/7/6.
 * 路径规划地图的驾车和步行规划页面
 */

public class RouteFragment extends Fragment implements  RouteSearch.OnRouteSearchListener{

    private TextureMapView mMapView;
    private AMap aMap;
    private LatLonPoint mEndPoint;
    private LatLonPoint mStartPoint;
    private RouteSearch mRouteSearch;

    private int drivingMode = RouteSearch.DRIVING_SINGLE_DEFAULT;// 驾车默认模式

    boolean isFirstTimeIn = true;

    private int Type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type = getArguments().getInt("type");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_route,container,false);
        mMapView = (TextureMapView)view.findViewById(R.id.frag_carroute);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mMapView.getMap();
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        Type = getArguments().getInt("type");
        mStartPoint=getActivity().getIntent().getParcelableExtra("startpoint");
        mEndPoint=getActivity().getIntent().getParcelableExtra("endpoint");
        mRouteSearch = new RouteSearch(getActivity());
        mRouteSearch.setRouteSearchListener(this);
        searchRouteResult(Type);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((RoutePlanActivity)activity).onAttachedToWindow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            mMapView.setVisibility(View.GONE);
        }else{
            mMapView.setVisibility(View.VISIBLE);
        }
    }

    
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


    public void searchRouteResult(int routeType) {
        int ROUTE_TYPE_DRIVE = 1;
        int ROUTE_TYPE_WALK = 0;
        if (mStartPoint == null) {
            Toast.makeText(getContext(),"正在定位...",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEndPoint == null) {
            Toast.makeText(getContext(),"正在获取marker位置",Toast.LENGTH_SHORT).show();
        }

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, drivingMode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询

        }
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                DriveRouteResult driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(getActivity(), aMap, drivePath, mStartPoint, mEndPoint,null);
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();

            } else {
                Toast.makeText(getContext(),"对不起，没有搜索到相关数据！",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),"对不起，没有搜索到相关数据！",Toast.LENGTH_SHORT).show();
        }
    }






    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                WalkRouteResult walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(getActivity(), aMap, walkPath, mStartPoint, mEndPoint);
                walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            } else {
                Toast.makeText(getContext(),"对不起，没有搜索到相关数据",Toast.LENGTH_SHORT).show();
            }
        } else if (rCode == 1804) {
            Toast.makeText(getContext(),"搜索失败,请检查网络连接！",Toast.LENGTH_SHORT).show();
        } else if (rCode == 1002) {
            Toast.makeText(getContext(),"key验证无效！",Toast.LENGTH_SHORT).show();
        }else if (rCode == 3003) {
            Toast.makeText(getContext(),"步行算路起点、终点距离过长导致算路失败",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),"未知错误，请稍后重试!错误码为",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


}
