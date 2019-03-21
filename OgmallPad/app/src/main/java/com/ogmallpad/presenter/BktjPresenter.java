package com.ogmallpad.presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.bean.JiaGeBean;
import com.ogmallpad.bean.ShopScreenBean;
import com.ogmallpad.contract.BktjContract;
import com.ogmallpad.databinding.DialogShopScreenLayoutBinding;
import com.ogmallpad.databinding.FgBrandLayoutBinding;
import com.ogmallpad.databinding.ItemFgLayoutBinding;
import com.ogmallpad.databinding.ItemShopScreenJgLayoutBinding;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;

/**
 * 爆款推荐
 * Created by chenjunshan on 2018-07-06.
 */

public class BktjPresenter extends HttpPresenter implements BktjContract.Presenter {
    private Context mContext;
    private BktjContract.View mView;
    public BktjPresenter(Context mContext, BktjContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}