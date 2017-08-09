package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.mine.ordersubmit;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.DateUtils;
import com.zfsoftmh.common.utils.DbHelper;
import com.zfsoftmh.common.utils.GsonHelper;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.EateryInfo;
import com.zfsoftmh.entity.FoodInfo;
import com.zfsoftmh.entity.OrderFoodInfo;
import com.zfsoftmh.entity.OrderInfo;
import com.zfsoftmh.entity.User;
import com.zfsoftmh.entity.UserInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.AgencyMattersDialogFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.AddressChange.AddressChangeActvity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by li
 * on 2017/7/31.
 */

public class OrderSubmitFragment extends BaseFragment<OrderSubmitPresenter> implements OrderSubmitContract.View, TimePickerDialog.OnTimeSetListener, View.OnClickListener, AgencyMattersDialogFragment.OnItemClickListener {
    TextView tv_address;
    TextView tv_addressdetail;
    TextView ordertime;
    RecyclerView recyclerView;
    EateryInfo eateryInfo;
    List<FoodInfo> order;
    OrderSubmitAdapter adapter;
    String totalPrice;
    LinearLayout ll_picker_time;
    String addressId;
    RelativeLayout rl_address;
    Calendar calendar;
    String finalDate;
    String time;
    TextView order_submit_totalprice;
    TextView pepole_count;
    TextView Submit;


    public static OrderSubmitFragment newInstance(EateryInfo info, List<FoodInfo> order, String totalprice) {
        OrderSubmitFragment fragment = new OrderSubmitFragment();
        if (info != null && order != null && order.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("info", info);
            bundle.putSerializable("order", (Serializable) order);
            bundle.putString("totalPrice", totalprice);
            fragment.setArguments(bundle);
        }
        return fragment;
    }


    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_ordersubmit;
    }

    @Override
    protected void initViews(View view) {
        rl_address = (RelativeLayout) view.findViewById(R.id.rl_address);
        recyclerView = (RecyclerView) view.findViewById(R.id.order_recycler);
        ordertime = (TextView) view.findViewById(R.id.order_time);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_addressdetail = (TextView) view.findViewById(R.id.tv_address_detail);
        ll_picker_time = (LinearLayout) view.findViewById(R.id.picker_time);
        order_submit_totalprice= (TextView) view.findViewById(R.id.order_submit_totalprice);
        Submit= (TextView) view.findViewById(R.id.tv_ordersubmit_submit);
        pepole_count= (TextView) view.findViewById(R.id.pepole_count);
        getAddress();
        Bundle bundle = getArguments();
        if (bundle != null) {
            eateryInfo = bundle.getParcelable("info");
            order = (List<FoodInfo>) bundle.getSerializable("order");
            totalPrice = bundle.getString("totalPrice");
        }

        totalPrice=""+(Double.valueOf(totalPrice)+Double.valueOf(eateryInfo.getLunchBox()));
        order_submit_totalprice.setText("￥"+totalPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderSubmitAdapter(getContext(), totalPrice, eateryInfo.getCanteenName());
        FoodInfo box=new FoodInfo();
        box.setCount(1);
        box.setFoodName("餐盒");
        box.setPrice(eateryInfo.getLunchBox());
        order.add(box);
        adapter.addData(order);
        recyclerView.setAdapter(adapter);

    }

    private void getAddress() {
        String address = SharedPreferenceUtils.readString(getContext(), "address", "first");
        String address_detail = SharedPreferenceUtils.readString(getContext(), "address", "second");
        addressId = SharedPreferenceUtils.readString(getContext(), "address", "id");
        if (addressId == null || addressId == "") {
            tv_address.setText("请选择地址");
        } else {
            tv_address.setText(address);
            tv_addressdetail.setText(address_detail);
        }
    }


    @Override
    protected void initListener() {
        ll_picker_time.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        pepole_count.setOnClickListener(this);
        Submit.setOnClickListener(this);

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date dateBefore=calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        Date date=calendar.getTime();
        String date2=DateUtils.getFullTimeStr(calendar);
        if (calendar.getTime().before(dateBefore)) {
            showToastMsgShort("您只能选择30分钟之后的时间！");
        } else {
            finalDate = date2;
            ordertime.setText("预计送达时间"+finalDate);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.picker_time) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30);
            int Hour = calendar.get(Calendar.HOUR_OF_DAY);
            int Minutes = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this, Hour, Minutes, true);
            timePickerDialog.show();
        } else if (v.getId() == R.id.rl_address) {
           startActivityForResult(AddressChangeActvity.class, 0);
           //


        }else if(v.getId()==R.id.pepole_count){
            showPeople();
        }else if(v.getId()==R.id.tv_ordersubmit_submit){
            submitorder();
        }

    }


    List<String> list;

    private void showPeople(){
        AgencyMattersDialogFragment fragment = AgencyMattersDialogFragment.newInstance();
        list=new ArrayList<>();
        for(int i=1;i<10;i++){
            list.add(""+i);
        }
        list.add("10人以上");
        fragment.setData(list);
        fragment.setOnItemClickListener(this);
        fragment.show(getFragmentManager(), "OrderSubmitActivity");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eateryInfo = null;
        order = null;
        totalPrice = "";

    }
//选人
    @Override
    public void onItemClick(int position) {
        pepole_count.setText(list.get(position));
    }


    @Override
    public void submitorder() {
        OrderInfo info=new OrderInfo();
        info.setAddressid(addressId);
        info.setArrivetime(finalDate);
        info.setCanteenid(eateryInfo.getCanteenId());
        info.setSummation(totalPrice);
        info.setPersonnumber(pepole_count.getText().toString());
        info.setDescription("  ");
        info.setUserid(DbHelper.getUserId(getContext()));
        List<OrderFoodInfo> orderFoodInfos=new ArrayList<>();
        for(int i=0;i<order.size();i++){
            OrderFoodInfo foodInfo=new OrderFoodInfo();
            foodInfo.setAmount(order.get(i).getCount()+"");
            foodInfo.setFoodid(order.get(i).getFoodId());
            orderFoodInfos.add(foodInfo);
        }
        info.setList(orderFoodInfos);
        String data= GsonHelper.objectToString(info);
        presenter.SubmitOrder(data);
    }

    @Override
    public void SubmitOrderSucess(String err) {
        Toast.makeText(getContext(),err,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SubmitOrderErr(String err) {
        Toast.makeText(getContext(),err,Toast.LENGTH_SHORT).show();
    }
}
