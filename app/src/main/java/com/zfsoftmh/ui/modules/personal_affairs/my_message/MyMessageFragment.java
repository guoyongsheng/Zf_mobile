package com.zfsoftmh.ui.modules.personal_affairs.my_message;

import android.os.Bundle;

import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.entity.MyMessageItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/8/1
 * @Description: 我的消息
 */

public class MyMessageFragment extends BaseListFragment<MyMessagePresenter, MyMessageItemInfo> implements MyMessageContract.View {
    private MyMessageAdapter adapter;

    public static MyMessageFragment newInstance() {
        return new MyMessageFragment();
    }

    @Override
    protected RecyclerArrayAdapter<MyMessageItemInfo> getAdapter() {
        adapter = new MyMessageAdapter(context);
        return adapter;
    }

    @Override
    protected void loadData() {
        loadMyMessageList(start_page, PAGE_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void loadMyMessageList(int start_page, int PAGE_SIZE) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", String.valueOf(start_page));
        params.put("size", String.valueOf(PAGE_SIZE));
        presenter.getMyMessageList(params);

    }

    @Override
    public void loadMyMessageListSuccess(ResponseListInfo<MyMessageItemInfo> data) {
        ResponseListInfo<MyMessageItemInfo> listInfo = new ResponseListInfo<>();
        List<MyMessageItemInfo> itemInfoList = new ArrayList<>();
        Gson gson = new Gson();
        if (data.getItemList() != null && data.getItemList().size() > 0) {
            for (int i = 0; i < data.getItemList().size(); i++) {
                MyMessageItemInfo bean = new MyMessageItemInfo();

                bean.tsry = data.getItemList().get(i).tsry;  // 收件人
                bean.tssjStr = data.getItemList().get(i).tssjStr; // 发送信息
                bean.tsnr = data.getItemList().get(i).tsnr; // 消息标题
                String dataJson = data.getItemList().get(i).extrasStr;
                if (dataJson != null && !dataJson.equals("") && !dataJson.equals("null")) {
                    MyMessageItemInfo info = gson.fromJson(dataJson, MyMessageItemInfo.class);
                    if (info.func_type != null) { // 判断消息是否是302通知公告，302邮件，306待办
                        if (info.func_type.equals("301") || info.func_type.equals("302") || info.func_type.equals("306")) {
                            bean.func_type = info.func_type;
                        } else {
                            if (info.xtbm != null) // 判断消息是jw还是oa
                                bean.func_type = info.xtbm;
                        }
                    }
                } else {
                    bean.func_type = "other";
                }
                itemInfoList.add(bean);
            }

            listInfo.setOvered(data.isOvered());
            listInfo.setItemList(itemInfoList);
            loadSuccess(listInfo);
        }

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }
}
