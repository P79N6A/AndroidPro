package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.ProductdetailsContract;
import com.ogmamllpadnew.databinding.FgProductdetailsLayoutBinding;
import com.ogmamllpadnew.presenter.ProductdetailsPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 * Created by 陈俊山 on 2018-11-08.
 */

public class ProductdetailsFragment extends BaseFragment<FgProductdetailsLayoutBinding, String> implements ProductdetailsContract.View {
    private ProductdetailsPresenter presenter;
    private int productId;
    private int dataType;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_productdetails_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_spxq);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductdetailsPresenter(getActivity(), this);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        productId = getActivity().getIntent().getIntExtra(Constants.ID, 0);
        dataType = getActivity().getIntent().getIntExtra(Constants.DATATYPE, 0);
        selectProductById();
        addHeadView();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.productdetails_header_layout, lvBinding.xrecyclerview, false);
        lvBinding.xrecyclerview.addHeaderView(view);
    }

    private List<String> lImages;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTBYID) {
            lImages = new ArrayList<>();
            ProductsdetailsBean detailsBean = (ProductsdetailsBean) baseBean;
            //绑定图片列表数据
            bindImages(detailsBean);
            EventBus.getDefault().post(new MessageEvent(MsgConstant.PRODUCTDETAILS, detailsBean));
        }
    }

    /**
     * 绑定图片列表数据
     */
    private void bindImages(ProductsdetailsBean detailsBean) {
        String images = detailsBean.getData().getImages();
        if (!TextUtils.isEmpty(images)) {
            if (images.contains(",")) {
                String[] sImages = images.split(",");
                for (int i = 0; i < sImages.length; i++) {
                    lImages.add(sImages[i]);
                }
            } else {
                lImages.add(images);
            }
        } else {
            lImages.add("");
        }
        setData(lImages);
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

    @Override
    protected void getListVewItem(FgProductdetailsLayoutBinding binding, String item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item, binding.iv);
    }

    /**
     * 根据商品ID查询商品详情（白名单接口）
     */
    private void selectProductById() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", String.valueOf(productId));//商品ID
        map.put("dataType", String.valueOf(dataType));//数据类型，0=平台商品，1=自营商品；缺省值=0
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("id", String.valueOf(0));
        }else {
            map.put("id", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        presenter.productselectProductById(map);
    }



}
