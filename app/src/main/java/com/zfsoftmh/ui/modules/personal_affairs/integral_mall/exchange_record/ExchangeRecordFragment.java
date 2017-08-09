package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

import android.content.Intent;
import android.os.Bundle;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.entity.ExchangeRecordItemInfo;
import com.zfsoftmh.entity.ResponseListInfo;
import com.zfsoftmh.ui.base.BaseListFragment;
import com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record_detail.ExchangeRecordDetailActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 兑换记录列表
 */

public class ExchangeRecordFragment extends BaseListFragment<ExchangeRecordPresenter, ExchangeRecordItemInfo>
        implements ExchangeRecordContract.View {

    private ExchangeRecordAdapter adapter;
    @Inject
    ExchangeRecordPresenter presenter;

    public static ExchangeRecordFragment newInstance() {
        return new ExchangeRecordFragment();
    }

    @Override
    protected int getLayoutResID() {
        DaggerExchangeRecordComponent.builder()
                .appComponent(getAppComponent())
                .exchangeRecordPresenterModule(new ExchangeRecordPresenterModule(this))
                .build()
                .inject(this);

        return super.getLayoutResID();
    }

    @Override
    protected RecyclerArrayAdapter<ExchangeRecordItemInfo> getAdapter() {
        adapter = new ExchangeRecordAdapter(context);
        return adapter;
    }

    @Override
    protected void loadData() {
//        setTestData();
        loadExchangeRecordList(start_page, PAGE_SIZE);
    }

    private void setTestData() {
        ResponseListInfo<ExchangeRecordItemInfo> listInfo = new ResponseListInfo<>();
        List<ExchangeRecordItemInfo> ls = new ArrayList<>();
        List<String> images = new ArrayList<String>();
        String image = null;
//        images.add("http://10.71.33.72:8088/zftal-mobile//memoFile/13942017062750668_2017062758017.jpg");
//        images.add("http://img1.imgtn.bdimg.com/it/u=2343882896,3900438165&fm=26&gp=0.jpg");
//        images.add("http://10.71.33.72:8088/zftal-mobile//memoFile/13942017062750668_2017062707820.jpg");
//        images.add("http://10.71.33.72:8088/zftal-mobile//memoFile/13942017062750668_2017062714140.jpg");
//        images.add("http://10.71.33.72:8088/zftal-mobile//memoFile/13942017062750668_2017062719946.jpg");
        for (int i = 0; i < 10; i++) {
            ExchangeRecordItemInfo bean = new ExchangeRecordItemInfo();
            bean.goodspicPath = image;
            bean.goodsname = "商品名看到那个肯定能够看到那个可能大方公开你的反馈给你快递费那个地方那个老大哥反垄断法能够";
//            bean.exchangeIntegral = "20";
//            bean.exchangeNumber = "2";
//            bean.exchangeOrderNumber = "909083390800";
//            bean.exchangeGoodsDescription = "vivo X9是vivo全新拍照旗舰智能手机，于2016年11月1日官方曝光。[1]  并于2016年11月16日在北京水立方正式发布。[2] \n" +
//                    "                    \"vivo X9主要特点为搭载了两个前置摄像头，一个2000万索尼定制IMX376传感器摄像头，一个800万的专业级[3]  虚化摄像头，协同拍照可以拍摄出大光圈效果的人像照片。vivo X9搭载了高通骁龙八核处理器（2.0GHz），采用了5.5寸的1080P屏幕，4G RAM和64G的ROM组合，配备3040mAh的电池，并支持双引擎闪充。[1] \\n\" +\n" +
//                    "                    \"2016年12月16日，vivo官方宣布vivo X9迎来星空灰版全新配色，星空灰版X9和之前的配色版本相比更具有“阳刚之气”，正面采用2.5D玻璃设计，采用黑色面板，背面为金属灰色，质感强烈。现官方商城已开启预售，将于12月24日正式发售。[4] \\n\" +\n" +
//                    "                    \"2017年3月，作为目前最为炙手可热的机型，vivo X9 已经拥有了金、玫瑰金、星空灰等多款配色可选。为了让男性用户更多的选择，vivo 决定再次推出全新的配色--vivo X9 磨砂黑配色，售价 2798 元。[3] 前置双摄\\n\" +\n" +
//                    "                    \"vivo X9前置拥有两个摄像头：\\n\" +\n" +
//                    "                    \"一个为2000万的高清摄像头，一个为800万的专业虚化摄像头，可以精准测量景深。两个摄像头在协同自拍情况下，所有的像素点拥有距离镜头的准确信息，实现远近物体的精确分层，使主体更为突出，达到景深拍照效果。[5] 前置2000万高清像素\\n\" +\n" +
//                    "                    \"vivo X9的2000万前置摄像头搭载了与索尼共同研发的IMX376传感器，拥有0.95-16的等效光圈，可以在弱光下合成更大感光面积，感光面积达到1/2.8英寸，有效提升画面亮度。[6] \\n\" +\n" +
//                    "                    \"先拍照后对焦\\n\" +\n" +
//                    "                    \"相对传统对焦后出片方式，vivo X9在拍照的时候可以先拍照，再根据所需突出的焦点范围重新选择对焦点，选取不同虚化效果。[6] vivo X9采用铝合金一体式设计，采用第四代镜面喷砂工艺，10纳米级极细405锆砂喷涂，色泽闪耀。中框与背部一体成型，整机自然弧度圆润过渡。\\n\" +\n" +
//                    "                    \"vivo X9外观\\n\" +\n" +
//                    "                    \"vivo X9外观(3张)\\n\" +\n" +
//                    "                    \"屏幕则采用第五代康宁玻璃，触感更加舒适，抗摔性能有所提升；采用超窄边框设计，拥有1.59mm超窄边框。而vivo X9后壳则采用了全金属流线机身设计，天线向首尾移动做成了穹顶U型。";
            ls.add(bean);
        }
        listInfo.setOvered(false);
        listInfo.setItemList(ls);
        loadSuccess(listInfo);
    }

    @Override
    public void onItemClick(int position) {
        ExchangeRecordItemInfo info = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ExchangeRecordDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ExchangeRecordFragment", info);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void createLoadingDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideUpLoadingDialog() {
        hideProgressDialog();
    }

    @Override
    public void loadExchangeRecordList(int start_page, int PAGE_SIZE) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("start", String.valueOf(start_page));
        params.put("size", String.valueOf(PAGE_SIZE));
        presenter.getExchangeRecord(params);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }
}
