package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ogmallpad.R;
import com.ogmallpad.contract.BktjContract;
import com.ogmallpad.databinding.FgBktjItemLayoutBinding;
import com.ogmallpad.presenter.BktjPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 爆款推荐
 * Created by chenjunshan on 2018-07-06.
 */

public class BktjFragment extends BaseFragment<FgBktjItemLayoutBinding, Object> implements BktjContract.View {
    private BktjPresenter presenter;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_bktj_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();

    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BktjPresenter(getActivity(), this);

    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgBktjItemLayoutBinding binding, Object item, int position) {
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