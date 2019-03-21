package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.databinding.Home3LayoutBinding;
import com.ogmamllpadnew.databinding.JdalLayoutBinding;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.AppselectHomeDataBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by root on 18-11-6.
 */

public class Home3Fragment extends BaseFragment<Home3LayoutBinding, Object> {
    private int height;
    private int width;

    @Override
    public int bindLayout() {
        return R.layout.home3_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        width = (int) (ScreenUtils.getScreenWidth() * 0.28);
        height = (int) (width * 0.75);
    }

    private CommonAdapter adapter;

    private void bindData() {
        final List<AppselectHomeDataBean.DataBean.PlanBean> plans = homeDataBean.getData().getPlan();
        /*if (adapter == null) {
            adapter = new CommonAdapter<JdalLayoutBinding, AppselectHomeDataBean.DataBean.PlanBean>(getActivity(), R.layout.jdal_layout, plans) {
                @Override
                protected void getItem(JdalLayoutBinding binding, final AppselectHomeDataBean.DataBean.PlanBean bean, int position) {
                    ViewGroup.LayoutParams params = binding.llItem.getLayoutParams();
                    params.height = height;
                    binding.llItem.setLayoutParams(params);
                    ImageCacheUtils.loadImage(getActivity(), bean.getHeadImage(), binding.imageview);
                    binding.tvName.setText(bean.getPlanName());
                }

                @Override
                protected void itemOnclick(JdalLayoutBinding binding, AppselectHomeDataBean.DataBean.PlanBean bean, int position) {
                    LogUtils.d("test" + ":itemOnclick");
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.ID, bean.getId());
                    startFragment(PlandetailsFragment.class, bundle);
                }
            };
            mBinding.gvJdal.setAdapter(adapter);
        }
        adapter.updata(plans);*/
        mBinding.gvJdal.removeAllViews();
        for (int i = 0; i < plans.size(); i++) {
            final int j = i;
            JdalLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.jdal_layout, mBinding.gvJdal, false);
            ViewGroup.LayoutParams params = binding.llItem.getLayoutParams();
            params.height = height;
            params.width = width;
            binding.llItem.setLayoutParams(params);
            ImageCacheUtils.loadImage(getActivity(), plans.get(i).getHeadImage(), binding.imageview);
            binding.tvName.setText(plans.get(i).getPlanName());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.ID, plans.get(j).getId());
                    startFragment(PlandetailsFragment.class, bundle);
                }
            });

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, 0);
            params2.weight = 1;
            View view = new View(getActivity());
            mBinding.gvJdal.addView(binding.getRoot());
            if (plans.size() > 0 && j != plans.size() - 1) {
                mBinding.gvJdal.addView(view, params2);
            }
        }
    }

    private AppselectHomeDataBean homeDataBean;

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.HOMEDATA) {
            homeDataBean = (AppselectHomeDataBean) msgEvent.getBean();
            if (homeDataBean != null) {
                bindData();
            }
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 3));
            }
        });
    }
}
