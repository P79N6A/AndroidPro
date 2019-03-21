package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.contract.ProductlookContract;
import com.ogmallpad.databinding.FgProductlookLayoutBinding;
import com.ogmallpad.presenter.ProductlookPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.bean.CategorysBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品展示
 * Created by chenjunshan on 2018-07-03.
 */

public class ProductlookFragment extends BaseFragment<FgProductlookLayoutBinding, Object> implements ProductlookContract.View {
    private ProductlookPresenter presenter;
    private int categoryId1;
    private int categoryId2;
    private int categoryId3;

    @Override
    public int bindLayout() {
        return R.layout.fg_productlook_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductlookPresenter(getActivity(), this);
    }

    private void getData() {
        //获取产品分类列表 categoryId 不传时，获取所有的一级分类
        Map<String, String> map = new HashMap<>();
        map.put("size", "3");//获取3条数据
        presenter.categorys(map);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bean == null) {
            getData();
        }
        ((HomeActivity)getActivity()).getBinding().icLogo.setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setImageResource(R.mipmap.ic_product_on);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setText("商品展示");
        ((HomeActivity)getActivity()).getBinding().llSearch.setVisibility(View.GONE);
    }

    private CategorysBean bean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CATEGORYS) {
            bean = (CategorysBean) baseBean;
            if (bean.getData().size() == 1) {
                categoryId1 = bean.getData().get(0).getId();
            } else if (bean.getData().size() == 2) {
                categoryId1 = bean.getData().get(0).getId();
                categoryId2 = bean.getData().get(1).getId();
            } else if (bean.getData().size() == 3) {
                categoryId1 = bean.getData().get(0).getId();
                categoryId2 = bean.getData().get(1).getId();
                categoryId3 = bean.getData().get(2).getId();
            }
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
        mBinding.fl1.setOnClickListener(this);
        mBinding.fl2.setOnClickListener(this);
        mBinding.fl3.setOnClickListener(this);
        mBinding.fl4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.fl1:
                bundle = new Bundle();
                bundle.putInt(Constants.ID, categoryId2);
                bundle.putString(Constants.TITLE,"软装家具");
                startFragment(BrandproductTabFragment.class, bundle);
                break;
            case R.id.fl2:
                startFragment(BktjtabFragment.class, null);
                break;
            case R.id.fl3:
                bundle = new Bundle();
                bundle.putInt(Constants.ID, categoryId1);
                bundle.putString(Constants.TITLE,"品牌家具");
                startFragment(BrandproductTabFragment.class, bundle);
                break;
            case R.id.fl4:
                bundle = new Bundle();
                bundle.putInt(Constants.ID, categoryId3);
                bundle.putString(Constants.TITLE,"个性生活");
                startFragment(BrandproductTabFragment.class, bundle);
                break;
        }
    }

}
