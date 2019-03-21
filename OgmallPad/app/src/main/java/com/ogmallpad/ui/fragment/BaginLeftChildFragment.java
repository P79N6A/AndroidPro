package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.databinding.FgBaginLeftChildItemLayoutBinding;
import com.ogmallpad.databinding.FgBaginLeftChildLayoutBinding;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.RoomTypespecsBean;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by root on 18-8-21.
 */

public class BaginLeftChildFragment extends BaseFragment<FgBaginLeftChildLayoutBinding, RoomTypespecsBean.DataBean.CategoryListBean> {
    private List<RoomTypespecsBean.DataBean.CategoryListBean> categoryList;//布局数据
    private int currentVpPos = 0;//当前viewpager position
    private int dataPos;//数据item
    private int position;

    public int getDataPos() {
        return dataPos;
    }

    public void setCurrentVpPos(int currentVpPos) {
        this.currentVpPos = currentVpPos;
    }

    public void setCategoryList(int position,List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeanList) {
        this.dataPos = position;
        categoryList = categoryListBeanList;
        bindData();
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        if (lvBinding != null && categoryList != null) {
            setData(categoryList);
        }
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_bagin_left_child_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        lvBinding.xrecyclerview.setPadding(0, 0, 0, 0);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        noDataTitle = getString(R.string.str_hint12);
        lvBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
        bindData();
    }

    @Override
    protected void getListVewItem(FgBaginLeftChildLayoutBinding binding, final RoomTypespecsBean.DataBean.CategoryListBean item, final int position) {
        super.getListVewItem(binding, item, position);
        binding.tvName.setText(item.getCategoryName());
        int num = 0;
        for (int i = 0; i < item.getProduct().size(); i++) {
            num += item.getProduct().get(i).getCount();
        }
        if (num > 0) {
            binding.tvNum.setText(String.valueOf(num));
            binding.tvNum.setVisibility(View.VISIBLE);
        } else {
            binding.tvNum.setVisibility(View.GONE);
        }
        if (item.isShow()) {
            binding.ll.setVisibility(View.VISIBLE);
            binding.llTop.setBackgroundResource(R.color.color_f0f0f0);
            binding.divider.setBackgroundResource(R.color.color_dddddd);
        } else {
            binding.ll.setVisibility(View.GONE);
            binding.llTop.setBackgroundResource(R.color.white);
            binding.divider.setBackgroundResource(R.color.color_f0f0f0);
        }
        //加载将要添加购物车数据
        binding.ll.setAdapter(new CommonAdapter<FgBaginLeftChildItemLayoutBinding, RoomTypespecsBean.DataBean.CategoryListBean.ProductBean>(getActivity(), R.layout.fg_bagin_left_child_item_layout, item.getProduct()) {
            @Override
            protected void getItem(FgBaginLeftChildItemLayoutBinding binding, final RoomTypespecsBean.DataBean.CategoryListBean.ProductBean bean, final int position) {
                ImageCacheUtils.loadImage(getActivity(), bean.getImage(), binding.ivHead);
                binding.tvTitle.setText(bean.getName());
                binding.tvSize.setText(bean.getSpec_size() + getString(R.string.str_zwzw) + bean.getSpec_color());
                binding.tvPrice.setText(PublicUtils.getRenminbi() + bean.getPrice());
                binding.tvCount.setText(String.valueOf(bean.getCount()));
                //刪除
                binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.getProduct().remove(position);
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLNUMDATA));
                    }
                });
                //加
                binding.ivAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bean.setCount(bean.getCount() + 1);
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLNUMDATA));
                    }
                });
                //减
                binding.ivSubtract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = bean.getCount();
                        if (count <= 1) {
//                            item.getProduct().remove(position);
                        } else {
                            bean.setCount(count - 1);
                        }
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLNUMDATA));
                    }
                });
            }

            @Override
            protected void itemOnclick(FgBaginLeftChildItemLayoutBinding binding, RoomTypespecsBean.DataBean.CategoryListBean.ProductBean bean, int position) {

            }
        });
    }

    @Override
    protected void onItemClick(RoomTypespecsBean.DataBean.CategoryListBean bean, int position) {
        super.onItemClick(bean, position);
        currentPos = position;
        for (int i = 0; i < categoryList.size(); i++) {
            if (i == position) {
                categoryList.get(i).setShow(true);
            } else {
                categoryList.get(i).setShow(false);
            }
        }
        adapter.notifyDataSetChanged();
        //发送获取产品的事件
        EventBus.getDefault().post(new MessageEvent(MsgConstant.CATEGORYID, String.valueOf(bean.getCategoryId())));
    }

    private int currentPos;//当前选中的item

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.LAYOUTDATA){
            LogUtils.e(currentVpPos+"----------------------"+position);
            if (currentVpPos == BaginLeftFragment.viewPager.getCurrentItem() && position == currentVpPos){
                LogUtils.e("----------------------");
                categoryList = BaginLeftFragment.roomTypespecsBean.getData().get(currentVpPos).getCategoryList();
                if (categoryList != null) {
                    RoomTypespecsBean.DataBean.CategoryListBean.ProductBean productBean = (RoomTypespecsBean.DataBean.CategoryListBean.ProductBean) msgEvent.getBean();
                    LogUtils.e("++++++++++++++++");
                    List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryList.get(currentPos).getProduct();
                    boolean isAdd = true;
                    LogUtils.e("products.size = "+ products.size());
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getSpec_id() == productBean.getSpec_id()) {
                            int count = products.get(i).getCount() + 1;
                            products.get(i).setCount(count);
                            isAdd = false;
                            ToastUtils.toast("添加成功");
                            break;
                        }
                    }
                    if (isAdd) {
                        ToastUtils.toast("添加成功");
                        products.add(productBean);
                    }
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLNUMDATA));
                    adapter.notifyDataSetChanged();
                }

            }
        }
