package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.listener.RecyclerViewScrollDetector;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.bean.ShopScreenBean;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BrandproductContract;
import com.ogmallpad.databinding.FgBrandproductItemLayoutBinding;
import com.ogmallpad.presenter.BrandproductPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductofCategotyBean;
import com.shan.netlibrary.bean.ProductsearchBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌家具
 * Created by chenjunshan on 2018-07-03.
 */

public class BrandproductFragment extends BaseFragment<FgBrandproductItemLayoutBinding, ProductofCategotyBean.DataBean.ListBean> implements BrandproductContract.View {
    private BrandproductPresenter presenter;
    public int id;
    private String searchWord;
    private boolean isAddHeader = true;
    private int parentId;

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setAddHeader(boolean addHeader) {
        isAddHeader = addHeader;
    }

    /**
     * 清除搜索关键词
     */
    public void clearSearchWord() {
        if (!TextUtils.isEmpty(searchWord)) {
            this.searchWord = null;
            onRefresh();
        }
    }

    //搜索
    public void startSearch(String searchWord) {
        this.searchWord = searchWord;
        clearData();
        onRefresh();
    }

    public void setId(int id) {
        this.id = id;
        if (lvBinding != null) {
            productofCategoty(false);
        }
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_brandproduct_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        setRecycViewGrid(3);
        presenter = new BrandproductPresenter(getActivity(), this);
        if (id != 0) {
            productofCategoty(false);
        }
//        addScrollTopView();
        //增加头部
        if (isAddHeader) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_header_layout, lvBinding.xrecyclerview, false);
            lvBinding.xrecyclerview.addHeaderView(view);
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTOFCATEGOTY) {
            ProductofCategotyBean bean = (ProductofCategotyBean) baseBean;
            isLoadingMore(bean.getData().getPageResult().getTotalPage());
            List<ProductofCategotyBean.DataBean.ListBean> list = bean.getData().getList();
            if (pageNum == 1){
                clearData();
            }
            addData(list);
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
            //分享成功
            ProductsshareBean productsshareBean = (ProductsshareBean) baseBean;
            presenter.showRqDialog(productsshareBean.getData().getUrl());
        }else if (mTag == HttpPresenter.PRODUCTSEARCH){
            ProductsearchBean bean = (ProductsearchBean) baseBean;
            List<ProductsearchBean.DataBean.ListBean> list = bean.getData().getList();
            List<ProductofCategotyBean.DataBean.ListBean> lis = new ArrayList<>();
            for (int i = 0 ; i < list.size() ; i++){
                ProductsearchBean.DataBean.ListBean bean1 = list.get(i);
                ProductofCategotyBean.DataBean.ListBean bean2 = new ProductofCategotyBean.DataBean.ListBean();
                bean2.setId(bean1.getId());
                bean2.setName(bean1.getName());
                bean2.setPrice(bean1.getPrice());
                bean2.setImage(bean1.getImage());
                bean2.setSpec_size(bean1.getSpec_size());
                lis.add(bean2);
            }
            Log.e("TAG","-------"+lis.size());
            clearData();
            addData(lis);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {
        if (mTag == HttpPresenter.CENTERSAVECOLLECTS) {
            //收藏失败
            adapter.notifyDataSetChanged();
        }
    }

    private ProductofCategotyBean.DataBean.ListBean categotyBean;

    @Override
    protected void getListVewItem(FgBrandproductItemLayoutBinding binding, final ProductofCategotyBean.DataBean.ListBean item, final int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.ivMenu);
        binding.tvTitle.setText(item.getName());
        binding.tvSize.setText(getString(R.string.str_gg) + item.getSpec_size());
        binding.tvPrice.setText("¥" + item.getPrice());
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
    protected void onItemClick(ProductofCategotyBean.DataBean.ListBean bean, int position) {
        super.onItemClick(bean, position);
        /*SoftKeyBoardUtils.closeKeybord(lvBinding.getRoot(), getActivity());
        //presenter.lookImage(bean.getImage());
        ProductsBean productsBean = new ProductsBean();
        for (int i = 0; i < adapter.getDatas().size(); i++) {
            ProductofCategotyBean.DataBean.ListBean listBean = adapter.getDatas().get(i);
            ProductsBean.DataBean dataBean = new ProductsBean.DataBean();
            dataBean.setId(String.valueOf(listBean.getId()));
            dataBean.setImage(listBean.getImage());
            dataBean.setPrice(listBean.getPrice());
            dataBean.setSpecification(listBean.getSpec_size());
            dataBean.setTitle(listBean.getName());
            productsBean.getData().add(dataBean);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, position);
        bundle.putSerializable(Constants.BEAN, productsBean);
        bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
        startFragment(ProductsFragment.class, bundle);*/
    }

    /**
     * 跳转到商品预览
     *
     * @param position
     */
    private void toProductsFragment(int position) {
        SoftKeyBoardUtils.closeKeybord(lvBinding.getRoot(), getActivity());
        //presenter.lookImage(bean.getImage());
        ProductsBean productsBean = new ProductsBean();
        for (int i = 0; i < adapter.getDatas().size(); i++) {
            ProductofCategotyBean.DataBean.ListBean listBean = adapter.getDatas().get(i);
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
        super.onRefresh();
        productofCategoty(true);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            productofCategoty(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    /**
     * 根据类型id 获取产品数据
     */
    private void productofCategoty(boolean isShow) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("searchWord", searchWord);
        if (id == Constants.ALLBEAN) {
            map.put("categotyId", String.valueOf(parentId));
        } else {
            map.put("categotyId", String.valueOf(id));
        }
        presenter.productofCategoty(map, isShow);
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

    private boolean isup = false;

    @Override
    protected void initEvent() {
        super.initEvent();
        lvBinding.xrecyclerview.addOnScrollListener(new RecyclerViewScrollDetector() {
            @Override
            protected void onScrollUp() {
                Log.i(TAG, "onScrollUp: ");
                if (!isup) {
                    isup = true;
//                    EventBus.getDefault().post(new MessageEvent(MsgConstant.ISHIDEPRODUCTTAB, true));
                }
            }

            @Override
            protected void onScrollDown() {
                Log.i(TAG, "onScrollDown: ");
                if (isup) {
                    isup = false;
//                    EventBus.getDefault().post(new MessageEvent(MsgConstant.ISHIDEPRODUCTTAB, false));
                }
            }
        });
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.SPSX_CONSTANT_S){
            ShopScreenBean bean = (ShopScreenBean) msgEvent.getBean();
            getData(bean);
        }else if (msgEvent.getType() == MsgConstant.COLLECT_SHOP){
            onRefresh();
        }
    }

    private void getData(ShopScreenBean bean) {
        Map<String, String> map = new HashMap<>();
        if (id == Constants.ALLBEAN) {
            map.put("categotyId", String.valueOf(parentId));
        } else {
            map.put("categotyId", String.valueOf(id));
        }
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (bean.getFgData() != 0)
            map.put("styleId", String.valueOf(bean.getFgData()));//风格id
        if (bean.getPpData() != 0)
            map.put("brandId", String.valueOf(bean.getPpData()));//品牌id
        if (bean.getMinPrice() != 0)
            map.put("minPrice", String.valueOf(bean.getMinPrice()));//最小价格
        if (bean.getMaxPrice() != 0)
            map.put("maxPrice", String.valueOf(bean.getMaxPrice()));//最大价格
        map.put("sortType", "1");//排序方式 1 最热，2 价格，3 最新
        map.put("order", "0");//排序方案 升序还是降序 0 降序，1升序
        presenter.productsearch(map, false);
    }

}