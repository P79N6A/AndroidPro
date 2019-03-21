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
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.AddressContract;
import com.ogmamllpadnew.databinding.AddressTitleLayoutBinding;
import com.ogmamllpadnew.databinding.FgAddressItemLayoutBinding;
import com.ogmamllpadnew.presenter.AddressPresenter;
import com.ogmamllpadnew.ui.BaseActivity;
import com.shan.netlibrary.bean.AddressBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 地址列表
 * Created by 陈俊山 on 2018-11-16.
 */

public class AddressActivity extends BaseActivity<FgAddressItemLayoutBinding, AddressBean.DataBean> implements AddressContract.View {
    private AddressPresenter presenter;
    private int width;
    private int type;//1省，2市，3区
    private String provinceId;
    private String cityId;

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
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new AddressPresenter(this, this);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        type = getIntent().getIntExtra(Constants.TYPE, 0);
        if (type == 2) {
            provinceId = getIntent().getStringExtra(Constants.ID);
        } else if (type == 3) {
            cityId = getIntent().getStringExtra(Constants.ID);
        }
        lvBinding.llParent.setBackgroundColor(Color.TRANSPARENT);
        width = ScreenUtils.getScreenWidth();
        lvBinding.llParent.setPadding((int) (width * 0.8), 0, 0, 0);
        lvBinding.fl.setBackgroundColor(Color.WHITE);
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        if (type == 1) {
            //获取省数据
            presenter.appgetAllProvince(null);
        } else if (type == 2) {
            //获取市数据
            Map<String, String> map = new HashMap<>();
            map.put("provinceId", provinceId);
            presenter.appgetCityByProvince(map);
        } else if (type == 3) {
            //获取区数据
            Map<String, String> map = new HashMap<>();
            map.put("cityId", cityId);
            presenter.appgetDistrictByCity(map);
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.APPGETALLPROVINCE || mTag == HttpPresenter.APPGETCITYBYPROVINCE || mTag == HttpPresenter.APPGETDISTRICTBYCITY) {
            AddressBean addressBean = (AddressBean) baseBean;
            setData(addressBean.getData());
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgAddressItemLayoutBinding binding, AddressBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvTitle.setText(item.getName());
    }

    @Override
    protected void onItemClick(AddressBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        if (type == 1) {
            EventBus.getDefault().post(new MessageEvent(MsgConstant.ADDRESS, new AddressCallbackBean(type, String.valueOf(bean.getId()), bean.getName())));
            type = 2;
            provinceId = String.valueOf(bean.getId());
            getData();
        } else if (type == 2) {
            EventBus.getDefault().post(new MessageEvent(MsgConstant.ADDRESS, new AddressCallbackBean(type, String.valueOf(bean.getId()), bean.getName())));
            type = 3;
            cityId = String.valueOf(bean.getId());
            getData();
        } else if (type == 3) {
            EventBus.getDefault().post(new MessageEvent(MsgConstant.ADDRESS, new AddressCallbackBean(type, String.valueOf(bean.getId()), bean.getName())));
            finish();
        }
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