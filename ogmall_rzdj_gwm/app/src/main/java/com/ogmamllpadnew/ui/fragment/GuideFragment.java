package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.databinding.FragmentGuideBinding;
import com.ogmamllpadnew.ui.BaseFragment;


/**
 * 引导页
 */
public final class GuideFragment extends BaseFragment<FragmentGuideBinding, Object> {
    public String guideBean = null;

    @Override
    public int bindLayout() {
        return R.layout.fragment_guide;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    public static GuideFragment newInstance(String guideBean) {
        GuideFragment fragment = new GuideFragment();
        fragment.guideBean = guideBean;
        return fragment;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        ImageCacheUtils.loadImage(getActivity(), guideBean, mBinding.imageView);
    }
}
