package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.google.gson.Gson;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.HomeContract;
import com.ogmamllpadnew.databinding.FgHomeItemLayoutBinding;
import com.ogmamllpadnew.presenter.HomePresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.ui.activity.LoginActivity;
import com.ogmamllpadnew.widget.DragImageView;
import com.shan.netlibrary.bean.AppselectHomeDataBean;
import com.shan.netlibrary.bean.LoginBean;
import com.shan.netlibrary.bean.ProductselectProductCategory1Bean;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * Created by 陈俊山 on 2018-11-02.
 */

public class HomeFragment extends BaseFragment<FgHomeItemLayoutBinding, Object> implements HomeContract.View {
    private HomePresenter presenter;
    private int width;
    private String name;
    private String passwd;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_home_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        initLogo();
        titleBinding.btnLeft.setScaleType(ImageView.ScaleType.FIT_START);
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new HomePresenter(getActivity(), this);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        width = ScreenUtils.getScreenWidth();
        List<Object> list = new ArrayList<>();
        list.add("");
        setData(list);
        addHeadView();
        //addDragView();
        name = (String) SPUtils.get(SpConstant.USERNAME, "");
        passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
        login();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fg_home_layout, lvBinding.xrecyclerview, false);
        lvBinding.xrecyclerview.addHeaderView(view);
    }

    /**
     * 添加可拖动View
     */
    private void addDragView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = width / 8;
        params.height = width / 8;
        DragImageView dragImageView = new DragImageView(getActivity());
        dragImageView.setImageResource(R.mipmap.ic_3d);
        lvBinding.ll.addView(dragImageView, params);
        dragImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginYqx();
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
        } else if (mTag == HttpPresenter.APPLOGIN) {
            //登录成功
            LoginBean loginBean = (LoginBean) baseBean;
            MyApp.getInstance().setLoginBean(loginBean);
            getData();
        } else if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTCATEGORY1) {
            //查询商品一级分类（白名单接口）回调
            ProductselectProductCategory1Bean bean = (ProductselectProductCategory1Bean) baseBean;
            //保存商品一级分类
            String data = new Gson().toJson(bean);
            SPUtils.put(SpConstant.PRODUCTLEVER1, data);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {
        if (mTag == HttpPresenter.APPLOGIN) {
            startActivity(LoginActivity.class, null);
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.cancelAllRequest();
        }
        super.onDestroy();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    protected void getListVewItem(FgHomeItemLayoutBinding binding, Object item, int position) {
        super.getListVewItem(binding, item, position);
        binding.getRoot().setVisibility(View.GONE);
    }

    private void start3DDesign() {
        // 当前环境配置
        AAConfig c = MyApp.getInstance().getAAConfig(getActivity());
        // 开启3D设计工具
        AE7showDirector.start(c);
        getActivity().finish();
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        if (msgEvent.getType() == BaseMsgConstant.NONET) {
            return;
        }
        super.onMsgEvent(msgEvent);
    }

    /**
     * 获取首页数据
     */
    private void getHomeData(boolean isShow) {
        //获取首页数据
        HttpCallback callback = new HttpCallback<AppselectHomeDataBean>(getActivity(), this, isShow) {
            @Override
            protected void onSuccess(AppselectHomeDataBean baseBean) {
                closeLoad();
                EventBus.getDefault().post(new MessageEvent(MsgConstant.HOMEDATA, baseBean));
                LogUtils.d("onSuccess:");
            }

            @Override
            protected void onFailure(Throwable e) {
                closeLoad();
                LogUtils.d("onFailure:" + e.toString());
            }
        };
        startRequest(HttpBuilder.httpService.appselectHomeData(AppParams.getParams(null)), callback);
    }

    /**
     * 登录
     */
    private void login() {
        if (MyApp.getInstance().isLogin()) {
            getData();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("contactPhone", name);
        map.put("password", passwd);
        presenter.applogin(map);
    }

    /**
     * 获取数据
     */
    private void getData() {
        //获取版本号
        EventBus.getDefault().post(new MessageEvent(MsgConstant.GETVERSION));
        //获取首页数据
        getHomeData(false);
        //查询商品一级分类（白名单接口）
        getLever1ProductStyle();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        getHomeData(true);
    }

    /**
     * 查询商品一级分类（白名单接口）
     */
    private void getLever1ProductStyle() {
        String data = (String) SPUtils.get(SpConstant.PRODUCTLEVER1, "");
        LogUtils.d("data111111111111:" + data);
        if (TextUtils.isEmpty(data)) {
            presenter.productselectProductCategory1(null);
        }
    }

    private void initLogo() {
        String logoStr = getResources().getString(R.string.strKey);
        if (logoStr.equals("Meiliyun")){
            titleBinding.btnLeft.setImageResource(R.mipmap.ic_home_logo_meiliyun);
        }else if (logoStr.equals("Jzrz")){
            titleBinding.btnLeft.setImageResource(R.mipmap.ic_home_logo_jzrz);
        }else if (logoStr.equals("Sdrz")){
            titleBinding.btnLeft.setImageResource(R.mipmap.ic_home_logo_sdrz);
        }else if (logoStr.equals("Ogmamllpadnew")){
            titleBinding.btnLeft.setImageResource(R.mipmap.ic_home_logo);
        }else if (logoStr.equals("Tianqin")){
            titleBinding.btnLeft.setImageResource(R.mipmap.ic_home_logo_tianqin);
        }

    }
}
