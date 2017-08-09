package com.zfsoftmh.ui.modules.chatting.tribe;

import android.os.Handler;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.tribe.IYWTribeService;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;

import java.util.ArrayList;

/**
 * Created by sy
 * on 2017/5/12.
 */

public class TribePresenter implements TribeContract.Presenter {

    private static final long POST_DELAYED_TIME = 300;
    private TribeContract.View mView;
    private YWIMKit mIMKit;
    private ArrayList<YWTribe> dataList;

    private Handler handler = new Handler();
    private Runnable getDataRunnable = new Runnable() {
        @Override
        public void run() {
            IYWTribeService tribeService = getTribeService();
            if (tribeService != null){
                tribeService.getAllTribesFromServer(new IWxCallback() {
                    @Override
                    public void onSuccess(Object... objects) {
                        mView.onStopLoading();
                        dataList.clear();
                        dataList.addAll((ArrayList<YWTribe>) objects[0]);
                        mView.refreshAdapter(dataList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        mView.onStopLoading();
                        mView.showErrMsg("同步群组失败");
                    }

                    @Override
                    public void onProgress(int i) {

                    }
                });
            }
        }
    };


    public TribePresenter(TribeContract.View v){
        this.mView = v;
        mView.setPresenter(this);
        mIMKit = IMKitHelper.getInstance().getIMKit();
        dataList = new ArrayList<>();
    }

    @Override
    public IYWTribeService getTribeService() {
        return mIMKit.getTribeService();
    }

    @Override
    public void getAllTribesFromServer() {
        mView.onLoading();
        handler.postDelayed(getDataRunnable,POST_DELAYED_TIME);
    }

    @Override
    public void onDestroy() {
        mView = null;
        handler.removeCallbacks(getDataRunnable);
        handler = null;
    }

    @Override
    public void cancelRequest() {

    }
}
