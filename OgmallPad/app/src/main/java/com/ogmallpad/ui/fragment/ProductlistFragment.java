package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.ProductlistContract;
import com.ogmallpad.databinding.FgProductlistLayoutBinding;
import com.ogmallpad.presenter.ProductlistPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.bean.ProductsoftypeBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品清单
 * Created by chenjunshan on 2018-07-02.
 */

public class ProductlistFragment extends BaseFragment<FgProductlistLayoutBinding, ProductsoftypeBean.DataBean.ListBean> implements ProductlistContract.View {
    private ProductlistPresenter presenter;
    private int id;//商品类型id
    private int planid;//方案id


    public void setPlanid(int planid) {
        this.planid = planid;
    }

    public void setId(int id) {
        this.id = id;
        if (lvBinding != null) {
            productsoftype();
        }
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_productlist_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductlistPresenter(getActivity(), this);
        setRecycViewGrid(3);
        lvBinding.llParent.setBackgroundResource(R.color.white);
        presenter = new ProductlistPresenter(getActivity(), this);
        if (id != 0) {
            productsoftype();
        }
//        addScrollTopView();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSOFTYPE) {
            //商定商品数据
            ProductsoftypeBean productsoftypeBean = (ProductsoftypeBean) baseBean;
            isLoadingMore(productsoftypeBean.getData().getPageResult().getTotalPage());

            List<ProductsoftypeBean.DataBean.ListBean> productList = productsoftypeBean.getData().getList();
            addData(productList);
        } else if (mTag == HttpPresenter.CENTERSAVECOLLECTS) {
            //收藏成功
            categotyBean.setCollect(!categotyBean.isCollect());
            adapter.notifyDataSetChanged();
            if (categotyBean.isCollect()) {
                ToastUtils.toast(getString(R.string.str_sccg));
            } else {
                ToastUtils.toast(getString(R.string.str_qxsccg));
            }
        } else if (mTag == HttpPresenter.PRODUCTSSHARE) {
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

    private ProductsoftypeBean.DataBean.ListBean categotyBean;

    @Override
    protected void getListVewItem(FgProductlistLayoutBinding binding, final ProductsoftypeBean.DataBean.ListBean item, final int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.ivMenu);
        binding.tvTitle.setText(item.getName());
        binding.tvNum.setText(getString(R.string.str_sl) + item.getCount());
        binding.tvMoney.setText("¥" + item.getPrice());
        binding.tvCollect.setChecked(item.isCollect());
        binding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*JSONArray array = new JSONArray();
                array.put(item.getId());
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), array.toString());
                presenter.centerdeletecollects(body);*/
                categotyBean = item;
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
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        productsoftype();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            productsoftype();
        }
    }

    /**
     * 获取产品数据
     */
    private void productsoftype() {
        //根据风格id返回品牌列表 当风格id为空时查询所有的品牌信息
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(planid));
        map.put("type", "2");//1 brand 2 design 3 room
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (id != 10000) {
            map.put("categotyId", String.valueOf(id));
        }
        presenter.productsoftype(map);
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
}
