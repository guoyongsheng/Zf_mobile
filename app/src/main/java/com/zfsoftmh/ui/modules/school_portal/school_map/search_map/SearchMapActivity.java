package com.zfsoftmh.ui.modules.school_portal.school_map.search_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.AMapUtil;
import com.zfsoftmh.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by li on 2017/7/2.
 */

 public class SearchMapActivity extends BaseActivity implements View.OnClickListener,
        TextWatcher, PoiSearch.OnPoiSearchListener, SearchResultAdapter.MapItemClickListener {
    private ImageButton back;
    private RecyclerView recyclerView;
    private EditText actv_keyword;
    private SearchResultAdapter adapter;
    private ArrayList<PoiItem> poiItems = new ArrayList<PoiItem>();
    private PoiResult poiResult;
    private PoiSearch.Query query;
    private LatLonPoint mSouthwest, mNortheast;

    @Override
    protected void initVariables() {
        adapter=new SearchResultAdapter(this,this);
    }

    //获取控制搜索范围的两个点
    @Override
    protected void handleBundle(@NonNull Bundle bundle) {
        mSouthwest=bundle.getParcelable("SouthWest");
        mNortheast=bundle.getParcelable("NorthEast");
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_sreach;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        back= (ImageButton)findViewById(R.id.back_map);
        recyclerView= (RecyclerView)findViewById(R.id.search_result);
        actv_keyword= (EditText) findViewById(R.id.keyWord_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SearchRecyclerDecoration(this,R.color.aliwx_common_text_color3));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void setUpInject() {

    }

    @Override
    protected void initListener() {
        actv_keyword.addTextChangedListener(this);
        back.setOnClickListener(this);
    }

    public void searchPoiByKeyWord() {
        String keyWord = AMapUtil.checkEditText(actv_keyword);
        int currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(mSouthwest, mNortheast));
        poiSearch.searchPOIAsyn();

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent=new Intent();
        this.setResult(1,intent);
        this.finish();
    }

    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.back_map){
            Intent intent=new Intent();
            this.setResult(1,intent);
            this.finish();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        searchPoiByKeyWord();//在这里写了搜索
    }

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

                    }

                }
            }
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onItemClick(View view, int positon) {
        Intent intent=new Intent();
        Bundle b=new Bundle();
        b.putParcelable("PoiItem",poiItems.get(positon));
        intent.putExtras(b);
        this.setResult(0,intent);
        this.finish();
    }
}
