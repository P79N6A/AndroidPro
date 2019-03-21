package com.ogmallpad.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.manager.AnimatorManager;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.BaginLeftContract;
import com.ogmallpad.databinding.AddItemLayoutBinding;
import com.ogmallpad.databinding.AddLayoutBinding;
import com.ogmallpad.databinding.EditItem2LayoutBinding;
import com.ogmallpad.databinding.EditItemLayoutBinding;
import com.ogmallpad.databinding.EditLayoutBinding;
import com.ogmallpad.databinding.FgBaginLeftLayoutBinding;
import com.ogmallpad.databinding.HxItemLayoutBinding;
import com.ogmallpad.databinding.SelectHxLayoutBinding;
import com.ogmallpad.ui.fragment.BaginLeftChildFragment;
import com.ogmallpad.ui.fragment.BaginLeftFragment;
import com.shan.netlibrary.bean.BagcategorysBean;
import com.shan.netlibrary.bean.RoomTypelistBean;
import com.shan.netlibrary.bean.RoomTypespecsBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public class BaginLeftPresenter extends HttpPresenter implements BaginLeftContract.Presenter, View.OnClickListener {
    private Context mContext;
    private BaginLeftContract.View mView;

    public BaginLeftPresenter(Context mContext, BaginLeftContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * *********************************************************************************************
     * 选择户型弹窗
     */
    private SelectHxLayoutBinding hxBinding = null;
    private RoomTypelistBean typelistBean;
    private CommonAdapter hxAdapter;
    private CommonDialog selectDialog;

    @Override
    public void showHx(final View view, final RoomTypelistBean typelistBean) {
        if (selectDialog == null) {
            this.typelistBean = typelistBean;
            selectDialog = new CommonDialog.Builder(mContext)
                    .setWidth(0f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.select_hx_layout)
                    .setGravity(Gravity.TOP | Gravity.LEFT)
                    .setAnimResId(0)
                    .build();
            hxBinding = (SelectHxLayoutBinding) selectDialog.getBinding();
            //绑定数据
            hxAdapter = new CommonAdapter<HxItemLayoutBinding, RoomTypelistBean.DataBean>(mContext, R.layout.hx_item_layout, typelistBean.getData()) {
                @Override
                protected void getItem(HxItemLayoutBinding binding, RoomTypelistBean.DataBean bean, int position) {
                    binding.tvName.setText(bean.getName());
                    if (bean.isCheck()) {
                        binding.tvName.setBackgroundResource(R.drawable.shape_type_on);
                        binding.tvName.setTextColor(mContext.getResources().getColor(R.color.color_c73c3a));
                    } else {
                        binding.tvName.setBackgroundResource(R.drawable.shape_type_off);
                        binding.tvName.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                    }
                }

                @Override
                protected void itemOnclick(HxItemLayoutBinding binding, RoomTypelistBean.DataBean bean, int position) {
                    changeHx((TextView) view,position);
                }
            };
            hxBinding.gv.setAdapter(hxAdapter);
        }
        selectDialog.show();
    }

    private boolean isFirst = true;

    /**
     * 切换户型
     *
     * @param position
     */
    private void showChangeHx(final int position) {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("切换户型会清除之前户型数据，确定要切换吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        changeHx(position);
                    }
                })
                .create().show();
    }

    /**
     * 切换户型
     *
     * @param position
     */
    private void changeHx(TextView view, final int position) {
        List<RoomTypelistBean.DataBean> list = typelistBean.getData();
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setCheck(true);
                view.setText(list.get(i).getName());
                mView.roomTypespecs(String.valueOf(list.get(i).getId()));
            } else {
                list.get(i).setCheck(false);
            }
        }
        hxAdapter.notifyDataSetChanged();
        selectDialog.dismiss();
    }

    /**
     * *********************************************************************************************
     * 编辑房间弹窗
     */
    private EditLayoutBinding editBinding = null;
    private CommonAdapter editAdapter;
    private boolean isReset = true;//关闭弹窗是否重置
    private CommonDialog editDialog;

    @Override
    public void showEdit(View view) {
        if (editDialog == null || !editDialog.isShowing()) {
            editDialog = new CommonDialog.Builder(mContext) {
                @Override
                public void diaLogDissmiss() {
                    super.diaLogDissmiss();
                    if (isReset) {
                        editBinding.tvHint.setVisibility(View.INVISIBLE);
                        editBinding.etContent.setText("");
                        //BaginLeftFragment.roomTypespecsBean = (RoomTypespecsBean) BaginLeftFragment.roomTypespecsBeanTemp.clone();
                        //editAdapter.updata(BaginLeftFragment.roomTypespecsBean.getData());
                    }
                    isReset = true;
                }
            }
                    .setWidth(0f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.edit_layout)
                    .setGravity(Gravity.TOP)
                    .setAnimResId(0)
                    .build();
            editBinding = (EditLayoutBinding) editDialog.getBinding();
            editBinding.llChild.setOnClickListener(this);
            editBinding.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editDialog.dismiss();
                }
            });

            //TODO 修改
            if (BaginLeftFragment.roomTypespecsBean != null ) {
                editAdapter = new CommonAdapter<EditItem2LayoutBinding, RoomTypespecsBean.DataBean>(mContext, R.layout.edit_item2_layout, BaginLeftFragment.roomTypespecsBean.getData()) {
                    @Override
                    protected void getItem(EditItem2LayoutBinding binding, final RoomTypespecsBean.DataBean bean, final int position) {
                        if (bean.isCheck()) {
                            binding.tvName.setBackgroundResource(R.drawable.shape_type_on);
                            binding.tvName.setTextColor(mContext.getResources().getColor(R.color.color_E70052));
                        } else {
                            binding.tvName.setBackgroundResource(R.drawable.shape_type_off);
                            binding.tvName.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                        }
                        binding.tvName.setText(bean.getSpecName());
                        //长按删除
                        binding.tvName.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                if (bean.isDefult()) {
                                    ToastUtils.toast("默认标签不能删除");
                                } else if (isHasProduct(position)) {
                                    isLongClick = true;
                                    deletePos = position;
                                    editBinding.tv.setText("该布局有商品，是否确认删除");
                                    editBinding.llDelete.setVisibility(View.VISIBLE);
                                } else {
                                    BaginLeftFragment.roomTypespecsBean.getData().remove(position);
                                    editAdapter.updata(BaginLeftFragment.roomTypespecsBean.getData());
                                    ToastUtils.toast("删除成功");
                                    mView.initViewPagerData();
                                }
                                return true;
                            }
                        });
                    }

                    @Override
                    protected void itemOnclick(EditItem2LayoutBinding binding, RoomTypespecsBean.DataBean bean, int position) {
                        if (isHasProduct(position) && bean.isCheck()) {
                            isLongClick = false;
                            deletePos = position;
                            editBinding.tv.setText("该布局有商品，是否确认取消选中");
                            editBinding.llDelete.setVisibility(View.VISIBLE);
                        } else {
                            bean.setCheck(!bean.isCheck());
                            editAdapter.notifyDataSetChanged();
                            mView.initViewPagerData();
                        }
                    }
                };
                //绑定数据
                editBinding.gv.setAdapter(editAdapter);
            }else {
                ToastUtils.toast("请选择户型");
                editDialog.dismiss();
                return;
            }
            //添加布局
            editBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取房间布局内容
                    String content = editBinding.etContent.getText().toString().trim();
                    //判断内容是否存在
                    List<RoomTypespecsBean.DataBean> datas = BaginLeftFragment.roomTypespecsBean.getData();
                    LogUtils.e("datas.size="+datas.size());
                    for (int i = 0; i < datas.size(); i++) {
                        if (content.equals(datas.get(i).getSpecName())) {
                            if (editBinding.tvHint.getVisibility() == View.INVISIBLE) {
                                editBinding.tvHint.setVisibility(View.VISIBLE);
                                AnimatorManager.setShakeAnimation(editBinding.tvHint);
                            }
                            return;
                        }
                    }
                    if (!TextUtils.isEmpty(content)) {
                        RoomTypespecsBean.DataBean dataBean = new RoomTypespecsBean.DataBean(content, true);
                        dataBean.setCategoryList(BaginLeftChildFragment.depCopy(BaginLeftFragment.roomTypespecsBean.getData().get(0).getCategoryList()));
                        BaginLeftFragment.roomTypespecsBean.getData().add(dataBean);
                        editAdapter.updata(BaginLeftFragment.roomTypespecsBean.getData());
                        editBinding.etContent.setText("");
                        mView.initViewPagerData();
                    }
                }
            });
            //监听内容变化
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    editBinding.tvHint.setVisibility(View.INVISIBLE);
                }
            };
            editBinding.etContent.addTextChangedListener(watcher);
            //重置
            editBinding.btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaginLeftFragment.roomTypespecsBean = (RoomTypespecsBean) BaginLeftFragment.roomTypespecsBeanTemp.clone();
                    editAdapter.updata(BaginLeftFragment.roomTypespecsBean.getData());
                    mView.initViewPagerData();
                    ToastUtils.toast(mContext.getResources().getString(R.string.str_czcg));
                }
            });
            //确定
            editBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isReset = false;
                    editDialog.dismiss();
                    mView.initViewPagerData();
                    //ToastUtils.toast("添加成功");
                }
            });
            editBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editBinding.llDelete.setVisibility(View.GONE);
                }
            });
            editBinding.btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editBinding.llDelete.setVisibility(View.GONE);
                    List<RoomTypespecsBean.DataBean> datas = BaginLeftFragment.roomTypespecsBean.getData();
                    if (isLongClick) {
                        BaginLeftFragment.roomTypespecsBean.getData().remove(deletePos);
                        editAdapter.updata(datas);
                        ToastUtils.toast("删除成功");
                        mView.initViewPagerData();
                    } else {
                        List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeans = datas.get(deletePos).getCategoryList();
                        for (int i = 0; i < categoryListBeans.size(); i++) {
                            categoryListBeans.get(i).setProduct(null);
                        }
                        datas.get(deletePos).setCheck(!datas.get(deletePos).isCheck());
                        editAdapter.notifyDataSetChanged();
                        mView.initViewPagerData();
                    }

                }
            });
        } else {
            if (BaginLeftFragment.roomTypespecsBean != null && editAdapter != null)
                editAdapter.updata(BaginLeftFragment.roomTypespecsBean.getData());
        }
        editBinding.llDelete.setVisibility(View.GONE);
        editDialog.show();
    }

    private int deletePos = 0;
    private boolean isLongClick = false;

    /**
     * 是否有商品
     *
     * @param pos
     * @return
     */
    private boolean isHasProduct(int pos) {
        RoomTypespecsBean.DataBean dataBean = BaginLeftFragment.roomTypespecsBean.getData().get(pos);
        boolean isHasProduct = false;
        for (int i = 0; i < dataBean.getCategoryList().size(); i++) {
            if (dataBean.getCategoryList().get(i).getProduct().size() > 0) {
                isHasProduct = true;
                break;
            }
        }
        return isHasProduct;
    }

    /**
     * *********************************************************************************************
     * 添加家具弹窗
     */
    private AddLayoutBinding addBinding = null;
    private BagcategorysBean bagcategorysBeanTemp;//保存初始值
    private CommonAdapter leftAdapter;
