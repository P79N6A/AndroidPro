package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.BaginlefttabContract;
import com.ogmamllpadnew.databinding.CheckLayoutBinding;
import com.ogmamllpadnew.databinding.EditLayoutBinding;
import com.ogmamllpadnew.databinding.HxLayoutBinding;
import com.ogmamllpadnew.ui.fragment.BaginlefttabFragment;
import com.shan.netlibrary.bean.HandbagselectAllHouseTypeBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拎包入住左边
 * Created by 陈俊山 on 2018-11-10.
 */

public class BaginlefttabPresenter extends HttpPresenter implements BaginlefttabContract.Presenter {
    private Context mContext;
    private BaginlefttabContract.View mView;

    public BaginlefttabPresenter(Context mContext, BaginlefttabContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * ###########################户型START########################################
     */
    private CommonDialog dialog;
    private HxLayoutBinding layoutBinding;
    private CommonAdapter adapterHx;

    /**
     * 显示户型弹框
     */
    @Override
    public void showHxDialog(final HandbagselectAllHouseTypeBean typeBean) {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.hx_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            layoutBinding = (HxLayoutBinding) dialog.getBinding();
            layoutBinding.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            layoutBinding.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        adapterHx = new CommonAdapter<CheckLayoutBinding, HandbagselectAllHouseTypeBean.DataBean>(mContext, R.layout.check_layout, typeBean.getData()) {
            @Override
            protected void getItem(CheckLayoutBinding binding, HandbagselectAllHouseTypeBean.DataBean bean, int position) {
                binding.cb.setText(bean.getTypeName());
                binding.cb.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(CheckLayoutBinding binding, final HandbagselectAllHouseTypeBean.DataBean bean, int position) {
                for (int i = 0; i < typeBean.getData().size(); i++) {
                    if (i == position) {
                        typeBean.getData().get(i).setCheck(true);
                    } else {
                        typeBean.getData().get(i).setCheck(false);
                    }
                }
                adapterHx.notifyDataSetChanged();
                mView.setHouseName(bean.getTypeName());
                BaginlefttabFragment.houseTypeId = String.valueOf(bean.getId());
                Map<String, String> map = new HashMap<>();
                map.put("roomCount", bean.getRoomCount());
                map.put("hallCount", bean.getHallCount());
                handbagselectLayout(map);
            }
        };
        layoutBinding.gv.setAdapter(adapterHx);

        dialog.show();
    }
    /**###########################户型END########################################*/


    /**
     * ###########################编辑START########################################
     */
    private CommonDialog dialogEdit;
    private EditLayoutBinding editBinding;
    private CommonAdapter adapterEdit;

    /**
     * 显示状态弹框
     */
    @Override
    public void showEditDialog() {
        if (dialogEdit == null) {
            dialogEdit = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.edit_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(0)
                    .build();
            editBinding = (EditLayoutBinding) dialogEdit.getBinding();
            editBinding.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEdit.dismiss();
                }
            });
            editBinding.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            editBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEdit.dismiss();
                }
            });
            editBinding.btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetData();
                }
            });
            editBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEdit.dismiss();
                    SoftKeyBoardUtils.closeKeybord(editBinding.etText,mContext);
                }
            });
        }

        HandbagselectLayoutBean layoutBean = MyApp.getInstance().getLayoutBean();
        final List<HandbagselectLayoutBean.DataBean> datas = layoutBean.getData();
        adapterEdit = new CommonAdapter<CheckLayoutBinding, HandbagselectLayoutBean.DataBean>(mContext, R.layout.check_layout, datas) {
            @Override
            protected void getItem(CheckLayoutBinding binding, HandbagselectLayoutBean.DataBean bean, int position) {
                binding.cb.setText(bean.getName());
                binding.cb.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(CheckLayoutBinding binding, final HandbagselectLayoutBean.DataBean bean, int position) {
                bean.setCheck(!bean.isCheck());
                adapterEdit.notifyDataSetChanged();
                sendEventBus();
            }
        };
        editBinding.gv.setAdapter(adapterEdit);
        editBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLayout();
            }
        });

        dialogEdit.show();
    }

    /**
     * 重置数据
     */
    private void resetData() {
        String data = (String) SPUtils.get(SpConstant.BAGIN_LAYOUT, "");
        HandbagselectLayoutBean layoutBean = new Gson().fromJson(data, HandbagselectLayoutBean.class);
        MyApp.getInstance().setLayoutBean(layoutBean);
        adapterEdit.updata(layoutBean.getData());
        sendEventBus();
        ToastUtils.toast(mContext.getResources().getString(R.string.str_czcg));
    }

    /**
     * 添加布局
     */
    private void addLayout() {
        String text = editBinding.etText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入房间布局名称");
            return;
        }
        HandbagselectLayoutBean layoutBean = MyApp.getInstance().getLayoutBean();
        List<HandbagselectLayoutBean.DataBean> datas = layoutBean.getData();
        for (int i = 0; i < datas.size(); i++) {
            if (text.equals(datas.get(i).getName())) {
                ToastUtils.toast("该布局已经存在");
                return;
            }
        }
        HandbagselectLayoutBean.DataBean dataBean = new HandbagselectLayoutBean.DataBean();
        dataBean.setName(text);
        dataBean.setDefult(false);
        dataBean.setCheck(true);
        dataBean.setValue(datas.get(0).getValue());
        datas.add(dataBean);
        adapterEdit.notifyDataSetChanged();
        sendEventBus();
    }
    /**###########################编辑END########################################*/

    /**
     * 关闭dialog
     */
    @Override
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (dialogEdit != null) {
            dialogEdit.dismiss();
        }
    }

    private void sendEventBus() {
        mView.initTitleData();
        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_RIGHT_EMPTY));
    }
}