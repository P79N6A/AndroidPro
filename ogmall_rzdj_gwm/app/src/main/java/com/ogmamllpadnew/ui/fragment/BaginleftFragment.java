package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.PublicUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.BaginleftContract;
import com.ogmamllpadnew.databinding.FgBaginleftChildItemLayoutBinding;
import com.ogmamllpadnew.databinding.FgBaginleftItemLayoutBinding;
import com.ogmamllpadnew.presenter.BaginleftPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean.DataBean.ValueBean;
import com.shan.netlibrary.net.BaseBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 拎包入住左边
 * Created by 陈俊山 on 2018-11-10.
 */

public class BaginleftFragment extends BaseFragment<FgBaginleftItemLayoutBinding, HandbagselectLayoutBean.DataBean.ValueBean> implements BaginleftContract.View {
    private BaginleftPresenter presenter;
    private int tabPos;
    private List<HandbagselectLayoutBean.DataBean.ValueBean> datas;

    public void setTabPos(int tabPos) {
        this.tabPos = tabPos;
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_baginleft_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginleftPresenter(getActivity(), this);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        //列表数据
        try{
            datas = MyApp.getInstance().getLayoutBean().getData().get(tabPos).getValue();
            setData(datas);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgBaginleftItemLayoutBinding binding, final ValueBean item, final int position) {
        super.getListVewItem(binding, item, position);
        //设置子item状态
        if (item.isCheck()) {
            binding.lv.setVisibility(View.VISIBLE);
            binding.llItem.setBackgroundResource(R.color.white);
        } else {
            binding.lv.setVisibility(View.GONE);
            binding.llItem.setBackgroundResource(R.color.color_f8f8f8);
        }

        binding.tvName.setText(item.getName());
        int allNum = 0;
        for (int i = 0; i < item.getProduct().size(); i++) {
            allNum += item.getProduct().get(i).getNum();
        }
        binding.tvNum.setText(String.valueOf(allNum));
        if (allNum == 0) {
            binding.tvNum.setVisibility(View.GONE);
        } else {
            binding.tvNum.setVisibility(View.VISIBLE);
        }

        CommonAdapter childAdapter = new CommonAdapter<FgBaginleftChildItemLayoutBinding, ValueBean.ProductBean>(getActivity(), R.layout.fg_baginleft_child_item_layout, item.getProduct()) {
            @Override
            protected void getItem(FgBaginleftChildItemLayoutBinding binding, final ValueBean.ProductBean bean, final int position) {
                ImageCacheUtils.loadImage(getActivity(), bean.getImage(), binding.ivHead);
                binding.tvTitle.setText(bean.getName());
                binding.tvGg.setText(bean.getSize() + getString(R.string.str_kg));
                binding.tvPrice.setText(PublicUtils.getRenminbi() + bean.getPrice());
                binding.tvNum.setText(String.valueOf(bean.getNum()));
                if (bean.getNum() == 0) {
                    binding.ivJian.setVisibility(View.INVISIBLE);
                } else {
                    binding.ivJian.setVisibility(View.VISIBLE);
                }
                binding.ivJian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bean.getNum() == 1)
                            return;
                        bean.setNum(bean.getNum() - 1);
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
                    }
                });
                binding.ivJia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bean.setNum(bean.getNum() + 1);
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
                    }
                });
                binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.getProduct().remove(position);
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
                    }
                });
            }

            @Override
            protected void itemOnclick(FgBaginleftChildItemLayoutBinding binding, ValueBean.ProductBean bean, int position) {

            }
        };
        binding.lv.setAdapter(childAdapter);

        binding.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApp.getInstance().setChildPos(position);
                for (int i = 0; i < datas.size(); i++) {
                    if (i == position) {
                        datas.get(i).setCheck(true);
                    } else {
                        datas.get(i).setCheck(false);
                    }
                }
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new MessageEvent(MsgConstant.BAGIN_GETPRODUCT, datas.get(position).getId()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        //刷新左边数据
        if (msgEvent.getType() == BaseMsgConstant.NONET) {
            return;
        }
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.BAGIN_LEFT_TYPE_REFRESH) {
            adapter.notifyDataSetChanged();
        } else if (msgEvent.getType() == MsgConstant.REFRESH_RIGHT_EMPTY) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setCheck(false);
            }
            adapter.notifyDataSetChanged();
        }
    }
}