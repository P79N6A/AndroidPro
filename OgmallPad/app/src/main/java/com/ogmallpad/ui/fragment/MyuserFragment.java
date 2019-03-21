package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.MyuserContract;
import com.ogmallpad.databinding.FgMyuserItemLayoutBinding;
import com.ogmallpad.databinding.MyUserHeaderLayoutBinding;
import com.ogmallpad.presenter.MyuserPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.CentercustomersBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的客户
 * Created by chenjunshan on 2018-07-04.
 */

public class MyuserFragment extends BaseFragment<FgMyuserItemLayoutBinding, CentercustomersBean.DataBean.ListBean> implements MyuserContract.View {
    private MyuserPresenter presenter;
    private MyUserHeaderLayoutBinding headBinding;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_myuser_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lvBinding.llEmpty.getLayoutParams();
        params.leftMargin = 160;
        params.rightMargin = 160;
        lvBinding.llEmpty.setLayoutParams(params);
        presenter = new MyuserPresenter(getActivity(), this);
        addHeadView();
        centercustomers();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        headBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.my_user__header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(headBinding.getRoot());
        intiStatusBar();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CENTERCUSTOMERS) {
            CentercustomersBean centercustomersBean = (CentercustomersBean) baseBean;
            isLoadingMore(centercustomersBean.getData().getPageResult().getTotalPage());
            List<CentercustomersBean.DataBean.ListBean> list = centercustomersBean.getData().getList();
            addData(list);
        } else if (mTag == HttpPresenter.CUSTOMERLOGIN) {
            ToastUtils.toast(getString(R.string.str_tjcg));
            MyApp.getInstance().currentTabHost = 0;
            MyApp.getInstance().setCurrentCustomerName(customerName);
            getActivity().finish();
            EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTRECORD));
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private String customerName;

    @Override
    protected void getListVewItem(FgMyuserItemLayoutBinding binding, final CentercustomersBean.DataBean.ListBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvId.setText(String.valueOf(item.getCustomerId()));
        binding.tvName.setText(item.getCustomerName());
        binding.tvMobile.setText(item.getCustomerMobile());
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(MyApp.getInstance().getCurrentCustomerName())) {
                    ToastUtils.toastlong(getString(R.string.str_hint3));
                } else {
                    customerName = item.getCustomerName();
                    if (TextUtils.isEmpty(customerName)) {
                        customerName = item.getCustomerMobile();
                    }
                    presenter.showDialog(item.getCustomerId());
                }
            }
        });
        binding.tvLywj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BEAN, item);
                startFragment(VoicefileFragment.class, bundle);
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        centercustomers();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            centercustomers();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    /**
     * 初始化状态栏
     */
    private void intiStatusBar() {
        if (headBinding != null && AppUtils.getSystemVersion() < 19) {
            headBinding.statusBar.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) headBinding.statusBar.getLayoutParams();
            linearParams.height = AppUtils.getStatusBarHeight();
            headBinding.statusBar.setLayoutParams(linearParams);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        headBinding.btnLeft.setOnClickListener(this);
        headBinding.etSearch.addTextChangedListener(textWatcher);
        headBinding.rlParent.setOnClickListener(this);
        headBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    search();
                }
                return false;
            }
        });
        headBinding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
            }
        });
    }

    private void search() {
        searchWord = headBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchWord)) {
            ToastUtils.toast("请输入搜索内容");
            return;
        }
        SoftKeyBoardUtils.closeKeybord(headBinding.etSearch, getActivity());
        onRefresh();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_left:
                getActivity().finish();
                break;
            case R.id.rl_parent:
                SoftKeyBoardUtils.closeKeybord(headBinding.etSearch, getActivity());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        headBinding.tvName.setText(loginBean.getData().getRealName());
        ImageCacheUtils.loadImage(getActivity(), loginBean.getData().getUserImage(), headBinding.ivHead, R.drawable.ic_defualt);
    }

    /**
     * 获取用户列表
     */
    private void centercustomers() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(loginBean.getData().getUserId()));
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (!TextUtils.isEmpty(searchWord)) {
            map.put("searchWord", searchWord);
        }
        presenter.centercustomers(map);
    }

    private String searchWord;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            searchWord = charSequence.toString();
            if (TextUtils.isEmpty(searchWord)) {
                onRefresh();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}