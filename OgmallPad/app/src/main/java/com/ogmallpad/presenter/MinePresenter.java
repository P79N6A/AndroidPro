package com.ogmallpad.presenter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.contract.MineContract;
import com.ogmallpad.databinding.LogoutLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心
 * Created by chenjunshan on 2018-07-03.
 */

public class MinePresenter extends HttpPresenter implements MineContract.Presenter {
    private Context mContext;
    private MineContract.View mView;

    public MinePresenter(Context mContext, MineContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog dialog;
    private LogoutLayoutBinding binding;

    /**
     * 签到成功弹窗
     */
    public void showDialog() {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.5f)
                    .setResId(R.layout.logout_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setGravity(Gravity.CENTER)
                    .build();
            binding = (LogoutLayoutBinding) dialog.getBinding();
            binding.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", String.valueOf(MyApp.getInstance().getLoginBean().getData().getUserId()));
                    userlogout(map);

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

    public void dismiss(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}