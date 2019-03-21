package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ogmallpad.R;
import com.ogmallpad.contract.OgmallproductContract;
import com.ogmallpad.databinding.FgOgmallproductItemLayoutBinding;
import com.ogmallpad.presenter.OgmallproductPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 软装家具
 * Created by chenjunshan on 2018-07-03.
 */

public class OgmallproductFragment extends BaseFragment<FgOgmallproductItemLayoutBinding, Object> implements OgmallproductContract.View {
    private OgmallproductPresenter presenter;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_ogmallproduct_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();

    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new OgmallproductPresenter(getActivity(), this);

    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgOgmallproductItemLayoutBinding binding, Object item, int position) {
        super.getListVewItem(binding, item, position);

    }

    @Override
    public void onRefresh() {
        super.onRefresh();

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();

    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initData() {
        super.initData();
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add("");
        }
        setData(data);
    }
}