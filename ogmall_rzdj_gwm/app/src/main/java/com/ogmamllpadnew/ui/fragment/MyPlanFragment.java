package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.PlanCheckBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.PlanContract;
import com.ogmamllpadnew.databinding.FgPlanLayoutBinding;
import com.ogmamllpadnew.databinding.TitleAddPlanLayoutBinding;
import com.ogmamllpadnew.presenter.PlanPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.AppaccountInfoBean;
import com.shan.netlibrary.bean.PlanselectPlanOfOneselfBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的方案
 * Created by 陈俊山 on 2018-11-02.
 */

public class MyPlanFragment extends BaseFragment<FgPlanLayoutBinding, PlanselectPlanOfOneselfBean.DataBean> implements PlanContract.View {
    private PlanPresenter presenter;
    private int width;
    private String keywords;
    private String auditState;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_plan_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wdfa);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PlanPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth();
        lvBinding.xrecyclerview.setPadding(width / 30, 40, width / 30, width / 30);
        setRecycViewGrid(2);
        initLayout();
        addHeadView();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PLANSELECTPLANOFONESELF) {
            PlanselectPlanOfOneselfBean bean = (PlanselectPlanOfOneselfBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
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

    /**
     * 初始化布局
     */
    private void initLayout() {
        int leftLayoutWidth = width / 4;//左边布局的宽度
        //右边布局
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lvBinding.fl.getLayoutParams();
        params.leftMargin = leftLayoutWidth;
        lvBinding.fl.setLayoutParams(params);
        //左边布局
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.my_plan_left_layout, lvBinding.ll, false);
        lvBinding.flParent.addView(view);
    }

    private TitleAddPlanLayoutBinding addPlanLayoutBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        //添加title右边View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addPlanLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_add_plan_layout, lvBinding.llHeader, false);
        titleBinding.rl.addView(addPlanLayoutBinding.getRoot(), params);
        AppaccountInfoBean.DataBean infoBean = MyApp.getInstance().getInfoBean().getData();
        //设置不用用户的显示
        if (infoBean.getType() == 30) {
            //市代理
            addPlanLayoutBinding.tvStatus.setVisibility(View.GONE);
            addPlanLayoutBinding.tvLook.setVisibility(View.GONE);
        } else if (infoBean.getType() == 20) {
            //店铺商
            addPlanLayoutBinding.tvStatus.setVisibility(View.GONE);
            addPlanLayoutBinding.tvLook.setVisibility(View.VISIBLE);
        } else if (infoBean.getType() == 10) {
            //设计师
            addPlanLayoutBinding.tvStatus.setVisibility(View.VISIBLE);
            addPlanLayoutBinding.tvLook.setVisibility(View.GONE);
        }
        addPlanLayoutBinding.tvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(AddplanFragment.class, null);
            }
        });
        addPlanLayoutBinding.tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(PlancheckFragment.class, null);
            }
        });
        addPlanLayoutBinding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    protected void getListVewItem(FgPlanLayoutBinding binding, PlanselectPlanOfOneselfBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.iv);
        binding.tvName.setText(item.getPlanName());
        if (item.getAuditState() == -1){
            binding.tvDsh.setVisibility(View.VISIBLE);
            binding.tvDsh.setText("未通过");
        }else if (item.getAuditState() == 0){
            binding.tvDsh.setVisibility(View.VISIBLE);
            binding.tvDsh.setText("待审核");
        }else if (item.getAuditState() == 1){
            binding.tvDsh.setVisibility(View.GONE);
            binding.tvDsh.setText("审核通过");
        }
    }

    @Override
    protected void onItemClick(PlanselectPlanOfOneselfBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        if (bean.getAuditState() == 1){
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.ID, bean.getId());
            startFragment(PlandetailsFragment.class, bundle);
        }else {
            ToastUtils.toast("该方案未审核或没有通过审核");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (houseStyleId != 0) {
            onRefresh();
        }
    }

    private int houseStyleId = 0;

    /**
     * 查询自己的方案
     */
    private void planselectPlanOfOneself() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", keywords);//关键词搜索
        map.put("auditState", auditState);//-1=审核未通过；0=未审核；1=审核通过,null为全部
        if (houseStyleId != Constants.AllBean) {
            map.put("houseStyleId", String.valueOf(houseStyleId));//风格ID
        }
        presenter.planselectPlanOfOneself(map);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        planselectPlanOfOneself();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            planselectPlanOfOneself();
        }
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.MYPLANLEFTLICK) {
            houseStyleId = (int) msgEvent.getBean();
            onRefresh();
        } else if (msgEvent.getType() == MsgConstant.PLANSEARCH) {
            keywords = msgEvent.getMsg();
            onRefresh();
        }
    }

    @Override
    public void setAuditState(PlanCheckBean bean) {
        this.auditState = bean.getId();
        addPlanLayoutBinding.tvStatus.setText(bean.getName());
        onRefresh();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        addPlanLayoutBinding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showStatusDialog();
            }
        });
    }
}
