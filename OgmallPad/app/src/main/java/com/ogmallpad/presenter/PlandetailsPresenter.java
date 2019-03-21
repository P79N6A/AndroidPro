package com.ogmallpad.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.adapter.PlanDetailsBannerAdapter;
import com.ogmallpad.contract.PlandetailsContract;
import com.ogmallpad.databinding.DialogImageLayoutBinding;
import com.ogmallpad.databinding.FgPlandetailsLayoutBinding;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 方案详情
 * Created by chenjunshan on 2018-07-02.
 */

public class PlandetailsPresenter extends HttpPresenter implements PlandetailsContract.Presenter, View.OnClickListener {
    private Context mContext;
    private PlandetailsContract.View mView;
    private FgPlandetailsLayoutBinding mBinding;

    public PlandetailsPresenter(Context mContext, PlandetailsContract.View mView, FgPlandetailsLayoutBinding mBinding) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
        this.mBinding = mBinding;
        mBinding.ivLeft.setOnClickListener(this);
        mBinding.ivRight.setOnClickListener(this);
    }

    private List<List<DesigndetailsBean.DataBean.ProductsBean>> data;

    /**
     * 绑定底部数据
     */
    @Override
    public void loadBottomVp(List<DesigndetailsBean.DataBean.ProductsBean> list) {
        data = new ArrayList<>();
        List<DesigndetailsBean.DataBean.ProductsBean> items = new ArrayList<>();
        int size = list.size() / 3;
        for (int i = 0; i < size * 3; i++) {
            items.add(list.get(i));
            if (i > 0 && (i + 1) % 3 == 0) {
                data.add(items);
                items = new ArrayList<>();
            }
        }
        for (int i = size * 3; i < list.size(); i++) {
            items.add(list.get(i));
            if (i == list.size() - 1) {
                data.add(items);
                items = new ArrayList<>();
            }
        }
        mBinding.banner.setPages(new CBViewHolderCreator<PlanDetailsBannerAdapter>() {
            @Override
            public PlanDetailsBannerAdapter createHolder() {
                return new PlanDetailsBannerAdapter();
            }
        }, data)
                .setCanLoop(false);
    }

    private CommonDialog dialog;
    private DialogImageLayoutBinding imageBinding;

    @Override
    public void lookImage(String img) {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.dialog_image_layout)
                    .setAnimResId(R.style.dialog_scale)
                    .build();
            imageBinding = (DialogImageLayoutBinding) dialog.getBinding();
            FragmentActivity activity = (FragmentActivity) mContext;
            List<String> list = new ArrayList<>();
            list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4121013473,2024297718&fm=15&gp=0.jpg");
            list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=281608904,4177268565&fm=15&gp=0.jpg");
            list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1714555076,3754840520&fm=27&gp=0.jpg");
            //GuideAdapter mAdapter = new GuideAdapter(activity.getSupportFragmentManager(), list);
            //imageBinding.pager.setAdapter(mAdapter);
        }
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                setLeftCurrentItem();
                break;
            case R.id.iv_right:
                setRightCurrentItem();
                break;
        }
    }

    private void setLeftCurrentItem() {
        if (data == null)
            return;
        int pos = mBinding.banner.getCurrentItem();
        if (pos == 0) {
            mBinding.banner.setcurrentitem(data.size() - 1);
        } else {
            mBinding.banner.setcurrentitem(--pos);
        }
    }

    private void setRightCurrentItem() {
        if (data == null)
            return;
        int pos = mBinding.banner.getCurrentItem();
        if (pos == data.size() - 1) {
            mBinding.banner.setcurrentitem(0);
        } else {
            mBinding.banner.setcurrentitem(++pos);
        }
    }
}