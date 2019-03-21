package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.ShareBean;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.BaginContract;
import com.ogmamllpadnew.databinding.AddClassifyLayoutBinding;
import com.ogmamllpadnew.databinding.Check2LayoutBinding;
import com.ogmamllpadnew.databinding.ClassifyLeftItemLayoutBinding;
import com.ogmamllpadnew.databinding.RqCodeLayoutBinding;
import com.ogmamllpadnew.ui.fragment.BaginlefttabFragment;
import com.shan.netlibrary.bean.HandbagselectHandbagCategoryBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.net.HttpPresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拎包入住
 * Created by 陈俊山 on 2018-11-02.
 */

public class BaginPresenter extends HttpPresenter implements BaginContract.Presenter, View.OnClickListener {
    private Context mContext;
    private BaginContract.View mView;

    public BaginPresenter(Context mContext, BaginContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**#######################分享START############################*/
    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog dialogShare;
    private RqCodeLayoutBinding rqCodeBinding;

    @Override
    public void showRqDialog() {
        if (dialogShare == null) {
            dialogShare = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.rq_code_layout)
                    .setGravity(Gravity.CENTER)
                    .build();
            rqCodeBinding = (RqCodeLayoutBinding) dialogShare.getBinding();
            rqCodeBinding.btnShare.setOnClickListener(this);
        }
        rqCodeBinding.ll1.setVisibility(View.GONE);
        rqCodeBinding.ll2.setVisibility(View.VISIBLE);
        dialogShare.show();
    }

    @Override
    public void setRqCode(String image) {
        rqCodeBinding.etPlanArea.setText("");
        rqCodeBinding.etPlanName.setText("");
        rqCodeBinding.ll1.setVisibility(View.VISIBLE);
        rqCodeBinding.ll2.setVisibility(View.GONE);
        Bitmap mBitmap = CodeUtils.createImage(image, 400, 400, null);
        rqCodeBinding.image.setImageBitmap(mBitmap);
    }

