package com.zfsoftmh.ui.modules.personal_affairs.school_eatery.addressmanager.addaddress;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.UserAddressInfo;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ljq on 2017/7/20.
 */

public class AddAddressFragment extends BaseFragment<AddAddressPresenter> implements AddAddressContract.View, View.OnClickListener {

    EditText et_name;
    EditText et_phone;
    EditText et_schoolname;
    EditText et_detailaddress;
    Button deletebutton;

    UserAddressInfo info;
    int OpreateType=0;//0是新建，1是修改，2是删除

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    public static AddAddressFragment newInstance(UserAddressInfo info){
        AddAddressFragment fragment= new AddAddressFragment();
        if(info!=null){
            Bundle b=new Bundle();
            b.putParcelable("address",info);
            fragment.setArguments(b);
        }
        return  fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_addaddress;
    }

    @Override
    protected void initViews(View view) {
        et_name= (EditText) view.findViewById(R.id.et_purchaser_name);
        et_phone= (EditText) view.findViewById(R.id.et_purchaser_phone);
        et_schoolname= (EditText) view.findViewById(R.id.et_purchaser_schoolname);
        et_detailaddress= (EditText) view.findViewById(R.id.et_purchaser_detailaddress);
        deletebutton= (Button) view.findViewById(R.id.address_delete);
        deletebutton.setOnClickListener(this);
        Bundle bundle=getArguments();
        if(bundle!=null){
            info=bundle.getParcelable("address");
            if(info!=null){
                deletebutton.setVisibility(View.VISIBLE);
                setText();
            }
        }


    }

    private  void setText(){
        et_name.setText(info.getName());
        et_phone.setText(info.getMobilePhone());
        et_schoolname.setText(info.getSchoolName());
        et_detailaddress.setText(info.getSpecificAddress());
    }








    @Override
    protected void initListener() {

    }


    public void check(int type){
        if(TextUtils.isEmpty(et_name.getText().toString())){
            Toast.makeText(getContext(),R.string.addaddress_noname,Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(et_phone.getText().toString())){
            Toast.makeText(getContext(),R.string.addaddress_nophone,Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(et_schoolname.getText().toString())){
            Toast.makeText(getContext(),R.string.addaddress_noschoolname,Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(et_detailaddress.getText().toString())){
            Toast.makeText(getContext(),R.string.addaddress_nodetail,Toast.LENGTH_SHORT).show();
        }else{
            if(type==0){
                submitAddress();
            }else{
                editAddress();
            }

        }
    }




    @Override
    public void OpreateSuccess(String msg) {
        hideProgressDialog();
        Toast.makeText(getContext(),"成功！",Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void OpreateErr(String err) {
        hideProgressDialog();
        Toast.makeText(getContext(),err,Toast.LENGTH_LONG).show();
    }

    @Override
    public void editAddress() {
        showProgressDialog("正在提交请稍后！");
        OpreateType=1;
        Map<String,String> params=new LinkedHashMap<>();
        params.put("method","update");
        params.put("name",et_name.getText().toString());
        params.put("addressid",info.getAddressId());
        params.put("mobilephone",et_phone.getText().toString());
        params.put("schoolname",et_schoolname.getText().toString());
        params.put("specificaddress",et_detailaddress.getText().toString());
        presenter.edtiAddress(params);
    }

    @Override
    public void deleteAddress() {
        showProgressDialog("正在删除请稍后！");
        OpreateType=2;
        Map<String,String> params=new LinkedHashMap<>();
        params.put("addressid",info.getAddressId());
        presenter.deleteAddress(params);

    }

    @Override
    public void submitAddress() {
        showProgressDialog("正在提交请稍后！");
        OpreateType=0;
        Map<String,String> params=new LinkedHashMap<>();
        params.put("method","add");
        params.put("name",et_name.getText().toString());
        params.put("mobilephone",et_phone.getText().toString());
        params.put("schoolname",et_schoolname.getText().toString());
        params.put("specificaddress",et_detailaddress.getText().toString());
        presenter.submitAddress(params);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.address_delete){

            deleteAddress();

        }

    }






}
