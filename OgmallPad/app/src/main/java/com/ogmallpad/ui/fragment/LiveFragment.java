package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ogmallpad.R;
import com.ogmallpad.contract.LiveContract;
import com.ogmallpad.databinding.FgLiveItemLayoutBinding;
import com.ogmallpad.presenter.LivePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 个性生活
 * Created by chenjunshan on 2018-07-03.
 */

public class LiveFragment extends BaseFragment<FgLiveItemLayoutBinding, Object> implements LiveContract.View {
    private LivePresenter presenter;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_live_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();

    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new LivePresenter(getActivity(), this);

    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgLiveItemLayoutBinding binding, Object item, int position) {
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