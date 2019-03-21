package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.contract.ShopaddproductContract;
import com.ogmamllpadnew.databinding.DialogAddSizeLayoutBinding;
import com.ogmamllpadnew.databinding.DialogClassifyLayoutBinding;
import com.ogmamllpadnew.databinding.ShopClassifyLayoutBinding;
import com.shan.netlibrary.bean.GoodsselectGoodsCategoryBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺商-添加商品
 * Created by 陈俊山 on 2018-12-05.
 */

public class ShopaddproductPresenter extends HttpPresenter implements ShopaddproductContract.Presenter {
    private Context mContext;
    private ShopaddproductContract.View mView;
    private int classifyId1;
    private int classifyId2;
    private int classifyId3;
    private String classifyName1;
    private String classifyName2;
    private String classifyName3;
    private List<GoodsselectGoodsCategoryBean.DataBean> classifyData1;
    private List<GoodsselectGoodsCategoryBean.DataBean> classifyData2;
    private List<GoodsselectGoodsCategoryBean.DataBean> classifyData3;

    private void clearData() {
        classifyId1 = 0;
        classifyId2 = 0;
        classifyId3 = 0;
        classifyName1 = "";
        classifyName2 = "";
        classifyName3 = "";
        classifyData1 = new ArrayList<>();
        classifyData2 = new ArrayList<>();
        classifyData3 = new ArrayList<>();
    }

