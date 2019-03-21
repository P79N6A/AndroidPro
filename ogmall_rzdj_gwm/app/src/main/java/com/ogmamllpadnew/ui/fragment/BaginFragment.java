package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.BaginContract;
import com.ogmamllpadnew.databinding.FgBaginLayoutBinding;
import com.ogmamllpadnew.presenter.BaginPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.HandbagselectHandbagCategoryBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.bean.ShareShoppingCartBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拎包入住
 * Created by 陈俊山 on 2018-11-02.
 */

public class BaginFragment extends BaseFragment<FgBaginLayoutBinding, Object> implements BaginContract.View {
    private BaginPresenter presenter;
    private BaginlefttabFragment baginlefttabFragment;
    private BaginrightFragment baginrightFragment;

    @Override
    public int bindLayout() {
        return R.layout.fg_bagin_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_lbrz);
        titleBinding.btnLeft.setImageResource(R.mipmap.ic_bagin_on);
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginPresenter(getActivity(), this);
        baginlefttabFragment = new BaginlefttabFragment();
        baginrightFragment = new BaginrightFragment();
        fragmentReplace(baginlefttabFragment, R.id.fl_left);
        fragmentReplace(baginrightFragment, R.id.fl_right);
    }

    private HandbagselectHandbagCategoryBean categoryBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.SHARESHOPPINGCART) {
            ShareShoppingCartBean bean = (ShareShoppingCartBean) baseBean;
            presenter.setRqCode(bean.getData().getShare());
        } else if (mTag == HttpPresenter.HANDBAGSELECTHANDBAGCATEGORY) {
            //查询拎包入住分类
            categoryBean = (HandbagselectHandbagCategoryBean) baseBean;
            presenter.showClassifyDialog(categoryBean);
        } else if (mTag == HttpPresenter.BATCHCOLLECTPRODUCT) {
            ToastUtils.toast(getString(R.string.str_sccg));
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect();
            }
        });
        mBinding.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showRqDialog();
            }
        });
        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApp.getInstance().getLayoutBean() == null) {
                    ToastUtils.toast(getString(R.string.str_qxzhx));
                    return;
                }
                if (categoryBean == null) {
                    selectHandbagCategory();
                } else {
                    presenter.showClassifyDialog(categoryBean);
                }
            }
        });
    }

    private void collect() {
        JSONArray jsonArray = new JSONArray();
        HandbagselectLayoutBean layoutBean = MyApp.getInstance().getLayoutBean();
        List<HandbagselectLayoutBean.DataBean> datas = layoutBean.getData();
        for (int i = 0; i < datas.size(); i++) {
            List<HandbagselectLayoutBean.DataBean.ValueBean> valueBeans = datas.get(i).getValue();
            for (int j = 0; j < valueBeans.size(); j++) {
                List<HandbagselectLayoutBean.DataBean.ValueBean.ProductBean> productBeans = valueBeans.get(j).getProduct();
                try {
                    for (int k = 0; k < productBeans.size(); k++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", productBeans.get(k).getProductId());
                        jsonObject.put("dataType", productBeans.get(k).getDataType());
                        jsonArray.put(jsonObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        Map<String, String> map = new HashMap<>();
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("customerId", String.valueOf(0));
        }else {
            map.put("customerId", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        map.put("productIds", jsonArray.toString());
        presenter.batchCollectProduct(map);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.REFRESH_BAGIN_PRICE) {
            try {
                HandbagselectLayoutBean layoutBean = MyApp.getInstance().getLayoutBean();
                //选中的布局
                double selectAllMoney = 0;
                HandbagselectLayoutBean.DataBean selectLayout = layoutBean.getData().get(MyApp.getInstance().getTabPos());
                if (selectLayout.isCheck()) {
                    List<HandbagselectLayoutBean.DataBean.ValueBean> valueBeans = selectLayout.getValue();
                    for (int i = 0; i < valueBeans.size(); i++) {
                        List<HandbagselectLayoutBean.DataBean.ValueBean.ProductBean> productBeans = valueBeans.get(i).getProduct();
                        for (int j = 0; j < productBeans.size(); j++) {
                            selectAllMoney += Double.parseDouble(productBeans.get(j).getPrice()) * productBeans.get(j).getNum();
                        }
                    }
                    mBinding.tvSelectAllMoney.setText(selectLayout.getName() + getString(R.string.zj) + getString(R.string.str_kg2) + PublicUtils.getRenminbi() + String.format("%.2f", selectAllMoney));
                }

                //房间总价
                double allMoney = 0;
                List<HandbagselectLayoutBean.DataBean> dataBeans = layoutBean.getData();
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).isCheck()) {
                        HandbagselectLayoutBean.DataBean dataBean = dataBeans.get(i);
                        List<HandbagselectLayoutBean.DataBean.ValueBean> allValueBeans = dataBean.getValue();
                        for (int k = 0; k < allValueBeans.size(); k++) {
                            List<HandbagselectLayoutBean.DataBean.ValueBean.ProductBean> productBeans = allValueBeans.get(k).getProduct();
                            for (int j = 0; j < productBeans.size(); j++) {
                                allMoney += Double.parseDouble(productBeans.get(j).getPrice()) * productBeans.get(j).getNum();
                            }
                        }
                    }
                }
                mBinding.tvallMoney.setText(getString(R.string.qbfjg) + getString(R.string.str_kg2) + PublicUtils.getRenminbi() + String.format("%.2f", allMoney));
                if (allMoney == 0) {
                    mBinding.tvShare.setVisibility(View.INVISIBLE);
                    mBinding.tvCollect.setVisibility(View.INVISIBLE);
                } else {
                    mBinding.tvShare.setVisibility(View.VISIBLE);
                    mBinding.tvCollect.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * POST /app/handbag/selectHandbagCategory
     * 查询拎包入住分类
     */
    private void selectHandbagCategory() {
        presenter.handbagselectHandbagCategory(null);
    }
}
