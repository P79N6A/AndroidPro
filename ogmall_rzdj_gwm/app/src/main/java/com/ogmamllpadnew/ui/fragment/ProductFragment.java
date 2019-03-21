package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.SPUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.ProductContract;
import com.ogmamllpadnew.databinding.FgProductLayoutBinding;
import com.ogmamllpadnew.databinding.ProductItemLayoutBinding;
import com.ogmamllpadnew.presenter.ProductPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductselectProductCategory1Bean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.List;

/**
 * 商品
 * <p>
 * Created by 陈俊山 on 2018-11-02.
 */

public class ProductFragment extends BaseFragment<FgProductLayoutBinding, Object> implements ProductContract.View {
    private ProductPresenter presenter;
    private ProductselectProductCategory1Bean bean;

    @Override
    public int bindLayout() {
        return R.layout.fg_product_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.btnLeft.setImageResource(R.mipmap.ic_product_on);
        setLeftText(R.string.str_spzs);
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductPresenter(getActivity(), this);
        String data = (String) SPUtils.get(SpConstant.PRODUCTLEVER1, "");
        bean = new Gson().fromJson(data, ProductselectProductCategory1Bean.class);
        if (bean != null) {
            handlerData();
        }
        Log.e("TAG",bean.toString());
        //查询商品一级分类（白名单接口）
        selectProductCategory1();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTCATEGORY1) {
            bean = (ProductselectProductCategory1Bean) baseBean;
            handlerData();
            //保存商品一级分类
            String data = new Gson().toJson(bean);
            SPUtils.put(SpConstant.PRODUCTLEVER1, data);
        }
    }

    /**
     * 处理数据
     */
    private void handlerData() {
        if (bean != null) {
            List<ProductselectProductCategory1Bean.DataBean> dataBeans = bean.getData();
            mBinding.gv.setAdapter(new CommonAdapter<ProductItemLayoutBinding, ProductselectProductCategory1Bean.DataBean>(getActivity(), R.layout.product_item_layout, dataBeans) {
                @Override
                protected void getItem(ProductItemLayoutBinding binding, ProductselectProductCategory1Bean.DataBean bean, int position) {
                    ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
                    params.height = mBinding.gv.getHeight() / 2;
                    binding.getRoot().setLayoutParams(params);
                    LogUtils.d("getImage:" + bean.getImage());
                    if(bean.getCategory1() == -2){  //进入套餐页面
                        binding.image.setImageResource(R.mipmap.icon_taocan);
                        return;
                    }
                    ImageCacheUtils.loadImage(getActivity(), bean.getImage(), binding.image);

                }
                @Override
                protected void itemOnclick(ProductItemLayoutBinding binding, ProductselectProductCategory1Bean.DataBean bean, int position) {
                    if(bean.getCategory1() == -2){  //进入套餐页面
                        startFragment(ScomboFragment.class,null);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.CATEGORY1ID, bean.getCategory1());
                    bundle.putString(Constants.TITLE, bean.getName());
                    startFragment(ProductlisttabFragment.class, bundle);
                }
            });
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.cancelAllRequest();
        }
        super.onDestroy();
    }

    /**
     * 查询商品一级分类（白名单接口）
     */
    private void selectProductCategory1() {
        presenter.productselectProductCategory1(null);
    }
}
