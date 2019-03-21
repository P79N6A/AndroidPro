package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.SupplementBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.ShopaddproductContract;
import com.ogmamllpadnew.databinding.FgShopaddproductLayoutBinding;
import com.ogmamllpadnew.databinding.SupplementLayoutBinding;
import com.ogmamllpadnew.presenter.ShopaddproductPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.widget.PhotoSelectDialog;
import com.shan.netlibrary.bean.AppuploadImgBean;
import com.shan.netlibrary.bean.GoodsselectCategoryByCategory3IdBean;
import com.shan.netlibrary.bean.GoodsselectPermGoodsListBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.shan.netlibrary.net.HttpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 店铺商-添加商品
 * Created by 陈俊山 on 2018-12-05.
 */

public class ShopaddproductFragment extends BaseFragment<FgShopaddproductLayoutBinding, Object> implements ShopaddproductContract.View {
    private ShopaddproductPresenter presenter;
    private int classifyId1;
    private int classifyId2;
    private int classifyId3;
    private String classifyName1;
    private String classifyName2;
    private String classifyName3;
    private String unit;//商品单位
    private String headImage;//商品主图
    private boolean isOtherUnit;
    private boolean isAddProduct;//true添加商品，false修改商品
    private GoodsselectPermGoodsListBean.DataBean goodsBean;

    @Override
    public boolean isAddProduct() {
        return isAddProduct;
    }

    @Override
    public void setClassifyId1(int classifyId1) {
        this.classifyId1 = classifyId1;
    }

    @Override
    public void setClassifyId2(int classifyId2) {
        this.classifyId2 = classifyId2;
    }

    @Override
    public void setClassifyId3(int classifyId3) {
        this.classifyId3 = classifyId3;
    }

    @Override
    public void setClassifyName1(String classifyName1) {
        this.classifyName1 = classifyName1;
    }

    @Override
    public void setClassifyName2(String classifyName2) {
        this.classifyName2 = classifyName2;
    }

    @Override
    public void setClassifyName3(String classifyName3) {
        this.classifyName3 = classifyName3;
        mBinding.tvSelectHx.setText(classifyName1 + " > " + classifyName2 + " > " + classifyName3);
    }

    @Override
    public int bindLayout() {
        return R.layout.fg_shopaddproduct_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        if (isAddProduct) {
            setLeftText(R.string.str_tjsp);
        } else {
            setLeftText(R.string.str_xgsp);
        }
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ShopaddproductPresenter(getActivity(), this);
        isAddProduct = getActivity().getIntent().getBooleanExtra(Constants.ISADDPRODUCT, true);
        goodsBean = (GoodsselectPermGoodsListBean.DataBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.GOODSADDGOODS) {
            ToastUtils.toast("保存成功");
            getActivity().finish();
        } else if (mTag == HttpPresenter.GOODSEDITGOODS) {
            ToastUtils.toast("修改成功");
            getActivity().finish();
        } else if (mTag == HttpPresenter.GOODSSELECTCATEGORYBYCATEGORY3ID) {
            GoodsselectCategoryByCategory3IdBean bean = (GoodsselectCategoryByCategory3IdBean) baseBean;
            setClassifyId1(bean.getData().getCategory1Id());
            setClassifyId2(bean.getData().getCategory2Id());
            setClassifyId3(bean.getData().getCategory3Id());
            setClassifyName1(bean.getData().getCategory1Name());
            setClassifyName2(bean.getData().getCategory2Name());
            setClassifyName3(bean.getData().getCategory3Name());
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
        mBinding.flClassify.setOnClickListener(this);
        //mBinding.btnAddSize.setOnClickListener(this);
        mBinding.btnAdd.setOnClickListener(this);
        mBinding.btnOk.setOnClickListener(this);
        mBinding.btnCancel.setOnClickListener(this);
        mBinding.ivFmzp.setOnClickListener(this);
        mBinding.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.cb6) {
                    mBinding.etOther.setFocusableInTouchMode(true);
                    mBinding.etOther.setFocusable(true);
                    mBinding.etOther.requestFocus();
                    mBinding.etOther.setBackgroundResource(R.drawable.shape_grap2);
                    mBinding.etOther.setHint(getString(R.string.str_srdw));
                    SoftKeyBoardUtils.openKeybord(mBinding.etOther, getActivity());
                    isOtherUnit = true;
                } else {
                    isOtherUnit = false;
                    mBinding.etOther.setFocusable(false);
                    mBinding.etOther.setFocusableInTouchMode(false);
                    mBinding.etOther.setBackgroundResource(R.color.transparent);
                    mBinding.etOther.setHint(getString(R.string.str_qt));
                    SoftKeyBoardUtils.closeKeybord(mBinding.etOther, getActivity());
                }
                switch (i) {
                    case R.id.cb1:
                        unit = "张";
                        break;
                    case R.id.cb2:
                        unit = "个";
                        break;
                    case R.id.cb3:
                        unit = "盏";
                        break;
                    case R.id.cb4:
                        unit = "套";
                        break;
                    case R.id.cb5:
                        unit = "件";
                        break;
                    case R.id.cb6:
                        unit = "";
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fl_classify:
                presenter.showClassify();
                break;
            case R.id.btn_add:
                addSupplement();
                break;
            case R.id.btn_ok:
                addProduct();
                break;
            case R.id.btn_cancel:
                getActivity().finish();
                break;
            case R.id.iv_fmzp:
                PhotoSelectDialog.open(this);
                break;
        }
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
        appuploadImg(path);
    }

