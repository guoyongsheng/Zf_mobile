package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteBusWalkItem;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.zfsoftmh.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ljq on 2017/7/6.
 * 公交规划结果页面，包含所有的规划结果列表
 */
public class BusResultFragment extends Fragment implements RouteSearch.OnRouteSearchListener, AdapterView.OnItemClickListener {

    private ListView resultList;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private int BusMode= RouteSearch.BUS_DEFAULT;
    String city="";
    private RouteSearch mRouteSearch;

    public static BusResultFragment newsInstance(LatLonPoint startPoint, LatLonPoint endPoint){
        BusResultFragment frg=new BusResultFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("startpoint",startPoint);
        bundle.putParcelable("endpoint",endPoint);
        frg.setArguments(bundle);
        return  frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_busresult,container,false);
        resultList= (ListView) view.findViewById(R.id.buslist);
        if(getArguments()!=null){
            mStartPoint=getArguments().getParcelable("startpoint");
            mEndPoint=getArguments().getParcelable("endpoint");
        }
        city=getActivity().getIntent().getStringExtra("city");
        mRouteSearch = new RouteSearch(getActivity());
        mRouteSearch.setRouteSearchListener(this);
        SearchBusRoute();
        return view;
    }


    private void SearchBusRoute(){
      final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        RouteSearch.BusRouteQuery query=new RouteSearch.BusRouteQuery(fromAndTo,BusMode,city,0);
        mRouteSearch.calculateBusRouteAsyn(query);
    }


    private ArrayList<BusRouteInfo> infoList=null;
    List<BusPath> pathList ;
    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                infoList=new ArrayList<BusRouteInfo>();
                 pathList = result.getPaths();
                /****路线在这里分开***/
                for (int j = 0; j < pathList.size(); j++) {
                    ArrayList<BusStationInfo> busStationInfos=new ArrayList<>();
                    BusPath busPath = pathList.get(j);
                    BusRouteInfo info = new BusRouteInfo();
                    info.setWalk_distance(busPath.getWalkDistance());
                    info.setCost(busPath.getCost());
                    List<BusStep> busStepList = busPath.getSteps();
                    float time = 0;
                    String BusName = "";
                    /***某条路线的公交路线的N个路段***/

                    for (int i = 0; i < busStepList.size(); i++) {
                        BusStep step = busPath.getSteps().get(i);
                        List<RouteBusLineItem> lineItems = step.getBusLines();
                        RouteBusWalkItem item=step.getWalk();

                        /****在某个路段里面的具体公交路线信息****/
                        for (int z = 0; z < lineItems.size(); z++) {
                            BusStationInfo stationInfo=new BusStationInfo();
                            RouteBusLineItem route = step.getBusLines().get(z);
                            String startStation = route.getDepartureBusStation().getBusStationName();
                            String arrivalStation = route.getArrivalBusStation().getBusStationName();
                            String busname=route.getBusLineName();
                            int index = busname.indexOf("(");
                            busname = busname.substring(0, index);
                            stationInfo.setArrivalStation(arrivalStation);
                            stationInfo.setDepartStation(startStation);
                            if (z > 0) {
                                BusName = BusName +"/"+ busname;
                            }else {
                                if(BusName==""){
                                    BusName = BusName + busname;
                                }else {
                                    BusName = BusName + "-->"+ busname;
                                }
                            }
                            time = time + route.getDuration();
                            stationInfo.setBusName(busname);
                            busStationInfos.add(i,stationInfo);
                        }
                        info.setBusname(BusName);
                        info.setTime(time);
                        info.setBusStationInfos(busStationInfos);
                    }
                   infoList.add(j,info);
                }
                resultList.setAdapter(new BusResultAdapter(getActivity().getApplicationContext(),infoList,0));
                resultList.setOnItemClickListener(this);
            }
        }else{
            Toast.makeText(getContext(), "由于路程太远或太近的原因，没有搜索到相关数据！", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BusPath buspth=pathList.get(position);
        Intent intent=new Intent();
        intent.setClass(getActivity(),BusRoutesActivity.class);
        intent.putExtra("startpoint",mStartPoint);
        intent.putExtra("endpoint",mEndPoint);
        intent.putExtra("buspath",buspth);
        startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

}
