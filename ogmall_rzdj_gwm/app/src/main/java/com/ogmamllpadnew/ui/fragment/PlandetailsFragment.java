package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.ui.activity.CommonActivity;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.HorizontalPagerAdapter;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.PlandetailsContract;
import com.ogmamllpadnew.databinding.FgPlandetailsLayoutBinding;
import com.ogmamllpadnew.presenter.PlandetailsPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.utils.Utils;
import com.shan.netlibrary.bean.PlanselectPlanByIdBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方案详情
 * Created by 陈俊山 on 2018-11-09.
 */

public class PlandetailsFragment extends BaseFragment<FgPlandetailsLayoutBinding, Object> implements PlandetailsContract.View {
    private PlandetailsPresenter presenter;
    private int id;
    private int type = 1;//1工厂方案，2平台方案
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                binData();
            }
        }
    };

    @Override
    public int bindLayout() {
        return R.layout.fg_plandetails_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_faxq);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PlandetailsPresenter(getActivity(), this);
        id = getActivity().getIntent().getIntExtra(Constants.ID, 0);
        type = getActivity().getIntent().getIntExtra(Constants.TYPE, 1);
        planselectPlanById();
    }

    private PlanselectPlanByIdBean planByIdBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PLANSELECTPLANBYID || mTag == HttpPresenter.PLANSELECTDESIGNBYID) {
            planByIdBean = (PlanselectPlanByIdBean) baseBean;
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
        if (handler != null) {
            handler.removeMessages(1);
            handler = null;
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tv3d.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_3d:
                if (planByIdBean == null)
                    return;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.URL, planByIdBean.getData().getVrurl());
                bundle.putBoolean(Constants.ISHIDETITLE, true);
                bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
                startFragment(BasewebviewFragment.class, bundle);
                break;
        }
    }

    /**
     * 获取方案数据
     */
    private void planselectPlanById() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        if (type == 1) {
            presenter.planselectPlanById(map);
        } else if (type == 2) {
            presenter.planselectDesignById(map);
        }
    }

    /**
     * 绑定数据
     */
    private void binData() {
        //绑定中间数据
        PlanselectPlanByIdBean.DataBean dataBean = planByIdBean.getData();
        final List<String> iamges = dataBean.getDetailsImageList();
        List<Utils.LibraryObject> data = new ArrayList<>();
        for (int i = 0; i < iamges.size(); i++) {
            data.add(new Utils.LibraryObject(iamges.get(i), ""));
        }
        HorizontalPagerAdapter mAdapter = new HorizontalPagerAdapter(getActivity(), data) {
            @Override
            public void showImage(List<Utils.LibraryObject> libraries, int position) {
                super.showImage(libraries, position);
                Bundle bundle = new Bundle();
                bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
                bundle.putBoolean(CommonActivity.ISSLIDINGCLOSE, false);
                bundle.putStringArrayList(Constants.LSIT, (ArrayList<String>) iamges);
                startFragment(PicturelookFragment.class, bundle);
            }
        };
        mBinding.hicvp.setAdapter(mAdapter);
        mBinding.tvHx.setText(getString(R.string.str_hx) + planByIdBean.getData().getHouseTypeIdName());
        mBinding.tvMj.setText(getString(R.string.str_mj) + planByIdBean.getData().getArea() + "㎡");
        mBinding.tvFazj.setText(getString(R.string.str_fazj) + planByIdBean.getData().getHouseTypeIdName());
        if (!TextUtils.isEmpty(planByIdBean.getData().getCreateTime())) {
            mBinding.tvSjsj.setVisibility(View.VISIBLE);
            mBinding.ivTime.setVisibility(View.VISIBLE);
            mBinding.viewTime.setVisibility(View.VISIBLE);
            mBinding.tvSjsj.setText(getString(R.string.str_sjsj) + planByIdBean.getData().getCreateTime());
        }
        mBinding.tvSjsxm.setText(getString(R.string.str_sjsxm) + planByIdBean.getData().getCreateAccountName());
        //设置3d预览状态
        if (TextUtils.isEmpty(dataBean.getVrurl())) {
            mBinding.tv3d.setVisibility(View.GONE);
        }
    }
}