    public ShopaddproductPresenter(Context mContext, ShopaddproductContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    //选择分类--------------------------------
    private CommonDialog dialogClassify;
    private DialogClassifyLayoutBinding classifyLayoutBinding;

    @Override
    public void showClassify() {
        clearData();
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        selectGoodsCategory(map, 1);
        if (dialogClassify == null) {
            dialogClassify = new CommonDialog.Builder(mContext)
                    .setWidth(0.7f)
                    .setHeight(0.7f)
                    .setResId(R.layout.dialog_classify_layout)
                    .setAnimResId(0)
                    .build();
            classifyLayoutBinding = (DialogClassifyLayoutBinding) dialogClassify.getBinding();
            classifyLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogClassify.dismiss();
                }
            });
            classifyLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogClassify.dismiss();
                }
            });
            classifyLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addClassify();
                }
            });
        }
        bindLever1Data();
        bindLever2Data();
        bindLever3Data();
        bindHint();
        dialogClassify.show();
    }

    /**
     * 设置提示信息
     */
    private void bindHint() {
        if (!TextUtils.isEmpty(classifyName1)) {
            classifyLayoutBinding.tvHint.setText("您当前的选中是：" + classifyName1);
        }
        if (!TextUtils.isEmpty(classifyName2)) {
            classifyLayoutBinding.tvHint.setText("您当前的选中是：" + classifyName1 + " > " + classifyName2);
        }
        if (!TextUtils.isEmpty(classifyName3)) {
            classifyLayoutBinding.tvHint.setText("您当前的选中是：" + classifyName1 + " > " + classifyName2 + " > " + classifyName3);
        }
        if (TextUtils.isEmpty(classifyName1)) {
            classifyLayoutBinding.tvHint.setText("您当前的选中是：");
        }
    }

    /**
     * 添加分类
     */
    private void addClassify() {
        if (classifyId1 == 0) {
            ToastUtils.toast("请选择一级分类");
            return;
        }
        if (classifyId2 == 0) {
            ToastUtils.toast("请选择二级分类");
            return;
        }
        if (classifyId3 == 0) {
            ToastUtils.toast("请选择三级分类");
            return;
        }
        mView.setClassifyId1(classifyId1);
        mView.setClassifyId2(classifyId2);
        mView.setClassifyId3(classifyId3);
        mView.setClassifyName1(classifyName1);
        mView.setClassifyName2(classifyName2);
        mView.setClassifyName3(classifyName3);
        dialogClassify.dismiss();
    }

    private CommonAdapter adapter1;
    private CommonAdapter adapter2;
    private CommonAdapter adapter3;

    /**
     * 绑定一级分类数据
     */
    private void bindLever1Data() {
        if (classifyData1 == null)
            return;
        //一级分类
        adapter1 = new CommonAdapter<ShopClassifyLayoutBinding, GoodsselectGoodsCategoryBean.DataBean>(mContext, R.layout.shop_classify_layout, classifyData1) {
            @Override
            protected void getItem(ShopClassifyLayoutBinding binding, GoodsselectGoodsCategoryBean.DataBean bean, int position) {
                if (bean.isCheck()) {
                    binding.cb.setChecked(true);
                    binding.viewArr.setVisibility(View.VISIBLE);
                } else {
                    binding.cb.setChecked(false);
                    binding.viewArr.setVisibility(View.GONE);
                }
                binding.cb.setText(bean.getName());
            }

            @Override
            protected void itemOnclick(ShopClassifyLayoutBinding binding, GoodsselectGoodsCategoryBean.DataBean bean, int position) {
                if (classifyId1 == bean.getId())
                    return;
                classifyId1 = bean.getId();
                classifyName1 = bean.getName();
                classifyName2 = "";
                classifyName3 = "";
                classifyId2 = 0;
                classifyId3 = 0;
                classifyData2 = new ArrayList<>();
                classifyData3 = new ArrayList<>();
                bindLever2Data();
                bindLever3Data();
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(bean.getId()));
                selectGoodsCategory(map, 2);
                //更新选中状态
                for (int i = 0; i < classifyData1.size(); i++) {
                    if (position == i) {
                        classifyData1.get(i).setCheck(true);
                    } else {
                        classifyData1.get(i).setCheck(false);
                    }
                }
                adapter1.notifyDataSetChanged();
                bindHint();
            }
        };
        classifyLayoutBinding.lv1.setAdapter(adapter1);
    }

    /**
     * 绑定二级分类数据
     */
    private void bindLever2Data() {
        if (classifyData2 == null)
            return;
        //一级分类
        adapter2 = new CommonAdapter<ShopClassifyLayoutBinding, GoodsselectGoodsCategoryBean.DataBean>(mContext, R.layout.shop_classify_layout, classifyData2) {
            @Override
            protected void getItem(ShopClassifyLayoutBinding binding, GoodsselectGoodsCategoryBean.DataBean bean, int position) {
                if (bean.isCheck()) {
                    binding.cb.setChecked(true);
                    binding.viewArr.setVisibility(View.VISIBLE);
                } else {
                    binding.cb.setChecked(false);
                    binding.viewArr.setVisibility(View.GONE);
                }
                binding.cb.setText(bean.getName());
            }

            @Override
            protected void itemOnclick(ShopClassifyLayoutBinding binding, GoodsselectGoodsCategoryBean.DataBean bean, int position) {
                if (classifyId2 == bean.getId())
                    return;
                classifyId2 = bean.getId();
                classifyName2 = bean.getName();
                classifyName3 = "";
                classifyId3 = 0;
                classifyData3 = new ArrayList<>();
                bindLever3Data();
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(bean.getId()));
                selectGoodsCategory(map, 3);
                //更新选中状态
                for (int i = 0; i < classifyData2.size(); i++) {
                    if (position == i) {
                        classifyData2.get(i).setCheck(true);
                    } else {
                        classifyData2.get(i).setCheck(false);
                    }
                }
                adapter2.notifyDataSetChanged();
                bindHint();
            }
        };
        classifyLayoutBinding.lv2.setAdapter(adapter2);
    }

    /**
     * 绑定三级分类数据
     */
    private void bindLever3Data() {
        if (classifyData3 == null)
            return;
        //一级分类
        adapter3 = new CommonAdapter<ShopClassifyLayoutBinding, GoodsselectGoodsCategoryBean.DataBean>(mContext, R.layout.shop_classify_layout, classifyData3) {
            @Override
            protected void getItem(ShopClassifyLayoutBinding binding, GoodsselectGoodsCategoryBean.DataBean bean, int position) {
                if (bean.isCheck()) {
                    binding.cb.setChecked(true);
                    binding.viewArr.setVisibility(View.VISIBLE);
                } else {
                    binding.cb.setChecked(false);
                    binding.viewArr.setVisibility(View.GONE);
                }
                binding.cb.setText(bean.getName());
            }

            @Override
            protected void itemOnclick(ShopClassifyLayoutBinding binding, GoodsselectGoodsCategoryBean.DataBean bean, int position) {
                if (classifyId3 == bean.getId())
                    return;
                classifyId3 = bean.getId();
                classifyName3 = bean.getName();
                //更新选中状态
                for (int i = 0; i < classifyData3.size(); i++) {
                    if (position == i) {
                        classifyData3.get(i).setCheck(true);
                    } else {
                        classifyData3.get(i).setCheck(false);
                    }
                }
                adapter3.notifyDataSetChanged();
                bindHint();
            }
        };
        classifyLayoutBinding.lv3.setAdapter(adapter3);
    }

    /**
     * 查询商品分类
     */
    private void selectGoodsCategory(Map<String, String> map, final int position) {
        HttpCallback callback = new HttpCallback<GoodsselectGoodsCategoryBean>(mContext, this, false) {
            @Override
            protected void onSuccess(GoodsselectGoodsCategoryBean baseBean) {
                if (position == 1) {
                    classifyData1 = baseBean.getData();
                    bindLever1Data();
                } else if (position == 2) {
                    classifyData2 = baseBean.getData();
                    bindLever2Data();
                } else if (position == 3) {
                    classifyData3 = baseBean.getData();
                    bindLever3Data();
                }
            }

            @Override
            protected void onFailure(Throwable e) {

            }
        };
        startRequest(HttpBuilder.httpService.goodsselectGoodsCategory(AppParams.getParams(map)), callback);
    }

    //新增规格--------------------------------
    private CommonDialog dialogAddSize;
    private DialogAddSizeLayoutBinding addSizeLayoutBinding;

    @Override
    public void showAddSize() {
        if (dialogAddSize == null) {
            dialogAddSize = new CommonDialog.Builder(mContext)
                    .setWidth(0.65f)
                    .setHeight(0.7f)
                    .setResId(R.layout.dialog_add_size_layout)
                    .setAnimResId(0)
                    .build();
            addSizeLayoutBinding = (DialogAddSizeLayoutBinding) dialogAddSize.getBinding();
            addSizeLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddSize.dismiss();
                }
            });
            addSizeLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddSize.dismiss();
                }
            });
            addSizeLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
        dialogAddSize.show();
    }
}