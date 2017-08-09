package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amap.api.services.core.LatLonPoint;
import com.zfsoftmh.R;

/**
 * Created by li on 2017/7/3.
 * 路径规划主页面
 */
public class RoutePlanActivity extends FragmentActivity implements View.OnClickListener {
    public static final int R_WALK=0;
    public static final int R_CAR=1;
    public static final int R_BUS=2;

    private ImageView car;
    private ImageView bus;
    private ImageView walk;
    private Button back;

    private RouteFragment CarRouteFragment;
    private RouteFragment WalkRouteFragment;
    private BusResultFragment busResultFragment;


    private String city;
    private LatLonPoint mStartPoint;
    private  LatLonPoint mEndPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeplan);
        back= (Button) findViewById(R.id.routeplan_back);
        car= (ImageView) findViewById(R.id.ways_car);
        bus= (ImageView) findViewById(R.id.ways_bus);
        walk= (ImageView) findViewById(R.id.ways_walker);
        back.setOnClickListener(this);
        car.setOnClickListener(this);
        bus.setOnClickListener(this);
        walk.setOnClickListener(this);
        mStartPoint=getIntent().getParcelableExtra("startpoint");
        mEndPoint=getIntent().getParcelableExtra("endpoint");
        setDefaultFragment();
        switchTabItemState(R_WALK);

    }


    public void setDefaultFragment(){
        WalkRouteFragment = new RouteFragment();
        Bundle b = new Bundle();
        b.putInt("type",R_WALK);
        WalkRouteFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.routeplan_content,WalkRouteFragment).commit();

    }

    protected void switchTabItemState(int tag) {
        switch (tag) {
            case R_CAR:
                setItemIcon(bus,R.mipmap.gray_bus);
                setItemIcon(car,R.mipmap.white_car);
                setItemIcon(walk,R.mipmap.gray_walk);
                break;
            case R_BUS:
                setItemIcon(bus,R.mipmap.white_bus);
                setItemIcon(car,R.mipmap.gray_car);
                setItemIcon(walk,R.mipmap.gray_walk);
                break;
            case R_WALK:
                setItemIcon(bus,R.mipmap.gray_bus);
                setItemIcon(car,R.mipmap.gray_car);
                setItemIcon(walk,R.mipmap.white_walk);

                break;
            default:
                break;
        }
    }
    private void setItemIcon(ImageView tabItem, int iconResId) {
        tabItem.setBackgroundResource(iconResId);
        //  tabItem.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int i = v.getId();
        if (i == R.id.ways_bus) {
            switchTabItemState(R_BUS);
            transaction.hide(WalkRouteFragment);
            if(CarRouteFragment != null){
                transaction.hide(CarRouteFragment);
            }

            if(busResultFragment==null){
                busResultFragment= BusResultFragment.newsInstance(mStartPoint,mEndPoint);
                /*******/
                transaction.add(R.id.routeplan_content,busResultFragment).commit();
            }else{
                transaction.show(busResultFragment).commit();
            }
        }

        if(i == R.id.ways_car){
            switchTabItemState(R_CAR);
            transaction.hide(WalkRouteFragment);

            if (busResultFragment != null) {
                transaction.hide(busResultFragment);
            }
            if (CarRouteFragment == null){
                CarRouteFragment = new RouteFragment();
                Bundle b = new Bundle();
                b.putInt("type",R_CAR);
                CarRouteFragment.setArguments(b);
                transaction.add(R.id.routeplan_content,CarRouteFragment).commit();
            }else{
                transaction.show(CarRouteFragment).commit();
            }
        }

        if(i == R.id.ways_walker){

            if (busResultFragment != null) {
                transaction.hide(busResultFragment);
            }
            switchTabItemState(R_WALK);
            if(CarRouteFragment != null){
                transaction.hide(CarRouteFragment);
            }
            transaction.show(WalkRouteFragment).commit();
        }

        if(i==R.id.routeplan_back){
            this.finish();
        }
    }



}

