package com.zfsoftmh.ui.modules.school_portal.school_map.search_map;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by ljq on 2017/6/29.
 */

public class SearchMapFragment extends BaseFragment implements TextWatcher ,SearchResultAdapter.MapItemClickListener, PoiSearch.OnPoiSearchListener, View.OnClickListener {


    private ImageButton back;
    private RecyclerView recyclerView;
    private AutoCompleteTextView actv_keyword;
    private SearchResultAdapter adapter;
    ArrayList<PoiItem> poiItems = new ArrayList<PoiItem>();
    private PoiResult poiResult;
    private SearchResultAdapter mAdapter;
    private PoiSearch.Query query;


    public static SearchMapFragment getInstance(){
        return  new SearchMapFragment();
    }



    @Override
    protected void initVariables() {
        adapter=new SearchResultAdapter(getContext(),this);

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_sreach;
    }

    @Override
    protected void initViews(View view) {
        back= (ImageButton)view.findViewById(R.id.back_map);
        recyclerView= (RecyclerView) view.findViewById(R.id.search_result);
        actv_keyword= (AutoCompleteTextView) view.findViewById(R.id.keyWord_search);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        actv_keyword.addTextChangedListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String s1=s.toString();
        if(s1!=null && (!s1.equals(""))){
            //这里要传入两个经纬度点
           searchPoiByKeyWord(s1,null,null);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    @Override
    public void onItemClick(View view, int positon) {





    }



    public void searchPoiByKeyWord(String keyWord, LatLonPoint mSouthwest, LatLonPoint mNortheast) {
        //    keyWord = AMapUtil.checkEditText(key_search);
        int currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(mSouthwest, mNortheast));
        poiSearch.searchPOIAsyn();

    }

    /**
     * 搜索的结果
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
            if (rCode == 1000) {
                if (result != null && result.getQuery() != null) {// 搜索poi的结果
                    if (result.getQuery().equals(query)) {// 是否是同一条
                        poiResult = result;
                        // 取得搜索到的poiitems有多少页
                        poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                        // CloudSearch();  /***启用云检索*/
                        if (poiItems != null && poiItems.size() > 0) {
                            adapter.ClearData();
                            adapter.addData(poiItems);
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onClick(View v) {

    }



}
