package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.view.View;

import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.contract.HomeContract;
import com.ogmamllpadnew.databinding.DialogQueryLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 首页
 * Created by 陈俊山 on 2018-11-02.
 */

public class HomePresenter extends HttpPresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.View mView;

    public HomePresenter(Context mContext, HomeContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog mDialog;
    private DialogQueryLayoutBinding queryLayoutBinding;

    @Override
    public void queryDialog() {
        if (mDialog == null) {
            mDialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            queryLayoutBinding = (DialogQueryLayoutBinding) mDialog.getBinding();
            queryLayoutBinding.tvHint.setText("提示");
            queryLayoutBinding.tvMsg.setText("该账户未开通权限，请联系客服！");
            queryLayoutBinding.btnCancel.setVisibility(View.GONE);
            queryLayoutBinding.view.setVisibility(View.GONE);
            queryLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();

                }
            });
        }
        mDialog.show();
    }
}