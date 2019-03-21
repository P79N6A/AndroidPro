package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SPUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.HomeBannerAdapter;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.databinding.Home1LayoutBinding;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.AppselectHomeDataBean;
import com.shan.netlibrary.bean.ProductselectProductCategory1Bean;

import java.util.List;

/**
 * Created by root on 18-11-6.
 */

public class Home1Fragment extends BaseFragment<Home1LayoutBinding, Object> {
    @Override
    public int bindLayout() {
        return R.layout.home1_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
    }

    /**
     * 加载banner图
     */
    public void loadViewPager() {
        final List<AppselectHomeDataBean.DataBean.BannerBean> datas = homeDataBean.getData().getBanner();
        mBinding.convenientBanner.setPages(new CBViewHolderCreator<HomeBannerAdapter>() {
            @Override
            public HomeBannerAdapter createHolder() {
                return new HomeBannerAdapter();
            }
        }, datas)
                .setPageIndicator(new int[]{R.mipmap.ic_point_nomal, R.mipmap.ic_point_focused})
                .startTurning(3000)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //1图片类型（不跳转）、2链接类型、3平台商品、4工厂商品、5品牌、6平台方案、7工厂方案
                        int type = datas.get(position).getType();
                        int typeId = datas.get(position).getTypeId();
                        switch (type) {
                            case 1:
                                break;
                            case 2:
                                String url = datas.get(position).getUrl();
                                if (!TextUtils.isEmpty(url)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constants.URL, url);
                                    startFragment(BasewebviewFragment.class, bundle);
                                }
                                break;
                            case 3:
                                if (typeId != 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.ID, typeId);
                                    startFragment(ProductdetailsFragment.class, bundle);
                                }
                                break;
                            case 4:

                                break;
                            case 5:
                                if (typeId != 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.ID, typeId);
                                    startFragment(BranddetailsFragment.class, bundle);
                                }
                                break;
                            case 6:
                                if (typeId != 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.ID, typeId);
                                    startFragment(PlandetailsFragment.class, bundle);
                                }
                                break;
                            case 7:

                                break;
                        }
                    }
                });
    }

    /**
     * 初始化潮流品类数据
     */
    private void initClplData() {
        mBinding.llClpl.removeAllViews();
        final List<AppselectHomeDataBean.DataBean.CategoryBean> datas = homeDataBean.getData().getCategory();
        for (int i = 0; i < datas.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.clpl_layout, mBinding.llClpl, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            ImageCacheUtils.loadImage(getActivity(), datas.get(i).getPicture(), imageView);
            textView.setText(datas.get(i).getName());
            mBinding.llClpl.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转到商品列表
                    toProductList(datas.get(j));
                }
            });
        }
        ImageCacheUtils.loadImage(getActivity(), homeDataBean.getData().getModuleHeadImage(), mBinding.ivType);
    }

    private AppselectHomeDataBean homeDataBean;

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.HOMEDATA) {
            homeDataBean = (AppselectHomeDataBean) msgEvent.getBean();
            if (homeDataBean != null) {
                loadViewPager();
                initClplData();
            }
        }
    }


    private void toProductList(AppselectHomeDataBean.DataBean.CategoryBean bean) {
        String data = (String) SPUtils.get(SpConstant.PRODUCTLEVER1, "");
        ProductselectProductCategory1Bean category1Bean = new Gson().fromJson(data, ProductselectProductCategory1Bean.class);
        if (category1Bean == null)
            return;
        String title = "";
        for (int i = 0; i < category1Bean.getData().size(); i++) {
            String name = category1Bean.getData().get(i).getName();
            int id = category1Bean.getData().get(i).getCategory1();
            if (id == bean.getCategory1Id()) {
                title = name;
                break;
            }
        }
        //跳转到商品列表
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CATEGORY1ID, bean.getCategory1Id());
        bundle.putInt(Constants.CATEGORY2ID, bean.getCategory2Id());
        bundle.putInt(Constants.CATEGORY3ID, bean.getCategory3Id());
        bundle.putString(Constants.TITLE, title);
        startFragment(ProductlisttabFragment.class, bundle);
    }
}
