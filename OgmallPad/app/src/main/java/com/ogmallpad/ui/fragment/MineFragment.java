package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.MineContract;
import com.ogmallpad.databinding.FgMineLayoutBinding;
import com.ogmallpad.presenter.MinePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.ogmallpad.ui.activity.LoginActivity;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * 个人中心
 * Created by chenjunshan on 2018-07-03.
 */

public class MineFragment extends BaseFragment<FgMineLayoutBinding, Object> implements MineContract.View {
    private MinePresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.fg_mine_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);

    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MinePresenter(getActivity(), this);
    }


    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.USERLOGOUT) {
            //退出登录
            presenter.dismiss();
            MyApp.getInstance().setLoginBean(null);
            EventBus.getDefault().post(new MessageEvent(MsgConstant.LOGOUT, 0));
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
        mBinding.llAddUser.setOnClickListener(this);
        mBinding.btnLogout.setOnClickListener(this);
        mBinding.llMyUser.setOnClickListener(this);
        mBinding.llCollect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_add_user:
                if (!TextUtils.isEmpty(name))
                    return;
                startFragment(AdduserFragment.class, null);
                break;
            case R.id.btn_logout:
                if (!TextUtils.isEmpty(MyApp.getInstance().getCurrentCustomerName())) {
                    ToastUtils.toast(getString(R.string.str_hint6));
                    return;
                }
                presenter.showDialog();
                break;
            case R.id.ll_my_user:
                startFragment(MyuserFragment.class, null);
                break;
            case R.id.ll_collect:
                if (MyApp.getInstance().isLogin()) {
                    startFragment(CollectFragment.class, null);

                } else {
                    startActivity(LoginActivity.class, null);
                }
                break;
        }
    }

    private String name;

    @Override
    public void onResume() {
        super.onResume();
        bindData();
        ((HomeActivity)getActivity()).getBinding().icLogo.setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setImageResource(R.mipmap.ic_mine_on);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setText("我的");
        ((HomeActivity)getActivity()).getBinding().llSearch.setVisibility(View.GONE);

        if (MyApp.getInstance().getLoginBean() == null){
            startActivity(LoginActivity.class,null);
            getActivity().finish();
        }
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        mBinding.tvName.setText(loginBean.getData().getRealName());
        ImageCacheUtils.loadImage(getActivity(), loginBean.getData().getUserImage(), mBinding.ivHead);
        name = MyApp.getInstance().getCurrentCustomerName();
        if (TextUtils.isEmpty(name)) {
            mBinding.ivTjkh.setImageResource(R.mipmap.ic_add_user);
            mBinding.tvTjkh.setText(getString(R.string.str_tjkh));
        } else {
            mBinding.ivTjkh.setImageResource(R.mipmap.ic_khtj);
            mBinding.tvTjkh.setText(getString(R.string.str_dqkh) + name);
        }
        //用户类型
        /**Normal(0,2.2D,"普通用户"),
         Manager(2,1.0D,"管理员"),
         //vip设计师1.15普通设计师为2.2
         Designer(4,1.15D,"设计师"),
         City(5,1D,"运营商"),
         Factory(6,2.2D,"工厂"),
         DecorateCompany(7,1.1D,"装修公司"),
         Seed(8,2.2D,"种子用户"),
         Test(9,2.2D,"测试用户"),
         Employee(10,2.2D,"欧工员工"),
         OgSchool(11,2.2D,"欧工软装学院");
         */
        mBinding.tvUserType.setText(loginBean.getData().getTypeName());
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.STOPRECORD) {
            onResume();
        }else if (msgEvent.getType() == MsgConstant.LOGOU_USER){
            MyApp.getInstance().setCurrentCustomerName(null);
            bindData();
        }
    }
}
