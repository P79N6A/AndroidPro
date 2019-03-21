package com.ogmallpad.ui.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.LogUtils;
import com.ogmallpad.R;
import com.ogmallpad.adapter.GuideAdapter;
import com.ogmallpad.adapter.HorizontalPagerAdapter;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.contract.PlandetailsContract;
import com.ogmallpad.databinding.FgPlandetailsLayoutBinding;
import com.ogmallpad.presenter.PlandetailsPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.utils.Utils;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 方案详情
 * Created by chenjunshan on 2018-07-02.
 */

public class PlandetailsFragment extends BaseFragment<FgPlandetailsLayoutBinding, Object> implements PlandetailsContract.View {
    private PlandetailsPresenter presenter;
    private DesigndetailsBean designdetailsBean;//方案详情

    @Override
    public int bindLayout() {
        return R.layout.fg_plandetails_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.VISIBLE);
        titleBinding.tvLeft.setText("方案详情");
    }

//    /**
//     * 初始化状态栏
//     */
//    private void intiStatusBar() {
//        if (AppUtils.getSystemVersion() < 19) {
//            mBinding.statusBar.setVisibility(View.GONE);
//        } else {
//            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mBinding.statusBar.getLayoutParams();
//            linearParams.height = AppUtils.getStatusBarHeight();
//            mBinding.statusBar.setLayoutParams(linearParams);
//        }
//    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
//        intiStatusBar();
        presenter = new PlandetailsPresenter(getActivity(), this, mBinding);
        designdetailsBean = (DesigndetailsBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
        if (designdetailsBean == null) {
            designdetailsBean = new DesigndetailsBean();
        }
        bindData();
        startFlick(mBinding.ivNext);
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

    private boolean isUp = false;//是否向上滑动

    @SuppressLint("NewApi")
    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvLookMore.setOnClickListener(this);
        mBinding.tv3d.setOnClickListener(this);
        mBinding.ivNext.setOnClickListener(this);
        mBinding.sl.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LogUtils.d("scrollY:" + scrollY);
                LogUtils.d("oldScrollX:" + oldScrollY);
                if (scrollY >= 200) {
                    if (!isUp) {
                        isUp = true;
                        mBinding.ivNext.setImageResource(R.mipmap.ic_next_top);
                    }

                } else {
                    if (isUp) {
                        isUp = false;
                        mBinding.ivNext.setImageResource(R.mipmap.ic_next_bottom);
                    }
                }
            }
        });
        mBinding.llClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.tv_look_more:
                //商品清单
                bundle = new Bundle();
                bundle.putSerializable(Constants.BEAN, designdetailsBean);
                startFragment(ProductListTabFragment.class, bundle);
                break;
            case R.id.tv_3d:
                //3D全景预览
                bundle = new Bundle();
                bundle.putString(Constants.URL, designdetailsBean.getData().get_$3DUrl());
                startFragment(BasewebviewFragment.class, bundle);
                break;
            case R.id.btn_left:
                getActivity().finish();
                break;
            case R.id.iv_next:
                //滑动到下一页
                if (isUp) {
                    mBinding.sl.fullScroll(ScrollView.FOCUS_UP);
                } else {
                    mBinding.sl.fullScroll(ScrollView.FOCUS_DOWN);
                }

                break;
            case R.id.ll_close:
                closeImage();
                break;
        }
    }

    private HorizontalPagerAdapter mAdapter;
    private List<Utils.LibraryObject> data;//绑定中间数据
    private GuideAdapter imageAdapter;

    /**
     * 绑定数据
     */
    private void bindData() {
        //绑定基本数据
        if (loginBean.getData().getUserType().equals("Designer")) {
            mBinding.tvSjsxm.setText(getString(R.string.str_wz) + designdetailsBean.getData().getCityName() + designdetailsBean.getData().getDistrictName());
            mBinding.ivSjsxm.setImageResource(R.mipmap.ic_location);
            mBinding.tvXx.setText(getString(R.string.str_xx) + designdetailsBean.getData().getDetails());
            mBinding.llTime.setVisibility(View.GONE);
            mBinding.llXx.setVisibility(View.VISIBLE);
        } else {
            mBinding.llXx.setVisibility(View.GONE);
            mBinding.tvSjsj.setText(getString(R.string.str_sjsj) + designdetailsBean.getData().getCreateTime());
            mBinding.tvSjsxm.setText(getString(R.string.str_sjsxm) + designdetailsBean.getData().getDesigner());
        }
        mBinding.tvHx.setText(getString(R.string.str_hx) + designdetailsBean.getData().getHouseType());
        mBinding.tvMj.setText(getString(R.string.str_mj) + designdetailsBean.getData().getArea());
        List<DesigndetailsBean.DataBean.ProductsBean> products = designdetailsBean.getData().getProducts();
        float num = 0;
        for (int i = 0; i < products.size(); i++) {
            float price = (float) (Double.parseDouble(products.get(i).getPrice()) * products.get(i).getCount());
            num += price;
        }
        String allMoney = String.valueOf(num);
        designdetailsBean.getData().setAllMoney(allMoney);
        mBinding.tvFazj.setText(getString(R.string.str_fazj) + "¥" + allMoney);
        //绑定中间数据
        data = new ArrayList<>();
        for (int i = 0; i < designdetailsBean.getData().getImages().size(); i++) {
            data.add(new Utils.LibraryObject(designdetailsBean.getData().getImages().get(i), ""));
        }
        mAdapter = new HorizontalPagerAdapter(getActivity(), data) {
            @Override
            public void showImage(List<Utils.LibraryObject> libraries, int position) {
                super.showImage(libraries, position);
                mBinding.rlImage.setVisibility(View.VISIBLE);
                titleBinding.getRoot().setVisibility(View.GONE);
                YoYo.with(Techniques.ZoomIn)
                        .duration(300)
                        .repeat(0).playOn(mBinding.rlImage);
                if (imageAdapter == null) {
                    imageAdapter = new GuideAdapter(getActivity().getSupportFragmentManager(), libraries);
                    mBinding.pager.setAdapter(imageAdapter);
                    mBinding.indicator.setViewPager(mBinding.pager);
                }
                mBinding.pager.setCurrentItem(position);
            }
        };
        mBinding.hicvp.setAdapter(mAdapter);
        //绑定底部数据
        if (products.size() == 0) {
            mBinding.llEmpty.setVisibility(View.VISIBLE);
            mBinding.llContent.setVisibility(View.GONE);
            mBinding.tvLookMore.setVisibility(View.GONE);
            mBinding.ivMore.setVisibility(View.GONE);
        } else {
            presenter.loadBottomVp(products);
        }
    }

    /**
     * 关闭图片
     */
    private void closeImage(){
        YoYo.with(Techniques.ZoomOut)
                .duration(300)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mBinding.rlImage.setVisibility(View.GONE);
                        titleBinding.getRoot().setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).playOn(mBinding.rlImage);
    }

    @Override
    protected void initData() {
        super.initData();
        if (TextUtils.isEmpty(designdetailsBean.getData().get_$3DUrl())) {
            mBinding.tv3d.setVisibility(View.GONE);
        }
    }

    /**
     * 开始闪烁动画
     *
     * @param view
     */
    private void startFlick(View view) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0.3f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }
}
