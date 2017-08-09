package com.zfsoftmh.ui.modules.school_portal.school_map;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.AMapUtil;
import com.zfsoftmh.entity.SchoolMapInfo;
import com.zfsoftmh.ui.base.BaseActivity;
import com.zfsoftmh.ui.modules.school_portal.school_map.Overlay.PoiOverlay;
import com.zfsoftmh.ui.modules.school_portal.school_map.poi_detail.PoiDetailActivity;
import com.zfsoftmh.ui.modules.school_portal.school_map.routeplan.RoutePlanActivity;
import com.zfsoftmh.ui.modules.school_portal.school_map.search_map.SearchMapActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.EasyPermissions;



/**
 * @author wesley
 * @date: 2017-6-27
 * @Description: 校园地图界面
 */

public class SchoolMapActivity extends BaseActivity implements SchoolMapContract.View,
        LocationSource, AMapLocationListener, AMap.OnPOIClickListener, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {

    private MapView mapView;

    private TextView swicthSchool;
    private TextView searchButton;
    private TextView keyword;

    private LinearLayout ll_poi_info;
    private TextView poi_name;
    private TextView poi_info;
    private TextView changePoiDetail;
    private TextView GoThere;

    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLonPoint mSouthWestPoint;
    private LatLonPoint mNorthEestPoint;
    private final int REQUEST_CODE = 0;
    LatLonPoint mStartPoint;
    LatLonPoint mEndPoint;//如果路径规划这个marker所在的点就是路径规划的终点
    @Inject
    SchoolMapPresenter presenter;


    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_school_map;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setDisplayHomeAsUpEnabled(true);
        EasyPermissions.requestPermissions(this, "需要定位权限", REQUEST_CODE, needPermissions);
        mapView = (MapView) findViewById(R.id.school_map_view);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setLocationSource(this);
        //定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        //  aMap.setOnCameraChangeListener(this);
        aMap.setOnPOIClickListener(this);

        swicthSchool = (TextView) findViewById(R.id.switchSchool);
        searchButton = (TextView) findViewById(R.id.searchButton);
        keyword = (TextView) findViewById(R.id.keyWord);
        ll_poi_info = (LinearLayout) findViewById(R.id.info);
        ll_poi_info.setVisibility(View.GONE);

        poi_name = (TextView) findViewById(R.id.loc_name);
        poi_info = (TextView) findViewById(R.id.loc_info);
        changePoiDetail = (TextView) findViewById(R.id.info_detail);
        changePoiDetail.setOnClickListener(this);
        GoThere = (TextView) findViewById(R.id.info_go);
        GoThere.setOnClickListener(this);


    }


    @Override
    protected void setUpInject() {
        DaggerSchoolMapComponent.builder()
                .appComponent(getAppComponent())
                .schoolMapPresentModule(new SchoolMapPresentModule(this))
                .build()
                .inject(this);

        presenter.loadData();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void onNavigationIconClick() {
        super.onNavigationIconClick();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void setPresenter(SchoolMapPresenter presenter) {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void showProgressDialog(String msg) {

    }

    @Override
    public void hideProgressDialog() {

    }


    PopupWindow popupWindow;

    /**
     * 切换校区
     *
     * @param view
     */
    public void switchschool(View view) {
        if (nameList != null && nameList.size() > 0) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.school_zone, null);
            popupWindow = new PopupWindow(findViewById(R.id.lv_school_zone), 300, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(contentView);

            ListView listView = (ListView) contentView.findViewById(R.id.lv_school_zone);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = nameList.get(position);
                    mSouthWestPoint = AMapUtil.convertToLatLonPoint(latLngsList.get(position * 2 + 1));
                    mNorthEestPoint = AMapUtil.convertToLatLonPoint(latLngsList.get(position * 2));
                    changeMapView(latLngsList.get(position * 2 + 1), latLngsList.get(position * 2));
                    popupWindow.dismiss();
                }
            });
            contentView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                    return false;
                }
            });

            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(swicthSchool);
        }
    }

    public void changeMapView(LatLng swest, LatLng neast) {
        aMap.moveCamera(CameraUpdateFactory
                .newLatLngBounds(LatLngBounds.builder().include(neast).include(swest).build(), 50));

    }


    /**
     * 跳转至搜索界面
     *
     * @param view
     */
    public void change_to_search(View view) {
      //  mNorthEestPoint=new LatLonPoint(30.31439469,120.09236813);
      //  mSouthWestPoint =new LatLonPoint(30.29497989,120.08103848);
        if(mSouthWestPoint!=null && mNorthEestPoint!=null) {
            Intent intent = new Intent(this, SearchMapActivity.class);
            intent.putExtra("SouthWest", mSouthWestPoint);
            intent.putExtra("NorthEast", mNorthEestPoint);
            openActivityForResult(intent, 0);
        }else{
            showToastMsgShort("对不起，由于没有获取到校区信息，无法进入搜索页面！");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            Bundle b = data.getExtras();
            item = b.getParcelable("PoiItem");
            PoiName=item.getTitle();
            AddMarkerToMap(item, 1);

        }
    }


    PoiOverlay poiOverlay = null;

    /**
     * 添加marker
     *
     * @param item 要添加的marker的poiitem
     * @param flag 是否镜头移到当前视角
     */
    private void AddMarkerToMap(PoiItem item, int flag) {
        mEndPoint = item.getLatLonPoint();
        ArrayList<PoiItem> mpoi = new ArrayList<>();
        mpoi.add(0, item);
        if (marker != null) {
            marker.remove();
        }
        if (poiOverlay != null) {
            poiOverlay.removeFromMap();
            poiOverlay.AddMark(mpoi);
        } else {
            poiOverlay = new PoiOverlay(aMap, mpoi);
        }
        poiOverlay.addToMap();
        if (flag == 1) {
            poiOverlay.zoomToSpan();
        }
        poi_name.setText(item.getTitle());
        poi_info.setText(item.getSnippet());
        ll_poi_info.setVisibility(View.VISIBLE);
    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
       //     mLocationOption.setOnceLocation(true);
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

    String city;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                CameraUpdateFactory cuf = new CameraUpdateFactory();
               /* CameraUpdate cup= cuf.changeLatLng(new LatLng (amapLocation.getLatitude(),amapLocation.getLongitude()));
                aMap.moveCamera(cup);*/
                city = amapLocation.getCity();
                mStartPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
         //       CameraPosition position = CameraPosition.builder().target(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())).zoom(20f).build();
          //      aMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Toast.makeText(this, errText, Toast.LENGTH_SHORT).show();
                Log.e("AmapErr", errText);
            }
        }
    }


    Marker marker;
    String PoiName;
    @Override
    public void onPOIClick(Poi poi) {
        PoiName= poi.getName();

        LatLng latLng = poi.getCoordinate();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        //  aMap.clear();
        if (marker != null) {
            marker.remove();
        }
        if (poiOverlay != null) {
            poiOverlay.removeFromMap();
        }
        markerOptions.visible(true);
        marker = aMap.addMarker(markerOptions);
        LatLonPoint latLonPoint = AMapUtil.convertToLatLonPoint(poi.getCoordinate());
        mEndPoint = latLonPoint;
        poi_name.setText(PoiName);
        regeocodeSearch(latLonPoint);

    }

    private void regeocodeSearch(LatLonPoint point) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, 100, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }


    //这里获取到所有的数据
    ArrayList<String> nameList;//校区名列表
    ArrayList<LatLng> latLngsList;//校区对应的经纬度列表

    @Override
    public void lodaDataErr(String err) {
        showToastMsgShort("获取校区信息失败," + err);
    }

    @Override
    public void loadData(ArrayList<SchoolMapInfo> data) {
        nameList = new ArrayList<>();
        latLngsList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            nameList.add(i, data.get(i).getName());
            LatLng latLng1 = new LatLng(data.get(i).getPointList().get(0).getX(), data.get(i).getPointList().get(0).getY());
            LatLng latLng2 = new LatLng(data.get(i).getPointList().get(1).getX(), data.get(i).getPointList().get(1).getY());
            latLngsList.add(latLng1);
            latLngsList.add(latLng2);
        }
        mSouthWestPoint = AMapUtil.convertToLatLonPoint(latLngsList.get(1));
        mNorthEestPoint = AMapUtil.convertToLatLonPoint(latLngsList.get(0));
        changeMapView(latLngsList.get(1), latLngsList.get(0));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.info_go) {
            if (mStartPoint != null && mEndPoint != null) {
                Intent intent = new Intent(this, RoutePlanActivity.class);
                intent.putExtra("startpoint", mStartPoint);
                intent.putExtra("endpoint", mEndPoint);
                intent.putExtra("city", city);
                startActivity(intent);
            }
        }else if(i ==R.id.info_detail){
            if(item!=null){
                Intent intent =new Intent(this, PoiDetailActivity.class);
                intent.putExtra("poiitem",item);
                intent.putExtra("poiname",PoiName);
                startActivity(intent);
            }

        }

    }

    /**
     * 将要传入poi详情页面的poiitem
     */
    PoiItem item;
    /**
     * //标志位，标志反地理编码搜索结果和点击的poi是否已匹配，匹配的话就传值给item，没有匹配的就直接将第一个传给item
     */
    boolean isSucess=false;
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (rCode == 1000) {
            String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            List<PoiItem> list=regeocodeResult.getRegeocodeAddress().getPois();
            for(int i=0;i<list.size();i++){
                PoiItem items=list.get(i);
                if(items.getTitle().contains(PoiName)){
                    item=items;
                    isSucess=true;
                    break;
                }
            }
            poi_info.setText(address);
            ll_poi_info.setVisibility(View.VISIBLE);
            if(isSucess){
                isSucess=false;
            }else{
                item=list.get(0);
            }
        }else{
            showToastMsgShort("获取Marker信息失败");
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    public void location(View view) {
        if(mStartPoint!=null){
            CameraPosition position =  CameraPosition.builder().target(AMapUtil.convertToLatLng(mStartPoint)).zoom(15f).build();
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        }else{
            showToastMsgShort("定位失败！");
        }
    }
}
