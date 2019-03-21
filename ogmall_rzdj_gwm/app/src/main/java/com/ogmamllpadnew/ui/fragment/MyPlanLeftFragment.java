package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.databinding.PlanLeftHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.PlanLeftItemLayoutBinding;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.PlanselectHouseStyleByOneselfPlanBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 方案左边布局
 * Created by root on 18-11-9.
 */

public class MyPlanLeftFragment extends BaseFragment<PlanLeftItemLayoutBinding, PlanselectHouseStyleByOneselfPlanBean.DataBean> {
    @Override
    public int bindItemLayout() {
        return R.layout.plan_left_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        addHeadView();
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        planselectHouseStyleByOneselfPlan();
        lvBinding.llParent.setBackgroundColor(Color.parseColor("#f6f6f6"));
    }

    private PlanLeftHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.plan_left_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
    }

    @Override
    protected void getListVewItem(PlanLeftItemLayoutBinding binding, PlanselectHouseStyleByOneselfPlanBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        if (item.isCheck()) {
            binding.cb.setChecked(true);
        } else {
            binding.cb.setChecked(false);
        }
        binding.cb.setText(item.getStyleName());
    }

    @Override
    protected void onItemClick(PlanselectHouseStyleByOneselfPlanBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        for (int i = 0; i < dataBeans.size(); i++) {
            if (i == position) {
                dataBeans.get(i).setCheck(true);
            } else {
                dataBeans.get(i).setCheck(false);
            }
        }
        adapter.notifyDataSetChanged();
        EventBus.getDefault().post(new MessageEvent(MsgConstant.MYPLANLEFTLICK, bean.getId()));
    }

    private List<PlanselectHouseStyleByOneselfPlanBean.DataBean> dataBeans;

    /**
     * 平板底部的方案里的风格列表查询
     */
    private void planselectHouseStyleByOneselfPlan() {
        HttpCallback callback = new HttpCallback<PlanselectHouseStyleByOneselfPlanBean>(getActivity(), this, false) {
            @Override
            protected void onSuccess(PlanselectHouseStyleByOneselfPlanBean baseBean) {
                dataBeans = baseBean.getData();
                PlanselectHouseStyleByOneselfPlanBean.DataBean dataBean = new PlanselectHouseStyleByOneselfPlanBean.DataBean();
                dataBean.setCheck(true);
                dataBean.setId(Constants.AllBean);
                dataBean.setStyleName(getString(R.string.str_qb));
                dataBeans.add(0, dataBean);
                setData(dataBeans);
                EventBus.getDefault().post(new MessageEvent(MsgConstant.MYPLANLEFTLICK, dataBean.getId()));
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        startRequest(HttpBuilder.httpService.planselectHouseStyleByOneselfPlan(null), callback);
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
        //键盘监听
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
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
                EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANSEARCH, text));
            }
        }
    };

    private void search() {
        String text = mHeaderBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入方案名称");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(mHeaderBinding.etSearch, getActivity());
        EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANSEARCH, text));
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        if (msgEvent.getType() == BaseMsgConstant.NONET) {
            return;
        }
        super.onMsgEvent(msgEvent);
    }
}
