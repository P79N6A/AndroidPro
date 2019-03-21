package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.SPUtils;
import com.ogmallpad.R;
import com.ogmallpad.adapter.ViewPagerAdapter3;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.BaginLeftContract;
import com.ogmallpad.databinding.FgBaginLeftLayoutBinding;
import com.ogmallpad.presenter.BaginLeftPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.BagcategorysBean;
import com.shan.netlibrary.bean.RoomTypelistBean;
import com.shan.netlibrary.bean.RoomTypespecsBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public class BaginLeftFragment extends BaseFragment<FgBaginLeftLayoutBinding, Object> implements BaginLeftContract.View {
    private BaginLeftPresenter presenter;
    public static RoomTypelistBean roomTypelistBean;//户型数据
    public static RoomTypespecsBean roomTypespecsBean;//根据户型id 获取布局数据
    public static BagcategorysBean bagcategorysBean;//拎包入住模块获取产品分类数据【品牌家具，软装生活】
    public static RoomTypespecsBean roomTypespecsBeanTemp;//保存初始值
    public static ViewPager viewPager;
    public static int currentPos = 0;//当前viewpager position
    public static String roomTypeId = "";//户型id



    @Override
    public int bindLayout() {
        return R.layout.fg_bagin_left_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginLeftPresenter(getActivity(), this);
        roomTypelist();
    }


    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.ROOMTYPELIST) {
            //获取所有的户型数据
            roomTypelistBean = (RoomTypelistBean) baseBean;
            if (roomTypelistBean == null)
                return;
            presenter.showHx(mBinding.tvSelectHx, roomTypelistBean);
        } else if (mTag == HttpPresenter.ROOMTYPESPECS) {
            //根据户型id 获取布局数据
            roomTypespecsBean = (RoomTypespecsBean) baseBean;
            for (int i = 0; i < roomTypespecsBean.getData().size(); i++) {
                roomTypespecsBean.getData().get(i).setCheck(true);
                roomTypespecsBean.getData().get(i).setDefult(true);
            }
            roomTypespecsBeanTemp = (RoomTypespecsBean) roomTypespecsBean.clone();
            //保存拎包入住模块数据
            String data = new Gson().toJson(roomTypespecsBean);
            SPUtils.put(SpConstant.ROOMTYPESPECSBEAN, data);
            initViewPagerData();
            EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLDATA));
        } else if (mTag == HttpPresenter.BAGCATEGORYS) {
            //拎包入住模块获取产品分类数据【品牌家具，软装生活】
            bagcategorysBean = (BagcategorysBean) baseBean;
            //保存拎包入住模块数据
            String data = new Gson().toJson(bagcategorysBean);
            SPUtils.put(SpConstant.BAGCATEGORYSBEAN, data);
            presenter.showAdd(mBinding.llBottom, fragments.get(currentPos).getDataPos(), currentPos);
        }
    }

    private List<BaginLeftChildFragment> fragments;
    private int pos = 0;//viewpager大小

    /**
     * 绑定viewpager数据
     */
    private String textTitle;
    @Override
    public void initViewPagerData() {
        pos = 0;
        currentPos = 0;
        mBinding.llLayout.setVisibility(View.VISIBLE);
        //初始化Viewpager，绑定布局数据
        fragments = new ArrayList<>();

        final List<String> titles = new ArrayList<>();
        List<RoomTypespecsBean.DataBean> datas = roomTypespecsBean.getData();
        for (int i = 0; i < datas.size(); i++) {
            //设置选中的数据
            BaginLeftChildFragment childFragment = new BaginLeftChildFragment();
            if (datas.get(i).isCheck()) {
                childFragment.setCategoryList(i,datas.get(i).getCategoryList());
                fragments.add(childFragment);
                titles.add(datas.get(i).getSpecName());
                pos++;
            }
            childFragment.setPosition(i);
        }
        textTitle = titles.get(0);
        mBinding.bagInVp.setAdapter(new ViewPagerAdapter3(getActivity().getSupportFragmentManager(), fragments, titles));
        mBinding.bagInTabs.setViewPager(mBinding.bagInVp);
        mBinding.bagInTabs.setTextSize(12);
        mBinding.bagInTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                textTitle = titles.get(currentPos);
                //EventBus.getDefault().post(new MessageEvent(MsgConstant.VIEWPAGERCHANGE));
                refreshData();
                //刷新右边数据
                fragments.get(position).refreshRightData();
                for (int i = 0 ; i < fragments.size() ; i++) {
                    fragments.get(i).setCurrentVpPos(currentPos);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
        roomTypelistBean = null;
        roomTypespecsBean = null;
        bagcategorysBean = null;
        roomTypespecsBeanTemp = null;
        currentPos = 0;
        roomTypeId = "";
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvHxHint.setOnClickListener(this);
        mBinding.tvEdit.setOnClickListener(this);
        mBinding.btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_hx_hint:
                if (roomTypelistBean == null)
                    return;
                presenter.showHx(mBinding.tvSelectHx, roomTypelistBean);
                break;
            case R.id.tv_edit:
                presenter.showEdit(mBinding.tvEdit);
                break;
            case R.id.btn_add:
                if (bagcategorysBean == null) {
                    bagcategorys();
                } else {
                    if (presenter != null && fragments != null)
                        presenter.showAdd(mBinding.llBottom, fragments.get(currentPos).getDataPos(), currentPos);
                }
                break;
        }
    }

    /**
     * 获取所有的户型数据
     */
    private void roomTypelist() {
        presenter.roomTypelist(null);
    }

    /**
     * 根据户型id 获取布局数据
     */
    @Override
    public void roomTypespecs(String roomTypeId) {
        mBinding.tvNumber.setText("总价   ￥0.00");
        this.roomTypeId = roomTypeId;
        Map<String, String> map = new HashMap<>();
        map.put("roomTypeId", roomTypeId);
        presenter.roomTypespecs(map);
    }

    /**
     * 拎包入住模块获取产品分类数据【品牌家具，软装生活】
     */
    @Override
    public void bagcategorys() {
        presenter.bagcategorys(null);
    }

    @Override
    public void refreshData(int position) {
        List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeanList = BaginLeftFragment.roomTypespecsBean.getData().get(position).getCategoryList();
        fragments.get(currentPos).setCategoryList(position,categoryListBeanList);
    }

    @Override
    public void addDialogClose() {
        //刷新右边数据
        fragments.get(currentPos).refreshRightData();
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.REFRESHALLNUMDATA) {
            refreshData();
        } else if (msgEvent.getType() == MsgConstant.REFRESHALLDATA) {
            refreshData();
        }else if (msgEvent.getType() == MsgConstant.SHOW_HX){
            Log.e("TAG","-----------------------------");
            if (TextUtils.isEmpty(roomTypeId)){
                if (roomTypelistBean == null) {
                    roomTypelist();
                    return;
                }
                presenter.showHx(mBinding.tvSelectHx, roomTypelistBean);
            }
        }else if (msgEvent.getType() == MsgConstant.LAYOUTDATA){
            //TODO 调用BaginLeftChildFragment的onMsgEvent()方法
            fragments.get(mBinding.bagInVp.getCurrentItem()).addBaginData((RoomTypespecsBean.DataBean.CategoryListBean.ProductBean) msgEvent.getBean(),mBinding.bagInVp.getCurrentItem());
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        int currentPos1 = 0;
        List<RoomTypespecsBean.DataBean> datas = roomTypespecsBean.getData();
        for (int i = 0 ; i < datas.size() ; i++){
            String spaceName = datas.get(i).getSpecName();
            if (spaceName.equals(textTitle)){
                currentPos1 = i;
                break;
            }
        }
        if (datas.size() > currentPos1) {
            RoomTypespecsBean.DataBean dataBean = datas.get(currentPos1);
            List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeans = dataBean.getCategoryList();
            double itemAllMoney = 0;
            double itemAllCount = 0;
            for (int i = 0; i < categoryListBeans.size(); i++) {
                List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryListBeans.get(i).getProduct();
                for (int j = 0; j < products.size(); j++) {
                    itemAllMoney += products.get(j).getCount() * Double.parseDouble(products.get(j).getPrice());
                    itemAllCount += products.get(j).getCount();
                }
            }
            if (itemAllCount == 0) {
                mBinding.tvNumber.setText(dataBean.getSpecName() + "总价   ￥0.00");
//                mBinding.tvNumber.setVisibility(View.GONE);
            } else {
//                mBinding.tvNumber.setVisibility(View.VISIBLE);
                String money = new DecimalFormat("######0.00").format(itemAllMoney);
                mBinding.tvNumber.setText(dataBean.getSpecName() + "总价   ￥" + money);
            }
        }
    }
}
