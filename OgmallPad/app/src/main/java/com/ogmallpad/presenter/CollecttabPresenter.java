package com.ogmallpad.presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.CollecttabContract;
import com.ogmallpad.databinding.DialogStyleLayoutBinding;
import com.ogmallpad.databinding.EditItemLayoutBinding;
import com.shan.netlibrary.bean.CollectsstylesBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 收藏
 * Created by chenjunshan on 2018-09-27.
 */

public class CollecttabPresenter extends HttpPresenter implements CollecttabContract.Presenter {
    private Context mContext;
    private CollecttabContract.View mView;

    public CollecttabPresenter(Context mContext, CollecttabContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }


    private DialogStyleLayoutBinding styleLayoutBinding = null;
    private CommonDialog styleDialog;
    private CommonAdapter adapter;

    @Override
    public void showStyle(boolean isStyle, final List<CollectsstylesBean.DataBean> data) {
        if (styleDialog == null) {
            styleDialog = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.dialog_style_layout)
                    .setGravity(Gravity.TOP | Gravity.LEFT)
                    .setAnimResId(0)
                    .build();
            styleLayoutBinding = (DialogStyleLayoutBinding) styleDialog.getBinding();
            if (adapter == null) {
                adapter = new CommonAdapter<EditItemLayoutBinding, CollectsstylesBean.DataBean>(mContext, R.layout.edit_item_layout, data) {
                    @Override
                    protected void getItem(EditItemLayoutBinding binding, CollectsstylesBean.DataBean bean, int position) {
                        binding.tvName.setText(bean.getName());
                        if (bean.isCheck()) {
                            binding.tvName.setBackgroundResource(R.drawable.shape_type_on);
                        } else {
                            binding.tvName.setBackgroundResource(R.drawable.shape_type_off);
                        }
                    }

                    @Override
                    protected void itemOnclick(EditItemLayoutBinding binding, CollectsstylesBean.DataBean bean, int position) {
                        for (int i = 0; i < data.size(); i++) {
                            if (i == position) {
                                data.get(i).setCheck(true);
                            } else {
                                data.get(i).setCheck(false);
                            }
                        }
                        mView.setStyleName(bean.getName());
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_STYLEID, String.valueOf(bean.getId())));
                        styleDialog.dismiss();
                    }
                };
            }
            styleLayoutBinding.gv.setAdapter(adapter);
            styleLayoutBinding.etMin.addTextChangedListener(minpriceTextWatcher);
            styleLayoutBinding.etMax.addTextChangedListener(maxpriceTextWatcher);
            styleLayoutBinding.etMin.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //是否是回车键
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                        String searchWord = styleLayoutBinding.etMin.getText().toString().trim();
                        if (TextUtils.isEmpty(searchWord)) {
                            ToastUtils.toast("请输入最低价");
                        } else {
                            EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_MINPRICE, searchWord));
                            SoftKeyBoardUtils.closeKeybord(styleLayoutBinding.etMin, mContext);
                            styleDialog.dismiss();
                        }
                    }
                    return false;
                }
            });
            styleLayoutBinding.etMax.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //是否是回车键
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                        String searchWord = styleLayoutBinding.etMax.getText().toString().trim();
                        if (TextUtils.isEmpty(searchWord)) {
                            ToastUtils.toast("请输入最高价");
                        } else {
                            EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_MAXPRICE, searchWord));
                            SoftKeyBoardUtils.closeKeybord(styleLayoutBinding.etMax, mContext);
                            styleDialog.dismiss();
                        }
                    }
                    return false;
                }
            });
        }
        if (isStyle) {
            styleLayoutBinding.llStyle.setVisibility(View.VISIBLE);
            styleLayoutBinding.llPrice.setVisibility(View.GONE);
        } else {
            styleLayoutBinding.llStyle.setVisibility(View.GONE);
            styleLayoutBinding.llPrice.setVisibility(View.VISIBLE);
        }
        styleDialog.show();
    }

    private TextWatcher minpriceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String searchWord = charSequence.toString();
            if (TextUtils.isEmpty(searchWord)) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_MINPRICE, searchWord));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher maxpriceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String searchWord = charSequence.toString();
            if (TextUtils.isEmpty(searchWord)) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_MAXPRICE, searchWord));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}