    /**
     * 上传图片
     *
     * @param filePath
     */
    public void appuploadImg(String filePath) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), new File(filePath));
        HttpCallback callback = new HttpCallback<AppuploadImgBean>(getActivity(), this, false) {
            @Override
            protected void onSuccess(AppuploadImgBean baseBean) {
                //封面照片
                headImage = baseBean.getMessage();
                ImageCacheUtils.loadImage(getActivity(), baseBean.getData().getPath(), mBinding.ivFmzp);
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        HttpBuilder.getInstance().execute(HttpBuilder.httpService.appuploadImg(requestFile), callback);
    }

    /**
     * 添加商品
     */
    private void addProduct() {
        String name = mBinding.etPlanTitle.getText().toString().trim();
        String subTitle = mBinding.etFbt.getText().toString().trim();
        if (isOtherUnit) {
            unit = mBinding.etOther.getText().toString().trim();
        }

        if (TextUtils.isEmpty(name)) {
            ToastUtils.toast(getString(R.string.str_hint11));
            return;
        }
        if (TextUtils.isEmpty(subTitle)) {
            ToastUtils.toast(getString(R.string.str_hint12));
            return;
        }
        if (classifyId3 == 0) {
            ToastUtils.toast(getString(R.string.str_hint13));
            return;
        }
        if (TextUtils.isEmpty(headImage) && isAddProduct) {
            ToastUtils.toast(getString(R.string.str_hint14));
            return;
        }
        if (TextUtils.isEmpty(unit)) {
            if (isOtherUnit) {
                ToastUtils.toast(getString(R.string.str_hint17));
            } else {
                ToastUtils.toast(getString(R.string.str_hint15));
            }
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("subTitle", subTitle);
        map.put("goodsCategoryId", String.valueOf(classifyId3));
        map.put("headImage", headImage);
        map.put("unit", unit);
        map.put("attribute", new Gson().toJson(supplementBeans));
        if (isAddProduct) {
            presenter.goodsaddGoods(map);
        } else {
            if (goodsBean == null)
                return;
            map.put("id", String.valueOf(goodsBean.getId()));
            presenter.goodseditGoods(map);
        }
    }

    private List<SupplementBean> supplementBeans;

    /**
     * 增加补充
     */
    private void addSupplement() {
        String key = mBinding.etKey.getText().toString().trim();
        String value = mBinding.etValue.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            JitterUtils.start(mBinding.etKey);
            return;
        }
        if (TextUtils.isEmpty(value)) {
            JitterUtils.start(mBinding.etValue);
            return;
        }
        if (supplementBeans == null) {
            supplementBeans = new ArrayList<>();
        }
        SupplementBean supplementBean = new SupplementBean(key, value);
        supplementBeans.add(supplementBean);
        refreshSupplementView();
    }

    /**
     * 刷新商品参数补充View
     */
    private void refreshSupplementView() {
        mBinding.llSpcsbc.removeAllViews();
        if (supplementBeans == null || supplementBeans.size() == 0)
            return;
        mBinding.etKey.setText("");
        mBinding.etValue.setText("");
        mBinding.llSpcsbc.setVisibility(View.VISIBLE);
        for (int i = 0; i < supplementBeans.size(); i++) {
            final int j = i;
            SupplementLayoutBinding supplementBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.supplement_layout, mBinding.llSpcsbc, false);
            mBinding.llSpcsbc.addView(supplementBinding.getRoot());
            supplementBinding.tvName.setText(supplementBeans.get(i).getValue() + "：" + supplementBeans.get(i).getName());
            supplementBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    supplementBeans.remove(j);
                    refreshSupplementView();
                }
            });
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (!isAddProduct && goodsBean != null) {
            mBinding.etPlanTitle.setText(goodsBean.getName());
            mBinding.etFbt.setText(goodsBean.getSubTitle());
            ImageCacheUtils.loadImage(getActivity(), goodsBean.getHeadImage(), mBinding.ivFmzp);
            //设置补充参数
            supplementBeans = new ArrayList<>();
            for (int i = 0; i < goodsBean.getAttributeList().size(); i++) {
                supplementBeans.add(new SupplementBean(goodsBean.getAttributeList().get(i).getName(), goodsBean.getAttributeList().get(i).getValue()));
            }
            refreshSupplementView();
            unit = goodsBean.getUnit();
            //设置商品单位
            if (unit.equals("张")) {
                mBinding.cb1.setChecked(true);
            } else if (unit.equals("个")) {
                mBinding.cb2.setChecked(true);
            } else if (unit.equals("盏")) {
                mBinding.cb3.setChecked(true);
            } else if (unit.equals("套")) {
                mBinding.cb4.setChecked(true);
            } else if (unit.equals("件")) {
                mBinding.cb5.setChecked(true);
            } else {
                mBinding.cb6.setChecked(true);
                mBinding.etOther.setText(unit);
            }
            //三级id
            classifyId3 = goodsBean.getGoodsCategoryId();
            Map<String, String> map = new HashMap<>();
            map.put("category3Id", String.valueOf(classifyId3));
            presenter.goodsselectCategoryByCategory3Id(map);
        }
    }
}
