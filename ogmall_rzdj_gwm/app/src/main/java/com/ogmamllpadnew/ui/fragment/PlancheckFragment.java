package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.PlanCheckBean;
import com.ogmamllpadnew.contract.PlancheckContract;
import com.ogmamllpadnew.databinding.FgPlanCheckHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgPlancheckItemLayoutBinding;
import com.ogmamllpadnew.presenter.PlancheckPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.PlanselectPlanOfOneselfDesignerBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 方案审核
 * Created by 陈俊山 on 2018-11-17.
 */

public class PlancheckFragment extends BaseFragment<FgPlancheckItemLayoutBinding, PlanselectPlanOfOneselfDesignerBean.DataBean> implements PlancheckContract.View {
    private PlancheckPresenter presenter;
    private int width;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_plancheck_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_fash);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PlancheckPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, 0, width, 0);
        addHeadView();
        planselectPlanOfOneselfDesigner();
    }

    private FgPlanCheckHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_plan_check_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
        mHeaderBinding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showStatusDialog();
            }
        });
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PLANSELECTPLANOFONESELFDESIGNER) {
            PlanselectPlanOfOneselfDesignerBean bean = (PlanselectPlanOfOneselfDesignerBean) baseBean;
            if (pageNum == 1){
                clearData();
            }
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        } else if (mTag == HttpPresenter.PLANSETAUDITSTATE) {
            presenter.getPlanCheckdialog().dismiss();
            onRefresh();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgPlancheckItemLayoutBinding binding, final PlanselectPlanOfOneselfDesignerBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvName.setText(item.getCreateAccountName());
        binding.tvTitle.setText(item.getPlanName());
        binding.tvHx.setText(item.getHouseTypeIdName());
        binding.tvStyle.setText(item.getHouseStyleIdName());
        binding.tvArea.setText(item.getArea());
        binding.tvTime.setText(item.getCreateTime());
        binding.tvStatus.setText(item.getAuditStateName());
        binding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showPlanCheck(item);
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        pageNum = 1;
        clearData();
        planselectPlanOfOneselfDesigner();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            planselectPlanOfOneselfDesigner();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private String auditState;

    /**
     * 查询自己的所有设计师下的所有方案
     */
    private void planselectPlanOfOneselfDesigner() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("auditState", auditState);//-1=审核未通过；0=未审核；1=审核通过
        presenter.planselectPlanOfOneselfDesigner(map);
    }

    @Override
    public void setAuditState(PlanCheckBean bean) {
        this.auditState = bean.getId();
        mHeaderBinding.tvStatus.setText(bean.getName());
        onRefresh();
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        onRefresh();
    }
}