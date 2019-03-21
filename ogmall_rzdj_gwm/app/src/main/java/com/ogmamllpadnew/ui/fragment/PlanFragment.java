package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.PlanCheckBean;
import com.ogmamllpadnew.bean.PlanleftBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.PlanContract;
import com.ogmamllpadnew.databinding.FgPlanLayoutBinding;
import com.ogmamllpadnew.databinding.PlanLeftHeader2LayoutBinding;
import com.ogmamllpadnew.presenter.PlanPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.PlanselectPlanOfFactoryBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 方案
 * Created by 陈俊山 on 2018-11-02.
 */

public class PlanFragment extends BaseFragment<FgPlanLayoutBinding, PlanselectPlanOfFactoryBean.DataBean> implements PlanContract.View {
    private PlanPresenter presenter;
    private int width;
    private String keywords;
    private int type = 1;//1工厂方案，2平台方案

    @Override
    public int bindItemLayout() {
        return R.layout.fg_plan_layout;
    }


    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.btnLeft.setImageResource(R.mipmap.ic_plan_on);
        setLeftText(R.string.str_gdfa);
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PlanPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lvBinding.xrecyclerview.getLayoutParams();
        params.topMargin = 100;
        lvBinding.xrecyclerview.setLayoutParams(params);
        lvBinding.xrecyclerview.setPadding(width / 22, 0, width / 22, 0);
        setRecycViewGrid(2);
        initLayout();
        addHeadView();
    }

    private PlanLeftHeader2LayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.plan_left_header2_layout, lvBinding.fl, false);
        lvBinding.fl.addView(mHeaderBinding.getRoot());
    }

    private void search() {
        String text = mHeaderBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入方案名称");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(mHeaderBinding.etSearch, getActivity());
        keywords = text;
        onRefresh();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PLANSELECTPLANOFFACTORY || mTag == HttpPresenter.PLANSELECTDESIGNLIST) {
            PlanselectPlanOfFactoryBean bean = (PlanselectPlanOfFactoryBean) baseBean;
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.plan_left_layout, lvBinding.ll, false);
        lvBinding.flParent.addView(view);
    }

    @Override
    protected void getListVewItem(FgPlanLayoutBinding binding, PlanselectPlanOfFactoryBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.iv);
        binding.tvName.setText(item.getPlanName());
    }

    @Override
    protected void onItemClick(PlanselectPlanOfFactoryBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        bundle.putInt(Constants.ID, bean.getId());
        startFragment(PlandetailsFragment.class, bundle);
    }

    private int houseStyleId = 0;

    /**
     * 平板底部的方案里查询对应工厂下级的方案
     */
    private void planselectPlanOfFactory() {
        if (type == 1) {
            Map<String, String> map = new HashMap<>();
            map.put("page", String.valueOf(pageNum));//第几页
            map.put("limit", String.valueOf(pageSize));//每页显示多少行
            map.put("keywords", keywords);//关键词搜索
            if (houseStyleId != Constants.AllBean) {
                map.put("houseStyleId", String.valueOf(houseStyleId));//风格ID
            }
            presenter.planselectPlanOfFactory(map);
        } else if (type == 2) {
            Map<String, String> map = new HashMap<>();
            map.put("page", String.valueOf(pageNum));//第几页
            map.put("limit", String.valueOf(pageSize));//每页显示多少行
            map.put("keywords", keywords);//关键词搜索
            if (houseStyleId != Constants.AllBean) {
                map.put("housestyleId", String.valueOf(houseStyleId));//风格ID
            }
            presenter.planselectDesignList(map);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        planselectPlanOfFactory();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            planselectPlanOfFactory();
        }
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.PLANLEFTLICK) {
            PlanleftBean planleftBean = (PlanleftBean) msgEvent.getBean();
            houseStyleId = planleftBean.getId();
            type = planleftBean.getType();
            onRefresh();
        }
    }

    @Override
    public void setAuditState(PlanCheckBean bean) {

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //监听搜索
        mHeaderBinding.etSearch.addTextChangedListener(textWatcher);
        mHeaderBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    search();
                }
                return false;
            }
        });
    }

    //监听搜索
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (TextUtils.isEmpty(text)) {
                keywords = "";
                onRefresh();
            }
        }
    };
}
