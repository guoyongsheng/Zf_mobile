package com.zfsoftmh.ui.modules.chatting.location;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zfsoftmh.R;

import java.util.ArrayList;


/**
 * Created by sy
 * on 2017/5/31.
 */
public class LocationFragment extends Fragment implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener, LocationPoiAdapter.MyItemClickListener {
    private Context context;
   private RecyclerView recyclerView;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private  AMapLocationClientOption mLocationOption;
    private PoiSearch poiSearch;
    private LocationPoiAdapter adapter;
    private ArrayList<PoiItem> poiList;
    private PoiItem choiceItem;


    public static LocationFragment newInstance(){
        return new LocationFragment();
    }

    private PoiChoiceCallBack poiChoiceCallBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        poiChoiceCallBack= (PoiChoiceCallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_location,null);
        recyclerView= (RecyclerView) v.findViewById(R.id.location_recyclerview);
        setHasOptionsMenu(true);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new MyDecoration(getActivity(),MyDecoration.VERTICAL_LIST));
        mapView= (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        poiList=new ArrayList<>();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setOnCameraChangeListener(this);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(true);//设置定位只定位一次
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }



    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null&&amapLocation != null) {
            if (amapLocation != null
                    &&amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                CameraUpdateFactory cuf=new CameraUpdateFactory();
               /* CameraUpdate cup= cuf.changeLatLng(new LatLng (amapLocation.getLatitude(),amapLocation.getLongitude()));
                aMap.moveCamera(cup);*/
                CameraPosition position =  CameraPosition.builder().target(new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude())).zoom(20f).build();
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
                //如果定位成功 那么获取周边poi信息
                searchPoiByLatLon(amapLocation.getLatitude(),amapLocation.getLongitude());
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    private  void searchPoiByLatLon(double latitude,double longtitude){
        PoiSearch.Query query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,longtitude), 500));
        poiSearch.searchPOIAsyn();

    }



    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                adapter=new LocationPoiAdapter(result.getPois());
                poiList=result.getPois();
                choiceItem=poiList.get(0);
                recyclerView.setAdapter(adapter);
                adapter.SetOnItemClickListener(this);
        }
    }
    }




    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {


    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        searchPoiByLatLon(cameraPosition.target.latitude,cameraPosition.target.longitude);
        MarkerOptions options=new MarkerOptions();
        options.position(new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude));
        aMap.clear();
        aMap.addMarker(options);
    }

    @Override
    public void setOnMyItemClickListener(View v, int position) {
        LatLonPoint latLonPoint=poiList.get(position).getLatLonPoint();
        choiceItem=poiList.get(position);
        LatLng latLng=new LatLng(poiList.get(position).getLatLonPoint().getLatitude(),poiList.get(position).getLatLonPoint().getLongitude());
        MarkerOptions options=new MarkerOptions();
        options.position(latLng);
        aMap.clear();
        aMap.addMarker(options);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sure, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        poiChoiceCallBack.onPoiChoice(choiceItem);
        return super.onOptionsItemSelected(item);
    }
}
