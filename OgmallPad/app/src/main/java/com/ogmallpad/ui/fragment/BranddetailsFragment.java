package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BranddetailsContract;
import com.ogmallpad.databinding.BrandDetailsHeaderLayoutBinding;
import com.ogmallpad.databinding.FgBranddetailsItemLayoutBinding;
import com.ogmallpad.presenter.BranddetailsPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.BranddetailsBean;
import com.shan.netlibrary.bean.ProductscategorysBean;
import com.shan.netlibrary.bean.ProductsoftypeBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌详情
 * Created by chenjunshan on 2018-07-03.
 */

public class BranddetailsFragment extends BaseFragment<FgBranddetailsItemLayoutBinding, ProductsoftypeBean.DataBean.ListBean> implements BranddetailsContract.View {
    private BranddetailsPresenter presenter;
    private int screenHeight;//屏幕高度
    private BrandDetailsHeaderLayoutBinding headBinding;
    private BranddetailsBean branddetailsBean;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_branddetails_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.VISIBLE);
        titleBinding.tvLeft.setText(getString(R.string.str_ppqx));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        branddetailsBean = (BranddetailsBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
        setRecycViewGrid(3);
        presenter = new BranddetailsPresenter(getActivity(), this);
        addHeadView();
        //获取品牌风格
        productsCategorys();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        screenHeight = (int) (ScreenUtils.getScreenHeight() * 0.92 - ScreenUtils.getStatusHeight());
        headBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.brand_details_header_layout, lvBinding.xrecyclerview, false);
        lvBinding.xrecyclerview.addHeaderView(headBinding.getRoot());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) headBinding.llHeadParent.getLayoutParams();
        params.height = screenHeight;
        params.bottomMargin = -85;
        headBinding.llHeadParent.setLayoutParams(params);
    }

    private List<RadioButton> radioButtons;

    private void refreshTopStatus(int pos) {
        if (radioButtons == null)
            return;
        for (int i = 0; i < radioButtons.size(); i++) {
            if (i == pos) {
                radioButtons.get(i).setChecked(true);
            } else {
                radioButtons.get(i).setChecked(false);
            }
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSCATEGORYS) {
            ProductscategorysBean bean = (ProductscategorysBean) baseBean;
            ProductscategorysBean.DataBean allBean = new ProductscategorysBean.DataBean();
            allBean.setCategoryName("全部");
            allBean.setCategoryId(10000);
            bean.getData().add(0, allBean);

            List<ProductscategorysBean.DataBean> datas = bean.getData();
            radioButtons = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                final int j = i;
                final ProductscategorysBean.DataBean dataBean = datas.get(i);
                RadioButton childView = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.brand_details_header_item_layout, headBinding.llType, false);
                radioButtons.add(childView);
                childView.setText(dataBean.getCategoryName());
                headBinding.llType.addView(childView);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentId = dataBean.getCategoryId();
                        onRefresh();
                        refreshTopStatus(j);
                    }
                });
                if (i == 0) {
                    childView.setChecked(true);
                }
            }
            //获取第一个分类数据
            currentId = datas.get(0).getCategoryId();
            productsoftype();
        } else if (mTag == HttpPresenter.PRODUCTSOFTYPE) {
            ProductsoftypeBean bean = (ProductsoftypeBean) baseBean;
            isLoadingMore(bean.getData().getPageResult().getTotalPage());
            List<ProductsoftypeBean.DataBean.ListBean> list = bean.getData().getList();
            addData(list);
        } else if (mTag == HttpPresenter.CENTERSAVECOLLECTS) {
            //收藏成功
            softypeBean.setCollect(!softypeBean.isCollect());
            adapter.notifyDataSetChanged();
            if (softypeBean.isCollect()) {
                ToastUtils.toast(getString(R.string.str_sccg));
            } else {
                ToastUtils.toast(getString(R.string.str_qxsccg));
            }

        } else if (mTag == HttpPresenter.PRODUCTSSHARE) {
            //分享成功
            ProductsshareBean productsshareBean = (ProductsshareBean) baseBean;
            presenter.showRqDialog(productsshareBean.getData().getUrl());
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {
        if (mTag == HttpPresenter.CENTERSAVECOLLECTS) {
            //收藏失败
            adapter.notifyDataSetChanged();
        }
    }

    private ProductsoftypeBean.DataBean.ListBean softypeBean;

    @Override
    protected void getListVewItem(FgBranddetailsItemLayoutBinding binding, final ProductsoftypeBean.DataBean.ListBean item, final int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.ivMenu);
        binding.tvTitle.setText(item.getName());
        binding.tvPrice.setText("¥" + item.getPrice());
        binding.tvCollect.setChecked(item.isCollect());
        binding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*JSONArray array = new JSONArray();
                array.put(item.getId());
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), array.toString());
                presenter.centerdeletecollects(body);*/
                softypeBean = item;
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(item.getId()));
                map.put("isCollect", String.valueOf(!item.isCollect()));
                presenter.centersavecollects(map);
            }
        });
        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toProductsFragment(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, String.valueOf(item.getId()));
                startFragment(ProductdetailsFragment.class, bundle);
            }
        });
        binding.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put("productId", String.valueOf(item.getId()));
                presenter.productsshare(map);
            }
        });
    }

    @Override
    protected void onItemClick(ProductsoftypeBean.DataBean.ListBean bean, int position) {
        super.onItemClick(bean, position);
        //presenter.lookImage(bean.getImage());
    }

    /**
     * 跳转到商品预览
     *
     * @param position
     */
    private void toProductsFragment(int position) {
        ProductsBean productsBean = new ProductsBean();
        for (int i = 0; i < adapter.getDatas().size(); i++) {
            ProductsoftypeBean.DataBean.ListBean listBean = adapter.getDatas().get(i);
            ProductsBean.DataBean dataBean = new ProductsBean.DataBean();
            dataBean.setId(String.valueOf(listBean.getId()));
            dataBean.setImage(listBean.getImage());
            dataBean.setPrice(listBean.getPrice());
            dataBean.setSpecification(listBean.getSpec_size());
            dataBean.setTitle(listBean.getName());
            dataBean.setCollect(listBean.isCollect());
            productsBean.getData().add(dataBean);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, position);
        bundle.putSerializable(Constants.BEAN, productsBean);
        bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
        startFragment(ProductsFragment.class, bundle);
    }

    @Override
    public void onRefresh() {
        lvBinding.xrecyclerview.setLoadingMoreEnabled(true);
        pageNum = 1;
        clearData();
        productsoftype();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            productsoftype();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initData() {
        super.initData();
//        addScrollTopView();
        ImageCacheUtils.loadImage(getActivity(), branddetailsBean.getData().getHeadimage(), headBinding.iv1);
        ImageCacheUtils.loadImage(getActivity(), branddetailsBean.getData().getImage(), headBinding.iv2);
        initDetails();
    }

    private int size;
    private int length;
    private boolean isLeft = false;

    private void initDetails() {
        final String details = branddetailsBean.getData().getContent();
        size = 22*7;
        length = details.length() / size;
        if (length == 0) {
            headBinding.tvDetails.setText(details);
            headBinding.ivLeft.setVisibility(View.GONE);
            headBinding.ivRight.setVisibility(View.GONE);
        } else {
            headBinding.tvDetails.setText(details.substring(0, size));
            headBinding.ivLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLeft) {
                        headBinding.tvDetails.setText(details.substring(0, size));
                        headBinding.ivLeft.setImageResource(R.mipmap.ic_brand_arr_left);
                        headBinding.ivRight.setImageResource(R.mipmap.ic_brand_arr_right);
                    }
                    isLeft = false;
                }
            });
            headBinding.ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isLeft) {
                        headBinding.tvDetails.setText(details.substring(size, details.length()));
                        headBinding.ivLeft.setImageResource(R.mipmap.ic_brand_arr_left_on);
                        headBinding.ivRight.setImageResource(R.mipmap.ic_brand_arr_right_off);
                    }
                    isLeft = true;
                }
            });
            headBinding.tvTitle.setText(branddetailsBean.getData().getName());
        }
    }

    /**
     * 增加底部VIew
     */
    public void addScrollTopView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.scroll_top_layout, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.height = 150;
        params.width = 150;
        params.rightMargin = 30;
        params.bottomMargin = 100;
        lvBinding.fl.addView(view, params);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothMoveToPosition(0);
            }
        });
    }

    /**
     * 获取品牌风格
     */
    private void productsCategorys() {
        if (branddetailsBean == null)
            return;
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(branddetailsBean.getData().getId()));
        map.put("type", "1");// 1 brand 2 design 3 room
        presenter.productscategorys(map);
    }

    private int currentId;

    /**
     * 根据风格id返回品牌列表 当风格id为空时查询所有的品牌信息
     */
    private void productsoftype() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(branddetailsBean.getData().getId()));
        map.put("type", "1");//1 brand 2 design 3 room
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (currentId != 10000) {
            map.put("categotyId", String.valueOf(currentId));
        }
        presenter.productsoftype(map);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        if (TextUtils.isEmpty(branddetailsBean.getData().get_$3dUrl())) {
            headBinding.tvZtyz.setVisibility(View.GONE);
        }
        headBinding.tvZtyz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3D全景预览
                Bundle bundle = new Bundle();
                bundle.putString(Constants.URL, branddetailsBean.getData().get_$3dUrl());
                startFragment(BasewebviewFragment.class, bundle);
            }
        });
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.COLLECT_SHOP){
            onRefresh();
        }
    }
}