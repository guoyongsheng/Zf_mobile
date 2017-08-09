package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.looking_for_notice.LookingForNoticePresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.lost_and_found.release_news.lost_and_found.LostAndFoundPresenterModule;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017-5-31
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = {LostAndFoundPresenterModule.class, LookingForNoticePresenterModule.class}, dependencies = AppComponent.class)
interface ReleaseNewsComponent {

    void inject(ReleaseNewsActivity releaseNewsActivity);
}
