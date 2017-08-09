package com.zfsoftmh.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author wesley
 * @date: 2017-8-3
 * @Description: ui---适用于一个页面只有一个请求的情况
 */

public abstract class BaseSingleFragment<T extends BasePresenter> extends BaseFragment<T>
        implements BaseSingleView<T> {

   // private ViewReplacer viewReplacer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void loadError(String errorMsg) {


    }


    //初始化ViewReplacer
    protected void initViewReplacer(View view) {
        //viewReplacer = new ViewReplacer(view);
    }

    //重置
    protected void restore() {
       /* if (viewReplacer == null) {
            return;
        }
        viewReplacer.restore();*/
    }
}
