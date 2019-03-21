package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.PlanleftBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.databinding.PlanLeftChildItemLayoutBinding;
import com.ogmamllpadnew.databinding.PlanLeftItemParentLayoutBinding;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.PlanselectAllHouseStyleAndHousestyleBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 方案左边布局
 * Created by root on 18-11-9.
 */

public class PlanLeftFragment extends BaseFragment<PlanLeftItemParentLayoutBinding, Object> {
    @Override
    public int bindLayout() {
        return R.layout.plan_left_item_parent_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        planselectAllHouseStyleAndHousestyle();
    }

    private CommonAdapter adapter1;
    private CommonAdapter adapter2;
    private PlanselectAllHouseStyleAndHousestyleBean.DataBean housestyleBean1;//平台
    private PlanselectAllHouseStyleAndHousestyleBean.DataBean housestyleBean2;//工厂
    private boolean tab1IsClick = true;
    private boolean tab2IsClick = false;

    /**
     * 初始化左边数据
     */
    private void initLeftData() {
        if (housestyleBean == null)
            return;
        housestyleBean1 = new PlanselectAllHouseStyleAndHousestyleBean.DataBean();
        housestyleBean2 = new PlanselectAllHouseStyleAndHousestyleBean.DataBean();
        /*if (housestyleBean.getData().size() > 0) {
            housestyleBean.getData().get(0).setCheck(true);
        }*/
        //添加全部类别
        for (int i = 0; i < housestyleBean.getData().size(); i++) {
            List<PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean> valueBeans = housestyleBean.getData().get(i).getValue();
            PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean valueBean = new PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean();
            valueBeans.add(0, valueBean);
            valueBean.setId(Constants.AllBean);
            valueBean.setStyleName(getString(R.string.str_qb));
            if (i == 0) {
                valueBean.setCheck(true);
                //获取方案列表
                EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANLEFTLICK, new PlanleftBean(2, Constants.AllBean)));
            }
        }
        // 平台方案
        if (housestyleBean.getData().size() > 0) {
            mBinding.ll1.setVisibility(View.VISIBLE);
            housestyleBean1 = housestyleBean.getData().get(0);
            mBinding.tvParent.setText(housestyleBean1.getName());
            adapter1 = new CommonAdapter<PlanLeftChildItemLayoutBinding, PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean>(getActivity(), R.layout.plan_left_child_item_layout, housestyleBean1.getValue()) {
                @Override
                protected void getItem(PlanLeftChildItemLayoutBinding binding, PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean bean, int pos) {
                    binding.cb.setText(bean.getStyleName());
                    binding.cb.setChecked(bean.isCheck());
                }

                @Override
                protected void itemOnclick(PlanLeftChildItemLayoutBinding binding, PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean bean, int pos) {
                    for (int i = 0; i < housestyleBean2.getValue().size(); i++) {
                        housestyleBean2.getValue().get(i).setCheck(false);
                    }
                    for (int i = 0; i < housestyleBean1.getValue().size(); i++) {
                        if (i == pos) {
                            housestyleBean1.getValue().get(i).setCheck(true);
                        } else {
                            housestyleBean1.getValue().get(i).setCheck(false);
                        }
                    }
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    //获取平台方案列表
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANLEFTLICK, new PlanleftBean(2, bean.getId())));
                }
            };
            mBinding.lv1.setAdapter(adapter1);
        }
        mBinding.tvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.lv2.setVisibility(View.GONE);
                housestyleBean1.setCheck(!housestyleBean1.isCheck());
                housestyleBean2.setCheck(false);
                if (housestyleBean1.isCheck()) {
                    mBinding.lv1.setVisibility(View.VISIBLE);
                } else {
                    mBinding.lv1.setVisibility(View.GONE);
                }
                if (!tab1IsClick) {
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANLEFTLICK, new PlanleftBean(2, Constants.AllBean)));
                    for (int i = 0; i < housestyleBean1.getValue().size(); i++) {
                        if (i==0){
                            housestyleBean1.getValue().get(i).setCheck(true);
                        }else {
                            housestyleBean1.getValue().get(i).setCheck(false);
                        }
                    }
                }
                tab1IsClick = true;
                tab2IsClick = false;
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });
        // 工厂方案
        if (housestyleBean.getData().size() > 1) {
            mBinding.ll2.setVisibility(View.VISIBLE);
            housestyleBean2 = housestyleBean.getData().get(1);
            mBinding.tvParent2.setText(housestyleBean2.getName());
            adapter2 = new CommonAdapter<PlanLeftChildItemLayoutBinding, PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean>(getActivity(), R.layout.plan_left_child_item_layout, housestyleBean2.getValue()) {
                @Override
                protected void getItem(PlanLeftChildItemLayoutBinding binding, PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean bean, int pos) {
                    binding.cb.setText(bean.getStyleName());
                    binding.cb.setChecked(bean.isCheck());
                }

                @Override
                protected void itemOnclick(PlanLeftChildItemLayoutBinding binding, PlanselectAllHouseStyleAndHousestyleBean.DataBean.ValueBean bean, int pos) {
                    for (int i = 0; i < housestyleBean1.getValue().size(); i++) {
                        housestyleBean1.getValue().get(i).setCheck(false);
                    }
                    for (int i = 0; i < housestyleBean2.getValue().size(); i++) {
                        if (i == pos) {
                            housestyleBean2.getValue().get(i).setCheck(true);
                        } else {
                            housestyleBean2.getValue().get(i).setCheck(false);
                        }
                    }
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    //获取平台方案列表
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANLEFTLICK, new PlanleftBean(1, bean.getId())));
                }
            };
            mBinding.lv2.setAdapter(adapter2);
        }
        mBinding.tvParent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.lv1.setVisibility(View.GONE);
                housestyleBean2.setCheck(!housestyleBean2.isCheck());
                housestyleBean1.setCheck(false);
                if (housestyleBean2.isCheck()) {
                    mBinding.lv2.setVisibility(View.VISIBLE);
                } else {
                    mBinding.lv2.setVisibility(View.GONE);
                }
                if (!tab2IsClick) {
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.PLANLEFTLICK, new PlanleftBean(1, Constants.AllBean)));
                    for (int i = 0; i < housestyleBean2.getValue().size(); i++) {
                        if (i==0){
                            housestyleBean2.getValue().get(i).setCheck(true);
                        }else {
                            housestyleBean2.getValue().get(i).setCheck(false);
                        }
                    }
                }
                tab1IsClick = false;
                tab2IsClick = true;
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });
    }

    private PlanselectAllHouseStyleAndHousestyleBean housestyleBean;

    /**
     * 查询方案列表（包含平台方案列表和工厂的方案列表）
     */
    public void planselectAllHouseStyleAndHousestyle() {
        HttpCallback callback = new HttpCallback<PlanselectAllHouseStyleAndHousestyleBean>(getActivity(), this) {
            @Override
            protected void onSuccess(PlanselectAllHouseStyleAndHousestyleBean baseBean) {
                housestyleBean = baseBean;
                initLeftData();
            }

            @Override
            protected void onFailure(Throwable e) {

            }
        };
        startRequest(HttpBuilder.httpService.planselectAllHouseStyleAndHousestyle(null), callback);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        if (msgEvent.getType() == BaseMsgConstant.NONET) {
            return;
        }
        super.onMsgEvent(msgEvent);
    }
}
