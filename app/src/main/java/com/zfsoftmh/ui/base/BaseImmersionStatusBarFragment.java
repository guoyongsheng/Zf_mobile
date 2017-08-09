package com.zfsoftmh.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * @author wesley
 * @date: 2017-7-20
 * @Description: 沉浸式状态栏的基类
 */

public abstract class BaseImmersionStatusBarFragment extends Fragment {


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden && getUserVisibleHint() && immersionEnabled()) {
            immersionInit();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (immersionEnabled()) {
            immersionInit();
        }
    }

    /**
     * 当前页面Fragment支持沉浸式初始化。子类可以重写返回false，设置不支持沉浸式初始化
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean immersionEnabled() {
        return false;
    }

    protected abstract void immersionInit();
}
