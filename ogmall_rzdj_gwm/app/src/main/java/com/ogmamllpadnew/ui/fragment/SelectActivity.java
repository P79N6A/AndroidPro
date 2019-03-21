package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.SelectBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.AddressContract;
import com.ogmamllpadnew.databinding.AddressTitleLayoutBinding;
import com.ogmamllpadnew.databinding.FgAddressItemLayoutBinding;
import com.ogmamllpadnew.presenter.AddressPresenter;
import com.ogmamllpadnew.ui.BaseActivity;
import com.shan.netlibrary.bean.PlanselectAllHouseStyleBean;
import com.shan.netlibrary.bean.PlanselectAllHouseTypeBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址列表
 * Created by 陈俊山 on 2018-11-16.
 */

public class SelectActivity extends BaseActivity<FgAddressItemLayoutBinding, SelectBean> implements AddressContract.View {
    private AddressPresenter presenter;
    private int width;
    private int type;//1户型选择，2风格选择,3客户类型选择

    @Override
    public int bindItemLayout() {
        return R.layout.fg_address_item_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
        addHeadView();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        AddressTitleLayoutBinding mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.address_title_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
        if (type == 1) {
            mHeaderBinding.tvTitle.setText(getString(R.string.str_hxxz));
        } else if (type == 2) {
            mHeaderBinding.tvTitle.setText(getString(R.string.str_fgxz));
        } else if (type == 3) {
            mHeaderBinding.tvTitle.setText(getString(R.string.str_khxl));
        }
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new AddressPresenter(this, this);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        type = getIntent().getIntExtra(Constants.TYPE, 0);
        lvBinding.llParent.setBackgroundColor(Color.parseColor("#60000000"));
        width = ScreenUtils.getScreenWidth();
        lvBinding.llParent.setPadding((int) (width * 0.65), 0, 0, 0);
        lvBinding.fl.setBackgroundColor(Color.WHITE);
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        if (type == 1) {
            //1户型选择
            presenter.planselectAllHouseType(null);
        } else if (type == 2) {
            //2风格选择
            presenter.planselectAllHouseStyle(null);
        } else if (type == 3) {
            //3客户类型选择
            /**客户标识——普通客户*//*
            public static final int CUSTOMER_SIGN_ORDINARY = 0;
            *//**客户标识——潜在意向客户*//*
            public static final int CUSTOMER_SIGN_POTENTIAL = 1;
            *//**客户标识——意向客户*//*
            public static final int CUSTOMER_SIGN_INTENTION = 2;*/
            List<SelectBean> beans = new ArrayList<>();
            beans.add(new SelectBean(type,"普通客户","0"));
            beans.add(new SelectBean(type,"潜在意向客户","1"));
            beans.add(new SelectBean(type,"意向客户","2"));
            setData(beans);
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PLANSELECTALLHOUSETYPE) {
            //1户型选择
            PlanselectAllHouseTypeBean bean = (PlanselectAllHouseTypeBean) baseBean;
            List<SelectBean> beans = new ArrayList<>();
            for (int i = 0; i < bean.getData().size(); i++) {
                PlanselectAllHouseTypeBean.DataBean dataBean = bean.getData().get(i);
                beans.add(new SelectBean(type, dataBean.getTypeName(), String.valueOf(dataBean.getId())));
            }
            setData(beans);
        } else if (mTag == HttpPresenter.PLANSELECTALLHOUSESTYLE) {
            //2风格选择
            PlanselectAllHouseStyleBean bean = (PlanselectAllHouseStyleBean) baseBean;
            List<SelectBean> beans = new ArrayList<>();
            for (int i = 0; i < bean.getData().size(); i++) {
                PlanselectAllHouseStyleBean.DataBean dataBean = bean.getData().get(i);
                beans.add(new SelectBean(type, dataBean.getStyleName(), String.valueOf(dataBean.getId())));
            }
            setData(beans);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgAddressItemLayoutBinding binding, SelectBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvTitle.setText(item.getName());
    }

    @Override
    protected void onItemClick(SelectBean bean, int position) {
        super.onItemClick(bean, position);
        EventBus.getDefault().post(new MessageEvent(MsgConstant.SELELCT, bean));
        finish();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        lvBinding.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}