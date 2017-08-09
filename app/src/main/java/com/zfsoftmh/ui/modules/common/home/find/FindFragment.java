package com.zfsoftmh.ui.modules.common.home.find;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zfsoftmh.R;
import com.zfsoftmh.common.utils.ImageLoaderHelper;
import com.zfsoftmh.common.utils.ImmersionStatusBarUtils;
import com.zfsoftmh.common.utils.ScreenUtils;
import com.zfsoftmh.entity.FindItemInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.personal_affairs.school_eatery.SchoolEateryActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wesley
 * @date: 2017-6-12
 * @Description: 发现ui
 */

public class FindFragment extends BaseFragment<FindPresenter> implements FindContract.View,
        FindAdapter.OnItemClickListener {

    private FindAdapter adapter; //适配器
    private Banner banner;

    @Override
    protected void initVariables() {
        adapter = new FindAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setHasFixedSize(true);

        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //divider
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(context,
                R.color.common_divider_color), context.getResources().getDimensionPixelSize(R.dimen.common_view_width),
                context.getResources().getDimensionPixelSize(R.dimen.common_button_height), 0);
        itemDecoration.setDrawLastItem(true);
        itemDecoration.setDrawHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);

        //adapter
        recyclerView.setAdapter(adapter);

        initHeaderView();
        initItemView();
    }

    @Override
    protected void initListener() {

    }

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public void initHeaderView() {
        ArrayList<String> listImage = new ArrayList<>();
        listImage.add("http://img1.imgtn.bdimg.com/it/u=2343882896,3900438165&fm=26&gp=0.jpg");
        View view = LayoutInflater.from(context).inflate(R.layout.find_header_view, null);
        adapter.setHeaderView(view);
        banner = (Banner) view.findViewById(R.id.find_navigation_banner);
        int bannerHeight = ScreenUtils.getScreenWidth(context) * 3 / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, bannerHeight);
        banner.setLayoutParams(params);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImages(listImage);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();

        getBannerInfo();
    }

    @Override
    public void initItemView() {
        List<FindItemInfo> list = new ArrayList<>();

        //学校新闻
        FindItemInfo school_news = new FindItemInfo();
        school_news.setResId(R.mipmap.news);
        school_news.setName(getResources().getString(R.string.school_news));
        list.add(school_news);

        //求职招聘
        FindItemInfo job_search = new FindItemInfo();
        job_search.setResId(R.mipmap.job);
        job_search.setName(getResources().getString(R.string.job_search));
        list.add(job_search);


        //大学直播间
        FindItemInfo university_live = new FindItemInfo();
        university_live.setResId(R.mipmap.video);
        university_live.setName(getResources().getString(R.string.university_live));
        list.add(university_live);

        //移动食堂
        FindItemInfo move_the_canteen = new FindItemInfo();
        move_the_canteen.setResId(R.mipmap.canteen);
        move_the_canteen.setName(getResources().getString(R.string.move_the_canteen));
        list.add(move_the_canteen);


        //跳骚市场
        FindItemInfo school_circle = new FindItemInfo();
        school_circle.setResId(R.mipmap.recovery);
        school_circle.setName(getResources().getString(R.string.flea_market));
        list.add(school_circle);

        adapter.insertItems(list);
    }

    @Override
    public void getBannerInfo() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
        /*
         * 学校新闻
         */
        case 0:
            break;

        /*
         * 求职招聘
         */
        case 1:
            break;

        /*
         * 大学直播间
         */
        case 2:
            break;

        /*
         * 移动食堂
         */
        case 3:
            startActivity(SchoolEateryActivity.class);
            break;

        /*
         * 跳骚市场
         */
        case 4:
            break;


        default:
            break;
        }
    }

    //图片加载器
    private static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path != null && path instanceof String) {
                ImageLoaderHelper.loadImage(context, imageView, (String) path);
            }
        }
    }

    @Override
    protected boolean immersionEnabled() {
        return true;
    }

    @Override
    protected void immersionInit() {
        super.immersionInit();
        ImmersionStatusBarUtils.initStatusBarInFragment(this, R.color.colorPrimary, true, 0f, immersionBar);
    }
}
