package com.ogmallpad.presenter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.contract.MyuserContract;
import com.ogmallpad.databinding.LogoutLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的客户
 * Created by chenjunshan on 2018-07-04.
 */

public class MyuserPresenter extends HttpPresenter implements MyuserContract.Presenter {
    private Context mContext;
    private MyuserContract.View mView;

    public MyuserPresenter(Context mContext, MyuserContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog dialog;
    private LogoutLayoutBinding binding;

    /**
     * 弹窗
     */
    public void showDialog(final int customerId) {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.5f)
                    .setResId(R.layout.logout_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setGravity(Gravity.CENTER)
                    .build();
            binding = (LogoutLayoutBinding) dialog.getBinding();
            binding.tvHint.setText(mContext.getResources().getString(R.string.str_hint4));
            binding.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", String.valueOf(MyApp.getInstance().getLoginBean().getData().getUserId()));
                    map.put("customerId", String.valueOf(customerId));
                    customerlogin(map);
                    MyApp.getInstance().setCustomerId(customerId);
                    MyApp.getInstance().setCreateTime(System.currentTimeMillis());
                }
            });
            binding.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}