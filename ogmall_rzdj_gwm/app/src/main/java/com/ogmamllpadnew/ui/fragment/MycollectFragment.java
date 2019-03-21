package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.MycollectContract;
import com.ogmamllpadnew.databinding.FgMycollectHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgMycollectItemLayoutBinding;
import com.ogmamllpadnew.presenter.MycollectPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductselectProductOfCollectBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的收藏
 * Created by 陈俊山 on 2018-11-12.
 */

public class MycollectFragment extends BaseFragment<FgMycollectItemLayoutBinding, ProductselectProductOfCollectBean.DataBean> implements MycollectContract.View {
    private MycollectPresenter presenter;
    private int width;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_mycollect_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wdsc);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MycollectPresenter(getActivity(), this);
        setRecycViewGrid(3);
        width = ScreenUtils.getScreenWidth();
        lvBinding.xrecyclerview.setPadding(width / 10, 0, width / 10, 0);
        //addHeadView();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        FgMycollectHeaderLayoutBinding mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_mycollect_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTOFCOLLECT) {
            ProductselectProductOfCollectBean bean = (ProductselectProductOfCollectBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgMycollectItemLayoutBinding binding, ProductselectProductOfCollectBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.ivHead);
        binding.tvName.setText(item.getName());
        binding.tvSubName.setText(item.getSubTitle());
        binding.tvPrice.setText(PublicUtils.getRenminbi() + item.getPrice());
    }

    @Override
    protected void onItemClick(ProductselectProductOfCollectBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, bean.getId());
        bundle.putInt(Constants.DATATYPE, bean.getDataType());
        startFragment(ProductdetailsFragment.class, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    /**
     * POST /app/product/selectProductOfCollect
     * 查询我的收藏商品或者客户的收藏商品
     */
    private void selectProductOfCollect() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("id", String.valueOf(0));
        }else {
            map.put("id", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        presenter.productselectProductOfCollect(map);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectProductOfCollect();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectProductOfCollect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}