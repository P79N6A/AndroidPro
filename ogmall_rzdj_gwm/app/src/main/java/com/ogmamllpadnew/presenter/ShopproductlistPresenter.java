package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.Productstatus1Bean;
import com.ogmamllpadnew.contract.ShopproductlistContract;
import com.ogmamllpadnew.databinding.CheckLayoutBinding;
import com.ogmamllpadnew.databinding.ProductListStatus1LayoutBinding;
import com.ogmamllpadnew.databinding.ProductListStatus2LayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺商-商品列表
 * Created by 陈俊山 on 2018-12-05.
 */

public class ShopproductlistPresenter extends HttpPresenter implements ShopproductlistContract.Presenter {
    private Context mContext;
    private ShopproductlistContract.View mView;

    public ShopproductlistPresenter(Context mContext, ShopproductlistContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * ###########################上下架START########################################
     */
    private CommonDialog dialog1;
    private ProductListStatus1LayoutBinding layoutBinding;
    private CommonAdapter adapter1;
    private List<Productstatus1Bean> datas;

    /**
     * 显示户型弹框
     */
    @Override
    public void showStatus1Dialog() {
        if (dialog1 == null) {
            dialog1 = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.product_list_status1_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            layoutBinding = (ProductListStatus1LayoutBinding) dialog1.getBinding();
            layoutBinding.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.dismiss();
                }
            });
            layoutBinding.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            //初始化数据
            datas = new ArrayList<>();
            datas.add(new Productstatus1Bean("全部","",true));
            datas.add(new Productstatus1Bean("下架","0"));
            datas.add(new Productstatus1Bean("上架","1"));
        }

        adapter1 = new CommonAdapter<CheckLayoutBinding,Productstatus1Bean>(mContext, R.layout.check_layout, datas) {
            @Override
            protected void getItem(CheckLayoutBinding binding, Productstatus1Bean bean, int position) {
                binding.cb.setText(bean.getName());
                binding.cb.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(CheckLayoutBinding binding, final Productstatus1Bean bean, int position) {
                for (int i = 0; i < datas.size(); i++) {
                    if (i == position) {
                        datas.get(i).setCheck(true);
                    } else {
                        datas.get(i).setCheck(false);
                    }
                }
                adapter1.notifyDataSetChanged();
                mView.setSellStatus(bean.getId(),bean.getName());
                dialog1.dismiss();
            }
        };
        layoutBinding.lv.setAdapter(adapter1);
        dialog1.show();
    }
    /**###########################上下架END########################################*/

    /**
     * ###########################审核START########################################
     */
    private CommonDialog dialog2;
    private ProductListStatus2LayoutBinding layoutBinding2;
    private CommonAdapter adapter2;
    private List<Productstatus1Bean> datas2;

    /**
     * 显示户型弹框
     */
    @Override
    public void showStatus2Dialog() {
        if (dialog2 == null) {
            dialog2 = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.product_list_status2_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            layoutBinding2 = (ProductListStatus2LayoutBinding) dialog2.getBinding();
            layoutBinding2.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                }
            });
            layoutBinding2.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            //初始化数据
            datas2 = new ArrayList<>();
            datas2.add(new Productstatus1Bean("全部","",true));
            datas2.add(new Productstatus1Bean("未审核","0"));
            datas2.add(new Productstatus1Bean("审核通过","1"));
            datas2.add(new Productstatus1Bean("审核未通过","2"));
            datas2.add(new Productstatus1Bean("待提交","3"));
        }

        adapter2 = new CommonAdapter<CheckLayoutBinding,Productstatus1Bean>(mContext, R.layout.check_layout, datas2) {
            @Override
            protected void getItem(CheckLayoutBinding binding, Productstatus1Bean bean, int position) {
                binding.cb.setText(bean.getName());
                binding.cb.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(CheckLayoutBinding binding, final Productstatus1Bean bean, int position) {
                for (int i = 0; i < datas2.size(); i++) {
                    if (i == position) {
                        datas2.get(i).setCheck(true);
                    } else {
                        datas2.get(i).setCheck(false);
                    }
                }
                adapter2.notifyDataSetChanged();
                mView.setStatus(bean.getId(),bean.getName());
                dialog2.dismiss();
            }
        };
        layoutBinding2.lv.setAdapter(adapter2);
        dialog2.show();
    }
    /**###########################审核END########################################*/
}