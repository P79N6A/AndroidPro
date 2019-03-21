package com.ogmallpad.presenter;

import android.content.Context;
import android.view.View;

import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.databinding.DialogQueryLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 首页
 * Created by chenjunshan on 2018-07-02.
 */

public class HomePresenter extends HttpPresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.View mView;
    private CommonDialog hintDialog;
    private DialogQueryLayoutBinding hintBinding;

    public HomePresenter(Context mContext, HomeContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void hintDialog() {
        if (hintDialog == null) {
            hintDialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            hintBinding = (DialogQueryLayoutBinding) hintDialog.getBinding();
            hintBinding.tvHint.setText("提示");
            hintBinding.tvMsg.setText("该账户未开通权限，请联系客服！");
            hintBinding.btnCancel.setVisibility(View.GONE);
            hintBinding.view.setVisibility(View.GONE);
            hintBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hintDialog.dismiss();

                }
            });
        }
        hintDialog.show();
    }

}