//    private CommonAdapter rightAdapter;
    private List<BagcategorysBean.DataBean> datas;//左边数据
    private List<BagcategorysBean.DataBean.ChildCaterotyBean> rightDatas;//右边数据
    private boolean isAddReset = true;//关闭弹窗是否重置
    private int position = 0;
    private List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeans;//默认选中的数据
    private CommonDialog addDialog;

    /**
     * 初始化数据
     */
    private void initData() {
        //获取默认数据
        datas = BaginLeftFragment.bagcategorysBean.getData();
        if (datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (i == 0) {
                    rightDatas = datas.get(i).getChildCateroty();
                    datas.get(i).setCheck(true);
                } else {
                    datas.get(i).setCheck(false);
                }
            }
        }
    }

    /**
     * 初始化选中数据
     */
    private void initCheckData() {
        categoryListBeans = BaginLeftFragment.roomTypespecsBean.getData().get(position).getCategoryList();
        for (int i = 0; i < datas.size(); i++) {
            BagcategorysBean.DataBean dataBean = datas.get(i);
            List<BagcategorysBean.DataBean.ChildCaterotyBean> childCateroty = dataBean.getChildCateroty();
            for (int j = 0; j < childCateroty.size(); j++) {
                childCateroty.get(j).setCheck(false);
                for (int k = 0; k < categoryListBeans.size(); k++) {
                    if (childCateroty.get(j).getId() == categoryListBeans.get(k).getCategoryId()) {
                        childCateroty.get(j).setCheck(true);
                        break;
                    }
                }

            }
        }
    }

    /**
     * 初始化选中数据
     */
    private void initResetCheckData() {
        //获取默认值
        String data = (String) SPUtils.get(SpConstant.ROOMTYPESPECSBEAN, "");
        RoomTypespecsBean roomTypespecsBean = new Gson().fromJson(data, RoomTypespecsBean.class);
        if (position >= roomTypespecsBean.getData().size()) {
            categoryListBeans = new ArrayList<>();
        } else {
            categoryListBeans = roomTypespecsBean.getData().get(position).getCategoryList();
        }
        BaginLeftFragment.roomTypespecsBean.getData().get(position).setCategoryList(categoryListBeans);
        for (int i = 0; i < datas.size(); i++) {
            BagcategorysBean.DataBean dataBean = datas.get(i);
            List<BagcategorysBean.DataBean.ChildCaterotyBean> childCateroty = dataBean.getChildCateroty();
            for (int j = 0; j < childCateroty.size(); j++) {
                childCateroty.get(j).setCheck(false);
                for (int k = 0; k < categoryListBeans.size(); k++) {
                    if (childCateroty.get(j).getId() == categoryListBeans.get(k).getCategoryId()) {
                        childCateroty.get(j).setCheck(true);
                        break;
                    }
                }

            }
        }
    }

    @Override
    public void showAdd(View view, int mPosition, final int pos) {
        //设置第一个Item为选中状态
        this.position = mPosition;
        initData();
        initCheckData();
        //深拷贝
        final String data = (String) SPUtils.get(SpConstant.BAGCATEGORYSBEAN, "");
        bagcategorysBeanTemp = new Gson().fromJson(data, BagcategorysBean.class);
        //初始化弹窗
        if (addDialog == null) {
            addDialog = new CommonDialog.Builder(mContext) {
                @Override
                public void diaLogDissmiss() {
                    super.diaLogDissmiss();
                    if (isAddReset) {
                        BaginLeftFragment.bagcategorysBean = bagcategorysBeanTemp;
                        initData();
                        leftAdapter.updata(datas);
//                        rightAdapter.updata(rightDatas);
                    }
                    isAddReset = true;
                    mView.addDialogClose();
                }
            }
                    .setWidth(0f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.add_layout)
                    .setGravity(Gravity.BOTTOM|Gravity.LEFT)
                    .build();
            addBinding = (AddLayoutBinding) addDialog.getBinding();
            //绑定左边数据
            leftAdapter = new CommonAdapter<AddItemLayoutBinding, BagcategorysBean.DataBean>(mContext, R.layout.add_item_layout, datas) {
                @Override
                protected void getItem(AddItemLayoutBinding binding, BagcategorysBean.DataBean bean, int position) {
                    if (bean.isCheck()) {
                        binding.tvName.setTextColor(Color.parseColor("#E70052"));
                        binding.tvName.setBackgroundColor(Color.WHITE);
                    } else {
                        binding.tvName.setTextColor(Color.parseColor("#787878"));

                    }
                    binding.tvName.setText(bean.getName());
                }

                @Override
                protected void itemOnclick(AddItemLayoutBinding binding, BagcategorysBean.DataBean bean, int position) {
                    //设置左边数据的选中状态
                    for (int i = 0; i < datas.size(); i++) {
                        if (position == i) {
                            datas.get(i).setCheck(true);
                        } else {
                            datas.get(i).setCheck(false);
                        }
                    }
                    //设置右边的数据
                    rightDatas = bean.getChildCateroty();
                    //刷新数据
                    setRightData(rightDatas);
                    leftAdapter.notifyDataSetChanged();
                }
            };
            addBinding.lv.setAdapter(leftAdapter);
            setRightData(rightDatas);
//            //绑定右边数据
//            rightAdapter = new CommonAdapter<EditItemLayoutBinding, BagcategorysBean.DataBean.ChildCaterotyBean>(mContext, R.layout.edit_item_layout, rightDatas) {
//                @Override
//                protected void getItem(EditItemLayoutBinding binding, BagcategorysBean.DataBean.ChildCaterotyBean bean, int position) {
//                    binding.tvName.setText(bean.getName());
//                    if (bean.isCheck()) {
//                        binding.tvName.setBackgroundResource(R.drawable.shape_type_on);
//                    } else {
//                        binding.tvName.setBackgroundResource(R.drawable.shape_type_off);
//                    }
//                }
//
//                @Override
//                protected void itemOnclick(EditItemLayoutBinding binding, BagcategorysBean.DataBean.ChildCaterotyBean bean, int mPos) {
//                    if (bean.isCheck()) {
//                        categoryListBeans = BaginLeftFragment.roomTypespecsBean.getData().get(position).getCategoryList();
//                        boolean isShowToast = false;
//                        for (int i = 0; i < categoryListBeans.size(); i++) {
//                            RoomTypespecsBean.DataBean.CategoryListBean dataBean = categoryListBeans.get(i);
//                            if (dataBean.getCategoryId() == bean.getId()) {
//                                if (dataBean.getProduct().size() > 0) {
//                                    isShowToast = true;
//                                }
//                                break;
//                            }
//                        }
//                        if (isShowToast) {
//                            addDeletePos = mPos;
//                            addBinding.llDelete.setVisibility(View.VISIBLE);
//                        } else {
//                            bean.setCheck(!bean.isCheck());
//                            rightAdapter.notifyDataSetChanged();
//                        }
//                    } else {
//                        bean.setCheck(!bean.isCheck());
//                        rightAdapter.notifyDataSetChanged();
//                    }
//                }
//            };
//            addBinding.gv.setAdapter(rightAdapter);
            //重置
            addBinding.btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaginLeftFragment.bagcategorysBean = bagcategorysBeanTemp;
                    initData();
                    initResetCheckData();
                    leftAdapter.updata(datas);
//                    rightAdapter.updata(rightDatas);
                    mView.refreshData(position);
                }
            });
            //确定
            addBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isAddReset = false;
                    handleData();
                    mView.refreshData(position);
                    addDialog.dismiss();
                }
            });
            addBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addBinding.llDelete.setVisibility(View.GONE);
                }
            });
            addBinding.btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addBinding.llDelete.setVisibility(View.GONE);
                    rightDatas.get(addDeletePos).setCheck(!rightDatas.get(addDeletePos).isCheck());
