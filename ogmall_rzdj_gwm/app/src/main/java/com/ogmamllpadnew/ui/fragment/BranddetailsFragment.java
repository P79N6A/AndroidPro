package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.BranddetailsContract;
import com.ogmamllpadnew.databinding.BrandDetailsHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgBranddetailsItemLayoutBinding;
import com.ogmamllpadnew.presenter.BranddetailsPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandselectBrandByIdBean;
import com.shan.netlibrary.bean.BrandselectCategory2ByBrandIdBean;
import com.shan.netlibrary.bean.ProductselectProductByCategoryBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌详情
 * Created by 陈俊山 on 2018-11-07.
 */

public class BranddetailsFragment extends BaseFragment<FgBranddetailsItemLayoutBinding, ProductselectProductByCategoryBean.DataBean> implements BranddetailsContract.View {
    private BranddetailsPresenter presenter;
    private int width;//屏幕宽度
    private int id;
    private int category2;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_branddetails_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_ppxq);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BranddetailsPresenter(getActivity(), this);
        id = getActivity().getIntent().getIntExtra(Constants.ID, 0);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        width = ScreenUtils.getScreenWidth() / 8;
        setRecycViewGrid(3);
        addHeadView();
        getBrandDetails();
        getStyle();
    }

    private BrandselectBrandByIdBean brandByIdBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.BRANDSELECTBRANDBYID) {
            brandByIdBean = (BrandselectBrandByIdBean) baseBean;
            bindData(brandByIdBean);
        } else if (mTag == HttpPresenter.BRANDSELECTCATEGORY2BYBRANDID) {
            BrandselectCategory2ByBrandIdBean bean = (BrandselectCategory2ByBrandIdBean) baseBean;
            BrandselectCategory2ByBrandIdBean.DataBean dataBean = new BrandselectCategory2ByBrandIdBean.DataBean();
            dataBean.setId(Constants.AllBean);
            dataBean.setName(getString(R.string.str_qb));
            dataBean.setCheck(true);
            bean.getData().add(0, dataBean);
            initTitleData(bean);
            category2 = Constants.AllBean;
            selectProductByCategory();
        } else if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTBYCATEGORY) {
            //商品类表
            ProductselectProductByCategoryBean bean = (ProductselectProductByCategoryBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
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

    /**
     * @param bean
     */
    private void bindData(BrandselectBrandByIdBean bean) {
        ImageCacheUtils.loadImage(getActivity(), bean.getData().getHeadimage(), headerLayoutBinding.image);
        ImageCacheUtils.loadImage(getActivity(), bean.getData().getImage(), headerLayoutBinding.image2);
        headerLayoutBinding.tvTitle.setText(bean.getData().getName());
        headerLayoutBinding.tvContent.setText(bean.getData().getContent());
        if (TextUtils.isEmpty(bean.getData().getDurl())) {
            headerLayoutBinding.tvLook.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private ProductselectProductByCategoryBean.DataBean clickItem;

    @Override
    protected void getListVewItem(FgBranddetailsItemLayoutBinding binding, final ProductselectProductByCategoryBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        if (position % 3 == 0) {
            binding.getRoot().setPadding(width, width / 3, 0, 0);
        } else if (position % 3 == 1) {
            binding.getRoot().setPadding(width / 2, width / 3, width / 2, 0);
        } else if (position % 3 == 2) {
            binding.getRoot().setPadding(0, width / 3, width, 0);
        }
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.imageview);
        binding.tvName.setText(item.getName());
        binding.tvSubName.setText(item.getSubTitle());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private BrandDetailsHeaderLayoutBinding headerLayoutBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        headerLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.brand_details_header_layout, lvBinding.xrecyclerview, false);
        lvBinding.xrecyclerview.addHeaderView(headerLayoutBinding.getRoot());
        headerLayoutBinding.tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (brandByIdBean == null)
                    return;
                Bundle bundle = new Bundle();
                bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
                bundle.putString(Constants.URL, brandByIdBean.getData().getDurl());
                bundle.putBoolean(Constants.ISHIDETITLE, true);
                startFragment(BasewebviewFragment.class, bundle);
            }
        });
    }

    private List<CheckBox> checkBoxes;
    private List<BrandselectCategory2ByBrandIdBean.DataBean> titleDatas;

    /**
     * 初始化title数据
     */
    private void initTitleData(BrandselectCategory2ByBrandIdBean bean) {
        titleDatas = bean.getData();
        checkBoxes = new ArrayList<>();
        for (int i = 0; i < titleDatas.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.brand_details_title_item_layout, headerLayoutBinding.autoLl, false);
            CheckBox cb = (CheckBox) view.findViewById(R.id.rb);
            cb.setText(titleDatas.get(i).getName());
            checkBoxes.add(cb);
            if (titleDatas.get(i).isCheck()) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshOneTileStatus(j);
                }
            });
            headerLayoutBinding.autoLl.addView(view);
        }
    }

    /**
     * 刷新一级title的状态
     *
     * @param i
     */
    private void refreshOneTileStatus(int i) {
        for (int j = 0; j < checkBoxes.size(); j++) {
            if (j == i) {
                checkBoxes.get(j).setChecked(true);
            } else {
                checkBoxes.get(j).setChecked(false);
            }
        }
        category2 = titleDatas.get(i).getId();
        onRefresh();
    }

    /**
     * 获取品牌详情
     */
    private void getBrandDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        presenter.brandselectBrandById(map);
    }

    /**
     * 获取风格列表
     */
    private void getStyle() {
        Map<String, String> map = new HashMap<>();
        map.put("brandId", String.valueOf(id));
        presenter.brandselectCategory2ByBrandId(map);
    }

    /**
     * 根据分类查询商品或联合关键字查询（白名单接口）
     */
    private void selectProductByCategory() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("brandId", String.valueOf(id));//关键词搜索
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("customerId", String.valueOf(0));
        }else {
            map.put("customerId", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        if (category2 != Constants.AllBean) {
            map.put("category2", String.valueOf(category2));//关键词搜索
        }
        presenter.productselectProductByCategory(map, false);
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
        if (MyApp.getInstance().getCurrentUser() != null){
            map.put("id", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
    }else {
            map.put("id", String.valueOf(0));
        }
        presenter.productcollectProduct(map);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        headerLayoutBinding.tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (brandByIdBean == null)
                    return;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.URL, brandByIdBean.getData().getDurl());
                bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
                bundle.putBoolean(Constants.ISHIDETITLE, true);
                startFragment(BasewebviewFragment.class, bundle);
            }
        });
    }
}