//        LogUtils.e("BaginLeftFragment.currentPos = "+ BaginLeftFragment.currentPos+",currentVpPos="+ currentVpPos);
//        if (BaginLeftFragment.currentPos != currentVpPos){
//            return;
//        }
//        if (msgEvent.getType() == MsgConstant.LAYOUTDATA) {
//            LogUtils.e("msgEvent:");
//            if (categoryList != null) {
//
//                RoomTypespecsBean.DataBean.CategoryListBean.ProductBean productBean = (RoomTypespecsBean.DataBean.CategoryListBean.ProductBean) msgEvent.getBean();
//                categoryList = BaginLeftFragment.roomTypespecsBean.getData().get(currentVpPos).getCategoryList();
//                List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryList.get(currentPos).getProduct();
//                boolean isAdd = true;
//                LogUtils.e("products.size = "+ products.size());
//                for (int i = 0; i < products.size(); i++) {
//                    if (products.get(i).getSpec_id() == productBean.getSpec_id()) {
//                        int count = products.get(i).getCount() + 1;
//                        products.get(i).setCount(count);
//                        isAdd = false;
//                        ToastUtils.toast("添加成功");
//                        break;
//                    }
//                }
//                if (isAdd) {
//                    ToastUtils.toast("添加成功");
//                    products.add(productBean);
//                }
//                EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLNUMDATA));
//                adapter.notifyDataSetChanged();
//            }
//        }
    }

    /**
     * 刷新右边数据
     */
    public void refreshRightData() {
        //ViewPager切换处理
        if (categoryList == null)
            return;
        boolean isShow = false;
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).isShow()) {
                isShow = true;
                //发送获取产品的事件
                currentPos = i;
                EventBus.getDefault().post(new MessageEvent(MsgConstant.CATEGORYID, String.valueOf(categoryList.get(i).getCategoryId())));
                break;
            }
        }
        if (!isShow) {
            EventBus.getDefault().post(new MessageEvent(MsgConstant.CATEGORYID, ""));
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addBaginData(RoomTypespecsBean.DataBean.CategoryListBean.ProductBean productBean, int currentItem) {
//        categoryList = BaginLeftFragment.roomTypespecsBean.getData().get(currentItem).getCategoryList();
        List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryList.get(currentPos).getProduct();
        boolean isAdd = true;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getSpec_id() == productBean.getSpec_id()) {
                int count = products.get(i).getCount() + 1;
                products.get(i).setCount(count);
                isAdd = false;
                ToastUtils.toast("添加成功");
                break;
            }
        }
        if (isAdd) {
            ToastUtils.toast("添加成功");
            products.add(productBean);
        }
        lvBinding.xrecyclerview.getAdapter().notifyDataSetChanged();
        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESHALLNUMDATA));
    }

    public static <T> List<T> depCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