//                    rightAdapter.notifyDataSetChanged();
                }
            });
        } else {
            leftAdapter.updata(datas);
//            rightAdapter.updata(rightDatas);
        }
        addBinding.llDelete.setVisibility(View.GONE);
        addDialog.show();
    }

    private void setRightData(final List<BagcategorysBean.DataBean.ChildCaterotyBean> rightDatas) {
        addBinding.gv.removeAllViews();
        for (int i = 0 ; i < rightDatas.size() ; i++){
            final BagcategorysBean.DataBean.ChildCaterotyBean bean = rightDatas.get(i);
            final EditItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.edit_item_layout, addBinding.gv, false);
            addBinding.gv.addView(binding.getRoot());
            binding.tvName.setText(rightDatas.get(i).getName());
            final int finalI = i;
            if (bean.isCheck()){
                binding.tvName.setTextColor(Color.parseColor("#E70052"));
                binding.tvName.setBackgroundResource(R.drawable.shape_red5);
            }else {
                binding.tvName.setTextColor(Color.parseColor("#787878"));
                binding.tvName.setBackgroundResource(R.drawable.shape_white_black_add);
            }
            binding.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.isCheck()) {
                        categoryListBeans = BaginLeftFragment.roomTypespecsBean.getData().get(position).getCategoryList();
                        boolean isShowToast = false;
                        for (int i = 0; i < categoryListBeans.size(); i++) {
                            RoomTypespecsBean.DataBean.CategoryListBean dataBean = categoryListBeans.get(i);
                            if (dataBean.getCategoryId() == bean.getId()) {
                                if (dataBean.getProduct().size() > 0) {
                                    isShowToast = true;
                                }
                                break;
                            }
                        }
                        if (isShowToast) {
                            addDeletePos = finalI;
                            addBinding.llDelete.setVisibility(View.VISIBLE);
                        } else {
                            bean.setCheck(!bean.isCheck());
                            binding.tvName.setTextColor(Color.parseColor("#787878"));
                            binding.tvName.setBackgroundResource(R.drawable.shape_white_black_add);
                        }
                    } else {
                        bean.setCheck(!bean.isCheck());
                        binding.tvName.setTextColor(Color.parseColor("#E70058"));
                        binding.tvName.setBackgroundResource(R.drawable.shape_red5);
                    }

                }
            });
        }
    }
    private int addDeletePos = 0;
    /**
     * 处理数据
     */
    private void handleData() {
        categoryListBeans = BaginLeftFragment.roomTypespecsBean.getData().get(position).getCategoryList();
        for (int i = 0; i < datas.size(); i++) {
            BagcategorysBean.DataBean dataBean = datas.get(i);
            List<BagcategorysBean.DataBean.ChildCaterotyBean> childCateroty = dataBean.getChildCateroty();
            for (int j = 0; j < childCateroty.size(); j++) {
                if (childCateroty.get(j).isCheck()) {
                    //被选中
                    boolean isAdd = true;
                    for (int k = 0; k < categoryListBeans.size(); k++) {
                        //如果两个id相等，bread
                        if (childCateroty.get(j).getId() == categoryListBeans.get(k).getCategoryId()) {
                            isAdd = false;
                            break;
                        }
                    }
                    if (isAdd) {
                        categoryListBeans.add(new RoomTypespecsBean.DataBean.CategoryListBean(childCateroty.get(j).getName(), childCateroty.get(j).getId()));
                    }
                } else {
                    //未选中
                    for (int k = 0; k < categoryListBeans.size(); k++) {
                        //如果两个id相等，bread
                        if (childCateroty.get(j).getId() == categoryListBeans.get(k).getCategoryId()) {
                            categoryListBeans.remove(k);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}