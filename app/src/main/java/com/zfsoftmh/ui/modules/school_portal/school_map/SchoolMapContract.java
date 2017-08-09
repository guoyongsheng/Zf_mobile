package com.zfsoftmh.ui.modules.school_portal.school_map;

import com.zfsoftmh.entity.SchoolMapInfo;
import com.zfsoftmh.ui.base.BasePresenter;
import com.zfsoftmh.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by ljq on 2017/6/28.
 */

public interface SchoolMapContract {
    interface  View extends BaseView<SchoolMapPresenter>{

      void lodaDataErr(String err);

     void loadData(ArrayList<SchoolMapInfo> data);


    }


    interface Presenter extends BasePresenter{
        void loadData();
    }




}
