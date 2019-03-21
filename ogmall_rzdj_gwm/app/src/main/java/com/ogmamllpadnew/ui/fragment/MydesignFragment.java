package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
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
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.MydesignContract;
import com.ogmamllpadnew.databinding.DialogQueryLayoutBinding;
import com.ogmamllpadnew.databinding.FgMyDesignHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgMydesignItemLayoutBinding;
import com.ogmamllpadnew.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.MydesignPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.UserselectDesignerBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的设计师
 * Created by 陈俊山 on 2018-11-16.
 */

public class MydesignFragment extends BaseFragment<FgMydesignItemLayoutBinding, UserselectDesignerBean.DataBean> implements MydesignContract.View {
    private MydesignPresenter presenter;
    private int width;
    private String keywords;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_mydesign_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wdsjs);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MydesignPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, width / 2, width, 0);
        addHeadView();
        selectDesigner(false);
    }

    private TitleRightSearchLayoutBinding searchLayoutBinding;
    private FgMyDesignHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_my_design_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());

        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, lvBinding.llHeader, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.etSearch.setHint(getString(R.string.str_sssjs));
        searchLayoutBinding.tvAdd.setText(getString(R.string.str_tjsjs));
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.USERSELECTDESIGNER) {
            UserselectDesignerBean bean = (UserselectDesignerBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        } else if (mTag == HttpPresenter.APPRESETPASSWORD) {
            dialogReset.dismiss();
            ToastUtils.toast(getString(R.string.str_czmmcg));
        } else if (mTag == HttpPresenter.USERADDACCOUNTOFDESIGNER) {
            //添加设计师成功
            ToastUtils.toast(getString(R.string.str_tjcg));
            presenter.addOnsuccess();
            onRefresh();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgMydesignItemLayoutBinding binding, final UserselectDesignerBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvName.setText(item.getContactName());
        binding.tvMobile.setText(item.getContactPhone());
        binding.tvAddress.setText(item.getProvinceName() + item.getCityName() + item.getDistrictName() + item.getAddress());
        binding.tvNum.setText(item.getCustomerCount());
        binding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswd(item.getId());
            }
        });
        binding.tvLookUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, item.getId());
                bundle.putString(Constants.NAME, item.getContactName());
                startFragment(LookuserFragment.class, bundle);
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectDesigner(false);
    }

    @Override
    public void onRefresh(boolean isShow) {
        super.onRefresh(isShow);
        selectDesigner(isShow);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectDesigner(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initData() {
        super.initData();
        ImageCacheUtils.loadImage(getActivity(), MyApp.getInstance().getInfoBean().getData().getPicture(), mHeaderBinding.ivHead);
        mHeaderBinding.tvName.setText(MyApp.getInstance().getInfoBean().getData().getContactName());
        mHeaderBinding.tvMobile.setText(MyApp.getInstance().getInfoBean().getData().getContactPhone());
        mHeaderBinding.tvType.setText(MyApp.getInstance().getInfoBean().getData().getTypeName());
    }

    private void selectDesigner(boolean isShow) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", keywords);//关键词搜索
        presenter.userselectDesigner(map, isShow);
    }

    /**
     * 重置密码
     */
    private CommonDialog dialogReset;
    private DialogQueryLayoutBinding queryLayoutBinding;

    private void resetPasswd(final String id) {
        if (dialogReset == null) {
            dialogReset = new CommonDialog.Builder(getActivity())
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            queryLayoutBinding = (DialogQueryLayoutBinding) dialogReset.getBinding();
            queryLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogReset.dismiss();
                }
            });
            queryLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", id);
                    presenter.appresetPassword(map);
                }
            });
        }
        dialogReset.show();
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
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        searchLayoutBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showAddDesigner();
            }
        });

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