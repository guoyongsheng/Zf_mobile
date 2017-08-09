package com.zfsoftmh.ui.modules.school_portal.school_map;

import android.util.Log;

import com.zfsoftmh.common.utils.HttpManager;
import com.zfsoftmh.entity.SchoolMapInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.SchoolPortalApi;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ljq on 2017/6/28.
 */

public class SchoolMapPresenter implements SchoolMapContract.Presenter{

    private SchoolMapContract.View view;
    private SchoolPortalApi schoolPortalApi;
    private HttpManager httpManager;
    private CompositeDisposable compositeDisposable;

   public SchoolMapPresenter(SchoolMapContract.View view,SchoolPortalApi schoolPortalApi, HttpManager httpManager){
       this.view=view;
       this.schoolPortalApi=schoolPortalApi;
       this.httpManager=httpManager;
       compositeDisposable = new CompositeDisposable();
       view.setPresenter(this);
   }

    @Override
    public void cancelRequest() {

    }

    @Override
    public void loadData() {
        httpManager.request(schoolPortalApi.getSchoolMapInfo(), compositeDisposable, view, new CallBackListener<ArrayList<SchoolMapInfo>>() {


            @Override
            public void onSuccess(ArrayList<SchoolMapInfo> data) {
                view.loadData(data);
            }

            @Override
            public void onError(String errorMsg) {
                view.lodaDataErr(errorMsg);
                Log.e("err",errorMsg);
            }
        });

    }



}
