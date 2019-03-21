package com.gwm.base;

import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Administrator on 2018/4/23 0023.
 *
 * 与ViewPager连用时该Fragment不会被加载进去  只会加载布局  不会加载数据，只有当Fragment可见时才会加载数据
 *      用法：
 *      (1).lazyLoad()方法去加载数据
        (2).stopLoad()方法可选，当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
 */

public abstract class BaseLazyLoadFragment extends BaseFragment {
    private boolean isInit;
    private boolean isLoad;

    @Override
    public void onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        super.onCreateView(inflater, savedInstanceState);
        isInit = true;
        isCanLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint() && !isLoad) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }
    protected abstract void stopLoad();

    protected abstract void lazyLoad();
}
