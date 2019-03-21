package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.ShopproductlistContract;
import com.ogmamllpadnew.databinding.FgShopproductlistItemLayoutBinding;
import com.ogmamllpadnew.databinding.ShopProductHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.ShopproductlistPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.GoodsselectPermGoodsListBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺商-商品列表
 * Created by 陈俊山 on 2018-12-05.
 */

public class ShopproductlistFragment extends BaseFragment<FgShopproductlistItemLayoutBinding, GoodsselectPermGoodsListBean.DataBean> implements ShopproductlistContract.View {
    private ShopproductlistPresenter presenter;
    private int width;
    private String keywords;
    private String status;//状态：0未审核，1已审核通过，2审核未通过，3待提交
    private String sellStatus;//上下架状态：默认下架0，上架1

    @Override
    public void setSellStatus(String sellStatus, String sellStatusName) {
        this.sellStatus = sellStatus;
        mHeaderBinding.tvStatus1.setText(sellStatusName);
        onRefresh();
    }

    @Override
    public void setStatus(String status, String statusName) {
        this.status = status;
        mHeaderBinding.tvStatus2.setText(statusName);
        onRefresh();
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_shopproductlist_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wdsp);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ShopproductlistPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, 0, width, 0);
        addHeadView();
    }

    private ShopProductHeaderLayoutBinding mHeaderBinding;
    private TitleRightSearchLayoutBinding searchLayoutBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.shop_product_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());

        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, lvBinding.llHeader, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.etSearch.setHint(getString(R.string.str_sssp));
        searchLayoutBinding.tvAdd.setVisibility(View.GONE);
        searchLayoutBinding.tvScreen.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.GOODSSELECTPERMGOODSLIST) {
            GoodsselectPermGoodsListBean bean = (GoodsselectPermGoodsListBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
            setAllSelectStatus();
        } else if (mTag == HttpPresenter.GOODSPUTAWAYGOODS) {
            ToastUtils.toast("操作成功");
            onRefresh();
        } else if (mTag == HttpPresenter.GOODSSUBMITGOODSAUDIT) {
            ToastUtils.toast("提交成功");
            onRefresh();
        } else if (mTag == HttpPresenter.GOODSDELGOODS) {
            ToastUtils.toast("删除成功");
            onRefresh();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgShopproductlistItemLayoutBinding binding, final GoodsselectPermGoodsListBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvName.setText(item.getName());
        binding.tvClassify.setText(item.getCategoryName());
        binding.tvTime.setText(item.getCreateTime());
        binding.tvStatus1.setText(item.getSellStatusName());
        binding.tvStatus2.setText(item.getStatusName());
        binding.cb.setChecked(item.isCheck());
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.image);
        String spec = "";
        List<String> specs = item.getSpecNameList();
        for (int i = 0; i < specs.size(); i++) {
            String specName = specs.get(i);
            if (specName == null)
                specName = "";
            if (specName.length() > 5) {
                specName = specName.substring(0, 5);
            }
            if (i == 0) {
                spec = specName;
            } else {
                spec = spec + ";\n" + specName;
            }
        }
        if (specs.size() == 3) {
            spec = spec + ";\n" + "...";
        }
        binding.tvSize.setText(spec);

        binding.tvSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, item.getId());
                startFragment(ShopspecificationlistFragment.class, bundle);
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
        binding.tvPutaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //状态：0未审核，1已审核通过，2审核未通过，3待提交
                if (item.getStatus() == 1) {
                    putawayGoods2("1", item.getId());
                } else {
                    ToastUtils.toast(getString(R.string.str_hint21));
                }
            }
        });
        binding.tvSoldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //状态：0未审核，1已审核通过，2审核未通过，3待提交
                if (item.getStatus() == 1) {
                    putawayGoods2("0", item.getId());
                } else {
                    ToastUtils.toast(getString(R.string.str_hint21));
                }
            }
        });
        binding.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitGoodsAudit2(item.getId());
            }
        });
        binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsdelGoods2(item.getId());
            }
        });
        binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.ISADDPRODUCT, false);
                bundle.putSerializable(Constants.BEAN, item);
                startFragment(ShopaddproductFragment.class, bundle);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mHeaderBinding.btnAddProduct.setOnClickListener(this);
        mHeaderBinding.tvDelete.setOnClickListener(this);
        mHeaderBinding.tvUp.setOnClickListener(this);
        mHeaderBinding.tvDown.setOnClickListener(this);
        mHeaderBinding.tvSh.setOnClickListener(this);
        mHeaderBinding.tvStatus1.setOnClickListener(this);
        mHeaderBinding.tvStatus2.setOnClickListener(this);
        mHeaderBinding.cbAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_add_product:
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.ISADDPRODUCT, true);
                startFragment(ShopaddproductFragment.class, bundle);
                break;
            case R.id.tv_delete:
                goodsdelGoods();
                break;
            case R.id.tv_up:
                putawayGoods("1");
                break;
            case R.id.tv_down:
                putawayGoods("0");
                break;
            case R.id.tv_sh:
                submitGoodsAudit();
                break;
            case R.id.tv_status1:
                presenter.showStatus1Dialog();
                break;
            case R.id.tv_status2:
                presenter.showStatus2Dialog();
                break;
            case R.id.cb_all:
                setAllSelect();
                break;
        }
    }

    /**
     * app/goods/delGoods
     * 批量删除商品（仅工厂和店铺拥有该权限）
     */
    private void goodsdelGoods() {
        if (adapter == null) {
            ToastUtils.toast(getString(R.string.str_qxzsp));
            return;
        }
        List<GoodsselectPermGoodsListBean.DataBean> datas = adapter.getDatas();
        if (datas == null || datas.size() == 0) {
            ToastUtils.toast(getString(R.string.str_qxzsp));
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
            ToastUtils.toast(getString(R.string.str_qxzsp));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("ids", ids);
        presenter.goodsdelGoods(map);
    }

    /**
     * app/goods/delGoods
     * 批量删除商品（仅工厂和店铺拥有该权限）
     */
    private void goodsdelGoods2(int ids) {
        Map<String, String> map = new HashMap<>();
        map.put("ids", String.valueOf(ids));
        presenter.goodsdelGoods(map);
    }

    /**
     * 批量上下架商品（仅工厂和店铺拥有该权限）
     */
    private void putawayGoods(String sellStatus) {
        if (adapter == null) {
            ToastUtils.toast(getString(R.string.str_qxzsp));
            return;
        }
        List<GoodsselectPermGoodsListBean.DataBean> datas = adapter.getDatas();
        if (datas == null || datas.size() == 0) {
            ToastUtils.toast(getString(R.string.str_qxzsp));
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
            ToastUtils.toast(getString(R.string.str_qxzsp));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("ids", ids);
        map.put("sellStatus", sellStatus);
        presenter.goodsputawayGoods(map);
    }

    /**
     * /app/goods/submitGoodsAudit
     * 批量提交商品审核（仅工厂和店铺拥有该权限）
     */
    private void submitGoodsAudit() {
        if (adapter == null) {
            ToastUtils.toast(getString(R.string.str_qxzsp));
            return;
        }
        List<GoodsselectPermGoodsListBean.DataBean> datas = adapter.getDatas();
        if (datas == null || datas.size() == 0) {
            ToastUtils.toast(getString(R.string.str_qxzsp));
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
            ToastUtils.toast(getString(R.string.str_qxzsp));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("ids", ids);
        presenter.goodssubmitGoodsAudit(map);
    }

    /**
     * /app/goods/submitGoodsAudit
     * 批量提交商品审核（仅工厂和店铺拥有该权限）
     */
    private void submitGoodsAudit2(int ids) {
        Map<String, String> map = new HashMap<>();
        map.put("ids", String.valueOf(ids));
        presenter.goodssubmitGoodsAudit(map);
    }

    /**
     * 批量上下架商品（仅工厂和店铺拥有该权限）
     */
    private void putawayGoods2(String sellStatus, int ids) {
        Map<String, String> map = new HashMap<>();
        map.put("ids", String.valueOf(ids));
        map.put("sellStatus", sellStatus);
        presenter.goodsputawayGoods(map);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectPermGoodsList(false);
    }

    @Override
    public void onRefresh(boolean isShow) {
        super.onRefresh(isShow);
        selectPermGoodsList(isShow);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectPermGoodsList(true);
        }
    }

    /**
     * 查询商品（仅管理员/工厂/店铺拥有该权限）
     *
     * @param isShow
     */
    private void selectPermGoodsList(boolean isShow) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", keywords);//关键词搜索
        map.put("status", status);//状态：0未审核，1已审核通过，2审核未通过，3待提交
        map.put("sellStatus", sellStatus);//上下架状态：默认下架0，上架1
        presenter.goodsselectPermGoodsList(map, isShow);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    /**
     * 是否已经全选
     *
     * @return
     */
    private boolean isAllSelect() {
        List<GoodsselectPermGoodsListBean.DataBean> datas = adapter.getDatas();
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
        List<GoodsselectPermGoodsListBean.DataBean> datas = adapter.getDatas();
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