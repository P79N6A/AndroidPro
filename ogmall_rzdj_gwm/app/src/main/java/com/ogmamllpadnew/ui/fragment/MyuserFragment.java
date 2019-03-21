package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.bean.SelectBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.MyuserContract;
import com.ogmamllpadnew.databinding.FgMyuserItemLayoutBinding;
import com.ogmamllpadnew.databinding.FgMyuserheaderLayoutBinding;
import com.ogmamllpadnew.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.MyuserPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的客户
 * Created by 陈俊山 on 2018-11-12.
 */

public class MyuserFragment extends BaseFragment<FgMyuserItemLayoutBinding, UserselectCustomerBean.DataBean> implements MyuserContract.View {
    private MyuserPresenter presenter;
    private int width;
    private String keywords;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_myuser_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wdkh);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MyuserPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, width / 2, width, 0);
        addHeadView();
        selectCustomer(false);
    }

    private FgMyuserheaderLayoutBinding mHeaderBinding;
    private TitleRightSearchLayoutBinding searchLayoutBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_myuserheader_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());

        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, lvBinding.llHeader, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.etSearch.setHint(getString(R.string.str_sskh));
        searchLayoutBinding.tvAdd.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.USERSELECTCUSTOMER) {
            UserselectCustomerBean bean = (UserselectCustomerBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        } else if (mTag == HttpPresenter.USEREDITCUSTOMER) {
            onRefresh();
            ToastUtils.toast(getString(R.string.str_xgcg));
            presenter.dissMiss();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgMyuserItemLayoutBinding binding, final UserselectCustomerBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvName.setText(item.getContactName());
        binding.tvMobile.setText(item.getContactPhone());
        binding.tvAddress.setText(item.getProvinceName() + item.getCityName() + item.getDistrictName() + item.getAddress());
        binding.tvLink.setText(item.getCreateAccountName());
        binding.tvType.setText(item.getSignName());
        binding.tvRecordVoiceFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BEAN, item);
                startFragment(VoicefileFragment.class, bundle);
            }
        });
        binding.tvEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.editUser(item);
            }
        });
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApp.getInstance().isHasUser()) {
                    ToastUtils.toast(getString(R.string.str_hint));
                } else {
                    MyApp.getInstance().setCurrentUser(item);
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOWUSER));
                    ToastUtils.toast(getString(R.string.str_tjcg));
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.UPDATE_CURRENT_USER));
                    //开始录音
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTRECORD));
                    MyApp.getInstance().setCreateTime(System.currentTimeMillis());
                    getActivity().finish();
                }
            }
        });
        /**客户标识——普通客户*//*
            public static final int CUSTOMER_SIGN_ORDINARY = 0;
            *//**客户标识——潜在意向客户*//*
            public static final int CUSTOMER_SIGN_POTENTIAL = 1;
            *//**客户标识——意向客户*//*
            public static final int CUSTOMER_SIGN_INTENTION = 2;*/
        if (item.getSign().equals("0")) {
            binding.tvType.setBackgroundResource(R.color.transparent);
            binding.tvType.setTextColor(Color.parseColor("#333333"));
        } else if (item.getSign().equals("1")) {
            binding.tvType.setBackgroundResource(R.drawable.shape_user_sign2);
            binding.tvType.setTextColor(Color.parseColor("#ffffff"));
        } else if (item.getSign().equals("2")) {
            binding.tvType.setBackgroundResource(R.drawable.shape_user_sign1);
            binding.tvType.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectCustomer(false);
    }

    @Override
    public void onRefresh(boolean isHide) {
        super.onRefresh(isHide);
        selectCustomer(isHide);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectCustomer(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private void selectCustomer(boolean isHide) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", keywords);//关键词搜索
        presenter.userselectCustomer(map, isHide);
    }

    @Override
    protected void initData() {
        super.initData();
        ImageCacheUtils.loadImage(getActivity(), MyApp.getInstance().getInfoBean().getData().getPicture(), mHeaderBinding.ivHead);
        mHeaderBinding.tvName.setText(MyApp.getInstance().getInfoBean().getData().getContactName());
        mHeaderBinding.tvMobile.setText(MyApp.getInstance().getInfoBean().getData().getContactPhone());
        mHeaderBinding.tvType.setText(MyApp.getInstance().getInfoBean().getData().getTypeName());
    }

    @Override
    public void toAddress(int type, String id) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        bundle.putString(Constants.ID, id);
        startActivity(AddressActivity.class, bundle);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.ADDRESS) {
            AddressCallbackBean addressBean = (AddressCallbackBean) msgEvent.getBean();
            presenter.setAddressBean(addressBean);
        } else if (msgEvent.getType() == MsgConstant.SELELCT) {
            //客户类型选择回调
            SelectBean selectBean = (SelectBean) msgEvent.getBean();
            if (selectBean != null) {
                if (selectBean.getType() == 3) {
                    //户型
                    presenter.setUserType(selectBean);
                }
            }
        }
    }

    @Override
    public void toSelectType() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, 3);
        startActivity(SelectActivity.class, bundle);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //监听搜索
        searchLayoutBinding.etSearch.addTextChangedListener(textWatcher);
        searchLayoutBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    search();
                }
                return false;
            }
        });
        //键盘监听
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
            }
        });
    }

    //监听搜索
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (TextUtils.isEmpty(text)) {
                keywords = text;
                onRefresh(true);
            }
        }
    };

    private void search() {
        String text = searchLayoutBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入搜索内容");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(searchLayoutBinding.etSearch, getActivity());
        keywords = text;
        onRefresh();
    }

}