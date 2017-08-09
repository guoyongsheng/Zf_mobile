package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteBusWalkItem;
import com.amap.api.services.route.WalkStep;
import com.zfsoftmh.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ljq on 2017/1/17.
 * 公交车路线规划某一条结果的详细信息 包含往哪里走 在哪里上下车等等的列表
 */
public class BusDetailActivity extends Activity implements View.OnClickListener {

    private BusPath mBusPath;
    private ListView mListView;
    private ArrayList<String> mList=new ArrayList<>();
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_busdetail);
        Intent intent=getIntent();
        mBusPath=intent.getParcelableExtra("buspath");
        mListView= (ListView) findViewById(R.id.pathlist);
        back= (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        getInfo();
    }


    private void getInfo(){
        List<BusStep> busStepList = mBusPath.getSteps();
        float time = 0;
        String BusName = "";
        /***某条路线的公交路线的N个路段***/
        for (int i = 0; i < busStepList.size(); i++) {
            BusStep step = mBusPath.getSteps().get(i);
            List<RouteBusLineItem> lineItems = step.getBusLines();
            RouteBusWalkItem item=step.getWalk();
            List<WalkStep> list_walk=item.getSteps();
            for(int k=0;k<list_walk.size();k++){
                WalkStep walkStep=list_walk.get(k);
                String walkrounte= walkStep.getInstruction();
                mList.add(walkrounte);
            }
            /****在某个路段里面的具体公交路线信息****/
            for (int z = 0; z < lineItems.size(); z++) {
                RouteBusLineItem route = step.getBusLines().get(z);
                String startStation = route.getDepartureBusStation().getBusStationName()+"上车";
                String arrivalStation = route.getArrivalBusStation().getBusStationName()+"下车";
                String busname = route.getBusLineName();
                mList.add(busname);
                mList.add(startStation);
                mList.add(arrivalStation);
               /* int index = busname.indexOf("(");
                busname = busname.substring(0, index);
                if (z > 0) {
                    BusName = BusName + "/" + busname;
                } else {
                    if (BusName == "") {
                        BusName = BusName + busname;
                    } else {
                        BusName = BusName + "-->" + busname;
                    }
                }*/
                time = time + route.getDuration();
            }
            mListView.setAdapter(new BusListAdapter(this,mList));
        }

    }

    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.back){
            this.finish();
        }
    }
}
