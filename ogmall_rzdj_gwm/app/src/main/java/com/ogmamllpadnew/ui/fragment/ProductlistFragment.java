package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.ShopScreenBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.ProductlistContract;
import com.ogmamllpadnew.databinding.FgProductlistLayoutBinding;
import com.ogmamllpadnew.presenter.ProductlistPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.bean.ProductselectProductByCategoryBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品列表
 * Created by 陈俊山 on 2018-11-08.
 */

public class ProductlistFragment extends BaseFragment<FgProductlistLayoutBinding, ProductselectProductByCategoryBean.DataBean> implements ProductlistContract.View {
    private ProductlistPresenter presenter;
    private int width;//屏幕宽度
    private int category2;
    private int category3;
    private int minPrice;
    private int maxPrice;

    public void setHousestyleId(int housestyleId) {
        this.housestyleId = housestyleId;
    }

    private String dataType;
    private int housestyleId;
    private int brandId;

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getCategory2() {
        return category2;
    }

    public int getCategory3() {
        return category3;
    }

    private String keywords;

    public void setCategory2(int category2) {
        this.category2 = category2;
    }

    public void setCategory3(int category3) {
        this.category3 = category3;
        Log.e("mTag","-------"+category3);
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
        width = ScreenUtils.getScreenWidth() / 8;
        setRecycViewGrid(3);
    }

    private ProductselectProductByCategoryBean categoryBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTBYCATEGORY) {
            categoryBean = (ProductselectProductByCategoryBean) baseBean;
            addData(categoryBean.getData());
            isLoadingMore(categoryBean.getCount());
        } else if (mTag == HttpPresenter.PRODUCTCOLLECTPRODUCT) {
            //收藏商品回调
            if (clickItem.isCollect()) {
                ToastUtils.toast(getString(R.string.str_yqxsc));
            } else {
                ToastUtils.toast(getString(R.string.str_sccg));
            }
            clickItem.setCollect(!clickItem.isCollect());
            adapter.notifyDataSetChanged();
        }
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

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    private ProductselectProductByCategoryBean.DataBean clickItem;

    @Override
    protected void getListVewItem(FgProductlistLayoutBinding binding, final ProductselectProductByCategoryBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        if (position % 3 == 0) {
            binding.getRoot().setPadding(width, width / 4, 0, width / 4);
        } else if (position % 3 == 1) {
            binding.getRoot().setPadding(width / 2, width / 4, width / 2, width / 4);
        } else if (position % 3 == 2) {
            binding.getRoot().setPadding(0, width / 4, width, width / 4);
        }
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.imageview);
        binding.tvName.setText(item.getName());
        binding.tvType.setText(item.getSubTitle());
        binding.tvPrice.setText(PublicUtils.getRenminbi() + item.getPrice());
        binding.cbCollect.setChecked(item.isCollect());

        binding.cbCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem = item;
                collectProduct();
            }
        });
        binding.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Map<String, String> map = new HashMap<>();
                map.put("productId", String.valueOf(item.getId()));
                presenter.productsshare(map);*/
                presenter.showRqDialog(HttpBuilder.BASE_URL+HttpBuilder.SHARE_URL+"id="+item.getId()+"&dataType="+item.getDataType());
            }
        });
    }

    @Override
    protected void onItemClick(ProductselectProductByCategoryBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, bean.getId());
        bundle.putInt(Constants.DATATYPE, bean.getDataType());
        startFragment(ProductdetailsFragment.class, bundle);
    }

    /**
     * 根据分类查询商品或联合关键字查询（白名单接口）
     */
    private void selectProductByCategory() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", keywords);//关键词搜索
        map.put("dataType", dataType);
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("customerId", String.valueOf(0));
        }else {
            map.put("customerId", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        if (category2 != Constants.AllBean) {
            map.put("category2", String.valueOf(category2));//关键词搜索
        }
        if (category3 != Constants.AllBean) {
            map.put("category3", String.valueOf(category3));//关键词搜索
        }
        if (housestyleId != 0){
            map.put("housestyleId",String.valueOf(housestyleId));
        }
        if (brandId != 0){
            map.put("brandId",String.valueOf(brandId));
        }
        if (minPrice != 0){
            map.put("minPrice",String.valueOf(minPrice));
        }
        if (maxPrice != 0){
            map.put("maxPrice",String.valueOf(maxPrice));
        }
        presenter.productselectProductByCategory(map, true);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectProductByCategory();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectProductByCategory();
        }
    }

    /**
     * POST /app/product/collectProduct
     * 帐号本身或者客户收藏或取消收藏商品商品
     */
    private void collectProduct() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", String.valueOf(clickItem.getId()));
        map.put("isCollect", String.valueOf(!clickItem.isCollect()));
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("id", String.valueOf(0));
        }else {
            map.put("id", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        map.put("dataType", String.valueOf(clickItem.getDataType()));
        presenter.productcollectProduct(map);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (categoryBean == null) {
            onRefresh();
        }
    }

    public void setKeyWords(String keyWords) {
        this.keywords = keyWords;
        onRefresh();
    }


    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.SPSX_CONSTANT_S && isVisible()){
            Log.e("mTag","postpostpostpostpostpostpost------------------------");
            ShopScreenBean bean = (ShopScreenBean) msgEvent.getBean();
            pageNum = 1;
            clearData();
            setHousestyleId(bean.getFgData());
            setBrandId(bean.getPpData());
            minPrice = bean.getMinPrice();
            maxPrice = bean.getMaxPrice();
            selectProductByCategory();
        }else if (msgEvent.getType() == MsgConstant.SHOP_SC_REFRESH){
            ProductsdetailsBean.DataBean dataBean = (ProductsdetailsBean.DataBean) msgEvent.getBean();
            List<ProductselectProductByCategoryBean.DataBean> datas = adapter.getDatas();
            for (ProductselectProductByCategoryBean.DataBean product : datas){
                if (product.getId() == dataBean.getId()){
                    product.setCollect(!product.isCollect());
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
