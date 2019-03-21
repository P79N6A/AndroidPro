package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.PlanCheckBean;
import com.ogmamllpadnew.contract.PlanContract;
import com.ogmamllpadnew.databinding.MyPlanStatusLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 方案
 * Created by 陈俊山 on 2018-11-02.
 */

public class PlanPresenter extends HttpPresenter implements PlanContract.Presenter {
    private Context mContext;
    private PlanContract.View mView;

    public PlanPresenter(Context mContext, PlanContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog dialog;
    private MyPlanStatusLayoutBinding layoutBinding;
    private List<PlanCheckBean> checkBeans;

    /**
     * 显示状态弹框
     */
    @Override
    public void showStatusDialog() {
        if (dialog == null) {
            checkBeans = new ArrayList<>();
            //-1=审核未通过；0=未审核；1=审核通过
            checkBeans.add(new PlanCheckBean("全部", "", true));
            checkBeans.add(new PlanCheckBean("审核通过", "1", false));
            checkBeans.add(new PlanCheckBean("待审核", "0", false));
            checkBeans.add(new PlanCheckBean("审核未通过", "-1", false));

            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.my_plan_status_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            layoutBinding = (MyPlanStatusLayoutBinding) dialog.getBinding();
            layoutBinding.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            layoutBinding.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            checkBoxes = new ArrayList<>();
            for (int i = 0; i < checkBeans.size(); i++) {
                final int j = i;
                final PlanCheckBean bean = checkBeans.get(i);
                RadioButton checkBox = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.plan_check_item_layout, layoutBinding.llContent, false);
                checkBoxes.add(checkBox);
                checkBox.setChecked(bean.isCheck());
                checkBox.setText(bean.getName());
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setAuditState(bean);
                        refreshStatus(j);
                        dialog.dismiss();
                    }
                });
                layoutBinding.llContent.addView(checkBox);
            }
        }
        dialog.show();
    }

    private List<RadioButton> checkBoxes;

    private void refreshStatus(int j) {
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (i == j) {
                checkBoxes.get(i).setChecked(true);
            } else {
                checkBoxes.get(i).setChecked(false);
            }
        }
    }
}