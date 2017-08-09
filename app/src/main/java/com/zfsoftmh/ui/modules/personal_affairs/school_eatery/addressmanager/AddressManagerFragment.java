package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.inspector.domstorage.SharedPreferencesHelper;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.SharedPreferenceUtils;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.entity.UserAddressInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress.AddAddressActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljq
 * on 2017/7/19.
 */

public class AddressManagerFragment extends BaseListFragment<AddressManagerPresenter,UserAddressInfo> implements AddressAdapter.OnMyItemClickListener,AddressManagerContract.View, View.OnClickListener {
    TextView  tv_newaddress;
    AddressAdapter adapter;
    List<UserAddressInfo> dataList;
    int finalChiocePos=-1;
    boolean isSwitchAddress=false;//false为切换地址，true为管理地址


    @Override
    protected void initVariables() {
        dataList=new ArrayList<>();
        Bundle bundle=getArguments();
        if(bundle!=null){
            isSwitchAddress=bundle.getBoolean("isSwitchAddress");
        }
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }
     public static AddressManagerFragment newInstance(boolean isSwitchAddress){
         AddressManagerFragment fragment=new AddressManagerFragment();
         Bundle bundle=new Bundle();
         bundle.putBoolean("isSwitchAddress",isSwitchAddress);
         fragment.setArguments(bundle);

     return fragment;
}




    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_addressmanager;
    }

   @Override
    protected void initViews(View view) {
       super.initViews(view);
       tv_newaddress= (TextView) view.findViewById(R.id.addressmanager_tv);
       tv_newaddress.setOnClickListener(this);



    }



    @Override
    protected void initListener() {

    }

    @Override
    protected RecyclerArrayAdapter<UserAddressInfo> getAdapter() {
        adapter=new AddressAdapter(getContext());
        adapter.setListener(this);
        adapter.setIschoice(isSwitchAddress);
        return adapter;
    }

    @Override
    protected void loadData() {
        getAddressList(start_page);
    }




    private void getAddressList(int page){
        Map<String,String> params= new LinkedHashMap<>();
        params.put("start",String.valueOf(page));
        params.put("size",String.valueOf(10));
        presenter.loadData(params);
    }

    @Override
    public void setOnMyItemClick(View view, int  pos,boolean isChioce) {
        if(!isChioce){
        Intent intent=new Intent(getActivity(),AddAddressActivity.class);
        intent.putExtra("address",dataList.get(pos));
        startActivity(intent);
        }else{
            finalChiocePos=pos;
            adapter.setPoschangecolor(finalChiocePos);
        }
    }



    @Override
    public void onItemClick(int position) {



    }

    @Override
    public void loadFailure(String errorMsg) {
        super.loadFailure(errorMsg);
    }

    @Override
    public void loadData(ResponseListInfo<UserAddressInfo> data) {
     if(data!=null ){
         loadSuccess(data);
         if(start_page==1){
             dataList.clear();
             dataList=data.getItemList();
         }else {
             dataList.addAll(data.getItemList()) ;
         }
         storage(0);

     }

    }


    private void storage(int pos){
        SharedPreferenceUtils.write(getContext(),"address","first",
                dataList.get(pos).getSchoolName()+dataList.get(pos).getSpecificAddress());
        SharedPreferenceUtils.write(getContext(),"address","second",
                dataList.get(pos).getName()+" "+dataList.get(pos).getMobilePhone());
        SharedPreferenceUtils.write(getContext(),"address","id",dataList.get(pos).getAddressId());
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addressmanager_tv){
            startActivity(AddAddressActivity.class);
        }
    }


    public void finish(){
        if(finalChiocePos!=-1) {
            storage(finalChiocePos);
            Intent intent=new Intent();
            intent.setFlags(1);
            getActivity().setResult(1,intent);
            getActivity().finish();
        }else{
            showToastMsgShort("请选择一个地址");
        }

    }


    public void setIschoice(boolean id){
        adapter.setIschoice(id);
    }

}
