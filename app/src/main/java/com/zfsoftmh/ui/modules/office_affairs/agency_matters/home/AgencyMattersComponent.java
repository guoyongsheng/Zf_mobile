package com.zfsoftmh.ui.modules.office_affairs.agency_matters.home;

import com.zfsoftmh.di.AppComponent;
import com.zfsoftmh.di.PerActivity;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.agency.AgencyMattersPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.has_been_done.HasBeenDoneMattersPresenterModule;
import com.zfsoftmh.ui.modules.office_affairs.agency_matters.home.settlement.SettlementMattersPresenterModule;

import dagger.Component;

/**
 * @author wesley
 * @date: 2017/3/31
 * @Description: 注入实例
 */

@PerActivity
@Component(modules = {AgencyMattersPresenterModule.class, HasBeenDoneMattersPresenterModule.class,
        SettlementMattersPresenterModule.class}, dependencies = AppComponent.class)
public interface AgencyMattersComponent {

    void inject(AgencyMattersActivity agencyMattersActivity);
}
