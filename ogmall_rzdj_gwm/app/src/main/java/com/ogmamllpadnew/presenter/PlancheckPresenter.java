package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.PlanCheckBean;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.PlancheckContract;
import com.ogmamllpadnew.databinding.DialogPlanCheckLayoutBinding;
import com.ogmamllpadnew.databinding.PlanCheckStatusLayoutBinding;
import com.shan.netlibrary.bean.PlanselectPlanOfOneselfDesignerBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方案审核
 * Created by 陈俊山 on 2018-11-17.
 */

public class PlancheckPresenter extends HttpPresenter implements PlancheckContract.Presenter {
    private Context mContext;
    private PlancheckContract.View mView;

    public PlancheckPresenter(Context mContext, PlancheckContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog dialog;
    private PlanCheckStatusLayoutBinding layoutBinding;
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
                    .setResId(R.layout.plan_check_status_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            layoutBinding = (PlanCheckStatusLayoutBinding) dialog.getBinding();
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


    /**
     * ##########################方案弹窗审核#############################
     */
    private CommonDialog planCheckdialog;
    private DialogPlanCheckLayoutBinding checkLayoutBinding;

    public CommonDialog getPlanCheckdialog() {
        return planCheckdialog;
    }

    /**
     * 显示详情弹框
     */
    @Override
    public void showPlanCheck(final PlanselectPlanOfOneselfDesignerBean.DataBean item) {
        if (planCheckdialog == null) {
            planCheckdialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.55f)
                    .setHeight(0.9f)
                    .setResId(R.layout.dialog_plan_check_layout)
                    .setAnimResId(0)
                    .build();
        }
        checkLayoutBinding = (DialogPlanCheckLayoutBinding) planCheckdialog.getBinding();
        checkLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planCheckdialog.dismiss();
            }
        });
        checkLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAuditState(String.valueOf(item.getId()), "-1");
            }
        });
        checkLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAuditState(String.valueOf(item.getId()), "1");
            }
        });
        if (item.getAuditState() == 1) {
            checkLayoutBinding.btnCancel.setVisibility(View.INVISIBLE);
            checkLayoutBinding.btnAdd.setVisibility(View.INVISIBLE);
        }else {
            checkLayoutBinding.btnCancel.setVisibility(View.VISIBLE);
            checkLayoutBinding.btnAdd.setVisibility(View.VISIBLE);
        }
        checkLayoutBinding.tvHx.setText(mContext.getResources().getString(R.string.str_hx) + item.getHouseTypeIdName());
        checkLayoutBinding.tvTime.setText(mContext.getResources().getString(R.string.str_sjsj) + item.getCreateTime());
        checkLayoutBinding.tvArea.setText(mContext.getResources().getString(R.string.str_mj) + item.getArea() + "㎡");
        checkLayoutBinding.tvDesignerName.setText(item.getCreateAccountName());
        bindImageData(item);
        planCheckdialog.show();
    }

    private List<View> imageViews;
    private List<String> images;

    /**
     * 绑定图片数据
     */
    private void bindImageData(PlanselectPlanOfOneselfDesignerBean.DataBean item) {
        checkLayoutBinding.llPiture.removeAllViews();
        if (item == null)
            return;
        images = new ArrayList<>();
        imageViews = new ArrayList<>();
        String headImage = item.getHeadImage();
        List<String> datas = item.getDetailsImageList();
        if (!TextUtils.isEmpty(headImage)) {
            images.add(0, headImage);
        }
        images.addAll(datas);
        if (images.size() > 0) {
            ImageCacheUtils.loadImage(mContext, images.get(0), checkLayoutBinding.ivHead);
        }

        for (int i = 0; i < images.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_layout, checkLayoutBinding.llPiture, false);
            imageViews.add(view);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            ImageCacheUtils.loadImage(mContext, images.get(i), imageView);
            checkLayoutBinding.llPiture.addView(view);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.shape_red3);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageCacheUtils.loadImage(mContext, images.get(j), checkLayoutBinding.ivHead);
                    setImageStstus(j);
                }
            });
        }
    }

    /**
     * 设置Image 选中状态
     */
    private void setImageStstus(int j) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (i == j) {
                imageViews.get(i).setBackgroundResource(R.drawable.shape_red3);
            } else {
                imageViews.get(i).setBackgroundResource(R.color.transparent);
            }
        }
    }

    /**
     * POST /app/plan/setAuditState
     * 店铺设置审核状态
     */
    private void setAuditState(String planId, String auditState) {
        Map<String, String> map = new HashMap<>();
        map.put("planId", planId);
        map.put("auditState", auditState);//-1=审核未通过；0=未审核；1=审核通过
        plansetAuditState(map);
    }
}