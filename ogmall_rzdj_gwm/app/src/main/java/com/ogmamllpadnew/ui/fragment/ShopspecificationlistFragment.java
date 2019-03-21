package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.ShopspecificationlistContract;
import com.ogmamllpadnew.databinding.FgShopspecificationlistItemLayoutBinding;
import com.ogmamllpadnew.databinding.ShopSpecificationHeaderLayoutBinding;
import com.ogmamllpadnew.presenter.ShopspecificationlistPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.widget.PhotoSelectDialog;
import com.shan.netlibrary.bean.GoodsselectGoodsSpecificationBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺商-规格列表
 * Created by 陈俊山 on 2018-12-11.
 */

public class ShopspecificationlistFragment extends BaseFragment<FgShopspecificationlistItemLayoutBinding, GoodsselectGoodsSpecificationBean.DataBean> implements ShopspecificationlistContract.View {
    private ShopspecificationlistPresenter presenter;
    private int width;
    private int goodsId;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_shopspecificationlist_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_spgg);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        pageSize = 100;
        presenter = new ShopspecificationlistPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, 0, width, 0);
        addHeadView();
        goodsId = getActivity().getIntent().getIntExtra(Constants.ID, 0);
        onRefresh();
    }

    private ShopSpecificationHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.shop_specification_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.GOODSADDGOODSSPECIFICATION) {
            ToastUtils.toast(getString(R.string.str_tjcg));
            onRefresh();
            presenter.getDialogAddSize().dismiss();
        } else if (mTag == HttpPresenter.GOODSSELECTGOODSSPECIFICATION) {
            GoodsselectGoodsSpecificationBean bean = (GoodsselectGoodsSpecificationBean) baseBean;
            setData(bean.getData());
            setAllSelectStatus();
        } else if (mTag == HttpPresenter.GOODSDELGOODSSPECIFICATION) {
            ToastUtils.toast("删除成功");
            onRefresh();
        } else if (mTag == HttpPresenter.GOODSEDITGOODSSPECIFICATION) {
            ToastUtils.toast(getString(R.string.str_xgcg));
            onRefresh();
            presenter.getDialogAddSize().dismiss();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgShopspecificationlistItemLayoutBinding binding, final GoodsselectGoodsSpecificationBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvName.setText(item.getName());
        binding.tvSpec.setText(item.getSpec());
        binding.tvColor.setText(item.getColor());
        binding.tvOldPrice.setText(item.getOriginalPrice());
        binding.tvPrice.setText(item.getPrice());
        ImageCacheUtils.loadImage(getActivity(), item.getImagesList().size() > 0 ? item.getImagesList().get(0) : "", binding.image);
        binding.cb.setChecked(item.isCheck());
        binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showAddSize(goodsId, true, item);
            }
        });
        binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delGoodsSpecification2(item.getId());
            }
        });
        binding.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setCheck(!item.isCheck());
                adapter.notifyDataSetChanged();
                setAllSelectStatus();
            }
        });
    }

    /**
     * app/goods/delGoodsSpecification
     * 批量删除商品规格（仅工厂和店铺拥有该权限）
     */
    private void delGoodsSpecification() {
        if (adapter == null) {
            ToastUtils.toast(getString(R.string.str_qxzfg));
            return;
        }
        List<GoodsselectGoodsSpecificationBean.DataBean> datas = adapter.getDatas();
        if (datas == null || datas.size() == 0) {
            ToastUtils.toast(getString(R.string.str_qxzfg));
            return;
        }
        String ids = "";
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isCheck()) {
                if (TextUtils.isEmpty(ids)) {
                    ids = String.valueOf(datas.get(i).getId());
                } else {
                    ids += "," + String.valueOf(datas.get(i).getId());
                }

            }
        }
        if (TextUtils.isEmpty(ids)) {
            ToastUtils.toast(getString(R.string.str_qxzfg));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("ids", ids);
        presenter.goodsdelGoodsSpecification(map);
    }

    /**
     * app/goods/delGoodsSpecification
     * 批量删除商品规格（仅工厂和店铺拥有该权限）
     */
    private void delGoodsSpecification2(int ids) {
        Map<String, String> map = new HashMap<>();
        map.put("ids", String.valueOf(ids));
        presenter.goodsdelGoodsSpecification(map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        goodsselectGoodsSpecification();
    }

    /**
     * 查询商品（仅管理员/工厂/店铺拥有该权限）
     */
    private void goodsselectGoodsSpecification() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("goodsId", String.valueOf(goodsId));//商品ID
        presenter.goodsselectGoodsSpecification(map);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mHeaderBinding.tvDelete.setOnClickListener(this);
        mHeaderBinding.btnAddProduct.setOnClickListener(this);
        mHeaderBinding.cbAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_delete:
                delGoodsSpecification();
                break;
            case R.id.btn_add_product:
                presenter.showAddSize(goodsId, false, null);
                break;
            case R.id.cb_all:
                setAllSelect();
                break;
        }
    }

    @Override
    public void uploadPictures() {
        PhotoSelectDialog.open(this);
    }

    @Override
    public void photoResult(String path) {
        super.photoResult(path);
        if (TextUtils.isEmpty(path)) {
            ToastUtils.toast(getString(R.string.str_scsb));
            return;
        } else if (path.contains(".gif")) {
            ToastUtils.toast(getString(R.string.str_bnscdt));
            return;
        }
        presenter.bindImage(path);
    }

    /**
     * 是否已经全选
     *
     * @return
     */
    private boolean isAllSelect() {
        List<GoodsselectGoodsSpecificationBean.DataBean> datas = adapter.getDatas();
        if (datas != null && datas.size() == 0) {
            return false;
        } else {
            boolean isTure = true;
            for (int i = 0; i < datas.size(); i++) {
                if (!datas.get(i).isCheck()) {
                    isTure = false;
                    break;
                }
            }
            return isTure;
        }
    }

    /**
     * 设置全选或者清空全选
     *
     * @return
     */
    private void setAllSelect() {
        List<GoodsselectGoodsSpecificationBean.DataBean> datas = adapter.getDatas();
        if (datas != null && datas.size() == 0)
            return;
        boolean isAllSelect = isAllSelect();
        if (isAllSelect) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setCheck(false);
            }
        } else {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setCheck(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置全部选中状态
     */
    private void setAllSelectStatus() {
        mHeaderBinding.cbAll.setChecked(isAllSelect());
    }
}