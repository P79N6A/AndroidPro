package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.GuideAdapter;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.PicturelookContract;
import com.ogmamllpadnew.databinding.FgPicturelookLayoutBinding;
import com.ogmamllpadnew.presenter.PicturelookPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 图片预览
 * Created by 陈俊山 on 2018-11-09.
 */

public class PicturelookFragment extends BaseFragment<FgPicturelookLayoutBinding, Object> implements PicturelookContract.View {
    private PicturelookPresenter presenter;
    private List<String> images;

    @Override
    public int bindLayout() {
        return R.layout.fg_picturelook_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PicturelookPresenter(getActivity(), this);
        images = getActivity().getIntent().getStringArrayListExtra(Constants.LSIT);
        initViewPager();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.llClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_close:
                getActivity().finish();
                break;
        }
    }

    /**
     * 初始化Viewpager
     */
    private void initViewPager() {
        if (images == null)
            return;
        GuideAdapter imageAdapter = new GuideAdapter(getActivity().getSupportFragmentManager(), images);
        mBinding.vpLook.setAdapter(imageAdapter);
        mBinding.indicatorLook.setViewPager(mBinding.vpLook);
        mBinding.vpLook.setCurrentItem(0);
    }
}
