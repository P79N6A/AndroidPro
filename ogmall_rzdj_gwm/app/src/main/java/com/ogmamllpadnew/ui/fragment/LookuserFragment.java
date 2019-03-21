package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.LookuserContract;
import com.ogmamllpadnew.databinding.FgDesigneruserHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgLookuserItemLayoutBinding;
import com.ogmamllpadnew.presenter.LookuserPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.UserselectCustomerOfDesignerBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 查看会员
 * Created by 陈俊山 on 2018-11-21.
 */

public class LookuserFragment extends BaseFragment<FgLookuserItemLayoutBinding, UserselectCustomerOfDesignerBean.DataBean> implements LookuserContract.View {
    private LookuserPresenter presenter;
    private String name;
    private String id;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_lookuser_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(name + "设计师名下会员");
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new LookuserPresenter(getActivity(), this);
        name = getActivity().getIntent().getStringExtra(Constants.NAME);
        id = getActivity().getIntent().getStringExtra(Constants.ID);
        addHeadView();
        onRefresh();
    }

    private FgDesigneruserHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_designeruser_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
    }


    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.USERSELECTCUSTOMEROFDESIGNER) {
            UserselectCustomerOfDesignerBean bean = (UserselectCustomerOfDesignerBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgLookuserItemLayoutBinding binding, UserselectCustomerOfDesignerBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvName.setText(item.getContactName());
        binding.tvMobile.setText(item.getContactPhone());
        binding.tvAddress.setText(item.getProvinceName() + item.getCityName() + item.getDistrictName() + item.getAddress());
        binding.tvType.setText(item.getSignName());
        /**客户标识——普通客户*//*
            public static final int CUSTOMER_SIGN_ORDINARY = 0;
            *//**客户标识——潜在意向客户*//*
            public static final int CUSTOMER_SIGN_POTENTIAL = 1;
            *//**客户标识——意向客户*//*
            public static final int CUSTOMER_SIGN_INTENTION = 2;*/
        if (item.getSign().equals("0")) {
            binding.tvType.setBackgroundResource(R.color.transparent);
            binding.tvType.setTextColor(Color.parseColor("#333333"));
        } else if (item.getSign().equals("1")) {
            binding.tvType.setBackgroundResource(R.drawable.shape_user_sign2);
            binding.tvType.setTextColor(Color.parseColor("#ffffff"));
        } else if (item.getSign().equals("2")) {
            binding.tvType.setBackgroundResource(R.drawable.shape_user_sign1);
            binding.tvType.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectCustomerOfDesigner();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectCustomerOfDesigner();
        }
    }

    /**
     * 店铺商查询自己设计师其下的客户（仅店铺商可查询）
     */
    private void selectCustomerOfDesigner() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("id", id);//设计师ID
        presenter.userselectCustomerOfDesigner(map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initData() {
        super.initData();
        ImageCacheUtils.loadImage(getActivity(), MyApp.getInstance().getInfoBean().getData().getPicture(), mHeaderBinding.ivHead);
        mHeaderBinding.tvName.setText(MyApp.getInstance().getInfoBean().getData().getContactName());
        mHeaderBinding.tvMobile.setText(MyApp.getInstance().getInfoBean().getData().getContactPhone());
        mHeaderBinding.tvType.setText(MyApp.getInstance().getInfoBean().getData().getTypeName());
    }
}