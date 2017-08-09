package com.zfsoftmh.ui.modules.personal_affairs.integral_mall.exchange_record;

import com.zfsoftmh.entity.ExchangeRecordItemInfo;
import com.zfsoftmh.ui.base.BaseListView;
import com.zfsoftmh.ui.base.BasePresenter;

import java.util.Map;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description:
 */

public interface ExchangeRecordContract {

    interface View extends BaseListView<ExchangeRecordPresenter, ExchangeRecordItemInfo> {
        /**
         * 数据加载框
         *
         * @param msg
         */
        void createLoadingDialog(String msg);

        /**
         * 隐藏加载框
         */
        void hideUpLoadingDialog();

        /**
         * 加载兑换记录列表
         *
         * @param start_page
         * @param PAGE_SIZE
         */
        void loadExchangeRecordList(int start_page, int PAGE_SIZE);

    }

    interface Presenter extends BasePresenter {

        /**
         * 获取兑换记录列表
         *
         * @param params
         */
        void getExchangeRecord(Map<String, String> params);
    }
}
