package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.SPUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.HomeContract;
import com.ogmamllpadnew.databinding.Home4LayoutBinding;
import com.ogmamllpadnew.presenter.HomePresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.AppselectHomeDataBean;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-11-6.
 */

public class Home4Fragment extends BaseFragment<Home4LayoutBinding, Object> implements HomeContract.View {
    private HomePresenter presenter;
    private String name;
    private String passwd;

    @Override
    public int bindLayout() {
        return R.layout.home4_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new HomePresenter(getActivity(), this);
        name = (String) SPUtils.get(SpConstant.USERNAME, "");
        passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
    }

    private AppselectHomeDataBean homeDataBean;
    private List<AppselectHomeDataBean.DataBean.PanoramicBean> panoramic;

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.HOMEDATA) {
            homeDataBean = (AppselectHomeDataBean) msgEvent.getBean();
            if (homeDataBean != null) {
                panoramic = homeDataBean.getData().getPanoramic();
                if (panoramic.size() > 0) {
                    ImageCacheUtils.loadImage(getActivity(), panoramic.get(0).getHeadImage(), mBinding.ivPlan1);
                    mBinding.ivPlan1.setVisibility(View.VISIBLE);
                }
                if (panoramic.size() > 1) {
                    ImageCacheUtils.loadImage(getActivity(), panoramic.get(1).getHeadImage(), mBinding.ivPlan2);
                    mBinding.ivPlan2.setVisibility(View.VISIBLE);
                }
                if (panoramic.size() > 2) {
                    ImageCacheUtils.loadImage(getActivity(), panoramic.get(2).getHeadImage(), mBinding.ivPlan3);
                    mBinding.ivPlan3.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 3));
            }
        });
        mBinding.ivDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginYqx();
            }
        });

        mBinding.ivPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, panoramic.get(0).getId());
                startFragment(PlandetailsFragment.class, bundle);
            }
        });
        mBinding.ivPlan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, panoramic.get(1).getId());
                startFragment(PlandetailsFragment.class, bundle);
            }
        });
        mBinding.ivPlan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, panoramic.get(2).getId());
                startFragment(PlandetailsFragment.class, bundle);
            }
        });
    }

    /**
     * 登录一起秀
     */
    private void loginYqx() {
        if (TextUtils.isEmpty(MyApp.getInstance().getmYqxToken())) {
            Map<String, String> map = new HashMap<>();
            //map.put("username", "18500000001");
            //map.put("password", MD5Utils.encryption("123456"));
            map.put("username", name);
            map.put("password", MD5Utils.encryption(Constants.YQXPASSWORD));
            map.put("csys", "1");//1-android 2-ios
            presenter.yqxLogin(map);
        } else {
            //打开3D设计
            start3DDesign();
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (HttpPresenter.YQXLOGIN == mTag) {
            YqxLoginBean yqxLoginBean = (YqxLoginBean) baseBean;
            int status = yqxLoginBean.getData().getStatus();
            if (status != 0) {
                presenter.queryDialog();
            } else {
                MyApp.getInstance().setmYqxToken(yqxLoginBean.getData().getData().getToken());
                MyApp.getInstance().setmYqxUserid(yqxLoginBean.getData().getData().getUser_id());
                //打开3D设计
                start3DDesign();
            }
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private void start3DDesign() {
        // 当前环境配置
        AAConfig c = MyApp.getInstance().getAAConfig(getActivity());
        // 开启3D设计工具
        AE7showDirector.start(c);
        getActivity().finish();
    }
}