    /**
     * 分享
     */
    @Override
    public void share(String name, String area) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrfamc));
            return;
        }
        if (TextUtils.isEmpty(area)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrxymj));
            return;
        }
        //获取所有数据
        List<HandbagselectLayoutBean.DataBean> datas = MyApp.getInstance().getLayoutBean().getData();
        List<ShareBean> shareBeanList = new ArrayList<>();
        for (int k = 0; k < datas.size(); k++) {
            HandbagselectLayoutBean.DataBean dataBean = datas.get(k);
            ShareBean shareBean = new ShareBean();
            shareBean.setName(dataBean.getName());
            List<HandbagselectLayoutBean.DataBean.ValueBean> categoryListBeans = dataBean.getValue();
            List<ShareBean.ProductsBean> productsBeans = new ArrayList<>();
            for (int i = 0; i < categoryListBeans.size(); i++) {
                List<HandbagselectLayoutBean.DataBean.ValueBean.ProductBean> products = categoryListBeans.get(i).getProduct();
                for (int j = 0; j < products.size(); j++) {
                    ShareBean.ProductsBean productsBean = new ShareBean.ProductsBean();
                    productsBean.setCount(products.get(j).getNum());
                    productsBean.setSpec_id(products.get(j).getSpecId());
                    productsBeans.add(productsBean);
                }
            }
            //产品数大于0则添加
            if (productsBeans.size() > 0) {
                shareBean.setProducts(productsBeans);
                shareBeanList.add(shareBean);
            }
        }
        String data = new Gson().toJson(shareBeanList);
        LogUtils.d("data:" + data);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("houseTypeId", BaginlefttabFragment.houseTypeId);
        map.put("area", area);
        map.put("jsonData", data);
        //分享
        shareShoppingCart(map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share:
                share(rqCodeBinding.etPlanName.getText().toString().trim(),
                        rqCodeBinding.etPlanArea.getText().toString().trim());
                break;
        }
    }
    /**#######################分享END############################*/

    /**
     * ###########################添加分类START########################################
     */
    private CommonDialog dialogEdit;
    private AddClassifyLayoutBinding addClassifyLayoutBinding;
    private CommonAdapter leftAdapter;
    private HandbagselectHandbagCategoryBean categoryBean;

    /**
     * 显示添加分类弹框
     */
    @Override
    public void showClassifyDialog(final HandbagselectHandbagCategoryBean categoryBean) {
        if (categoryBean == null)
            return;
        this.categoryBean = categoryBean;
        if (dialogEdit == null) {
            dialogEdit = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.add_classify_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            addClassifyLayoutBinding = (AddClassifyLayoutBinding) dialogEdit.getBinding();
            addClassifyLayoutBinding.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEdit.dismiss();
                }
            });
            addClassifyLayoutBinding.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            addClassifyLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEdit.dismiss();
                }
            });
            addClassifyLayoutBinding.btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetData();
                }
            });
        }
        //左边数据
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            if (i == 0) {
                categoryBean.getData().get(i).setCheck(true);
            } else {
                categoryBean.getData().get(i).setCheck(false);
            }
        }
        leftAdapter = new CommonAdapter<ClassifyLeftItemLayoutBinding, HandbagselectHandbagCategoryBean.DataBean>(mContext, R.layout.classify_left_item_layout, categoryBean.getData()) {
            @Override
            protected void getItem(ClassifyLeftItemLayoutBinding binding, HandbagselectHandbagCategoryBean.DataBean bean, int position) {
                binding.tvName.setText(bean.getName());
                binding.tvName.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(ClassifyLeftItemLayoutBinding binding, HandbagselectHandbagCategoryBean.DataBean bean, int position) {
                for (int i = 0; i < categoryBean.getData().size(); i++) {
                    if (i == position) {
                        categoryBean.getData().get(i).setCheck(true);
                    } else {
                        categoryBean.getData().get(i).setCheck(false);
                    }
                }
                leftAdapter.notifyDataSetChanged();
                setRightData(bean.getValue());
            }
        };
        addClassifyLayoutBinding.listView.setAdapter(leftAdapter);
        //右边数据
        if (categoryBean.getData().size() > 0) {
            setRightData(categoryBean.getData().get(0).getValue());
        }
        dialogEdit.show();
    }

    /**
     * 重置数据
     */
    private void resetData() {
        String data = (String) SPUtils.get(SpConstant.BAGIN_LAYOUT, "");
        HandbagselectLayoutBean layoutBean = new Gson().fromJson(data, HandbagselectLayoutBean.class);
        MyApp.getInstance().setLayoutBean(layoutBean);
        //左边数据
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            if (i == 0) {
                categoryBean.getData().get(i).setCheck(true);
            } else {
                categoryBean.getData().get(i).setCheck(false);
            }
        }
        leftAdapter.updata(categoryBean.getData());
        //右边数据
        if (categoryBean.getData().size() > 0) {
            setRightData(categoryBean.getData().get(0).getValue());
        }
        EventBus.getDefault().post(new MessageEvent(MsgConstant.BAGIN_LEFT_TYPE_REFRESH));
        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_RIGHT_EMPTY));
        ToastUtils.toast(mContext.getResources().getString(R.string.str_czcg));
    }

    /**
     * 设置右边数据
     *
     * @param valueBeans
     */
    private void setRightData(final List<HandbagselectHandbagCategoryBean.DataBean.ValueBean> valueBeans) {
        addClassifyLayoutBinding.llRight.removeAllViews();

        for (int i = 0; i < valueBeans.size(); i++) {
            valueBeans.get(i).setCheck(false);
        }

        //所有选中数据
        HandbagselectLayoutBean layoutBean = MyApp.getInstance().getLayoutBean();
        HandbagselectLayoutBean.DataBean dataBean = layoutBean.getData().get(MyApp.getInstance().getTabPos());
        final List<HandbagselectLayoutBean.DataBean.ValueBean> dataBeanValue = dataBean.getValue();
        for (int i = 0; i < valueBeans.size(); i++) {
            for (int j = 0; j < dataBeanValue.size(); j++) {
                if (valueBeans.get(i).getId() == dataBeanValue.get(j).getId()) {
                    valueBeans.get(i).setCheck(true);
                    break;
                }
            }
        }

        for (int i = 0; i < valueBeans.size(); i++) {
            final int j = i;
            Check2LayoutBinding rightBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.check2_layout, addClassifyLayoutBinding.llRight, false);
            rightBinding.cb.setText(valueBeans.get(i).getName());
            rightBinding.cb.setChecked(valueBeans.get(i).isCheck());
            addClassifyLayoutBinding.llRight.addView(rightBinding.getRoot());
            rightBinding.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        dataBeanValue.add(new HandbagselectLayoutBean.DataBean.ValueBean(valueBeans.get(j).getId(), valueBeans.get(j).getName()));
                    } else {
                        for (int k = 0; k < dataBeanValue.size(); k++) {
                            if (dataBeanValue.get(k).getId() == valueBeans.get(j).getId()) {
                                dataBeanValue.remove(k);
                                break;
                            }
                        }
                    }
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.BAGIN_LEFT_TYPE_REFRESH));
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_RIGHT_EMPTY));
                }
            });
        }
    }
}